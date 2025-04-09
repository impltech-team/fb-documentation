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
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class ParticipantServiceImpl extends ExternalIdReadServiceImpl<Participant, ParticipantDTO, Long> implements ParticipantService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("team_name", "acronym");

    private final ParticipantRepository repository;

    private final MarketService marketService;

    private final StatRepository statRepository;

    protected ParticipantServiceImpl(ParticipantRepository repository, MarketService marketService, StatRepository statRepository) {
        super(repository);
        this.repository = repository;
        this.marketService = marketService;
        this.statRepository = statRepository;
    }

    @Override
    public PaginatedResponse<ParticipantDTO> listParticipants(Integer competitionId, List<Integer> participantIds,
                                                              Integer marketId, RequestQueryDTO requestQuery) {
        validateRequest(requestQuery);

        Set<String> statNames = Set.of("3pt jump shot attempts", "Turnovers", "Points", "Blocks");
                //marketService.getStatsByMarket(competitionId, marketId, MarketType.PARTICIPANT);
        List<ParticipantStatRow> stats = statRepository.listParticipantStats(
                competitionId,
                participantIds == null ? Collections.emptyList() : participantIds,
                marketId,
                requestQuery.getFilter(),
                requestQuery.getSortBy(),
                requestQuery.getSortOrder(),
                (requestQuery.getPage() - 1) * requestQuery.getPageSize(),
                requestQuery.getPageSize(),
                statNames
        );
        Integer count = repository.countParticipants(competitionId,
                participantIds == null ? Collections.emptyList() : participantIds,
                requestQuery.getFilter());

        return PaginationUtils.buildPaginatedResponse(ParticipantMapper.toDTO(stats), count, requestQuery.getPage(), requestQuery.getPageSize());
    }

    @Override
    public ParticipantDTO getParticipantById(Integer participantId, Integer marketId) {
        Participant rawParticipant = repository.findByExternalId(participantId)
                .orElseThrow(() -> new FlexBetsSportNotFoundException(String.format("Participant %s Not Found", participantId)));
        Set<String> statNames = Set.of("3pt jump shot attempts", "Turnovers", "Points", "Blocks");
                //marketService.getStatsByMarket(rawParticipant.getCompetition().getExternalId(), marketId, MarketType.PARTICIPANT);
        List<ParticipantStatRow> participantStatsDetails = statRepository.getParticipantStatsDetails(
                participantId, statNames);
        return ParticipantMapper.toDTO(participantStatsDetails)
                .stream()
                .findFirst()
                .orElseThrow(() -> new FlexBetsSportNotFoundException(String.format("Participant %s Not Found", participantId)));
    }

    private void validateRequest(RequestQueryDTO requestQuery) {
        if (StringUtils.isNotBlank(requestQuery.getSortBy()) && !SUPPORTED_SORT_FIELDS.contains(requestQuery.getSortBy())) {
            throw new ValidationException(String.format("Invalid sortBy: %s. Available options: %s", requestQuery.getSortBy(), SUPPORTED_SORT_FIELDS));
        }
    }
}
