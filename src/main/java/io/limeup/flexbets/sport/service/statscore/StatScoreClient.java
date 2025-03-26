package io.limeup.flexbets.sport.service.statscore;

import io.limeup.flexbets.sport.dto.statscore.ListWrapper;
import io.limeup.flexbets.sport.dto.statscore.StatScoreAreaDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreBracketDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreResponse;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportLiteDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreVenueDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.AreaQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.ParticipantQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.SportQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.VenueQueryParams;
import io.netty.channel.socket.DatagramChannel;
import reactor.core.publisher.Mono;

import java.util.List;

public interface StatScoreClient {

    Mono<StatScoreResponse<ListWrapper<StatScoreSubParticipantDTO>>> getEventSubParticipants(Integer eventId);

    Mono<StatScoreResponse<ListWrapper<StatScoreParticipantDTO>>> getParticipants(ParticipantQueryParams params);

    Mono<StatScoreParticipantDTO> getParticipantById(Integer participantId);

    Mono<StatScoreResponse<ListWrapper<StatScoreSubParticipantDTO>>> getSquadSubParticipants(Integer participantId, Integer seasonId);

    Mono<StatScoreResponse<ListWrapper<StatScoreCompetitionDTO>>> getEvents(EventQueryParams query);

    Mono<StatScoreCompetitionDTO> getEventById(Integer eventId);

    Mono<StatScoreResponse<ListWrapper<StatScoreAreaDTO>>> getAreas(AreaQueryParams query);

    Mono<StatScoreResponse<ListWrapper<StatScoreSportLiteDTO>>> getSports(SportQueryParams query);

    Mono<StatScoreSportDTO> getSportById(Integer sportId);

    Mono<StatScoreResponse<ListWrapper<StatScoreVenueDTO>>> getVenues(VenueQueryParams query);

    Mono<StatScoreVenueDTO> getVenueById(Integer venueId);

    Mono<StatScoreResponse<ListWrapper<StatScoreBracketDTO>>> getBrackets(Integer stageId);
}
