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
import static com.github.tomakehurst.wiremock.client.WireMock.matching;

@Profile("mock")
@Slf4j
@Configuration
public class SubParticipantWireMockBase extends WireMockBase {

    @Bean
    public CommandLineRunner setupSubParticipantWireMock() {
        return args -> {
            WireMock.configureFor(getWireMockHost(), getWireMockPort());
            WireMock.stubFor(get(urlPathMatching("/v1/sub-participants/list"))
                    .withQueryParam("competition_id", matching("\\d+"))
                    .willReturn(withCommonHeaders(aResponse())
                            .withStatus(200)
                            .withTransformerParameter("subParticipantMapping", Map.of(
                                    "1", "LeBron James",
                                    "2", "Stephen Curry",
                                    "3", "Kevin Durant",
                                    "4", "Luka Doncic",
                                    "5", "Giannis Antetokounmpo"
                            ))
                            .withTransformerParameter("participantMapping", Map.of(
                                    "1", "Los Angeles Lakers",
                                    "2", "Golden State Warriors",
                                    "3", "Brooklyn Nets",
                                    "4", "Milwaukee Bucks",
                                    "5", "Dallas Mavericks"
                            )).withTransformerParameter("marketMapping", Map.of(
                                    "1", "Under/Over Player Points",
                                    "2", "Under/Over Player Assists",
                                    "3", "Under/Over Player Blocks",
                                    "4", "Under/Over Player Rebounds"
                            ))
                            .withTransformers("response-template")
                            .withBody("""
                                    {
                                        "count": {{randomInt lower=1 upper=5}},
                                        "page": 1,
                                        "page_size": 25,
                                        "total_pages": 1,
                                        "sub_participants": [
                                            {{#each (range 1 (randomInt lower=1 upper=25))}}
                                            {{#assign "subParticipantId"}}{{randomInt lower=1 upper=5}}{{/assign}}
                                            {{#assign "participantId"}}{{randomInt lower=1 upper=5}}{{/assign}}
                                            {
                                                "id": {{subParticipantId}},
                                                "player_name": "{{lookup parameters.subParticipantMapping subParticipantId}}",                                           
                                                "competition": "NBA",
                                                "competition_id": {{request.query.competition_id}},
                                                "avatar_url": null,
                                                "participant_id": {{participantId}},
                                                "team_name": "{{lookup parameters.participantMapping participantId}}",                                                                                                 
                                                "position": "{{pickRandom 'Forward' 'Guard' 'Center'}}",
                                                "next_event": {
                                                    "event_id": {{randomInt lower=1000 upper=9999}},
                                                    "event_name": "{{pickRandom 'Lakers vs Warriors' 'Nets vs Bucks' 'Mavs vs Clippers'}}",
                                                    "event_date": "{{now offset='+3 days' format='yyyy-MM-dd HH:mm:ss'}}",
                                                    "opponent": "GSW"
                                                },
                                                "shirt_nr": {{randomInt lower=1 upper=99}},
                                                "area_id": 3,
                                                "area_name": "United States",
                                                "gender": "Male",
                                                "weight": "{{randomInt lower=80 upper=120}} kg",
                                                "height": "{{randomInt lower=180 upper=220}} cm",
                                                "birthdate": "{{randomInt lower=1980 upper=2005}}-12-30",
                                                "historical_stats": [
                                                    {
                                                        "stat_name": "Points",
                                                        "average": {{randomInt lower=10 upper=30}},
                                                        "count": 5,
                                                        "max_value": {{randomInt lower=20 upper=50}},
                                                        "min_value": {{randomInt lower=0 upper=10}},
                                                        "event_statistics": [
                                                            {{#each (range 1 5)}}
                                                            {
                                                                "event_id": {{randomInt lower=1000 upper=9999}},
                                                                "event_name": "{{pickRandom 'Lakers vs Warriors' 'Nets vs Bucks' 'Mavs vs Clippers'}}",
                                                                "event_date": "{{now offset='+3 days' format='yyyy-MM-dd HH:mm:ss'}}",
                                                                "value": {{randomInt lower=5 upper=40}},
                                                                "opponent": "GSW"
                                                            }
                                                            {{#unless @last}},{{/unless}}
                                                            {{/each}}
                                                        ]
                                                    }
                                                ],
                                                "odds": [
                                                            {{#each (range 1 (randomInt lower=1 upper=3))}}
                                                            {{#assign "marketId"}}{{randomInt lower=1 upper=4}}{{/assign}}
                                                            {
                                                                "id": {{randomInt lower=100000 upper=999999}},
                                                                "market_id": {{marketId}},
                                                                "market_name": "{{lookup parameters.marketMapping marketId}}",
                                                                "line": "{{randomInt lower=10 upper=30}}.5",
                                                                "bet_type": "{{pickRandom 'Over' 'Under'}}",
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
                                    """)
                            .withTransformers("response-template")));

            WireMock.stubFor(get(urlPathMatching("/v1/sub-participants/list"))
                    .atPriority(10)
                    .willReturn(aResponse()
                            .withStatus(400)
                            .withHeader("Content-Type", "application/json")
                            .withBody("""
                    {
                        "error": "competition_id is required"
                    }
                """)));

            WireMock.stubFor(get(urlPathMatching("/v1/sub-participants/\\d+"))
                    .willReturn(withCommonHeaders(aResponse())
                            .withStatus(200)
                            .withTransformerParameter("subParticipantMapping", Map.of(
                                    "1", "LeBron James",
                                    "2", "Stephen Curry",
                                    "3", "Kevin Durant",
                                    "4", "Luka Doncic",
                                    "5", "Giannis Antetokounmpo"
                            ))
                            .withTransformerParameter("participantMapping", Map.of(
                                    "1", "Los Angeles Lakers",
                                    "2", "Golden State Warriors",
                                    "3", "Brooklyn Nets",
                                    "4", "Milwaukee Bucks",
                                    "5", "Dallas Mavericks"
                            )).withTransformerParameter("marketMapping", Map.of(
                                    "1", "Under/Over Player Points",
                                    "2", "Under/Over Player Assists",
                                    "3", "Under/Over Player Blocks",
                                    "4", "Under/Over Player Rebounds"
                            ))
                            .withTransformers("response-template")
                            .withBody("""
                                    {
                                        {{#assign "subParticipantId"}}{{request.pathSegments.[2]}}{{/assign}}
                                        {{#assign "participantId"}}{{randomInt lower=1 upper=5}}{{/assign}}
                                        "id": {{subParticipantId}},
                                        "player_name": "{{lookup parameters.subParticipantMapping subParticipantId}}",    
                                        "competition": "NBA",
                                        "competition_id": 1001,
                                        "avatar_url": null,
                                        "participant_id": {{participantId}},
                                        "team_name": "{{lookup parameters.participantMapping participantId}}",
                                        "position": "{{pickRandom 'Forward' 'Guard' 'Center'}}",
                                        "next_event": {
                                            "event_id": {{randomInt lower=1000 upper=9999}},
                                            "event_name": "{{pickRandom 'Lakers vs Warriors' 'Nets vs Bucks' 'Mavs vs Clippers'}}",
                                            "event_date": "{{now offset='+3 days' format='yyyy-MM-dd HH:mm:ss'}}",
                                            "opponent": "GSW"
                                        },
                                        "shirt_nr": {{randomInt lower=1 upper=99}},
                                        "area_id": 3,
                                        "area_name": "United States",
                                        "gender": "Male",
                                        "weight": "{{randomInt lower=80 upper=120}} kg",
                                        "height": "{{randomInt lower=180 upper=220}} cm",
                                        "birthdate": "{{randomInt lower=1980 upper=2005}}-12-30",
                                        "historical_stats": [
                                            {
                                                "stat_name": "Points",
                                                "average": {{randomInt lower=10 upper=30}},
                                                "count": 2,
                                                "max_value": {{randomInt lower=20 upper=50}},
                                                "min_value": {{randomInt lower=0 upper=10}},
                                                "event_statistics": [
                                                    {{#each (range 1 5)}}
                                                    {
                                                        "event_id": {{randomInt lower=1000 upper=9999}},
                                                        "event_name": "{{pickRandom 'Lakers vs Warriors' 'Nets vs Bucks' 'Mavs vs Clippers'}}",
                                                        "event_date": "{{now offset='+3 days' format='yyyy-MM-dd HH:mm:ss'}}",
                                                        "value": {{randomInt lower=5 upper=40}},
                                                        "opponent": "GSW"
                                                    }
                                                    {{#unless @last}},{{/unless}}
                                                    {{/each}}
                                                ]
                                            }
                                        ],
                                        "odds": [
                                                    {{#each (range 1 (randomInt lower=1 upper=3))}}
                                                    {{#assign "marketId"}}{{randomInt lower=1 upper=4}}{{/assign}}
                                                    {
                                                        "id": {{randomInt lower=100000 upper=999999}},
                                                        "market_id": {{marketId}},
                                                        "market_name": "{{lookup parameters.marketMapping marketId}}",
                                                        "line": "{{randomInt lower=10 upper=30}}.5",
                                                        "bet_type": "{{pickRandom 'Over' 'Under'}}",
                                                        "price": "{{randomDecimal lower=1.50 upper=2.50}}",
                                                        "status": "Open",
                                                        "last_updated_date": "{{now offset='+3 days' format='yyyy-MM-dd HH:mm:ss'}}"
                                                    }
                                                    {{#unless @last}},{{/unless}}
                                                    {{/each}}
                                                ]
                                    }
                                    """)
                            .withTransformers("response-template")));
            log.info("WireMock Stub Sub-Participants with Randomized Data Initialized!");
        };
    }
}
