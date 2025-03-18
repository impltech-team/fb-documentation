package io.limeup.flexbets.sport.config.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.limeup.flexbets.sport.config.wiremock.transformer.CustomPaginationTransformer;
import io.limeup.flexbets.sport.config.wiremock.stub.WireMockBase;
import io.limeup.flexbets.sport.config.wiremock.transformer.LoggingTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Profile("mock")
@Slf4j
@Configuration
public class WireMockConfig extends WireMockBase {

    @Bean(destroyMethod = "stop")
    public WireMockServer wireMockServer() {
        WireMockServer wireMockServer = new WireMockServer(options().port(getWireMockPort())
                .extensions(CustomPaginationTransformer.class, LoggingTransformer.class)
                .templatingEnabled(true));
        wireMockServer.start();
        log.info("WireMock server started on port {} with response templating", getWireMockPort());
        return wireMockServer;
    }

    @Bean
    public CustomPaginationTransformer customPaginationTransformer() {
        return new CustomPaginationTransformer();
    }

}
