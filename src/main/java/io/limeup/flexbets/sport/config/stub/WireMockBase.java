package io.limeup.flexbets.sport.config.stub;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class WireMockBase {

    @Value("${wiremock.port}")
    private Integer wireMockPort;

    @Value("${wiremock.host}")
    private String wireMockHost;

    protected ResponseDefinitionBuilder withCommonHeaders(ResponseDefinitionBuilder response) {
        return response
                .withHeader("Content-Type", "application/json")
                .withHeader("Access-Control-Allow-Origin", "*")
                .withHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .withHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
}
