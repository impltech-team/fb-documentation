package io.limeup.flexbets.sport.service.statscore;

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

import java.awt.*;

public interface StatScoreProxyService {

    PaginatedResponse<StatScoreSubParticipantDTO> listEventSubParticipants(Integer eventId, boolean retryEnabled);

    PaginatedResponse<StatScoreSubParticipantDTO> listSquadSubParticipants(Integer participantId, Integer seasonId, boolean retryEnabled);

    PaginatedResponse<StatScoreParticipantDTO> listParticipants(ParticipantQueryParams participantQueryParams, boolean retryEnabled);

    StatScoreParticipantDTO getParticipantById(Integer participantId, boolean retryEnabled);

    PaginatedResponse<StatScoreCompetitionDTO> listEvents(EventQueryParams query, boolean retryEnabled);

    StatScoreCompetitionDTO getEventById(Integer eventId, boolean retryEnabled);

    PaginatedResponse<StatScoreAreaDTO> listAreas(AreaQueryParams query, boolean retryEnabled);

    PaginatedResponse<StatScoreSportLiteDTO> listSports(SportQueryParams query, boolean retryEnabled);

    StatScoreSportDTO getSportById(Integer sportId, boolean retryEnabled);

    PaginatedResponse<StatScoreVenueDTO> listVenues(VenueQueryParams query, boolean retryEnabled);

    StatScoreVenueDTO getVenueById(Integer venueId, boolean retryEnabled);

    PaginatedResponse<StatScoreBracketDTO> listBracketsByStageId(Integer stageId, boolean retryEnabled);

    SingleRootItemPaginatedResponse<StatScoreCompetitionDTO> listGroups(GroupQueryParams query, boolean retryEnabled);

    PaginatedResponse<StatScoreCompetitionDTO> listSeasons(SeasonQueryParams query, boolean retryEnabled);

    StatScoreCompetitionDTO getSeasonById(Integer seasonId, boolean retryEnabled);

    SingleRootItemPaginatedResponse<StatScoreCompetitionDTO> listStages(StageQueryParams query, boolean retryEnabled);

    StatScoreCompetitionDTO getStageById(Integer stageId, boolean retryEnabled);

    PaginatedResponse<StatScoreStandingDTO> listStandings(StandingQueryParams query, boolean retryEnabled);

    StatScoreStandingDTO getStanding(Integer standingId, StandingByIdQueryParams query, boolean retryEnabled);

    PaginatedResponse<StatScoreCompetitionDTO> listCompetitions(CompetitionQueryParams query, boolean retryEnabled);

    StatScoreCompetitionDTO getCompetition(Integer competitionId, boolean retryEnabled);

    PaginatedResponse<StatScoreParticipantDTO> listEventParticipants(Integer externalId, boolean retryEnabled);
}
