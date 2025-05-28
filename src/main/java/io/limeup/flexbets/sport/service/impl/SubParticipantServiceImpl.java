package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.cache.EventBasedCache;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.mapper.SubParticipantMapper;
import io.limeup.flexbets.sport.model.enums.BetStatus;
import io.limeup.flexbets.sport.model.enums.MarketType;
import io.limeup.flexbets.sport.model.SubParticipant;
import io.limeup.flexbets.sport.repository.StatRepository;
import io.limeup.flexbets.sport.repository.SubParticipantRepository;
import io.limeup.flexbets.sport.repository.projection.BetRow;
import io.limeup.flexbets.sport.repository.projection.SubParticipantStatRow;
import io.limeup.flexbets.sport.service.BetService;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.MarketService;
import io.limeup.flexbets.sport.service.SubParticipantService;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import io.limeup.flexbets.sport.utils.ValidationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class SubParticipantServiceImpl extends ExternalIdReadServiceImpl<SubParticipant, SubParticipantDTO, Long> implements SubParticipantService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("player_name", "team_name", "position", "event_time");

    private final StatRepository statRepository;

    private final SubParticipantRepository subParticipantRepository;

    private final MarketService marketService;

    private final SubParticipantMapper mapper;

    private final BetService betService;

    protected SubParticipantServiceImpl(SubParticipantRepository subParticipantRepository, StatRepository statRepository,
                                        MarketService marketService, SubParticipantMapper mapper, BetService betService) {
        super(subParticipantRepository);
        this.statRepository = statRepository;
        this.subParticipantRepository = subParticipantRepository;
        this.marketService = marketService;
        this.mapper = mapper;
        this.betService = betService;
    }

    @EventBasedCache(cacheName = "subParticipantsListCache",
            key = "T(java.util.Objects).hash(#competitionId, #positions, #participantIds, #marketId, #maxHistoricalDataCount, #requestQuery.page, #requestQuery.pageSize, #requestQuery.sortOrder, #requestQuery.sortBy, #requestQuery.filter)")
    @Override
    public PaginatedResponse<SubParticipantDTO> listSubParticipants(Integer competitionId, List<String> positions,
                                                                    List<Integer> participantIds, Integer marketId,
                                                                    Integer maxHistoricalDataCount, RequestQueryDTO requestQuery) {
        ValidationUtils.validateSortFieldsInRequest(requestQuery, SUPPORTED_SORT_FIELDS);

        Set<String> statNames = marketService.getStatsByMarket(competitionId, marketId, MarketType.SUB_PARTICIPANT);

        Long count = subParticipantRepository.countSubParticipants(competitionId,
                positions == null ? Collections.emptyList() : positions,
                participantIds == null ? Collections.emptyList() : participantIds,
                requestQuery.getFilter());

        if (count == 0) {
            return PaginationUtils.buildPaginatedResponse(null, count, requestQuery.getPage(), requestQuery.getPageSize());
        }

        List<SubParticipantStatRow> stats = statRepository.listSubParticipantStats(
                competitionId,
                positions == null ? Collections.emptyList() : positions,
                participantIds == null ? Collections.emptyList() : participantIds,
                marketId,
                maxHistoricalDataCount,
                requestQuery.getFilter(),
                requestQuery.getSortBy(),
                requestQuery.getSortOrder(),
                (requestQuery.getPage() - 1) * requestQuery.getPageSize(),
                requestQuery.getPageSize(),
                statNames
        );

        Map<Integer, Map<String, List<BetRow>>> retrieveEventIdSubParticipantBetMap = retrieveEventIdSubParticipantBetMap(stats);

        return PaginationUtils.buildPaginatedResponse(
                mapper.toDTO(stats, retrieveEventIdSubParticipantBetMap(stats)), count, requestQuery.getPage(), requestQuery.getPageSize());
    }

    @EventBasedCache(cacheName = "subParticipantDetailsCache",
            key = "T(java.util.Objects).hash(#subParticipantId, #marketId, #maxHistoricalDataCount)")
    @Override
    public SubParticipantDTO getSubParticipantById(Integer subParticipantId, Integer marketId, Integer maxHistoricalDataCount) {
        SubParticipant rawSubParticipant = externalIdRepository.findByExternalId(subParticipantId)
                .orElseThrow(() -> new FlexBetsSportNotFoundException(String.format("SubParticipant %s Not Found", subParticipantId)));
        Set<String> statNames = marketService.getStatsByMarket(
                rawSubParticipant.getCompetition().getExternalId(), marketId, MarketType.SUB_PARTICIPANT);
        List<SubParticipantStatRow> subParticipantStatsDetails = statRepository.getSubParticipantStatsDetails(
                subParticipantId, maxHistoricalDataCount, statNames);

        return mapper.toDTO(subParticipantStatsDetails, retrieveEventIdSubParticipantBetMap(subParticipantStatsDetails))
                .stream()
                .findFirst()
                .orElseThrow(() -> new FlexBetsSportNotFoundException(String.format("SubParticipant %s Not Found", subParticipantId)));
    }

    private Map<Integer, Map<String, List<BetRow>>> retrieveEventIdSubParticipantBetMap(List<SubParticipantStatRow> subParticipantStatsDetails) {
        Set<Integer> eventIds = subParticipantStatsDetails.stream().map(SubParticipantStatRow::getFutureEventId).collect(Collectors.toSet());
        Map<Integer, List<BetRow>> eventIdBetsMap = betService.getBetsEventMapByEventExternalIdsAndMarketTypeAndBetStatus(eventIds, MarketType.SUB_PARTICIPANT, BetStatus.OPEN);
        Map<Integer, Map<String, List<BetRow>>> result = new HashMap<>();
        eventIdBetsMap.keySet().forEach(eventId -> {
            Map<String, List<BetRow>> subParticipantNameBetMap = eventIdBetsMap.get(eventId).stream()
                    .collect(Collectors.groupingBy(BetRow::getParticipantName));
            result.put(eventId, subParticipantNameBetMap);
        });
        return result;
    }

}
