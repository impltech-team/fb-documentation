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

    @Value("${trade360.username}")
    private String trade360Username;

    @Value("${trade360.password}")
    private String trade360Password;

    @Value("${trade360.prematch-package-id}")
    private String trade360PrematchPackageId;

    @Value("${trade360.inplay-package-id}")
    private String trade360InPlayPackageId;
}
