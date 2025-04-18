package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.ParticipantDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.mapper.ParticipantMapper;
import io.limeup.flexbets.sport.model.MarketType;
import io.limeup.flexbets.sport.model.Participant;
import io.limeup.flexbets.sport.repository.ParticipantRepository;
import io.limeup.flexbets.sport.repository.StatRepository;
import io.limeup.flexbets.sport.repository.projection.ParticipantStatRow;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.MarketService;
import io.limeup.flexbets.sport.service.ParticipantService;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import io.limeup.flexbets.sport.utils.ValidationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class ParticipantServiceImpl extends ExternalIdReadServiceImpl<Participant, ParticipantDTO, Long> implements ParticipantService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("team_name", "acronym");

    private final ParticipantRepository participantRepository;

    private final MarketService marketService;

    private final StatRepository statRepository;

    protected ParticipantServiceImpl(ParticipantRepository repository, MarketService marketService, StatRepository statRepository) {
        super(repository);
        this.participantRepository = repository;
        this.marketService = marketService;
        this.statRepository = statRepository;
    }

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

        return PaginationUtils.buildPaginatedResponse(ParticipantMapper.toDTO(stats), count, requestQuery.getPage(), requestQuery.getPageSize());
    }

    @Override
    public ParticipantDTO getParticipantById(Integer participantId, Integer marketId, Integer maxHistoricalDataCount) {
        Participant rawParticipant = participantRepository.findByExternalId(participantId)
                .orElseThrow(() -> new FlexBetsSportNotFoundException(String.format("Participant %s Not Found", participantId)));
        Set<String> statNames = marketService.getStatsByMarket(rawParticipant.getCompetition().getExternalId(), marketId, MarketType.PARTICIPANT);
        List<ParticipantStatRow> participantStatsDetails = statRepository.getParticipantStatsDetails(
                participantId, maxHistoricalDataCount, statNames);
        return ParticipantMapper.toDTO(participantStatsDetails)
                .stream()
                .findFirst()
                .orElseThrow(() -> new FlexBetsSportNotFoundException(String.format("Participant %s Not Found", participantId)));
    }

}
