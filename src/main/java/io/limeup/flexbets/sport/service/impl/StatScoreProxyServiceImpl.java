package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.SingleRootItemPaginatedResponse;
import io.limeup.flexbets.sport.dto.statscore.StatScoreAreaDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreBracketDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportLiteDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreStandingDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreVenueDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.AreaQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.GroupQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.ParticipantQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.SportQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StandingByIdQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StandingQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StatScoreSeasonQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StatScoreStageQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.VenueQueryParams;
import io.limeup.flexbets.sport.service.StatScoreProxyService;
import io.limeup.flexbets.sport.service.statscore.StatScoreClient;
import io.limeup.flexbets.sport.utils.StatScorePaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
        return Objects.requireNonNull(statScoreClient.getParticipantById(participantId).block()).getApi().getData();
    }

    @Override
    public PaginatedResponse<StatScoreCompetitionDTO> listEvents(EventQueryParams query) {
        return StatScorePaginationUtils.buildPaginatedResponse(
                statScoreClient.getEvents(query).block(), query.getPage(), query.getLimit());
    }

    @Override
    public StatScoreCompetitionDTO getEventById(Integer eventId) {
        return Objects.requireNonNull(statScoreClient.getEventById(eventId).block()).getApi().getData();
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
        return Objects.requireNonNull(statScoreClient.getSportById(sportId).block()).getApi().getData();
    }

    @Override
    public PaginatedResponse<StatScoreVenueDTO> listVenues(VenueQueryParams query) {
        return StatScorePaginationUtils.buildPaginatedResponse(
                statScoreClient.getVenues(query).block(), query.getPage(), query.getLimit());

    }

    @Override
    public StatScoreVenueDTO getVenueById(Integer venueId) {
        return Objects.requireNonNull(statScoreClient.getVenueById(venueId).block()).getApi().getData();
    }

    @Override
    public PaginatedResponse<StatScoreBracketDTO> listBracketsByStageId(Integer stageId) {
        return StatScorePaginationUtils.buildPaginatedResponse(
                statScoreClient.getBrackets(stageId).block(), null, null);
    }

    @Override
    public SingleRootItemPaginatedResponse<StatScoreCompetitionDTO> listGroups(GroupQueryParams query) {
        return StatScorePaginationUtils.buildPaginatedResponseForSingleRoot(
                statScoreClient.getGroups(query).block(), query.getPage(), query.getLimit(), "competition");
    }

    @Override
    public PaginatedResponse<StatScoreCompetitionDTO> listSeasons(StatScoreSeasonQueryParams query) {
        return StatScorePaginationUtils.buildPaginatedResponse(
                statScoreClient.getSeasons(query).block(), query.getPage(), query.getLimit());
    }

    @Override
    public StatScoreCompetitionDTO getSeasonById(Integer seasonId) {
        return Objects.requireNonNull(statScoreClient.getSeasonById(seasonId).block()).getApi().getData();
    }

    @Override
    public SingleRootItemPaginatedResponse<StatScoreCompetitionDTO> listStages(StatScoreStageQueryParams query) {
        return StatScorePaginationUtils.buildPaginatedResponseForSingleRoot(
                statScoreClient.getStages(query).block(), query.getPage(), query.getLimit(), "competition");
    }

    @Override
    public StatScoreCompetitionDTO getStageById(Integer stageId) {
        return Objects.requireNonNull(statScoreClient.getStageById(stageId).block()).getApi().getData();
    }

    @Override
    public PaginatedResponse<StatScoreStandingDTO> listStandings(StandingQueryParams query) {
        return StatScorePaginationUtils.buildPaginatedResponse(
                statScoreClient.getStandings(query).block(), query.getPage(), query.getLimit());
    }

    @Override
    public StatScoreStandingDTO getStanding(Integer standingId, StandingByIdQueryParams query) {
        return Objects.requireNonNull(statScoreClient.getStandingById(standingId, query).block()).getApi().getData();
    }
}
