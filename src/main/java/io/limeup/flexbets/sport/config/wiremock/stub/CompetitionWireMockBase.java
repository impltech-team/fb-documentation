package io.limeup.flexbets.sport.config.wiremock.stub;

import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@Profile("mock")
@Slf4j
@Configuration
public class CompetitionWireMockBase extends WireMockBase {

    @Bean
    public CommandLineRunner setupCompetitionWireMock() {
        return args -> {
            WireMock.configureFor(getWireMockHost(), getWireMockPort());
            WireMock.stubFor(get(urlPathMatching("/v1/competitions/list"))
                    .willReturn(withCommonHeaders(aResponse())
                            .withTransformerParameter("sportMapping", Map.of(
                                    "1", "Soccer",
                                    "2", "Basketball",
                                    "3", "Tennis",
                                    "4", "Speedway",
                                    "5", "Rugby"
                            ))
                            .withTransformerParameter("areaMapping", Map.of(
                                    "1", "United States",
                                    "2", "Europe",
                                    "3", "South America",
                                    "4", "Asia",
                                    "5", "Africa"
                            ))
                            .withTransformers("response-template", "custom-pagination-transformer", "competitions-filtering-transformer")
                            .withBody("""
                            {
                                "count": {{parameters.count}},
                                "page": {{parameters.page}},
                                "page_size": {{parameters.page_size}},
                                "total_pages": {{parameters.total_pages}},
                                "competitions": [
                                    {{#each (range 1 parameters.current_page_count)}}
                                    {{#assign "competitionId"}}{{randomInt lower=1000 upper=9999}}{{/assign}}
                                    {{#assign "sportId"}}{{randomInt lower=1 upper=5}}{{/assign}}
                                    {{#assign "areaId"}}{{randomInt lower=1 upper=5}}{{/assign}}
                                    {
                                        "id": {{competitionId}},
                                        "name": "{{pickRandom 'NBA' 'UEFA Champions League' 'Premier League' 'FIFA World Cup' 'Grand Slam'}}",
                                        "type": "{{pickRandom 'country_league' 'country_cups' 'international' 'international_clubs' 'friendly' 'individual' 'team' 'single' 'double' 'mixed' 'undefined'}}",
                                        "sport_id": {{sportId}},
                                        "sport_name": "{{lookup parameters.sportMapping sportId}}",
                                        "area_id": {{areaId}},
                                        "area_name": "{{lookup parameters.areaMapping areaId}}",
                                        "status_type": "{{pickRandom 'active' 'upcoming' 'finished'}}",
                                        "gender": "{{pickRandom 'male' 'female' 'mixed'}}"
                                    }
                                    {{#unless @last}},{{/unless}}
                                    {{/each}}
                                ]
                            }
                        """)));
            log.info("WireMock Stub Competition with Randomized Data Initialized!");
        };
    }
}
