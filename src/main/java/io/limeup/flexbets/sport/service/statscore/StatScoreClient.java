package io.limeup.flexbets.sport.service.statscore;

import io.limeup.flexbets.sport.dto.statscore.ListWrapper;
import io.limeup.flexbets.sport.dto.statscore.StatScoreAreaDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreBracketDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreResponse;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportLiteDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreStandingDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreParticipantDTO;
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
import reactor.core.publisher.Mono;

public interface StatScoreClient {

    Mono<StatScoreResponse<ListWrapper<StatScoreSubParticipantDTO>>> getEventSubParticipants(Integer eventId);

    Mono<StatScoreResponse<ListWrapper<StatScoreParticipantDTO>>> getParticipants(ParticipantQueryParams params);

    Mono<StatScoreResponse<StatScoreParticipantDTO>> getParticipantById(Integer participantId);

    Mono<StatScoreResponse<ListWrapper<StatScoreSubParticipantDTO>>> getSquadSubParticipants(Integer participantId, Integer seasonId);

    Mono<StatScoreResponse<ListWrapper<StatScoreCompetitionDTO>>> getEvents(EventQueryParams query);

    Mono<StatScoreResponse<StatScoreCompetitionDTO>> getEventById(Integer eventId);

    Mono<StatScoreResponse<ListWrapper<StatScoreAreaDTO>>> getAreas(AreaQueryParams query);

    Mono<StatScoreResponse<ListWrapper<StatScoreSportLiteDTO>>> getSports(SportQueryParams query);

    Mono<StatScoreResponse<StatScoreSportDTO>> getSportById(Integer sportId);

    Mono<StatScoreResponse<ListWrapper<StatScoreVenueDTO>>> getVenues(VenueQueryParams query);

    Mono<StatScoreResponse<StatScoreVenueDTO>> getVenueById(Integer venueId);

    Mono<StatScoreResponse<ListWrapper<StatScoreBracketDTO>>> getBrackets(Integer stageId);

    Mono<StatScoreResponse<StatScoreCompetitionDTO>> getGroups(GroupQueryParams query);

    Mono<StatScoreResponse<ListWrapper<StatScoreCompetitionDTO>>> getSeasons(StatScoreSeasonQueryParams query);

    Mono<StatScoreResponse<StatScoreCompetitionDTO>> getSeasonById(Integer seasonId);

    Mono<StatScoreResponse<StatScoreCompetitionDTO>> getStages(StatScoreStageQueryParams query);

    Mono<StatScoreResponse<StatScoreCompetitionDTO>> getStageById(Integer stageId);

    Mono<StatScoreResponse<ListWrapper<StatScoreStandingDTO>>> getStandings(StandingQueryParams query);

    Mono<StatScoreResponse<StatScoreStandingDTO>> getStandingById(Integer standingId, StandingByIdQueryParams query);
}
