package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.ParticipantQueryParams;

public interface StatScoreProxyService {

    PaginatedResponse<StatScoreSubParticipantDTO> listEventSubParticipants(Integer eventId);

    PaginatedResponse<StatScoreSubParticipantDTO> listSquadSubParticipants(Integer participantId, Integer seasonId);

    PaginatedResponse<StatScoreParticipantDTO> listParticipants(ParticipantQueryParams participantQueryParams);

    StatScoreParticipantDTO getParticipantById(Integer participantId);

    PaginatedResponse<StatScoreCompetitionDTO> listEvents(EventQueryParams query);

    StatScoreCompetitionDTO getEventById(Integer eventId);
}
