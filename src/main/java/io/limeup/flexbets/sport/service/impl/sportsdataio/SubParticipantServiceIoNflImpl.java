package io.limeup.flexbets.sport.service.impl.sportsdataio;

import io.limeup.flexbets.sport.dto.*;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.model.IoPlayerGameStatsNFL;
import io.limeup.flexbets.sport.model.IoTeamNFL;
import io.limeup.flexbets.sport.mapper.IoPlayerNFLMapper;
import io.limeup.flexbets.sport.model.enums.IoBetMarketStatus;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataBetRow;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataPlayerRow;
import io.limeup.flexbets.sport.repository.sportsdataio.*;
import io.limeup.flexbets.sport.service.SubParticipantService;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import io.limeup.flexbets.sport.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@Service("sportsDataIoNflProvider")
@Slf4j
public class SubParticipantServiceIoNflImpl implements SubParticipantService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("player_name", "team_name", "position");

    private final IoPlayerNFLRepository playerRepository;
    private final IoBetNFLRepository betRepository;
    private final IoPlayerNFLMapper playerMapper;
    private final IoPlayerGameStatsNFLRepository gameStatsRepo;
    private final IoTeamNFLRepository teamRepository;

    public SubParticipantServiceIoNflImpl(IoPlayerNFLRepository playerRepository,
                                          IoBetNFLRepository betRepository,
                                          IoPlayerNFLMapper playerMapper,
                                          IoPlayerGameStatsNFLRepository gameStatsRepo,
                                          IoTeamNFLRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.betRepository = betRepository;
        this.playerMapper = playerMapper;
        this.gameStatsRepo = gameStatsRepo;
        this.teamRepository = teamRepository;
    }

    @Override
    public PaginatedResponse<SubParticipantDTO> listSubParticipants(Integer competitionId,
                                                                    List<String> positions,
                                                                    List<Integer> teamIds,
                                                                    Integer marketId,
                                                                    Boolean onlyWithOdds,
                                                                    Integer maxHistorical,
                                                                    RequestQueryDTO requestQuery) {
        ValidationUtils.validateSortFieldsInRequest(requestQuery, SUPPORTED_SORT_FIELDS);
        boolean odds = Boolean.TRUE.equals(onlyWithOdds);

        int limit = requestQuery.getPageSize();
        int offset = (requestQuery.getPage() - 1) * limit;

        List<SportsDataPlayerRow> players = playerRepository.listPlayersWithFilters(
                offset,
                limit,
                odds,
                requestQuery.getSortBy(),
                requestQuery.getSortOrder(),
                requestQuery.getFilter(),
                competitionId,
                positions == null ? Collections.emptyList() : positions,
                teamIds == null ? Collections.emptyList() : teamIds
        );

        int[] ids = players.stream()
                .map(SportsDataPlayerRow::getId)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .toArray();

        List<Long> playerIds = Arrays.stream(ids)
                .boxed()
                .map(Long::valueOf)
                .toList();

        // Currently bet data is not available so returning empty .
        List<SportsDataBetRow> bets = Collections.emptyList();
        /*List<SportsDataBetRow> bets = marketId == null
                ? betRepository.findAvailablePlayerBets(IoBetMarketStatus.PLAYER_PROP.getName(), playerIds)
                : betRepository.findAvailablePlayerBetsWithMarketId(IoBetMarketStatus.PLAYER_PROP.getName(), Long.valueOf(marketId), playerIds);*/

        /*List<SportsDataBetRow> bets = (marketId == null)
                ? betRepository.findAvailablePlayerBets(IoBetMarketStatus.PLAYER_PROP.getName(), playerIds)
                : betRepository.findAvailablePlayerBetsWithMarketId(IoBetMarketStatus.PLAYER_PROP.getName(), marketId.longValue(), playerIds);*/


        Map<Long, List<SportsDataBetRow>> playerBets = bets.stream()
                .collect(Collectors.groupingBy(SportsDataBetRow::getPlayerId));

        List<SubParticipantDTO> dtoList = players.stream()
                .map(p -> {
                    List<SportsDataBetRow> pb = playerBets.getOrDefault((long)p.getId(), Collections.emptyList());
                    SubParticipantDTO dto = playerMapper.toSubParticipantDTO(p, pb);
                    if (maxHistorical != null && maxHistorical > 0) {
                        dto.setHistoricalStats(buildHistoricalStats(p.getId(), maxHistorical));
                    }
                    return dto;
                }).collect(Collectors.toList());

        return PaginationUtils.buildPaginatedResponse(dtoList, (long) players.size(), requestQuery.getPage(), requestQuery.getPageSize());
    }

    @Override
    public SubParticipantDTO getSubParticipantById(Integer subParticipantId, Integer marketId, Integer maxHistorical) {

        long playerId = subParticipantId.longValue();

        SportsDataPlayerRow player = playerRepository.getNFLPlayerWithBetsById(playerId)
                .orElseThrow(() -> new FlexBetsSportNotFoundException("NFL player not found: " + subParticipantId));

        // Convert to List<Long>
        List<Long> playerIds = List.of(playerId);

        // Currently bet data is not available so returning empty .
        List<SportsDataBetRow> bets = Collections.emptyList();
        /*List<SportsDataBetRow> bets = marketId == null
                ? betRepository.findAvailablePlayerBets(IoBetMarketStatus.PLAYER_PROP.getName(), playerIds)
                : betRepository.findAvailablePlayerBetsWithMarketId(IoBetMarketStatus.PLAYER_PROP.getName(), Long.valueOf(marketId), playerIds);*/


        SubParticipantDTO dto = playerMapper.toSubParticipantDTO(player, bets);

        if (maxHistorical != null && maxHistorical > 0) {
            dto.setHistoricalStats(buildHistoricalStats(playerId, maxHistorical));
        }

        return dto;
    }

    private List<HistoricalStatDTO> buildHistoricalStats(long playerId, int maxHistorical) {
        Pageable pageable = PageRequest.of(0, maxHistorical);
        List<IoPlayerGameStatsNFL> stats = gameStatsRepo.findByPlayerIdOrderByGameDateDesc(playerId, pageable);

        Set<Long> tIds = stats.stream()
                .flatMap(g -> Stream.of(g.getTeamId(), g.getOpponentId()))
                .filter(Objects::nonNull)
                .map(Integer::longValue) // Convert to Long
                .collect(Collectors.toSet());

        Map<Long, String> teamNames = teamRepository.findAllByTeamIdIn(tIds).stream()
                .collect(Collectors.toMap(IoTeamNFL::getTeamId, t -> t.getCity() + " " + t.getName()));

        Map<String, Function<IoPlayerGameStatsNFL, Number>> extractors = Map.of(
                "Passing Yards", IoPlayerGameStatsNFL::getPassingYards,
                "Passing TDs", IoPlayerGameStatsNFL::getPassingTouchdowns,
                "Rushing Yards", IoPlayerGameStatsNFL::getRushingYards,
                "Rushing TDs", IoPlayerGameStatsNFL::getRushingTouchdowns,
                "Receiving Yards", IoPlayerGameStatsNFL::getReceivingYards,
                "Receiving TDs", IoPlayerGameStatsNFL::getReceivingTouchdowns
        );

        return extractors.entrySet().stream()
                .map(e -> buildStat(e.getKey(), stats, e.getValue(), teamNames))
                .filter(dto -> dto.getCount() > 0)
                .collect(Collectors.toList());
    }

    private HistoricalStatDTO buildStat(String name,
                                        List<IoPlayerGameStatsNFL> stats,
                                        Function<IoPlayerGameStatsNFL, Number> getter,
                                        Map<Long, String> teamNames) {
        List<EventStatisticDTO> events = new ArrayList<>();
        DoubleSummaryStatistics summary = new DoubleSummaryStatistics();

        for (IoPlayerGameStatsNFL g : stats) {
            Number val = getter.apply(g);
            if (val == null) continue;
            double dbl = val.doubleValue();
            summary.accept(dbl);
            String home = teamNames.getOrDefault(g.getTeamId(), "");
            String opp = teamNames.getOrDefault(g.getOpponentId(), "");
            events.add(EventStatisticDTO.builder()
                    .eventId(Math.toIntExact(g.getGlobalGameId()))
                    .eventName(home + " vs " + opp)
                    .eventDate(g.getGameDate())
                    .value(dbl)
                    .rawValue(val.toString())
                    .opponent(opp)
                    .build());
        }

        if (summary.getCount() == 0) {
            return HistoricalStatDTO.builder().statName(name).count(0).eventStatistics(Collections.emptyList()).build();
        }

        return HistoricalStatDTO.builder()
                .statName(name)
                .average(round(summary.getAverage(), 2))
                .count((int) summary.getCount())
                .maxValue((int) summary.getMax())
                .minValue((int) summary.getMin())
                .eventStatistics(events)
                .build();
    }

    private BigDecimal round(double value, int decimals) {
        return BigDecimal.valueOf(value).setScale(decimals, RoundingMode.HALF_UP);
    }
}
