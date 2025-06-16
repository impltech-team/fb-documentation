package io.limeup.flexbets.sport.service.statscore.impl;

import io.limeup.flexbets.sport.config.FlexBetsSportConfiguration;
import io.limeup.flexbets.sport.dto.statscore.AuthResponse;
import io.limeup.flexbets.sport.service.statscore.StatScoreAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class StatScoreAuthServiceImpl implements StatScoreAuthService {

    @Qualifier("statScoreWebClient")
    private final ObjectProvider<WebClient> webClient;
    private final FlexBetsSportConfiguration flexBetsSportConfiguration;

    private final AtomicReference<String> token = new AtomicReference<>(null);
    private final AtomicReference<Long> tokenExpiration = new AtomicReference<>(0L);


    public StatScoreAuthServiceImpl(@Qualifier("statScoreWebClient") ObjectProvider<WebClient> webClient, FlexBetsSportConfiguration flexBetsSportConfiguration) {
        this.webClient = webClient;
        this.flexBetsSportConfiguration = flexBetsSportConfiguration;
    }

    public Mono<String> getToken() {
        String authUrl = "/oauth?client_id=" + flexBetsSportConfiguration.getStatScoreClient()
                + "&secret_key=" + flexBetsSportConfiguration.getStatScoreSecret();
        long now = Instant.now().getEpochSecond();

        if (token.get() != null && now < tokenExpiration.get()) {
            return Mono.just(token.get());
        }

        return webClient.getObject().get()
                .uri(authUrl)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .map(authResponse -> {
                    String newToken = authResponse.getApi().getData().getToken();
                    long expiration = authResponse.getApi().getData().getTokenExpiration();

                    token.set(newToken);
                    tokenExpiration.set(expiration);

                    return newToken;
                });
    }
}
