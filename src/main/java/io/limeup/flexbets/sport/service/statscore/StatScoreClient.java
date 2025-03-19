package io.limeup.flexbets.sport.service.statscore;

import reactor.core.publisher.Mono;

public interface StatScoreClient {

    Mono<String> getEventSubParticipants(String eventId);

}
