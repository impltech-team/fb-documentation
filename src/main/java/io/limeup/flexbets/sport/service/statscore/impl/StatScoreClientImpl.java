package io.limeup.flexbets.sport.service.statscore.impl;

import io.limeup.flexbets.sport.service.statscore.StatScoreClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
@Service
public class StatScoreClientImpl implements StatScoreClient {

    private final WebClient webClient;

    @Override
    public Mono<String> getEventSubParticipants(String eventId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/events/{eventId}/sub-participants")
                        .build(eventId))
                .retrieve()
                .bodyToMono(String.class);
    }

}
