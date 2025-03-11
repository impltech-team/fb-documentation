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
public class EventWireMockBase extends WireMockBase {

    @Bean
    public CommandLineRunner setupEventWireMock() {
        return args -> {
            WireMock.configureFor(getWireMockHost(), getWireMockPort());
            WireMock.stubFor(get(urlPathMatching("/v1/events/list"))
                    .willReturn(withCommonHeaders(aResponse())
                            .withTransformerParameter("participantMapping", Map.of(
                                    "1", "Los Angeles Lakers",
                                    "2", "Golden State Warriors",
                                    "3", "Boston Celtics",
                                    "4", "Miami Heat",
                                    "5", "Brooklyn Nets"
                            ))
                            .withTransformerParameter("venueMapping", Map.of(
                                    "200", "Crypto.com Arena",
                                    "201", "TD Garden",
                                    "202", "Chase Center",
                                    "203", "United Center"
                            ))
                            .withTransformers("response-template")
                            .withBody("""
                            {
                                "count": {{randomInt lower=1 upper=5}},
                                "page": 1,
                                "page_size": 25,
                                "total_pages": 1,
                                "events": [
                                    {{#each (range 1 (randomInt lower=1 upper=3))}}
                                    {{#assign "eventId"}}{{randomInt lower=1000 upper=9999}}{{/assign}}
                                    {{#assign "competitionId"}}1001{{/assign}}
                                    {{#assign "venueId"}}{{randomInt lower=200 upper=203}}{{/assign}}
                                    {{#assign "participant1Id"}}{{randomInt lower=1 upper=5}}{{/assign}}
                                    {{#assign "participant2Id"}}{{randomInt lower=1 upper=5}}{{/assign}}
                                    {{#assign "participant1Name"}}{{lookup parameters.participantMapping participant1Id}}{{/assign}}
                                    {{#assign "participant2Name"}}{{lookup parameters.participantMapping participant2Id}}{{/assign}}
                                    {
                                        "id": {{eventId}},
                                        "competition": "NBA",
                                        "competition_id": {{competitionId}},
                                        "event_name": "{{participant1Name}} vs {{participant2Name}}",
                                        "event_date": "{{now offset='+3 days' format='yyyy-MM-dd HH:mm:ss'}}",
                                        "participants": [
                                            {
                                                "participant_id": {{participant1Id}},
                                                "team_name": "{{participant1Name}}",
                                                "acronym": "{{pickRandom 'LAL' 'GSW' 'BOS' 'MIA' 'BKN'}}"
                                            },
                                            {
                                                "participant_id": {{participant2Id}},
                                                "team_name": "{{participant2Name}}",
                                                "acronym": "{{pickRandom 'LAL' 'GSW' 'BOS' 'MIA' 'BKN'}}"
                                            }
                                        ],
                                        "venue": {
                                            "venue_id": {{venueId}},
                                            "venue_name": "{{lookup parameters.venueMapping venueId}}",
                                            "location": "{{pickRandom 'Los Angeles, CA' 'Boston, MA' 'San Francisco, CA' 'Chicago, IL'}}",
                                            "capacity": "{{randomInt lower=18000 upper=22000}}"
                                        },
                                        "odds": {
                                            "markets": [
                                                {
                                                    "market_id": 8,
                                                    "market_name": "Match Winner",
                                                    "bets": [
                                                        {
                                                            "id": {{randomInt lower=100000 upper=999999}},
                                                            "participant_id": {{participant1Id}},
                                                            "participant_name": "{{participant1Name}}",
                                                            "bet_type": "Win",
                                                            "price": "{{randomDecimal lower=1.50 upper=2.50}}"
                                                        },
                                                        {
                                                            "id": {{randomInt lower=100000 upper=999999}},
                                                            "participant_id": {{participant2Id}},
                                                            "participant_name": "{{participant2Name}}",
                                                            "bet_type": "Win",
                                                            "price": "{{randomDecimal lower=1.50 upper=2.50}}"
                                                        }
                                                    ]
                                                }
                                            ]
                                        }
                                    }
                                    {{#unless @last}},{{/unless}}
                                    {{/each}}
                                ]
                            }
                        """)));

            WireMock.stubFor(get(urlPathMatching("/v1/events/\\d+"))
                    .willReturn(withCommonHeaders(aResponse())
                            .withTransformerParameter("participantMapping", Map.of(
                                    "1", "Los Angeles Lakers",
                                    "2", "Golden State Warriors",
                                    "3", "Boston Celtics",
                                    "4", "Miami Heat",
                                    "5", "Brooklyn Nets"
                            ))
                            .withTransformerParameter("subParticipantMapping", Map.of(
                                    "101", "LeBron James",
                                    "102", "Anthony Davis",
                                    "103", "Kevin Durant",
                                    "201", "Stephen Curry",
                                    "202", "Draymond Green"
                            ))
                            .withTransformerParameter("venueMapping", Map.of(
                                    "200", "Crypto.com Arena",
                                    "201", "TD Garden",
                                    "202", "Chase Center",
                                    "203", "United Center"
                            ))
                            .withTransformers("response-template")
                            .withBody("""
                                        {
                                            {{#assign "eventId"}}{{request.pathSegments.[1]}}{{/assign}}
                                            {{#assign "eventStatus"}}{{pickRandom 'scheduled' 'in_progress' 'finished'}}{{/assign}}
                                            {{#assign "competitionId"}}1001{{/assign}}
                                            {{#assign "venueId"}}{{randomInt lower=200 upper=203}}{{/assign}}
                                            {{#assign "participant1Id"}}{{randomInt lower=1 upper=5}}{{/assign}}
                                            {{#assign "participant2Id"}}{{randomInt lower=1 upper=5}}{{/assign}}
                                            {{#assign "participant1Name"}}{{lookup parameters.participantMapping participant1Id}}{{/assign}}
                                            {{#assign "participant2Name"}}{{lookup parameters.participantMapping participant2Id}}{{/assign}}
                                            "id": {{eventId}},
                                            "competition": "NBA",
                                            "competition_id": 1001,
                                            "event_name": "{{participant1Name}} vs {{participant2Name}}",
                                            "event_date": "{{now offset='+3 days' format='yyyy-MM-dd HH:mm:ss'}}",
                                            "status": "{{eventStatus}}",
                                            "participants":  [
                                                {
                                                    "participant_id": {{participant1Id}},
                                                    "team_name": "{{participant1Name}}",
                                                    "acronym": "{{pickRandom 'LAL' 'GSW' 'BOS' 'MIA' 'BKN'}}",
                                                    "score": {{#if (eq eventStatus 'scheduled')}}null{{else}}"{{randomInt lower=80 upper=120}}"{{/if}},
                                                    "lineups": {{#if (eq eventStatus 'scheduled')}}null{{else}}[
                                                        {{#each (range 1 2)}}
                                                        {{#assign "subParticipantId"}}{{randomInt lower=101 upper=202}}{{/assign}}
                                                        {
                                                            "sub_participant_id": {{subParticipantId}},
                                                            "sub_participant_name": "{{lookup parameters.subParticipantMapping subParticipantId}}",
                                                            "position": "{{pickRandom 'Forward' 'Guard' 'Center'}}"
                                                        }
                                                        {{#unless @last}},{{/unless}}
                                                        {{/each}}
                                                    ]{{/if}},
                                                    "stats": {{#if (eq eventStatus 'scheduled')}}null{{else}}[
                                                        {
                                                            "stat_name": "Points",
                                                            "value": "{{randomInt lower=80 upper=120}}"
                                                        },
                                                        {
                                                            "stat_name": "Rebounds",
                                                            "value": "{{randomInt lower=30 upper=60}}"
                                                        },
                                                        {
                                                            "stat_name": "Assists",
                                                            "value": "{{randomInt lower=15 upper=30}}"
                                                        }
                                                    ]{{/if}}
                                                },
                                                {
                                                    "participant_id": {{participant2Id}},
                                                    "team_name": "{{participant2Name}}",
                                                    "acronym": "{{pickRandom 'LAL' 'GSW' 'BOS' 'MIA' 'BKN'}}",
                                                    "score": {{#if (eq eventStatus 'scheduled')}}null{{else}}"{{randomInt lower=80 upper=120}}"{{/if}},
                                                    "lineups": {{#if (eq eventStatus 'scheduled')}}null{{else}} [
                                                        {{#each (range 1 2)}}
                                                        {{#assign "subParticipantId"}}{{randomInt lower=101 upper=202}}{{/assign}}
                                                        {
                                                            "sub_participant_id": {{subParticipantId}},
                                                            "sub_participant_name": "{{lookup parameters.subParticipantMapping subParticipantId}}",
                                                            "position": "{{pickRandom 'Forward' 'Guard' 'Center'}}"
                                                        }
                                                        {{#unless @last}},{{/unless}}
                                                        {{/each}}
                                                    ]{{/if}},
                                                    "stats": {{#if (eq eventStatus 'scheduled')}}null{{else}}[
                                                        {
                                                            "stat_name": "Points",
                                                            "value": "{{randomInt lower=80 upper=120}}"
                                                        },
                                                        {
                                                            "stat_name": "Rebounds",
                                                            "value": "{{randomInt lower=30 upper=60}}"
                                                        },
                                                        {
                                                            "stat_name": "Assists",
                                                            "value": "{{randomInt lower=15 upper=30}}"
                                                        }
                                                    ]{{/if}}
                                                }
                                            ],
                                            "venue": {
                                                "venue_id": {{venueId}},
                                                "venue_name": "{{lookup parameters.venueMapping venueId}}",
                                                "location": "{{pickRandom 'Los Angeles, CA' 'Boston, MA' 'San Francisco, CA' 'Chicago, IL'}}",
                                                "capacity": "{{randomInt lower=18000 upper=22000}}"
                                            },
                                            "incidents": {{#if (eq eventStatus 'scheduled')}}null{{else}}[
                                                 {
                                                     "time": "Q3 {{randomInt lower=0 upper=12}}:{{randomInt lower=0 upper=59}}",
                                                     "participant_id": {{participant1Id}},
                                                     "participant_name": "{{participant1Name}}",
                                                     "sub_participant_id": "{{pickRandom '101' '102'}}",
                                                     "sub_participant_name": "{{pickRandom 'LeBron James' 'Anthony Davis'}}",
                                                     "type": "{{pickRandom '3PT Made' 'Dunk' 'Steal' 'Foul'}}"
                                                 },
                                                 {
                                                     "time": "Q4 {{randomInt lower=0 upper=12}}:{{randomInt lower=0 upper=59}}",
                                                     "participant_id": {{participant2Id}},
                                                     "participant_name": "{{participant2Name}}",
                                                     "sub_participant_id": {{pickRandom '201' '202'}},
                                                     "sub_participant_name": "{{pickRandom 'Stephen Curry' 'Draymond Green'}}",
                                                     "type": "{{pickRandom '3PT Made' 'Dunk' 'Steal' 'Foul'}}"
                                                 }
                                            ]{{/if}},
                                            "odds": {
                                                "markets": [
                                                {
                                                    "market_id": 8,
                                                    "market_name": "Match Winner",
                                                    "bets": [
                                                        {
                                                            "id": {{randomInt lower=100000 upper=999999}},
                                                            "participant_id": {{participant1Id}},
                                                            "participant_name": "{{participant1Name}}",
                                                            "bet_type": "Win",
                                                            "price": "{{randomDecimal lower=1.50 upper=2.50}}"
                                                        },
                                                        {
                                                            "id": {{randomInt lower=100000 upper=999999}},
                                                            "participant_id": {{participant2Id}},
                                                            "participant_name": "{{participant2Name}}",
                                                            "bet_type": "Win",
                                                            "price": "{{randomDecimal lower=1.50 upper=2.50}}"
                                                        }
                                                    ]
                                                }
                                            ]
                                        }
                                    """)));
            log.info("WireMock Stub Events with Randomized Data Initialized!");
        };
    }
}
