package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.cache.EventBasedCache;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.ParticipantDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.mapper.ParticipantMapper;
import io.limeup.flexbets.sport.model.enums.MarketType;
import io.limeup.flexbets.sport.model.Participant;
import io.limeup.flexbets.sport.repository.ParticipantRepository;
import io.limeup.flexbets.sport.repository.StatRepository;
import io.limeup.flexbets.sport.repository.projection.BetRow;
import io.limeup.flexbets.sport.repository.projection.ParticipantStatRow;
import io.limeup.flexbets.sport.service.BetService;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.MarketService;
import io.limeup.flexbets.sport.service.ParticipantService;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import io.limeup.flexbets.sport.utils.ValidationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class ParticipantServiceImpl extends ExternalIdReadServiceImpl<Participant, ParticipantDTO, Long> implements ParticipantService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("team_name", "acronym");

    private final ParticipantRepository participantRepository;

    private final MarketService marketService;

    private final StatRepository statRepository;

    private final BetService betService;

    protected ParticipantServiceImpl(ParticipantRepository repository, MarketService marketService, StatRepository statRepository, BetService betService) {
        super(repository);
        this.participantRepository = repository;
        this.marketService = marketService;
        this.statRepository = statRepository;
        this.betService = betService;
    }

    @EventBasedCache(cacheName = "participantsListCache",
            key = "T(java.util.Objects).hash(#competitionId, #participantIds, #marketId, #maxHistoricalDataCount, #requestQuery.page, #requestQuery.pageSize, #requestQuery.sortOrder, #requestQuery.sortBy, #requestQuery.filter)")
    @Override
    public PaginatedResponse<ParticipantDTO> listParticipants(Integer competitionId, List<Integer> participantIds,
                                                              Integer marketId, Integer maxHistoricalDataCount, RequestQueryDTO requestQuery) {
        ValidationUtils.validateSortFieldsInRequest(requestQuery, SUPPORTED_SORT_FIELDS);

        Set<String> statNames = marketService.getStatsByMarket(competitionId, marketId, MarketType.PARTICIPANT);

        Long count = participantRepository.countParticipants(competitionId,
                participantIds == null ? Collections.emptyList() : participantIds,
                requestQuery.getFilter());

        if (count == 0) {
            return PaginationUtils.buildPaginatedResponse(null, count, requestQuery.getPage(), requestQuery.getPageSize());
        }

        List<ParticipantStatRow> stats = statRepository.listParticipantStats(
                competitionId,
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

        Set<Integer> eventIds = stats.stream().map(ParticipantStatRow::getFutureEventId).collect(Collectors.toSet());
        Map<Integer, List<BetRow>> eventIdBetsMap = betService.getBetsEventMapByEventExternalIdsAndMarketType(eventIds, MarketType.PARTICIPANT);


        return PaginationUtils.buildPaginatedResponse(ParticipantMapper.toDTO(stats, eventIdBetsMap), count, requestQuery.getPage(), requestQuery.getPageSize());
    }

    @EventBasedCache(cacheName = "participantDetailsCache",
            key = "T(java.util.Objects).hash(#participantId, #marketId, #maxHistoricalDataCount)")
    @Override
    public ParticipantDTO getParticipantById(Integer participantId, Integer marketId, Integer maxHistoricalDataCount) {
        Participant rawParticipant = participantRepository.findByExternalId(participantId)
                .orElseThrow(() -> new FlexBetsSportNotFoundException(String.format("Participant %s Not Found", participantId)));
        Set<String> statNames = marketService.getStatsByMarket(rawParticipant.getCompetition().getExternalId(), marketId, MarketType.PARTICIPANT);
        List<ParticipantStatRow> participantStatsDetails = statRepository.getParticipantStatsDetails(
                participantId, maxHistoricalDataCount, statNames);
        Set<Integer> eventIds = participantStatsDetails.stream().map(ParticipantStatRow::getFutureEventId).collect(Collectors.toSet());
        Map<Integer, List<BetRow>> eventIdBetsMap = betService.getBetsEventMapByEventExternalIdsAndMarketType(eventIds, MarketType.PARTICIPANT);

        return ParticipantMapper.toDTO(participantStatsDetails, eventIdBetsMap)
                .stream()
                .findFirst()
                .orElseThrow(() -> new FlexBetsSportNotFoundException(String.format("Participant %s Not Found", participantId)));
    }

}
