package io.limeup.flexbets.sport.service.impl.sportsdataio;

import io.limeup.flexbets.sport.cache.EventBasedCache;
import io.limeup.flexbets.sport.dto.*;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.mapper.IoPlayerMapper;
import io.limeup.flexbets.sport.model.IoPlayerGameStats;
import io.limeup.flexbets.sport.model.IoTeam;
import io.limeup.flexbets.sport.model.enums.IoBetMarketStatus;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataBetRow;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataPlayerRow;
import io.limeup.flexbets.sport.repository.sportsdataio.IoBetRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoPlayerGamesStatsRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoPlayerRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoTeamRepository;
import io.limeup.flexbets.sport.service.SubParticipantService;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import io.limeup.flexbets.sport.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@Service("sportsDataIoProvider")
@Slf4j
public class SubParticipantServiceIoMlbImpl implements SubParticipantService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("player_name", "team_name", "position", "event_time");

    private final IoPlayerRepository playerRepository;
    private final IoBetRepository betRepository;
    private final IoPlayerMapper playerMapper;
    private final IoPlayerGamesStatsRepository gameStatsRepo;
    private final IoTeamRepository ioTeamRepository;

    public SubParticipantServiceIoMlbImpl(IoPlayerRepository playerRepository, IoBetRepository betRepository,
                                          IoPlayerMapper playerMapper, IoPlayerGamesStatsRepository gameStatsRepo,
                                          IoTeamRepository ioTeamRepository) {
        this.playerRepository = playerRepository;
        this.betRepository = betRepository;
        this.playerMapper = playerMapper;
        this.gameStatsRepo = gameStatsRepo;
        this.ioTeamRepository = ioTeamRepository;
    }

//    @EventBasedCache(cacheName = "subParticipantsListCache", key = "T(java.util.Objects).hash(#competitionId, #positions," +
//            " #participantIds, #marketId, #maxHistoricalDataCount, #requestQuery.page, #requestQuery.pageSize,   " +
//            "  #requestQuery.sortOrder, #requestQuery.sortBy, #requestQuery.filter,#odds)")
    @Override
    public PaginatedResponse<SubParticipantDTO> listSubParticipants(Integer competitionId, List<String> positions,
                                                                    List<Integer> participantIds, Integer marketId,
                                                                    Boolean odds, Integer maxHistoricalDataCount,
                                                                    RequestQueryDTO requestQuery) {

        ValidationUtils.validateSortFieldsInRequest(requestQuery, SUPPORTED_SORT_FIELDS);
        odds = Boolean.TRUE.equals(odds);

        long count = playerRepository.countPlayersWithFilters(odds, requestQuery.getFilter(), marketId,
                positions == null ? Collections.emptyList() : positions,
                participantIds == null ? Collections.emptyList() : participantIds
        );

        List<SportsDataPlayerRow> players = fetchFilteredPlayers(requestQuery, marketId, positions, participantIds, odds);
        Map<Long, List<SportsDataBetRow>> playerBets = fetchPlayerBets(players, marketId);

        List<SubParticipantDTO> dtoList = mapPlayersToDTOs(players, playerBets, maxHistoricalDataCount);

        return PaginationUtils.buildPaginatedResponse(dtoList, count, requestQuery.getPage(), requestQuery.getPageSize());
    }

