package io.limeup.flexbets.sport.service.impl.sportsdataio;

import io.limeup.flexbets.sport.cache.EventBasedCache;
import io.limeup.flexbets.sport.dto.*;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.model.IoPlayerGameStats;
import io.limeup.flexbets.sport.model.IoTeam;
import io.limeup.flexbets.sport.model.dto.IoPlayerMapper;
import io.limeup.flexbets.sport.model.enums.IoBetMarketStatus;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataBetRow;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataPlayerRow;
import io.limeup.flexbets.sport.repository.sportsdataio.IoBetRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoPlayerGamesStatsRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoPlayerRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoTeamRepository;
import io.limeup.flexbets.sport.service.SubParticipantService;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;

@Transactional
@Service("sportsDataIoProvider")
@Slf4j
public class SportsDataIoSubParticipantServiceImpl implements SubParticipantService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of(
            "player_name", "team_name", "position", "event_time");

    private final IoPlayerRepository playerRepository;
    private final IoBetRepository betRepository;
    private final IoPlayerMapper playerMapper;
    private final IoPlayerGamesStatsRepository gameStatsRepo;
    private final IoTeamRepository ioTeamRepository;


    private static final Map<String, Function<IoPlayerGameStats, Number>> GAME_EXTRACT =
            Map.ofEntries(
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

    public SportsDataIoSubParticipantServiceImpl(
            IoPlayerRepository playerRepository,
            IoBetRepository betRepository,
            IoPlayerMapper playerMapper,
            IoPlayerGamesStatsRepository gameStatsRepo,
            IoTeamRepository ioTeamRepository) {
        this.playerRepository = playerRepository;
        this.betRepository = betRepository;
        this.playerMapper = playerMapper;
        this.gameStatsRepo = gameStatsRepo;
        this.ioTeamRepository = ioTeamRepository;
    }

    @EventBasedCache(cacheName = "subParticipantsListCache",
            key = "T(java.util.Objects).hash(#competitionId, #positions, #participantIds, #marketId, #maxHistoricalDataCount, #requestQuery.page, #requestQuery.pageSize, #requestQuery.sortOrder, #requestQuery.sortBy, #requestQuery.filter)")

    @Override
    public PaginatedResponse<SubParticipantDTO> listSubParticipants(
            Integer competitionId, List<String> positions,
            List<Integer> participantIds, Integer marketId,
            Integer maxHistoricalDataCount, RequestQueryDTO requestQuery) {

        long count = playerRepository.countPlayersWithUpcomingEvent();
        if (count == 0) {
            return PaginationUtils.buildPaginatedResponse(
                    null, 0L, requestQuery.getPage(), requestQuery.getPageSize());
        }

        List<SportsDataPlayerRow> players =
                playerRepository.listPlayersWithUpcomingEvent(
                        (requestQuery.getPage() - 1) * requestQuery.getPageSize(),
                        requestQuery.getPageSize());

        Map<Long, List<SportsDataBetRow>> playerIdBetMap =
                betRepository.findAllByMarketTypeAndEventIdInAndAnyBetsAvailableTrue(
                                IoBetMarketStatus.PLAYER_PROP.getName(),
                                players.stream()
                                        .map(SportsDataPlayerRow::getEventId)
                                        .filter(Objects::nonNull)
                                        .collect(Collectors.toSet()))
                        .stream()
                        .collect(Collectors.groupingBy(SportsDataBetRow::getPlayerId));

        List<SubParticipantDTO> dtoList =
                playerMapper.toSubParticipantDTOList(players, playerIdBetMap);

        for (SubParticipantDTO dto : dtoList) {
            dto.setHistoricalStats(buildHistoricalStats((long) dto.getId()));
        }

        return PaginationUtils.buildPaginatedResponse(
                dtoList, count, requestQuery.getPage(), requestQuery.getPageSize());
    }


    @EventBasedCache(cacheName = "subParticipantDetailsCache",
            key = "T(java.util.Objects).hash(#subParticipantId, #marketId, #maxHistoricalDataCount)")
    @Override
    public SubParticipantDTO getSubParticipantById(
            Integer subParticipantId, Integer marketId, Integer maxHistoricalDataCount) {

        SportsDataPlayerRow player = playerRepository.getPlayerWithBetsById(subParticipantId)
                .orElseThrow(() -> new FlexBetsSportNotFoundException(
                        "SubParticipant %s Not Found".formatted(subParticipantId)));

        List<SportsDataBetRow> playerBets =
                betRepository.findAllByMarketTypeAndEventIdInAndPlayerIdAndAnyBetsAvailableTrue(
                        IoBetMarketStatus.PLAYER_PROP.getName(),
                        Set.of(player.getEventId()),
                        player.getId().longValue());

        SubParticipantDTO dto = playerMapper.toSubParticipantDTO(player, playerBets);

        List<HistoricalStatDTO> hist = buildHistoricalStats(player.getId().longValue());
        if (maxHistoricalDataCount != null && maxHistoricalDataCount > 0) {
            hist.forEach(h -> h.setEventStatistics(
                    h.getEventStatistics().stream()
                            .limit(maxHistoricalDataCount)
                            .toList()));
        }
        dto.setHistoricalStats(hist);
        return dto;
    }


    public List<HistoricalStatDTO> buildHistoricalStats(Long playerId) {

        List<IoPlayerGameStats> games =
                gameStatsRepo.findAllByPlayerIdOrderByGameDatetimeDesc(playerId);

        Set<Long> teamIds = games.stream()
                .flatMap(g -> Stream.of(g.getTeamId(), g.getOpponentId()))
                .collect(Collectors.toSet());


        Map<Long, String> teamNames = ioTeamRepository.findAllByTeamIdIn(teamIds)
                .stream()
                .collect(Collectors.toUnmodifiableMap(
                        IoTeam::getTeamId,   // ключ
                        IoTeam::getName));

        return GAME_EXTRACT.entrySet().stream()
                .map(e -> buildStat(e.getKey(), e.getValue(), games, teamNames))
                .filter(h -> h.getCount() > 0)
                .toList();
    }


    private HistoricalStatDTO buildStat(
            String name,
            Function<IoPlayerGameStats, Number> gameGetter,
            List<IoPlayerGameStats> games,
            Map<Long, String> teamNames) {

        List<EventStatisticDTO> ev = new ArrayList<>(games.size());
        DoubleSummaryStatistics stats = new DoubleSummaryStatistics();

        for (IoPlayerGameStats g : games) {
            Number n = gameGetter.apply(g);
            if (n == null) continue;

            double v = n.doubleValue();
            stats.accept(v);

            ev.add(EventStatisticDTO.builder()
                    .eventId(Math.toIntExact(g.getGameId()))
                    .eventName(STR."\{teamNames.get(g.getTeamId())} - \{teamNames.get(g.getOpponentId())}")
                    .eventDate(g.getGameDatetime())
                    .value(v)
                    .rawValue(n.toString())
                    .opponent(teamNames.get(g.getOpponentId()))
                    .build());
        }

        if (stats.getCount() == 0)
            return HistoricalStatDTO.builder()
                    .statName(name)
                    .count(0)
                    .eventStatistics(List.of())
                    .build();

        return HistoricalStatDTO.builder()
                .statName(name)
                .average(BigDecimal.valueOf(round(stats.getAverage(), 2)))
                .count((int) stats.getCount())
                .maxValue((int) stats.getMax())
                .minValue((int) stats.getMin())
                .eventStatistics(ev)
                .build();
    }


    private static double round(double v, int scale) {
        return BigDecimal.valueOf(v)
                .setScale(scale, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
