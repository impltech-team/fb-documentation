package io.limeup.flexbets.sport.service.statscore;

import reactor.core.publisher.Mono;

public interface StatScoreAuthService {
    Mono<String> getToken();
}
