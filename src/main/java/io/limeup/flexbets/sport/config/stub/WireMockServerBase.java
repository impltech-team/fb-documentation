package io.limeup.flexbets.sport.config.stub;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Profile("mock")
@Slf4j
@Configuration
public class WireMockServerBase extends WireMockBase {

    @Bean(destroyMethod = "stop")
    public WireMockServer wireMockServer() {
        WireMockServer wireMockServer = new WireMockServer(options().port(getWireMockPort()).templatingEnabled(true));
        wireMockServer.start();
        log.info("WireMock server started on port {} with response templating", getWireMockPort());
        return wireMockServer;
    }

}