//    @EventBasedCache(cacheName = "subParticipantDetailsCache", key = "T(java.util.Objects).hash(#subParticipantId, #marketId, #maxHistoricalDataCount)")
    @Override
    public SubParticipantDTO getSubParticipantById(Integer subParticipantId, Integer marketId, Integer maxHistoricalDataCount) {
        SportsDataPlayerRow player = playerRepository.getPlayerWithBetsById(subParticipantId)
                .orElseThrow(() -> new FlexBetsSportNotFoundException("SubParticipant %s Not Found".formatted(subParticipantId)));

        List<SportsDataBetRow> bets = (marketId == null)
                ? betRepository.findAvailablePlayerBets(IoBetMarketStatus.PLAYER_PROP.getName(), new int[]{player.getId()})
                : betRepository.findAvailablePlayerBetsWithMarketId(IoBetMarketStatus.PLAYER_PROP.getName(), marketId, new int[]{player.getId()});

        SubParticipantDTO dto = playerMapper.toSubParticipantDTO(player, bets);
        dto.setHistoricalStats(buildHistoricalStats((long) player.getId(), maxHistoricalDataCount));
        return dto;
    }

    private List<SportsDataPlayerRow> fetchFilteredPlayers(RequestQueryDTO requestQuery,
                                                           Integer marketId, List<String> positions, List<Integer> participantIds, Boolean odds) {
        int limit = requestQuery.getPageSize();
        int offset = (requestQuery.getPage() - 1) * limit;

        return playerRepository.listPlayersWithFilters(offset, limit, odds, requestQuery.getSortBy(), requestQuery.getSortOrder(), requestQuery.getFilter(),
                marketId, positions == null ? Collections.emptyList() : positions, participantIds == null ? Collections.emptyList() : participantIds);
    }

    private Map<Long, List<SportsDataBetRow>> fetchPlayerBets(List<SportsDataPlayerRow> players, Integer marketId) {
        int[] playerIds = players.stream().mapToInt(SportsDataPlayerRow::getId).toArray();

        List<SportsDataBetRow> bets = (marketId == null)
                ? betRepository.findAvailablePlayerBets(IoBetMarketStatus.PLAYER_PROP.getName(), playerIds)
                : betRepository.findAvailablePlayerBetsWithMarketId(IoBetMarketStatus.PLAYER_PROP.getName(), marketId, playerIds);

        return bets.stream().collect(Collectors.groupingBy(SportsDataBetRow::getPlayerId));
    }

    private List<SubParticipantDTO> mapPlayersToDTOs(List<SportsDataPlayerRow> players,
                                                     Map<Long, List<SportsDataBetRow>> playerBets,
                                                     Integer maxHistoricalDataCount) {
        List<SubParticipantDTO> dtoList = playerMapper.toSubParticipantDTOList(players, playerBets);
        dtoList.forEach(dto -> dto.setHistoricalStats(buildHistoricalStats((long) dto.getId(), maxHistoricalDataCount)));
        return dtoList;
    }

    private List<HistoricalStatDTO> buildHistoricalStats(Long playerId, Integer maxHistoricalDataCount) {
        List<IoPlayerGameStats> games = gameStatsRepo.findTopByPlayerIdLimit(playerId, maxHistoricalDataCount);

        Map<Integer, String> teamNames = ioTeamRepository.findAllByTeamIdIn(
                        games.stream().flatMap(g -> Stream.of(g.getTeamId(), g.getOpponentId())).collect(Collectors.toSet()))
                .stream().collect(Collectors.toUnmodifiableMap(IoTeam::getTeamId, IoTeam::getName));

        return IoPlayerStatsUtils.buildStatsFromGames(games, teamNames);
    }

    static class IoPlayerStatsUtils {
        private static final Map<String, Function<IoPlayerGameStats, Number>> GAME_EXTRACT = Map.ofEntries(
                Map.entry("At bats", IoPlayerGameStats::getAtBats),
                Map.entry("Runs", IoPlayerGameStats::getRuns),
                Map.entry("Hits", IoPlayerGameStats::getHits),
                Map.entry("Singles", IoPlayerGameStats::getSingles),
                Map.entry("Doubles", IoPlayerGameStats::getDoubles),
                Map.entry("Triples", IoPlayerGameStats::getTriples),
                Map.entry("Home runs", IoPlayerGameStats::getHomeRuns),
                Map.entry("Runs batted in", IoPlayerGameStats::getRunsBattedIn),
                Map.entry("Strikeouts", IoPlayerGameStats::getStrikeouts),
                Map.entry("Walks", IoPlayerGameStats::getWalks),
                Map.entry("Hit by pitch", IoPlayerGameStats::getHitByPitch),
                Map.entry("Outs", IoPlayerGameStats::getOuts),
                Map.entry("Sacrifices", IoPlayerGameStats::getSacrifices),
                Map.entry("Sacrifice flies", IoPlayerGameStats::getSacrificeFlies),
                Map.entry("Ground into DP", IoPlayerGameStats::getGroundIntoDoublePlay),
                Map.entry("Stolen bases", IoPlayerGameStats::getStolenBases),
                Map.entry("Caught stealing", IoPlayerGameStats::getCaughtStealing)
        );

        public static List<HistoricalStatDTO> buildStatsFromGames(List<IoPlayerGameStats> games, Map<Integer, String> teamNames) {
            return GAME_EXTRACT.entrySet().stream()
                    .map(e -> buildStat(e.getKey(), e.getValue(), games, teamNames))
                    .filter(h -> h.getCount() > 0)
                    .toList();
        }

        private static HistoricalStatDTO buildStat(String name, Function<IoPlayerGameStats, Number> getter,
                                                   List<IoPlayerGameStats> games, Map<Integer, String> teamNames) {
            List<EventStatisticDTO> ev = new ArrayList<>(games.size());
            DoubleSummaryStatistics stats = new DoubleSummaryStatistics();

            for (IoPlayerGameStats g : games) {
                Number n = getter.apply(g);
                if (n == null) continue;
                double v = n.doubleValue();
                stats.accept(v);

                ev.add(EventStatisticDTO.builder()
                        .eventId(Math.toIntExact(g.getGameId()))
                        .eventName(teamNames.getOrDefault(g.getTeamId(), "") + " - " + teamNames.getOrDefault(g.getOpponentId(), ""))
                        .eventDate(g.getGameDatetime())
                        .value(v)
                        .rawValue(n.toString())
                        .opponent(teamNames.get(g.getOpponentId()))
                        .build());
            }

            if (stats.getCount() == 0) {
                return HistoricalStatDTO.builder().statName(name).count(0).eventStatistics(List.of()).build();
            }

            return HistoricalStatDTO.builder()
                    .statName(name)
                    .average(round(stats.getAverage(), 2))
                    .count((int) stats.getCount())
                    .maxValue((int) stats.getMax())
                    .minValue((int) stats.getMin())
                    .eventStatistics(ev)
                    .build();
        }

        private static BigDecimal round(double value, int scale) {
            return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP);
        }
    }
}
