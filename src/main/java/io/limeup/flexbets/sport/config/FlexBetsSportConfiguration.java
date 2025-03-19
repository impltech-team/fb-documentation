package io.limeup.flexbets.sport.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class FlexBetsSportConfiguration {

    @Value("${wiremock.host}")
    private String wireMockHost;

    @Value("${wiremock.port}")
    private String wireMockPort;

    //Will be moved to AWS secret manager
    @Value("${statscore.client}")
    private Integer statScoreClient;

    @Value("${statscore.secret}")
    private String statScoreSecret;
}
