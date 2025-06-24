package io.limeup.flexbets.sport.config.webclient;

import io.limeup.flexbets.sport.service.statscore.StatScoreAuthService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@RequiredArgsConstructor
public class SportsApiWebClientConfig {

    private static final String STATSCORE_BASE = "https://api.statscore.com/v2";
    private static final Set<String> NO_AUTH = Set.of(
            "/v2/areas", "/v2/sports", "/oauth/token"
    );

    @Bean
    @Qualifier("statScoreWebClient")
    public WebClient statScoreWebClient(StatScoreAuthService auth) {
        return WebClient.builder()
                .baseUrl(STATSCORE_BASE)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().compress(true)))
                .codecs(c -> c.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
                .filter(authFilter(auth))
                .build();
    }

    private ExchangeFilterFunction authFilter(StatScoreAuthService auth) {
        return ExchangeFilterFunction.ofRequestProcessor(req -> {
            String path = req.url().getPath();
            if (NO_AUTH.contains(path)) return Mono.just(req);

            return auth.getToken().map(token -> {
                URI newUri = URI.create(
                        req.url() + (req.url().getQuery() == null ? "?" : "&") + "token=" + token);
                return ClientRequest.from(req).url(newUri).build();
            });
        });
    }


    @Bean
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
