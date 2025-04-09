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
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class SubParticipantServiceImpl extends ExternalIdReadServiceImpl<SubParticipant, SubParticipantDTO, Long> implements SubParticipantService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("player_name", "team_name", "position", "event_time");

    private final StatRepository statRepository;

    private final SubParticipantRepository subParticipantRepository;

    private final MarketService marketService;

    protected SubParticipantServiceImpl(SubParticipantRepository subParticipantRepository, StatRepository statRepository, MarketService marketService) {
        super(subParticipantRepository);
        this.statRepository = statRepository;
        this.subParticipantRepository = subParticipantRepository;
        this.marketService = marketService;
    }

    @Override
    public PaginatedResponse<SubParticipantDTO> listSubParticipants(Integer competitionId, List<String> positions,
                                                                          List<Integer> participantIds, Integer marketId, RequestQueryDTO requestQuery) {
        validateRequest(requestQuery);

        Set<String> statNames = marketService.getStatsByMarket(competitionId, marketId, MarketType.SUB_PARTICIPANT);
        List<SubParticipantStatRow> stats = statRepository.listSubParticipantStats(
                competitionId,
                positions == null ? Collections.emptyList() : positions,
                participantIds == null ? Collections.emptyList() : participantIds,
                marketId,
                requestQuery.getFilter(),
                requestQuery.getSortBy(),
                requestQuery.getSortOrder(),
                (requestQuery.getPage() - 1) * requestQuery.getPageSize(),
                requestQuery.getPageSize(),
                statNames
        );
        Integer count = subParticipantRepository.countSubParticipants(competitionId,
                positions == null ? Collections.emptyList() : positions,
                participantIds == null ? Collections.emptyList() : participantIds,
                requestQuery.getFilter());

        return PaginationUtils.buildPaginatedResponse(SubParticipantMapper.toDTO(stats), count, requestQuery.getPage(), requestQuery.getPageSize());
    }

    @Override
    public SubParticipantDTO getSubParticipantById(Integer subParticipantId, Integer marketId) {
        SubParticipant rawParticipant = repository.findByExternalId(subParticipantId)
                .orElseThrow(() -> new FlexBetsSportNotFoundException(String.format("SubParticipant %s Not Found", subParticipantId)));
        Set<String> statNames = marketService.getStatsByMarket(
                rawParticipant.getCompetition().getExternalId(), marketId, MarketType.SUB_PARTICIPANT);
        List<SubParticipantStatRow> subParticipantStatsDetails = statRepository.getSubParticipantStatsDetails(
                subParticipantId, statNames);
        return SubParticipantMapper.toDTO(subParticipantStatsDetails)
                .stream()
                .findFirst()
                .orElseThrow(() -> new FlexBetsSportNotFoundException(String.format("SubParticipant %s Not Found", subParticipantId)));
    }

    private void validateRequest(RequestQueryDTO requestQuery) {
        if (StringUtils.isNotBlank(requestQuery.getSortBy()) && !SUPPORTED_SORT_FIELDS.contains(requestQuery.getSortBy())) {
            throw new ValidationException(String.format("Invalid sortBy: %s. Available options: %s", requestQuery.getSortBy(), SUPPORTED_SORT_FIELDS));
        }
    }

}
