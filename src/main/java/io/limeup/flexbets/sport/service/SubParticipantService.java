package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;
import io.limeup.flexbets.sport.model.SubParticipant;

import java.util.List;

public interface SubParticipantService extends ExternalIdReadService<SubParticipant, SubParticipantDTO, Long> {

    PaginatedResponse<SubParticipantDTO> listSubParticipants(Integer competitionId, List<String> positions,
                                                             List<Integer> participantIds, Integer marketId,
                                                             RequestQueryDTO requestQuery);

    SubParticipantDTO getSubParticipantById(Integer subParticipantId, Integer marketId);

}
