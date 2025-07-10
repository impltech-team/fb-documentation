package io.limeup.flexbets.sport.config.webclient;

import io.limeup.flexbets.sport.service.statscore.StatScoreAuthService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.time.Duration;
import java.util.Set;

@Configuration
public class SportsApiWebClientConfig {

    private static final String BASE_URL = "https://api.statscore.com/v2";

    private static final Set<String> NO_AUTH_ENDPOINTS = Set.of(
            "/v2/sports",
            "/v2/oauth",
            "/v2/areas"
    );

    @Bean
    @Qualifier("statScoreWebClient")
    public WebClient webClient(StatScoreAuthService authService) {
        return WebClient.builder()
                .baseUrl(BASE_URL)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().compress(true)
                ))
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
                .filter(authFilter(authService))
                .build();
    }

    private ExchangeFilterFunction authFilter(StatScoreAuthService authService) {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            String path = request.url().getPath();

            if (NO_AUTH_ENDPOINTS.contains(path)) {
                return Mono.just(request);
            }

            return authService.getToken()
                    .map(token -> {
                        String originalUrl = request.url().toString();
                        URI modifiedUrl = URI.create(originalUrl.contains("?") ?
                                originalUrl + "&token=" + token :
                                originalUrl + "?token=" + token);

                        return ClientRequest.from(request)
                                .url(modifiedUrl)
                                .build();
                    });
        });
    }
    @Qualifier("sportsDataWebClient")
    public WebClient sportsDataWebClient(SportsDataProps props) {
        return WebClient.builder()
                .baseUrl(props.getBaseUrl())
                .defaultHeader("Accept", "application/json")
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().responseTimeout(Duration.ofSeconds(10))))
                .build();
    }
}
