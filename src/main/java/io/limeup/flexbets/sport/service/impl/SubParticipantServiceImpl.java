package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;
import io.limeup.flexbets.sport.mapper.SubParticipantMapper;
import io.limeup.flexbets.sport.model.SubParticipant;
import io.limeup.flexbets.sport.repository.StatRepository;
import io.limeup.flexbets.sport.repository.SubParticipantRepository;
import io.limeup.flexbets.sport.repository.projection.SubParticipantStatRow;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.SubParticipantService;
import io.limeup.flexbets.sport.service.statscore.StatScoreDataService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SubParticipantServiceImpl extends ExternalIdReadServiceImpl<SubParticipant, SubParticipantDTO, Long> implements SubParticipantService {

    private final StatScoreDataService statScoreDataService;
    private final StatScoreProxyService statScoreService;
    private final StatRepository statRepository;

    protected SubParticipantServiceImpl(SubParticipantRepository repository, StatScoreDataService statScoreDataService,
                                        StatScoreProxyService statScoreService, StatRepository statRepository) {
        super(repository);
        this.statScoreDataService = statScoreDataService;
        this.statScoreService = statScoreService;
        this.statRepository = statRepository;
    }

    @Override
    public List<SubParticipantDTO> listSubParticipants(Integer competitionId, List<String> positions,
                                                       List<Integer> participantIds, Integer marketId, RequestQueryDTO requestQuery) {
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
                List.of("Assists", "Rebounds total", "Points", "Blocks", "Steals", "Turnovers")
        );

        return SubParticipantMapper.toDTO(stats);
    }

    @Override
    public SubParticipantDTO getSubParticipantById(Integer subParticipantId) {
        return null;
    }

}
