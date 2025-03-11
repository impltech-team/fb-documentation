package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.ParticipantDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.service.ParticipantService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService {
    @Override
    public List<ParticipantDTO> listParticipants(Integer competitionId, List<Integer> participantIds, RequestQueryDTO requestQuery) {
        return null;
    }

    @Override
    public ParticipantDTO getParticipantById(Long participantId) {
        return null;
    }
}
