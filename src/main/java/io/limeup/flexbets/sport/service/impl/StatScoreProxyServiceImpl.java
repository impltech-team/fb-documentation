package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.statscore.ListWrapper;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreResponse;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.ParticipantQueryParams;
import io.limeup.flexbets.sport.service.StatScoreProxyService;
import io.limeup.flexbets.sport.service.statscore.StatScoreClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StatScoreProxyServiceImpl implements StatScoreProxyService {

    private final StatScoreClient statScoreClient;

    @Override
    public PaginatedResponse<StatScoreSubParticipantDTO> listEventSubParticipants(Integer eventId) {
        StatScoreResponse<ListWrapper<StatScoreSubParticipantDTO>> result = statScoreClient.getEventSubParticipants(eventId).block();
        return PaginatedResponse.<StatScoreSubParticipantDTO>builder()
                .count(result.getApi().getMethod().getTotal_items())
                .items(result.getApi().getData().getItems())
                .build();
    }

    @Override
    public PaginatedResponse<StatScoreSubParticipantDTO> listSquadSubParticipants(Integer participantId, Integer seasonId) {
        StatScoreResponse<ListWrapper<StatScoreSubParticipantDTO>> result = statScoreClient.getSquadSubParticipants(participantId, seasonId).block();
        return PaginatedResponse.<StatScoreSubParticipantDTO>builder()
                .count(result.getApi().getMethod().getTotal_items())
                .items(result.getApi().getData().getItems())
                .build();
    }

    @Override
    public PaginatedResponse<StatScoreParticipantDTO> listParticipants(ParticipantQueryParams query) {
        StatScoreResponse<ListWrapper<StatScoreParticipantDTO>> result = statScoreClient.getParticipants(query).block();
        return PaginatedResponse.<StatScoreParticipantDTO>builder()
                .count(result.getApi().getMethod().getTotal_items())
                .page(query.getPage())
                .pageSize(query.getLimit())
                .totalPages((int) Math.ceil((double) result.getApi().getMethod().getTotal_items() / query.getLimit()))
                .items(result.getApi().getData().getItems())
                .build();
    }

    @Override
    public StatScoreParticipantDTO getParticipantById(Integer participantId) {
        return statScoreClient.getParticipantById(participantId).block();
    }

    @Override
    public PaginatedResponse<StatScoreCompetitionDTO> listEvents(EventQueryParams query) {
        StatScoreResponse<ListWrapper<StatScoreCompetitionDTO>> result = statScoreClient.getEvents(query).block();
        return PaginatedResponse.<StatScoreCompetitionDTO>builder()
                .count(result.getApi().getMethod().getTotal_items())
                .page(query.getPage())
                .pageSize(query.getLimit())
                .totalPages((int) Math.ceil((double) result.getApi().getMethod().getTotal_items() / query.getLimit()))
                .items(result.getApi().getData().getItems())
                .build();
    }

    @Override
    public StatScoreCompetitionDTO getEventById(Integer eventId) {
        return statScoreClient.getEventById(eventId).block();
    }
}
