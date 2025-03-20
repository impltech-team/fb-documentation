package io.limeup.flexbets.sport.config.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.limeup.flexbets.sport.config.wiremock.transformer.AreasFilteringTransformer;
import io.limeup.flexbets.sport.config.wiremock.transformer.BaseFilteringTransformer;
import io.limeup.flexbets.sport.config.wiremock.transformer.CompetitionsFilteringTransformer;
import io.limeup.flexbets.sport.config.wiremock.transformer.CustomPaginationTransformer;
import io.limeup.flexbets.sport.config.wiremock.stub.WireMockBase;
import io.limeup.flexbets.sport.config.wiremock.transformer.EventsFilteringTransformer;
import io.limeup.flexbets.sport.config.wiremock.transformer.LoggingTransformer;
import io.limeup.flexbets.sport.config.wiremock.transformer.ParticipantsFilteringTransformer;
import io.limeup.flexbets.sport.config.wiremock.transformer.SportsFilteringTransformer;
import io.limeup.flexbets.sport.config.wiremock.transformer.SubParticipantsFilteringTransformer;
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
                .extensions(CustomPaginationTransformer.class, LoggingTransformer.class, SubParticipantsFilteringTransformer.class
                , ParticipantsFilteringTransformer.class, EventsFilteringTransformer.class, CompetitionsFilteringTransformer.class
                , AreasFilteringTransformer.class, SportsFilteringTransformer.class)
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
