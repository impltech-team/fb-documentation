package io.limeup.flexbets.sport.config.stub;

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
                                    "10", "United States",
                                    "20", "Europe",
                                    "30", "South America",
                                    "40", "Asia",
                                    "50", "Africa"
                            ))
                            .withTransformers("response-template")
                            .withBody("""
                            {
                                "count": {{randomInt lower=1 upper=5}},
                                "page": 1,
                                "limit": 50,
                                "total_pages": 1,
                                "competitions": [
                                    {{#each (range 1 (randomInt lower=1 upper=3))}}
                                    {{#assign "competitionId"}}{{randomInt lower=1000 upper=9999}}{{/assign}}
                                    {{#assign "sportId"}}{{randomInt lower=1 upper=5}}{{/assign}}
                                    {{#assign "areaId"}}{{randomInt lower=10 upper=50 step=10}}{{/assign}}
                                    {
                                        "id": {{competitionId}},
                                        "name": "{{pickRandom 'NBA' 'UEFA Champions League' 'Premier League' 'FIFA World Cup' 'Grand Slam'}}",
                                        "type": "{{pickRandom 'country_league' 'country_cups' 'international' 'international_clubs' 'friendly' 'individual' 'team' 'single' 'double' 'mixed' 'undefined'}}",
                                        "sport_id": {{sportId}},
                                        "sport_name": "{{lookup parameters.sportMapping sportId}}",
                                        "area_id": {{areaId}},
                                        "area_name": "{{lookup parameters.areaMapping areaId}}",
                                        "status_type": "{{pickRandom 'active' 'upcoming' 'finished'}}",
                                        "gender": "{{pickRandom 'male' 'female' 'mixed'}}",
                                        "start_date": "{{now offset='-30 days' format='yyyy-MM-dd'}}",
                                        "end_date": "{{now offset='+120 days' format='yyyy-MM-dd'}}"
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
