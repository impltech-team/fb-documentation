package io.limeup.flexbets.sport.service.statscore.impl;

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
import io.limeup.flexbets.sport.dto.statscore.prams.CompetitionQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.GroupQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.ParticipantQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.SportQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StandingByIdQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StandingQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.SeasonQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StageQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.VenueQueryParams;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import io.limeup.flexbets.sport.service.statscore.StatScoreClient;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class StatScoreProxyServiceImpl implements StatScoreProxyService {

    private final StatScoreClient statScoreClient;

    @Override
    public PaginatedResponse<StatScoreSubParticipantDTO> listEventSubParticipants(Integer eventId, boolean retryEnabled) {
        return PaginationUtils.buildStatScorePaginatedResponse(
                statScoreClient.getEventSubParticipants(eventId, retryEnabled).block(), null, null);
    }

    @Override
    public PaginatedResponse<StatScoreSubParticipantDTO> listSquadSubParticipants(Integer participantId, Integer seasonId, boolean retryEnabled) {
        return PaginationUtils.buildStatScorePaginatedResponse(
                statScoreClient.getSquadSubParticipants(participantId, seasonId, retryEnabled).block(), null, null);
    }

    @Override
    public PaginatedResponse<StatScoreParticipantDTO> listParticipants(ParticipantQueryParams query, boolean retryEnabled) {
        return PaginationUtils.buildStatScorePaginatedResponse(
                statScoreClient.getParticipants(query, retryEnabled).block(), query.getPage(), query.getLimit());
    }

    @Override
    public StatScoreParticipantDTO getParticipantById(Integer participantId, boolean retryEnabled) {
        return Objects.requireNonNull(statScoreClient.getParticipantById(participantId, retryEnabled).block()).getApi().getData();
    }

    @Override
    public PaginatedResponse<StatScoreCompetitionDTO> listEvents(EventQueryParams query, boolean retryEnabled) {
        return PaginationUtils.buildStatScorePaginatedResponse(
                statScoreClient.getEvents(query, retryEnabled).block(), query.getPage(), query.getLimit());
    }

    @Override
    public StatScoreCompetitionDTO getEventById(Integer eventId, boolean retryEnabled) {
        return Objects.requireNonNull(statScoreClient.getEventById(eventId, retryEnabled).block()).getApi().getData();
    }

    @Override
    public PaginatedResponse<StatScoreAreaDTO> listAreas(AreaQueryParams query, boolean retryEnabled) {
        return PaginationUtils.buildStatScorePaginatedResponse(
                statScoreClient.getAreas(query, retryEnabled).block(), query.getPage(), query.getLimit());
    }

    @Override
    public PaginatedResponse<StatScoreSportLiteDTO> listSports(SportQueryParams query, boolean retryEnabled) {
        return PaginationUtils.buildStatScorePaginatedResponse(
                statScoreClient.getSports(query, retryEnabled).block(), query.getPage(), query.getLimit());
    }

    @Override
    public StatScoreSportDTO getSportById(Integer sportId, boolean retryEnabled) {
        return Objects.requireNonNull(statScoreClient.getSportById(sportId, retryEnabled).block()).getApi().getData();
    }

    @Override
    public PaginatedResponse<StatScoreVenueDTO> listVenues(VenueQueryParams query, boolean retryEnabled) {
        return PaginationUtils.buildStatScorePaginatedResponse(
                statScoreClient.getVenues(query, retryEnabled).block(), query.getPage(), query.getLimit());

    }

    @Override
    public StatScoreVenueDTO getVenueById(Integer venueId, boolean retryEnabled) {
        return Objects.requireNonNull(statScoreClient.getVenueById(venueId, retryEnabled).block()).getApi().getData();
    }

    @Override
    public PaginatedResponse<StatScoreBracketDTO> listBracketsByStageId(Integer stageId, boolean retryEnabled) {
        return PaginationUtils.buildStatScorePaginatedResponse(
                statScoreClient.getBrackets(stageId, retryEnabled).block(), null, null);
    }

    @Override
    public SingleRootItemPaginatedResponse<StatScoreCompetitionDTO> listGroups(GroupQueryParams query, boolean retryEnabled) {
        return PaginationUtils.buildStatScorePaginatedResponseForSingleRoot(
                statScoreClient.getGroups(query, retryEnabled).block(), query.getPage(), query.getLimit(), "competition");
    }

    @Override
    public PaginatedResponse<StatScoreCompetitionDTO> listSeasons(SeasonQueryParams query, boolean retryEnabled) {
        return PaginationUtils.buildStatScorePaginatedResponse(
                statScoreClient.getSeasons(query, retryEnabled).block(), query.getPage(), query.getLimit());
    }

    @Override
    public StatScoreCompetitionDTO getSeasonById(Integer seasonId, boolean retryEnabled) {
        return Objects.requireNonNull(statScoreClient.getSeasonById(seasonId, retryEnabled).block()).getApi().getData();
    }

    @Override
    public SingleRootItemPaginatedResponse<StatScoreCompetitionDTO> listStages(StageQueryParams query, boolean retryEnabled) {
        return PaginationUtils.buildStatScorePaginatedResponseForSingleRoot(
                statScoreClient.getStages(query, retryEnabled).block(), query.getPage(), query.getLimit(), "competition");
    }

    @Override
    public StatScoreCompetitionDTO getStageById(Integer stageId, boolean retryEnabled) {
        return Objects.requireNonNull(statScoreClient.getStageById(stageId, retryEnabled).block()).getApi().getData();
    }

    @Override
    public PaginatedResponse<StatScoreStandingDTO> listStandings(StandingQueryParams query, boolean retryEnabled) {
        return PaginationUtils.buildStatScorePaginatedResponse(
                statScoreClient.getStandings(query, retryEnabled).block(), query.getPage(), query.getLimit());
    }

    @Override
    public StatScoreStandingDTO getStanding(Integer standingId, StandingByIdQueryParams query, boolean retryEnabled) {
        return Objects.requireNonNull(statScoreClient.getStandingById(standingId, query, retryEnabled).block()).getApi().getData();
    }

    @Override
    public PaginatedResponse<StatScoreCompetitionDTO> listCompetitions(CompetitionQueryParams query, boolean retryEnabled) {
        return PaginationUtils.buildStatScorePaginatedResponse(
                statScoreClient.getCompetitions(query, retryEnabled).block(), query.getPage(), query.getLimit());
    }

    @Override
    public StatScoreCompetitionDTO getCompetition(Integer competitionId, boolean retryEnabled) {
        return Objects.requireNonNull(statScoreClient.getCompetitionById(competitionId, retryEnabled).block()).getApi().getData();
    }

    @Override
    public PaginatedResponse<StatScoreParticipantDTO> listEventParticipants(Integer eventId, boolean retryEnabled) {
        return PaginationUtils.buildStatScorePaginatedResponse(
                statScoreClient.getEventParticipants(eventId, retryEnabled).block(), null, null);
    }
}
