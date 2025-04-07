package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;
import io.limeup.flexbets.sport.model.SubParticipant;
import io.limeup.flexbets.sport.repository.SubParticipantRepository;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.SubParticipantService;
import io.limeup.flexbets.sport.service.statscore.StatScoreDataService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubParticipantServiceImpl extends ExternalIdReadServiceImpl<SubParticipant, SubParticipantDTO, Long> implements SubParticipantService {

    private final StatScoreDataService statScoreDataService;
    private final StatScoreProxyService statScoreService;

    protected SubParticipantServiceImpl(SubParticipantRepository repository, StatScoreDataService statScoreDataService,
                                        StatScoreProxyService statScoreService) {
        super(repository);
        this.statScoreDataService = statScoreDataService;
        this.statScoreService = statScoreService;
    }

    @Override
    public List<SubParticipantDTO> listSubParticipants(Integer competitionId, List<String> positions,
                                                       List<Integer> participantIds, Integer marketId, RequestQueryDTO requestQuery) {
        return null;
    }

    @Override
    public SubParticipantDTO getSubParticipantById(Integer subParticipantId) {
        return null;
    }

}
