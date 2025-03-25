package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;
import io.limeup.flexbets.sport.service.SubParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubParticipantServiceImpl implements SubParticipantService {

    @Override
    public List<SubParticipantDTO> listSubParticipants(Integer competitionId, List<String> positions, List<Integer> participantIds, Integer marketId, RequestQueryDTO requestQuery) {
        return null;
    }

    @Override
    public SubParticipantDTO getSubParticipantById(Integer subParticipantId) {
        return null;
    }
}
