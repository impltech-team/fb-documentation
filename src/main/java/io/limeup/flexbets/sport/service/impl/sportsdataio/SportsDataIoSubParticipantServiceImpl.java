package io.limeup.flexbets.sport.service.impl.sportsdataio;

import io.limeup.flexbets.sport.cache.EventBasedCache;
import io.limeup.flexbets.sport.dto.*;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.model.IoPlayerGameStats;
import io.limeup.flexbets.sport.model.IoPlayersStats;
import io.limeup.flexbets.sport.model.dto.IoPlayerMapper;
import io.limeup.flexbets.sport.model.enums.IoBetMarketStatus;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataBetRow;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataPlayerRow;
import io.limeup.flexbets.sport.repository.sportsdataio.*;
import io.limeup.flexbets.sport.service.SubParticipantService;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import io.limeup.flexbets.sport.utils.ValidationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Map.entry;

@Transactional
@Service("sportsDataIoProvider")
public class SportsDataIoSubParticipantServiceImpl implements SubParticipantService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("player_name", "team_name", "position", "event_time");

    private final IoPlayerRepository playerRepository;
    private final IoBetRepository betRepository;
    private final IoPlayerMapper playerMapper;
    private final IoPlayerStatsRepository playerStatsRepo;
    private final IoPlayerGamesStatsRepository gameStatsRepo;
    private final IoTeamRepository ioTeamRepository;

    public SportsDataIoSubParticipantServiceImpl(IoPlayerRepository playerRepository, IoBetRepository betRepository, IoPlayerMapper playerMapper, IoPlayerStatsRepository playerStatsRepo, IoPlayerGamesStatsRepository gameStatsRepo, IoTeamRepository ioTeamRepository) {
        this.playerRepository = playerRepository;
        this.betRepository = betRepository;
        this.playerMapper = playerMapper;
        this.playerStatsRepo = playerStatsRepo;
        this.gameStatsRepo = gameStatsRepo;
        this.ioTeamRepository = ioTeamRepository;
    }

    @EventBasedCache(cacheName = "subParticipantsListCache",
            key = "T(java.util.Objects).hash(#competitionId, #positions, #participantIds, #marketId, #maxHistoricalDataCount, #requestQuery.page, #requestQuery.pageSize, #requestQuery.sortOrder, #requestQuery.sortBy, #requestQuery.filter)")
    @Override
    public PaginatedResponse<SubParticipantDTO> listSubParticipants(Integer competitionId, List<String> positions,
                                                                    List<Integer> participantIds, Integer marketId,
                                                                    Integer maxHistoricalDataCount, RequestQueryDTO requestQuery) {
        ValidationUtils.validateSortFieldsInRequest(requestQuery, SUPPORTED_SORT_FIELDS);

//        Set<String> statNames = marketService.getStatsByMarket(competitionId, marketId, MarketType.SUB_PARTICIPANT);

        Long count = playerRepository.countPlayersWithUpcomingEvent();

        if (count == 0) {
            return PaginationUtils.buildPaginatedResponse(null, count, requestQuery.getPage(), requestQuery.getPageSize());
        }

        List<SportsDataPlayerRow> players = playerRepository.listPlayersWithUpcomingEvent(
                (requestQuery.getPage() - 1) * requestQuery.getPageSize(),
                requestQuery.getPageSize()
        );

        Set<Integer> eventIds = players.stream()
                .map(SportsDataPlayerRow::getEventId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<Long, List<SportsDataBetRow>> playerIdBetMap = betRepository.findAllByMarketTypeAndEventIdInAndAnyBetsAvailableTrue(IoBetMarketStatus.PLAYER_PROP.getName(), eventIds)
                .stream()
                .collect(Collectors.groupingBy(SportsDataBetRow::getPlayerId));

        List<SubParticipantDTO> subParticipantDTOList = playerMapper.toSubParticipantDTOList(players, playerIdBetMap);

        for (SubParticipantDTO player : subParticipantDTOList) {
            player.setHistoricalStats(buildHistoricalStats((long) player.getId()));
        }

        return PaginationUtils.buildPaginatedResponse(subParticipantDTOList, count, requestQuery.getPage(), requestQuery.getPageSize());
    }

    @EventBasedCache(cacheName = "subParticipantDetailsCache",
            key = "T(java.util.Objects).hash(#subParticipantId, #marketId, #maxHistoricalDataCount)")
    @Override
    public SubParticipantDTO getSubParticipantById(Integer subParticipantId, Integer marketId, Integer maxHistoricalDataCount) {
        SportsDataPlayerRow player = playerRepository.getPlayerWithBetsById(subParticipantId)
                .orElseThrow(() -> new FlexBetsSportNotFoundException(
                        "SubParticipant %s Not Found".formatted(subParticipantId)));

        List<SportsDataBetRow> playerBets = betRepository
                .findAllByMarketTypeAndEventIdInAndPlayerIdAndAnyBetsAvailableTrue(
                        IoBetMarketStatus.PLAYER_PROP.getName(),
                        Set.of(player.getEventId()),
                        player.getId().longValue());

        List<HistoricalStatDTO> hist = buildHistoricalStats(Long.valueOf(player.getId()));

        if (maxHistoricalDataCount != null && maxHistoricalDataCount > 0) {
            hist.forEach(h -> {
                List<EventStatisticDTO> limited = h.getEventStatistics()
                        .stream()
                        .limit(maxHistoricalDataCount)
                        .toList();
                h.setEventStatistics(limited);
            });
        }

        SubParticipantDTO dto = playerMapper.toSubParticipantDTO(player, playerBets);
        dto.setHistoricalStats(hist);

        return dto;
    }


    public List<HistoricalStatDTO> buildHistoricalStats(Long playerId) {

        IoPlayersStats season = playerStatsRepo
                .findTopByPlayerIdOrderByUpdatedDesc(playerId)
                .orElse(null);

        List<IoPlayerGameStats> games =
                gameStatsRepo.findAllByPlayerIdOrderByGameDatetimeDesc(playerId);

        Map<String, Function<IoPlayerGameStats, Number>> gameExtract = Map.ofEntries(
                entry("At bats", IoPlayerGameStats::getAtBats),
                entry("Runs", IoPlayerGameStats::getRuns),
                entry("Hits", IoPlayerGameStats::getHits),
                entry("Singles", IoPlayerGameStats::getSingles),
                entry("Doubles", IoPlayerGameStats::getDoubles),
                entry("Triples", IoPlayerGameStats::getTriples),
                entry("Home runs", IoPlayerGameStats::getHomeRuns),
                entry("Runs batted in", IoPlayerGameStats::getRunsBattedIn),
                entry("Strikeouts", IoPlayerGameStats::getStrikeouts),
                entry("Walks", IoPlayerGameStats::getWalks),
                entry("Hit by pitch", IoPlayerGameStats::getHitByPitch),
                entry("Outs", IoPlayerGameStats::getOuts),
                entry("Sacrifices", IoPlayerGameStats::getSacrifices),
                entry("Sacrifice flies", IoPlayerGameStats::getSacrificeFlies),
                entry("Ground into DP", IoPlayerGameStats::getGroundIntoDoublePlay),
                entry("Stolen bases", IoPlayerGameStats::getStolenBases),
                entry("Caught stealing", IoPlayerGameStats::getCaughtStealing)
        );


        Map<String, Function<IoPlayersStats, Number>> seasonExtract = Map.ofEntries(
                entry("At bats", IoPlayersStats::getAtBats),
                entry("Runs", IoPlayersStats::getRuns),
                entry("Hits", IoPlayersStats::getHits),
                entry("Singles", IoPlayersStats::getSingles),
                entry("Doubles", IoPlayersStats::getDoublesCount),
                entry("Triples", IoPlayersStats::getTriples),
                entry("Home runs", IoPlayersStats::getHomeRuns),
                entry("Runs batted in", IoPlayersStats::getRunsBattedIn),
                entry("Strikeouts", IoPlayersStats::getStrikeouts),
                entry("Walks", IoPlayersStats::getWalks),
                entry("Hit by pitch", IoPlayersStats::getHitByPitch),
                entry("Outs", IoPlayersStats::getOuts),
                entry("Sacrifices", IoPlayersStats::getSacrifices),
                entry("Sacrifice flies", IoPlayersStats::getSacrificeFlies),
                entry("Ground into DP", IoPlayersStats::getGroundIntoDoublePlay),
                entry("Stolen bases", IoPlayersStats::getStolenBases),
                entry("Caught stealing", IoPlayersStats::getCaughtStealing)
        );

        return gameExtract.entrySet().stream()
                .map(e -> buildStat(e.getKey(),
                        e.getValue(),
                        seasonExtract.get(e.getKey()),
                        season, games))
                .filter(h -> h.getCount() > 0)
                .toList();
    }

    private HistoricalStatDTO buildStat(
            String name,
            Function<IoPlayerGameStats, Number> gameGetter,
            Function<IoPlayersStats, Number> seasonGetter,
            IoPlayersStats season,
            List<IoPlayerGameStats> games) {

        List<EventStatisticDTO> ev = IntStream.range(0, games.size())
                .mapToObj(i -> {
                    IoPlayerGameStats g = games.get(i);
                    Number n = gameGetter.apply(g);

                    if (n == null) return null;

                    return EventStatisticDTO.builder()
                            .eventId(Math.toIntExact(g.getGameId()))
                            .eventName(ioTeamRepository.findByTeamId(g.getTeamId()).get().getName()
                            + " - " + ioTeamRepository.findByTeamId(g.getOpponentId()).get().getName() )
                            .eventDate(g.getGameDatetime())
                            .value(n.doubleValue())
                            .rawValue(n.toString())
                            .opponent(ioTeamRepository.findByTeamId(g.getOpponentId()).get().getName())
                            .build();
                })
                .filter(Objects::nonNull)
                .toList();


        if (ev.isEmpty()) {
            return HistoricalStatDTO.builder()
                    .statName(name)
                    .count(0)
                    .eventStatistics(List.of())
                    .build();
        }

        DoubleSummaryStatistics s = ev.stream()
                .mapToDouble(EventStatisticDTO::getValue)
                .summaryStatistics();

        double average;
        if (season != null
                && seasonGetter.apply(season) != null
                && season.getGames() != null
                && season.getGames() > 0) {

            average = seasonGetter.apply(season).doubleValue() / season.getGames();

            if (!Double.isFinite(average)) {
                average = round(s.getAverage(), 2);
            }
        } else {
            average = round(s.getAverage(), 2);
        }

        return HistoricalStatDTO.builder()
                .statName(name)
                .average(BigDecimal.valueOf(average))
                .count((int) s.getCount())
                .maxValue((int) s.getMax())
                .minValue((int) s.getMin())
                .eventStatistics(ev)
                .build();
    }

    private static double round(double v, int scale) {
        return BigDecimal.valueOf(v).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }
}
