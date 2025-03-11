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
public class SportWireMockBase extends WireMockBase {

    @Bean
    public CommandLineRunner setupSportWireMock() {
        return args -> {
            WireMock.configureFor(getWireMockHost(), getWireMockPort());
            WireMock.stubFor(get(urlPathMatching("/v1/sports/list"))
                    .willReturn(withCommonHeaders(aResponse())
                            .withTransformerParameter("sportMapping", Map.of(
                                    "1", "Soccer",
                                    "2", "Basketball",
                                    "3", "Tennis",
                                    "4", "Baseball",
                                    "5", "American Football",
                                    "6", "Hockey",
                                    "7", "Cricket",
                                    "8", "Rugby",
                                    "9", "Golf"
                            ))
                            .withTransformers("response-template")
                            .withBody("""
                            {
                              "count": {{randomInt lower=1 upper=5}},
                              "page": 1,
                              "limit": 50,
                              "total_pages": 1,
                              "sports": [
                                {{#each (range 1 (randomInt lower=1 upper=3))}}
                                {{#assign "sportId"}}{{randomInt lower=1 upper=9}}{{/assign}}
                                {
                                  "id": "{{sportId}}",
                                  "name": "{{lookup parameters.sportMapping sportId}}"
                                }
                                {{#unless @last}},{{/unless}}
                                {{/each}}
                              ]
                            }
                            """)));

            WireMock.stubFor(get(urlPathMatching("/v1/sports/\\d+"))
                    .willReturn(withCommonHeaders(aResponse())
                            .withTransformerParameter("sportMapping", Map.of(
                                    "1", "Soccer",
                                    "2", "Basketball",
                                    "3", "Tennis",
                                    "4", "Baseball",
                                    "5", "American Football",
                                    "6", "Hockey",
                                    "7", "Cricket",
                                    "8", "Rugby",
                                    "9", "Golf"
                            ))
                            .withTransformers("response-template")
                            .withBody("""
                            {
                              {{#assign "sportId"}}{{request.pathSegments.[1]}}{{/assign}}
                              "id": "{{sportId}}",
                              "name": "{{lookup parameters.sportMapping sportId}}",
                              "statuses": [
                                {
                                  "status": "Scheduled",
                                  "description": "The event is scheduled to be played."
                                },
                                {
                                  "status": "In Progress",
                                  "description": "The event is currently being played."
                                },
                                {
                                  "status": "Finished",
                                  "description": "The event has ended and final results are available."
                                },
                                {
                                  "status": "Canceled",
                                  "description": "The event has been canceled and will not be played."
                                }
                              ],
                              "result_types": [
                                {
                                  "type": "Regular Time",
                                  "description": "Final result after all regular periods."
                                },
                                {
                                  "type": "First Half",
                                  "description": "Result at the end of the first half."
                                },
                                {
                                  "type": "Overtime",
                                  "description": "Result if the game went into overtime."
                                }
                              ],
                              "event_statistics": [
                                {
                                  "stat_name": "Total Points",
                                  "description": "Total points scored in the event."
                                },
                                {
                                  "stat_name": "Total Fouls",
                                  "description": "Total fouls committed by both teams."
                                },
                                {
                                  "stat_name": "Possession",
                                  "description": "Percentage of time each team had the ball."
                                }
                              ],
                              "team_statistics": [
                                {
                                  "stat_name": "Points",
                                  "description": "Total points scored by the team."
                                },
                                {
                                  "stat_name": "Rebounds",
                                  "description": "Total rebounds collected by the team."
                                },
                                {
                                  "stat_name": "Assists",
                                  "description": "Total assists made by the team."
                                },
                                {
                                  "stat_name": "Turnovers",
                                  "description": "Total number of turnovers committed by the team."
                                }
                              ],
                              "player_statistics": [
                                {
                                  "stat_name": "Points",
                                  "description": "Total points scored by a player."
                                },
                                {
                                  "stat_name": "Assists",
                                  "description": "Number of assists made by a player."
                                },
                                {
                                  "stat_name": "Rebounds",
                                  "description": "Total rebounds collected by a player."
                                },
                                {
                                  "stat_name": "Field Goal Percentage",
                                  "description": "The percentage of successful field goal attempts by a player."
                                }
                              ],
                              "incidents": [
                                {
                                  "incident_type": "3PT Made",
                                  "description": "A successful three-point shot."
                                },
                                {
                                  "incident_type": "Foul Committed",
                                  "description": "A player commits a foul."
                                },
                                {
                                  "incident_type": "Substitution",
                                  "description": "A player is substituted for another."
                                }
                              ]
                            }
                            """)));

            log.info("WireMock Stub Sports with Randomized Data Initialized!");
        };

    }
}
