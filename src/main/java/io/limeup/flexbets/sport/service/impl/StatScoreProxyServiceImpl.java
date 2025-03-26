package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.statscore.StatScoreAreaDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreBracketDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportLiteDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreVenueDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.AreaQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.ParticipantQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.SportQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.VenueQueryParams;
import io.limeup.flexbets.sport.service.StatScoreProxyService;
import io.limeup.flexbets.sport.service.statscore.StatScoreClient;
import io.limeup.flexbets.sport.utils.StatScorePaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StatScoreProxyServiceImpl implements StatScoreProxyService {

    private final StatScoreClient statScoreClient;

    @Override
    public PaginatedResponse<StatScoreSubParticipantDTO> listEventSubParticipants(Integer eventId) {
        return StatScorePaginationUtils.buildPaginatedResponse(
                statScoreClient.getEventSubParticipants(eventId).block(), null, null);
    }

    @Override
    public PaginatedResponse<StatScoreSubParticipantDTO> listSquadSubParticipants(Integer participantId, Integer seasonId) {
        return StatScorePaginationUtils.buildPaginatedResponse(
                statScoreClient.getSquadSubParticipants(participantId, seasonId).block(), null, null);
    }

    @Override
    public PaginatedResponse<StatScoreParticipantDTO> listParticipants(ParticipantQueryParams query) {
        return StatScorePaginationUtils.buildPaginatedResponse(
                statScoreClient.getParticipants(query).block(), query.getPage(), query.getLimit());
    }

    @Override
    public StatScoreParticipantDTO getParticipantById(Integer participantId) {
        return statScoreClient.getParticipantById(participantId).block();
    }

    @Override
    public PaginatedResponse<StatScoreCompetitionDTO> listEvents(EventQueryParams query) {
        return StatScorePaginationUtils.buildPaginatedResponse(
                statScoreClient.getEvents(query).block(), query.getPage(), query.getLimit());
    }

    @Override
    public StatScoreCompetitionDTO getEventById(Integer eventId) {
        return statScoreClient.getEventById(eventId).block();
    }

    @Override
    public PaginatedResponse<StatScoreAreaDTO> listAreas(AreaQueryParams query) {
        return StatScorePaginationUtils.buildPaginatedResponse(
                statScoreClient.getAreas(query).block(), query.getPage(), query.getLimit());
    }

    @Override
    public PaginatedResponse<StatScoreSportLiteDTO> listSports(SportQueryParams query) {
        return StatScorePaginationUtils.buildPaginatedResponse(
                statScoreClient.getSports(query).block(), query.getPage(), query.getLimit());
    }

    @Override
    public StatScoreSportDTO getSportById(Integer sportId) {
        return statScoreClient.getSportById(sportId).block();
    }

    @Override
    public PaginatedResponse<StatScoreVenueDTO> listVenues(VenueQueryParams query) {
        return StatScorePaginationUtils.buildPaginatedResponse(
                statScoreClient.getVenues(query).block(), query.getPage(), query.getLimit());

    }

    @Override
    public StatScoreVenueDTO getVenueById(Integer venueId) {
        return statScoreClient.getVenueById(venueId).block();
    }

    @Override
    public PaginatedResponse<StatScoreBracketDTO> listBracketsByStageId(Integer stageId) {
        return StatScorePaginationUtils.buildPaginatedResponse(
                statScoreClient.getBrackets(stageId).block(), null, null);
    }

}
