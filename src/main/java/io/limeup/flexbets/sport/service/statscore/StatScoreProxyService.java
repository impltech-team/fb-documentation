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

    PaginatedResponse<StatScoreSubParticipantDTO> listEventSubParticipants(Integer eventId);

    PaginatedResponse<StatScoreSubParticipantDTO> listSquadSubParticipants(Integer participantId, Integer seasonId);

    PaginatedResponse<StatScoreParticipantDTO> listParticipants(ParticipantQueryParams participantQueryParams);

    StatScoreParticipantDTO getParticipantById(Integer participantId);

    PaginatedResponse<StatScoreCompetitionDTO> listEvents(EventQueryParams query);

    StatScoreCompetitionDTO getEventById(Integer eventId);

    PaginatedResponse<StatScoreAreaDTO> listAreas(AreaQueryParams query);

    PaginatedResponse<StatScoreSportLiteDTO> listSports(SportQueryParams query);

    StatScoreSportDTO getSportById(Integer sportId);

    PaginatedResponse<StatScoreVenueDTO> listVenues(VenueQueryParams query);

    StatScoreVenueDTO getVenueById(Integer venueId);

    PaginatedResponse<StatScoreBracketDTO> listBracketsByStageId(Integer stageId);

    SingleRootItemPaginatedResponse<StatScoreCompetitionDTO> listGroups(GroupQueryParams query);

    PaginatedResponse<StatScoreCompetitionDTO> listSeasons(SeasonQueryParams query);

    StatScoreCompetitionDTO getSeasonById(Integer seasonId);

    SingleRootItemPaginatedResponse<StatScoreCompetitionDTO> listStages(StageQueryParams query);

    StatScoreCompetitionDTO getStageById(Integer stageId);

    PaginatedResponse<StatScoreStandingDTO> listStandings(StandingQueryParams query);

    StatScoreStandingDTO getStanding(Integer standingId, StandingByIdQueryParams query);

    PaginatedResponse<StatScoreCompetitionDTO> listCompetitions(CompetitionQueryParams query);

    StatScoreCompetitionDTO getCompetition(Integer competitionId);

    PaginatedResponse<StatScoreParticipantDTO> listEventParticipants(Integer externalId);
}
