package io.limeup.flexbets.sport.config.wiremock.stub;

import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@Profile("mock")
@Slf4j
@Configuration
public class ParticipantWireMockBase extends WireMockBase {

    @Bean
    public CommandLineRunner setupParticipantWireMock() {
        return args -> {
            WireMock.configureFor(getWireMockHost(), getWireMockPort());
            WireMock.stubFor(get(urlPathMatching("/v1/participants/list"))
                    .withQueryParam("competition_id", matching("\\d+"))
                    .willReturn(withCommonHeaders(aResponse())
                            .withTransformerParameter("participantMapping", Map.of(
                                    "1", "Los Angeles Lakers",
                                    "2", "Golden State Warriors",
                                    "3", "Brooklyn Nets",
                                    "4", "Milwaukee Bucks",
                                    "5", "Dallas Mavericks"
                            ))
                            .withTransformerParameter("marketMapping", Map.of(
                                    "1", "Match Winner",
                                    "2", "Under/Over Fouls - Away Team",
                                    "3", "Under/Over Fouls - Home Team",
                                    "4", "Under/Over Home Team Assists",
                                    "5", "Under/Over Away Team Assists"
                            ))
                            .withTransformers("response-template", "custom-pagination-transformer", "participants-filtering-transformer")
                            .withBody("""
                                        {
                                            "count": {{parameters.count}},
                                            "page": {{parameters.page}},
                                            "page_size": {{parameters.page_size}},
                                            "total_pages": {{parameters.total_pages}},
                                            "participants": [
                                                {{#each (range 1 parameters.current_page_count)}}
                                                {{#assign "participantId"}}{{randomInt lower=1 upper=5}}{{/assign}}
                                                {
                                                    "id": {{participantId}},
                                                    "team_name": "{{lookup parameters.participantMapping participantId}}",
                                                    "acronym": "{{pickRandom 'LAL' 'GSW' 'BKN' 'MIL' 'DAL'}}",
                                                    "competition": "NBA",
                                                    "competition_id": {{request.query.competition_id}},
                                                    "next_event": {
                                                        "event_id": {{randomInt lower=1000 upper=9999}},
                                                        "event_name": "{{pickRandom 'Lakers vs Warriors' 'Nets vs Bucks' 'Mavs vs Clippers'}}",
                                                        "event_date": "{{now offset='+3 days' format='yyyy-MM-dd HH:mm:ss'}}",
                                                        "opponent": "{{pickRandom 'GSW' 'LAL' 'MIA' 'BOS'}}"
                                                    },
                                                    "historical_stats": [
                                                        {
                                                            "stat_name": "Points",
                                                            "average": {{randomInt lower=90 upper=120}},
                                                            "count": 5,
                                                            "max_value": {{randomInt lower=110 upper=130}},
                                                            "min_value": {{randomInt lower=80 upper=100}},
                                                            "event_statistics": [
                                                                {{#each (range 1 5)}}
                                                                {
                                                                    "event_id": {{randomInt lower=1000 upper=9999}},
                                                                    "event_name": "{{pickRandom 'Lakers vs Warriors' 'Nets vs Bucks' 'Mavs vs Clippers'}}",
                                                                    "event_date": "{{now offset='+3 days' format='yyyy-MM-dd HH:mm:ss'}}",
                                                                    "value": {{randomInt lower=80 upper=130}},
                                                                    "opponent": "{{pickRandom 'GSW' 'LAL' 'MIA' 'BOS'}}"
                                                                }
                                                                {{#unless @last}},{{/unless}}
                                                                {{/each}}
                                                            ]
                                                        }
                                                    ],
                                                    "odds": [
                                                        {{#each (range 1 (randomInt lower=1 upper=3))}}
                                                        {{#assign "marketId"}}
                                                            {{#if request.query.market_id}}
                                                                {{request.query.market_id}}
                                                            {{else}}
                                                                {{randomInt lower=1 upper=5}}
                                                            {{/if}}
                                                        {{/assign}}
                                                        {
                                                            "id": {{randomInt lower=100000 upper=999999}},
                                                            "market_id": {{marketId}},
                                                            "market_name": "{{lookup parameters.marketMapping marketId}}",
                                                            "line": "{{randomInt lower=10 upper=30}}.5",
                                                            "bet_type": {{#if (eq marketId "5")}}null{{else}}"{{pickRandom 'Over' 'Under'}}"{{/if}},
                                                            "price": "{{randomDecimal lower=1.50 upper=2.50}}",
                                                            "status": "Open",
                                                            "last_updated_date": "{{now offset='+3 days' format='yyyy-MM-dd HH:mm:ss'}}"
                                                        }
                                                        {{#unless @last}},{{/unless}}
                                                        {{/each}}
                                                    ]
                                                }
                                                {{#unless @last}},{{/unless}}
                                                {{/each}}
                                            ]
                                        }
                                    """)));
            WireMock.stubFor(get(urlPathMatching("/v1/participants/list"))
                    .atPriority(10)
                    .willReturn(aResponse()
                            .withTransformers("logging-transformer")
                            .withStatus(400)
                            .withHeader("Content-Type", "application/json")
                            .withBody("""
                    {
                        "error": "competition_id is required"
                    }
                """)));

            WireMock.stubFor(get(urlPathMatching("/v1/participants/\\d+"))
                    .willReturn(withCommonHeaders(aResponse())
                            .withTransformerParameter("participantMapping", Map.of(
                                    "1", "Los Angeles Lakers",
                                    "2", "Golden State Warriors",
                                    "3", "Brooklyn Nets",
                                    "4", "Milwaukee Bucks",
                                    "5", "Dallas Mavericks"
                            ))
                            .withTransformerParameter("marketMapping", Map.of(
                                    "5", "Match Winner",
                                    "6", "Under/Over Fouls - Away Team",
                                    "7", "Under/Over Fouls - Home Team",
                                    "8", "Under/Over Home Team Assists",
                                    "9", "Under/Over Away Team Assists"
                            ))
                            .withTransformers("response-template")
                            .withBody("""
                {
                    {{#assign "participantId"}}{{request.pathSegments.[2]}}{{/assign}}
                    "id": {{participantId}},
                    "team_name": "{{lookup parameters.participantMapping participantId}}",
                    "acronym": "{{pickRandom 'LAL' 'GSW' 'BKN' 'MIL' 'DAL'}}",
                    "competition": "NBA",
                    "competition_id": 1001,
                    "next_event": {
                        "event_id": {{randomInt lower=1000 upper=9999}},
                        "event_name": "{{pickRandom 'Lakers vs Warriors' 'Nets vs Bucks' 'Mavs vs Clippers'}}",
                        "event_date": "{{now offset='+3 days' format='yyyy-MM-dd HH:mm:ss'}}",
                        "opponent": "{{pickRandom 'GSW' 'LAL' 'MIA' 'BOS'}}"
                    },
                    "historical_stats": [
                        {
                            "stat_name": "Points Per Game",
                            "average": {{randomInt lower=90 upper=120}},
                            "count": 5,
                            "max_value": {{randomInt lower=110 upper=130}},
                            "min_value": {{randomInt lower=80 upper=100}},
                            "event_statistics": [
                                {{#each (range 1 5)}}
                                {
                                    "event_id": {{randomInt lower=1000 upper=9999}},
                                    "event_name": "{{pickRandom 'Lakers vs Warriors' 'Nets vs Bucks' 'Mavs vs Clippers'}}",
                                    "event_date": "{{now offset='+3 days' format='yyyy-MM-dd HH:mm:ss'}}",
                                    "value": {{randomInt lower=80 upper=130}},
                                    "opponent": "{{pickRandom 'GSW' 'LAL' 'MIA' 'BOS'}}"
                                }
                                {{#unless @last}},{{/unless}}
                                {{/each}}
                            ]
                        },
                        {
                            "stat_name": "Rebounds Per Game",
                            "average": {{randomInt lower=30 upper=50}},
                            "count": 5,
                            "max_value": {{randomInt lower=40 upper=60}},
                            "min_value": {{randomInt lower=20 upper=30}},
                            "event_statistics": [
                                {{#each (range 1 5)}}
                                {
                                    "event_id": {{randomInt lower=1000 upper=9999}},
                                    "event_name": "{{pickRandom 'Lakers vs Warriors' 'Nets vs Bucks' 'Mavs vs Clippers'}}",
                                    "event_date": "{{now offset='+3 days' format='yyyy-MM-dd HH:mm:ss'}}",
                                    "value": {{randomInt lower=20 upper=60}},
                                    "opponent": "{{pickRandom 'GSW' 'LAL' 'MIA' 'BOS'}}"
                                }
                                {{#unless @last}},{{/unless}}
                                {{/each}}
                            ],
                            "odds": [
                                {{#each (range 1 (randomInt lower=1 upper=3))}}
                                {{#assign "marketId"}}{{randomInt lower=5 upper=9}}{{/assign}}
                                {
                                    "id": {{randomInt lower=100000 upper=999999}},
                                    "market_id": {{marketId}},
                                    "market_name": "{{lookup parameters.marketMapping marketId}}",
                                    "line": "{{randomInt lower=10 upper=30}}.5",
                                    "bet_type": {{#if (eq marketId "5")}}null{{else}}"{{pickRandom 'Over' 'Under'}}"{{/if}},
                                    "price": "{{randomDecimal lower=1.50 upper=2.50}}",
                                    "status": "Open",
                                    "last_updated_date": "{{now offset='+3 days' format='yyyy-MM-dd HH:mm:ss'}}"
                                }
                                {{#unless @last}},{{/unless}}
                                {{/each}}
                            ]
                        }
                    ]
                }
                """)));

            log.info("WireMock Stub Participants with Randomized Data Initialized!");
        };
    }
}
