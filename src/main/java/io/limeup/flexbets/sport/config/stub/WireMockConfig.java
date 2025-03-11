package io.limeup.flexbets.sport.config.stub;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class WireMockConfig {

    @Value("${wiremock.port}")
    private Integer wireMockPort;

    @Value("${wiremock.host}")
    private String wireMockHost;
}
