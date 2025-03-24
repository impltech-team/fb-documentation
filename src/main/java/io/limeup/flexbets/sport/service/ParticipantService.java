package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.ParticipantDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ParticipantService {
    PaginatedResponse<ParticipantDTO> listParticipants(Integer competitionId, List<Integer> participantIds, RequestQueryDTO requestQuery);

    ParticipantDTO getParticipantById(Long participantId);
}
