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

    @Value("${statscore.client}")
    private Integer statScoreClient;

    @Value("${statscore.secret}")
    private String statScoreSecret;

    @Value("${convert.imperial}")
    private boolean convertToImperial;
}
