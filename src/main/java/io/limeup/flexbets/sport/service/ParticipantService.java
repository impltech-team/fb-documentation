package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.ParticipantDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;

import java.util.List;

public interface ParticipantService {
    PaginatedResponse<ParticipantDTO> listParticipants(Integer competitionId, List<Integer> participantIds,
                                                       Integer marketId, Integer maxHistoricalDataCount,
                                                       RequestQueryDTO requestQuery);

    ParticipantDTO getParticipantById(Integer participantId, Integer marketId, Integer maxHistoricalDataCount);
}
