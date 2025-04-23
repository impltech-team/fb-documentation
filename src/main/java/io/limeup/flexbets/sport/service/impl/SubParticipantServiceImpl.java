package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.mapper.SubParticipantMapper;
import io.limeup.flexbets.sport.model.MarketType;
import io.limeup.flexbets.sport.model.SubParticipant;
import io.limeup.flexbets.sport.repository.StatRepository;
import io.limeup.flexbets.sport.repository.SubParticipantRepository;
import io.limeup.flexbets.sport.repository.projection.SubParticipantStatRow;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.MarketService;
import io.limeup.flexbets.sport.service.SubParticipantService;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import io.limeup.flexbets.sport.utils.ValidationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class SubParticipantServiceImpl extends ExternalIdReadServiceImpl<SubParticipant, SubParticipantDTO, Long> implements SubParticipantService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("player_name", "team_name", "position", "event_time");

    private final StatRepository statRepository;

    private final SubParticipantRepository subParticipantRepository;

    private final MarketService marketService;

    private final SubParticipantMapper mapper;

    protected SubParticipantServiceImpl(SubParticipantRepository subParticipantRepository, StatRepository statRepository,
                                        MarketService marketService, SubParticipantMapper mapper) {
        super(subParticipantRepository);
        this.statRepository = statRepository;
        this.subParticipantRepository = subParticipantRepository;
        this.marketService = marketService;
        this.mapper = mapper;
    }

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

        return PaginationUtils.buildPaginatedResponse(
                mapper.toDTO(stats), count, requestQuery.getPage(), requestQuery.getPageSize());
    }

    @Override
    public SubParticipantDTO getSubParticipantById(Integer subParticipantId, Integer marketId, Integer maxHistoricalDataCount) {
        SubParticipant rawSubParticipant = externalIdRepository.findByExternalId(subParticipantId)
                .orElseThrow(() -> new FlexBetsSportNotFoundException(String.format("SubParticipant %s Not Found", subParticipantId)));
        Set<String> statNames = marketService.getStatsByMarket(
                rawSubParticipant.getCompetition().getExternalId(), marketId, MarketType.SUB_PARTICIPANT);
        List<SubParticipantStatRow> subParticipantStatsDetails = statRepository.getSubParticipantStatsDetails(
                subParticipantId, maxHistoricalDataCount, statNames);
        return mapper.toDTO(subParticipantStatsDetails)
                .stream()
                .findFirst()
                .orElseThrow(() -> new FlexBetsSportNotFoundException(String.format("SubParticipant %s Not Found", subParticipantId)));
    }

}
