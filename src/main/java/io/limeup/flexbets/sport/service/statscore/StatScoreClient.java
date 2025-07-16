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
import io.limeup.flexbets.sport.dto.statscore.params.AreaQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.CompetitionQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.EventQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.GroupQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.ParticipantQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.SportQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.StandingByIdQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.StandingQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.SeasonQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.StageQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.VenueQueryParams;
import reactor.core.publisher.Mono;

public interface StatScoreClient {

    Mono<StatScoreResponse<ListWrapper<StatScoreSubParticipantDTO>>> getEventSubParticipants(Integer eventId, boolean retryEnabled);

    Mono<StatScoreResponse<ListWrapper<StatScoreParticipantDTO>>> getParticipants(ParticipantQueryParams params, boolean retryEnabled);

    Mono<StatScoreResponse<StatScoreParticipantDTO>> getParticipantById(Integer participantId, boolean retryEnabled);

    Mono<StatScoreResponse<ListWrapper<StatScoreSubParticipantDTO>>> getSquadSubParticipants(Integer participantId, Integer seasonId, boolean retryEnabled);

    Mono<StatScoreResponse<ListWrapper<StatScoreCompetitionDTO>>> getEvents(EventQueryParams query, boolean retryEnabled);

    Mono<StatScoreResponse<StatScoreCompetitionDTO>> getEventById(Integer eventId, boolean retryEnabled);

    Mono<StatScoreResponse<ListWrapper<StatScoreAreaDTO>>> getAreas(AreaQueryParams query, boolean retryEnabled);

    Mono<StatScoreResponse<ListWrapper<StatScoreSportLiteDTO>>> getSports(SportQueryParams query, boolean retryEnabled);

    Mono<StatScoreResponse<StatScoreSportDTO>> getSportById(Integer sportId, boolean retryEnabled);

    Mono<StatScoreResponse<ListWrapper<StatScoreVenueDTO>>> getVenues(VenueQueryParams query, boolean retryEnabled);

    Mono<StatScoreResponse<StatScoreVenueDTO>> getVenueById(Integer venueId, boolean retryEnabled);

    Mono<StatScoreResponse<ListWrapper<StatScoreBracketDTO>>> getBrackets(Integer stageId, boolean retryEnabled);

    Mono<StatScoreResponse<StatScoreCompetitionDTO>> getGroups(GroupQueryParams query, boolean retryEnabled);

    Mono<StatScoreResponse<ListWrapper<StatScoreCompetitionDTO>>> getSeasons(SeasonQueryParams query, boolean retryEnabled);

    Mono<StatScoreResponse<StatScoreCompetitionDTO>> getSeasonById(Integer seasonId, boolean retryEnabled);

    Mono<StatScoreResponse<StatScoreCompetitionDTO>> getStages(StageQueryParams query, boolean retryEnabled);

    Mono<StatScoreResponse<StatScoreCompetitionDTO>> getStageById(Integer stageId, boolean retryEnabled);

    Mono<StatScoreResponse<ListWrapper<StatScoreStandingDTO>>> getStandings(StandingQueryParams query, boolean retryEnabled);

    Mono<StatScoreResponse<StatScoreStandingDTO>> getStandingById(Integer standingId, StandingByIdQueryParams query, boolean retryEnabled);

    Mono<StatScoreResponse<ListWrapper<StatScoreCompetitionDTO>>> getCompetitions(CompetitionQueryParams query, boolean retryEnabled);

    Mono<StatScoreResponse<StatScoreCompetitionDTO>> getCompetitionById(Integer competitionId, boolean retryEnabled);

    Mono<StatScoreResponse<ListWrapper<StatScoreParticipantDTO>>> getEventParticipants(Integer eventId, boolean retryEnabled);
}
