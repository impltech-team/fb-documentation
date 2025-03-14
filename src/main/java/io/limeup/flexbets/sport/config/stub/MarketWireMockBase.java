package io.limeup.flexbets.sport.config.stub;

import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@Profile("mock")
@Slf4j
@Configuration
public class MarketWireMockBase extends WireMockBase {

    @Bean
    public CommandLineRunner setupMarketWireMock() {
        return args -> {
            WireMock.configureFor(getWireMockHost(), getWireMockPort());
            WireMock.stubFor(get(urlPathMatching("/v1/markets/list"))
                    .withQueryParam("competition_id", matching("\\d+"))
                    .willReturn(withCommonHeaders(aResponse())
                            .withStatus(200)
                            .withTransformerParameter("subParticipantMarkets", Map.of(
                                    "1", "Under/Over Player Points",
                                    "2", "Under/Over Player Assists",
                                    "3", "Under/Over Player Blocks",
                                    "4", "Under/Over Player Rebounds"
                            ))
                            .withTransformerParameter("participantMarkets", Map.of(
                                    "5", "Match Winner",
                                    "6", "Under/Over Fouls - Away Team",
                                    "7", "Under/Over Fouls - Home Team",
                                    "8", "Under/Over Home Team Assists",
                                    "9", "Under/Over Away Team Assists"
                            ))
                            .withTransformers("response-template")
                            .withBody("""
                            [
                                {{#each (range 1 (randomInt lower=1 upper=5))}}
                                {{#assign "marketId"}}{{randomInt lower=1 upper=9}}{{/assign}}
                                {
                                    "market_id": "{{marketId}}",
                                    "market_name": "{{#if (lte marketId '4')}}{{lookup parameters.subParticipantMarkets marketId}}{{else}}{{lookup parameters.participantMarkets marketId}}{{/if}}",
                                    "type": "{{#if (lte marketId '4')}}sub-participant{{else}}participant{{/if}}"
                                }
                                {{#unless @last}},{{/unless}}
                                {{/each}}
                            ]
                            """)));

            WireMock.stubFor(get(urlPathMatching("/v1/markets/list"))
                    .atPriority(10)
                    .willReturn(aResponse()
                            .withStatus(400)
                            .withHeader("Content-Type", "application/json")
                            .withBody("""
                    {
                        "error": "competition_id is required"
                    }
                """)));

            WireMock.stubFor(post(urlPathMatching("/v1/odds/batch"))
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
                            ))
                            .withTransformerParameter("marketMapping", Map.of(
                                    "1", "Under/Over Player Points",
                                    "2", "Under/Over Player Assists",
                                    "3", "Under/Over Player Blocks",
                                    "4", "Under/Over Player Rebounds"
                            ))
                            .withTransformers("response-template")
                            .withBody("""
                [
                    {{#each (jsonPath request.body '$.queries') as |query|}}
                    {{#assign "marketId"}}{{query.market_id}}{{/assign}}
                    {{#assign "participantId"}}{{pickRandom '1' '2' '3'}}{{/assign}}
                    {
                        "sub_participant_id": {{query.sub_participant_id}},
                        "sub_participant_name": "{{lookup parameters.subParticipantMapping query.sub_participant_id}}",
                        "participant_id": {{participantId}},
                        "participant_name": "{{lookup parameters.participantMapping participantId}}",
                        "event_id": {{query.event_id}},
                        "odds": [
                            {{#each (range 1 (randomInt lower=1 upper=3))}}
                            {
                                "id": {{randomInt lower=100000 upper=999999}},
                                "market_id": {{marketId}},
                                "market_name": "{{lookup parameters.marketMapping marketId}}",
                                "line": "{{randomInt lower=10 upper=30}}.5",
                                "bet_type": "{{pickRandom 'Over' 'Under'}}",
                                "price": "{{randomDecimal lower=1.50 upper=2.50}}",
                                "status": "Open",
                                "last_updated_date": "{{now offset='+3 days' format='yyyy-MM-dd HH:mm:ss'}}",
                                "stat_value": "{{randomInt lower=5 upper=40}}"
                            }
                            {{#unless @last}},{{/unless}}
                            {{/each}}
                        ]
                    }
                    {{#unless @last}},{{/unless}}
                    {{/each}}
                ]
                """)));

            log.info("WireMock Stub Markets with Randomized Data Initialized!");

        };
    }
}
