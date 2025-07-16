package io.limeup.flexbets.sport.service.impl.sportsdataio;

import io.limeup.flexbets.sport.cache.EventBasedCache;
import io.limeup.flexbets.sport.dto.*;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.model.IoPlayerGameStats;
import io.limeup.flexbets.sport.model.IoTeam;
import io.limeup.flexbets.sport.model.enums.IoBetMarketStatus;
import io.limeup.flexbets.sport.repository.projection.OddsRow;
import io.limeup.flexbets.sport.repository.projection.ParticipantStatRow;
import io.limeup.flexbets.sport.repository.sportsdataio.IoBetRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoPlayerGamesStatsRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoTeamRepository;
import io.limeup.flexbets.sport.service.ParticipantService;
import io.limeup.flexbets.sport.utils.IoPlayerStatsUtils;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import io.limeup.flexbets.sport.utils.ValidationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("sportsDataIoParticipantService")
@Transactional
public class ParticipantServiceIoMlbImpl implements ParticipantService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("team_name", "acronym");
    private static final int COMPETITION_ID = 5466;
    private static final String COMPETITION_NAME = "MLB";

    private final IoTeamRepository teamRepository;
    private final IoBetRepository betRepository;
    private final IoPlayerGamesStatsRepository gameStatsRepo;

    public ParticipantServiceIoMlbImpl(IoTeamRepository teamRepository, IoBetRepository betRepository, IoPlayerGamesStatsRepository gameStatsRepo) {
        this.teamRepository = teamRepository;
        this.betRepository = betRepository;
        this.gameStatsRepo = gameStatsRepo;
    }


        @EventBasedCache(cacheName = "participantsListCache",
            key = "T(java.util.Objects).hash(#competitionId, #participantIds, #marketId, #maxHistoricalDataCount," +
                    " #requestQuery.page, #requestQuery.pageSize, #requestQuery.sortOrder, #requestQuery.sortBy, #requestQuery.filter)")
    @Override
    public PaginatedResponse<ParticipantDTO> listParticipants(Integer competitionId,
                                                              List<Integer> participantIds,
                                                              Integer marketId,
                                                              Integer maxHistoricalDataCount,
                                                              RequestQueryDTO requestQuery) {
        ValidationUtils.validateSortFieldsInRequest(requestQuery, SUPPORTED_SORT_FIELDS);
        int page = requestQuery.getPage();
        int pageSize = requestQuery.getPageSize();
        long count = (marketId == null)
                ? teamRepository.countTeamsWithAnyBetAvailable(participantIds == null ? Collections.emptyList() : participantIds, requestQuery.getFilter())
                : teamRepository.countTeamsWithMarketId(marketId, participantIds == null ? Collections.emptyList() : participantIds, requestQuery.getFilter());


        List<ParticipantStatRow> teams = (marketId == null)
                ? teamRepository.listParticipantStats(participantIds == null ? Collections.emptyList() : participantIds,
                requestQuery.getFilter(), requestQuery.getSortBy(), requestQuery.getSortOrder())
                : teamRepository.listParticipantStatByTeamIdAndMarketId(marketId, participantIds == null ? Collections.emptyList() : participantIds,
                requestQuery.getFilter(), requestQuery.getSortBy(), requestQuery.getSortOrder());


        Set<Integer> teamIds = teams.stream()
                .map(ParticipantStatRow::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        int total = teams.size();
        int pageZeroBased = Math.max(page - 1, 0);
        int fromIndex = Math.min(pageZeroBased * pageSize, total);
        int toIndex = Math.min(fromIndex + pageSize, total);
        List<ParticipantStatRow> paged = teams.subList(fromIndex, toIndex);

        List<OddsRow> allOdds = (marketId == null)
                ? betRepository.getBetsForTeam(teamIds, IoBetMarketStatus.TEAM_PROP.getName())
                : betRepository.getBetsForTeamWithMarketId(teamIds, IoBetMarketStatus.TEAM_PROP.getName(), marketId);
        Map<Integer, List<OddsRow>> oddsByTeamId = allOdds.stream()
                .filter(o -> o.getTeamId() != null)
                .collect(Collectors.groupingBy(OddsRow::getTeamId));

        List<ParticipantDTO> dtoList = paged.stream()
                .filter(team -> marketId == null || oddsByTeamId.containsKey(team.getId()))
                .map(team -> {
                    List<OddsRow> teamOdds = oddsByTeamId.getOrDefault(team.getId(), List.of());
                    List<OddsDTO> mappedOdds = mapOdds(teamOdds);
                    return toDto(team, mappedOdds, maxHistoricalDataCount);
                })
                .toList();

        return PaginationUtils.buildPaginatedResponse(dtoList, count, page, pageSize);
    }

        @EventBasedCache(cacheName = "participantDetailsCache",
            key = "T(java.util.Objects).hash(#participantId, #marketId, #maxHistoricalDataCount)")
    @Override
    public ParticipantDTO getParticipantById(Integer participantId, Integer marketId, Integer maxHistoricalDataCount) {
        IoTeam team = teamRepository.findByTeamId(participantId)
                .orElseThrow(() -> new FlexBetsSportNotFoundException(
                        String.format("Participant %s Not Found", participantId)));


        boolean includeAll = participantId == null;
        Integer[] ids = includeAll ? new Integer[0] : new Integer[]{participantId};

        List<ParticipantStatRow> statRows = teamRepository.listParticipantStats(List.of(ids),
                null, null, null);

        if (statRows.isEmpty()) {
            throw new FlexBetsSportNotFoundException("ParticipantStatRow not found for participantId = " + participantId);
        }

        List<OddsRow> odds = (marketId == null)
                ? betRepository.getBetsForTeam(Set.of(team.getTeamId()), IoBetMarketStatus.TEAM_PROP.getName())
                : betRepository.getBetsForTeamWithMarketId(Set.of(team.getTeamId()), IoBetMarketStatus.TEAM_PROP.getName(), marketId);
        List<OddsDTO> mappedOdds = mapOdds(odds);

        return toDto(statRows.getFirst(), mappedOdds, maxHistoricalDataCount);
    }

    private ParticipantDTO toDto(ParticipantStatRow team, List<OddsDTO> bet, Integer maxHistoricalDataCount) {
        ParticipantDTO dto = new ParticipantDTO();
        dto.setId(team.getId());
        dto.setTeamName(team.getTeamName());
        dto.setAcronym(team.getAcronym());
        dto.setCompetition(COMPETITION_NAME);
        dto.setCompetitionId(COMPETITION_ID);
        dto.setOdds(bet != null ? bet : new ArrayList<>());

        List<IoPlayerGameStats> games = gameStatsRepo.findTopByTeamIdLimit(team.getId().longValue(), maxHistoricalDataCount);
        Map<Integer, String> teamNames = teamRepository.findAllByTeamIdIn(
                games.stream()
                        .flatMap(g -> Stream.of(g.getTeamId(), g.getOpponentId()))
                        .collect(Collectors.toSet())
        ).stream().collect(Collectors.toUnmodifiableMap(IoTeam::getTeamId, IoTeam::getName));

        List<HistoricalStatDTO> stats = IoPlayerStatsUtils.buildStatsFromGames(games, teamNames);
        dto.setHistoricalStats(stats);

        if (team.getFutureEventId() != null) {
            EventLiteDTO eventLiteDTO = new EventLiteDTO();
            eventLiteDTO.setEventId(team.getFutureEventId());
            eventLiteDTO.setEventName(team.getFutureEventName());
            eventLiteDTO.setEventDate(team.getFutureEventStartDate());
            String[] acronyms = team.getFutureEventAcronyms() != null
                    ? team.getFutureEventAcronyms().split(",")
                    : new String[0];
            String opponent = Arrays.stream(acronyms)
                    .filter(a -> !a.equalsIgnoreCase(team.getAcronym()))
                    .findFirst()
                    .orElse(null);
            eventLiteDTO.setOpponent(opponent);

            dto.setNextEvent(eventLiteDTO);
        }

        return dto;
    }

    private List<OddsDTO> mapOdds(List<OddsRow> projections) {

        return projections.stream()
                .map(p -> {
                    OddsDTO dto = new OddsDTO();
                    dto.setId(p.getId());
                    dto.setMarketId(p.getMarketId());
                    dto.setMarketName(p.getMarketName());
                    dto.setLine(p.getLine());
                    dto.setBetType(p.getBetType());
                    dto.setPrice(new BigDecimal(p.getPrice())
                            .setScale(3, RoundingMode.HALF_UP)
                            .toPlainString());
                    dto.setStatus(p.getStatus());
                    dto.setLastUpdatedDate(p.getLastUpdatedDate());
                    return dto;
                })
                .toList();
    }

}
