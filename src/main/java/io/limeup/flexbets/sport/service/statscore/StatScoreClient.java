package io.limeup.flexbets.sport.service.statscore;

import io.limeup.flexbets.sport.dto.statscore.ListWrapper;
import io.limeup.flexbets.sport.dto.statscore.StatScoreResponse;
import io.limeup.flexbets.sport.dto.statscore.StatsScoreParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.ParticipantQueryParams;
import reactor.core.publisher.Mono;

public interface StatScoreClient {

    Mono<String> getEventSubParticipants(String eventId);

    Mono<StatScoreResponse<ListWrapper<StatsScoreParticipantDTO>>> getParticipants(ParticipantQueryParams params);

    Mono<StatsScoreParticipantDTO> getParticipantById(String participantId);

}
