package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;

import java.util.List;

public interface SubParticipantService {

    List<SubParticipantDTO> listSubParticipants(Integer competitionId, List<String> positions, List<Integer> participantIds, Integer marketId, RequestQueryDTO requestQuery);

    SubParticipantDTO getSubParticipantById(Integer subParticipantId);

}
