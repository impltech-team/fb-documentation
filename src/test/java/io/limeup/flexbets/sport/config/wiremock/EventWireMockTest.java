package io.limeup.flexbets.sport.config.wiremock;

import com.atlassian.ta.wiremockpactgenerator.WireMockPactGenerator;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class EventWireMockTest extends BaseWireMockTest {

    private static final String EVENTS_LIST_ENDPOINT = "/events/list";

    private static final String EVENT_DETAILS_ENDPOINT = "/events/";

    @BeforeAll
    public void setupStub() {
        getWireMockServer().addMockServiceRequestListener(
                WireMockPactGenerator.builder("events-provider", "events-consumer").build()
        );

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(EVENTS_LIST_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader(CONTENT_TYPE, CONTENT_TYPE)
                        .withBodyFile("events_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathMatching(EVENT_DETAILS_ENDPOINT + "\\d+"))
                .willReturn(WireMock.aResponse()
                        .withHeader(CONTENT_TYPE, CONTENT_TYPE)
                        .withBodyFile("event_response.json")
                        .withStatus(200)));
    }

    @Test
    @DisplayName("Verify Events List API Response")
    public void testEventsList() {
        Response response = given()
                .queryParam(COMPETITION_ID, 1001)
                .when()
                .get(EVENTS_LIST_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        // Validate main response structure
        assertThat(jsonPath.getInt("count")).isEqualTo(2);
        assertThat(jsonPath.getInt("page")).isEqualTo(1);
        assertThat(jsonPath.getInt("total_pages")).isEqualTo(1);

        List<Map<String, Object>> events = jsonPath.getList("events");
        assertThat(events).isNotEmpty().hasSize(1);

        // Validate first event
        Map<String, Object> firstEvent = events.get(0);
        assertThat(firstEvent.get("competition")).isEqualTo("NBA");
        assertThat(firstEvent.get(COMPETITION_ID)).isEqualTo(1001);
        assertThat(firstEvent.get("event_name")).isEqualTo("Los Angeles Lakers vs Golden State Warriors");

        // Validate participants in first event
        List<Map<String, Object>> participants = (List<Map<String, Object>>) firstEvent.get(PARTICIPANTS);
        assertThat(participants).isNotEmpty().hasSize(2);
        assertThat(participants.get(0).get("team_name")).isEqualTo("Los Angeles Lakers");

        // Validate venue details
        Map<String, Object> venue = (Map<String, Object>) firstEvent.get("venue");
        assertThat(venue.get("venue_name")).isEqualTo("Crypto.com Arena");

        // Validate odds in first event
        Map<String, Object> odds = (Map<String, Object>) firstEvent.get("odds");
        List<Map<String, Object>> markets = (List<Map<String, Object>>) odds.get("markets");
        assertThat(markets.get(0).get("market_name")).isEqualTo("Match Winner");
    }

    @Test
    @DisplayName("Filter Events by Participant ID")
    public void testFilterEventsByParticipantId() {
        Response response = given()
                .queryParam(COMPETITION_ID, 1001)
                .queryParam("participant_ids", "1")
                .when()
                .get(EVENTS_LIST_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> events = jsonPath.getList("events");

        assertThat(events).isNotEmpty();

        List<Map<String, Object>> filteredEvents = events.stream()
                .filter(event -> {
                    List<Map<String, Object>> participants = (List<Map<String, Object>>) event.get(PARTICIPANTS);
                    return participants.stream().anyMatch(p -> "1".equals(p.get("participant_id").toString()));
                })
                .toList();

        assertThat(filteredEvents).isEqualTo(events);
    }

    @Test
    @DisplayName("Verify Event Details API Response")
    public void testEventDetails() {
        int eventId = 4784;

        Response response = given()
                .when()
                .get(EVENT_DETAILS_ENDPOINT + eventId)
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        // Validate main response structure
        assertThat(jsonPath.getInt("id")).isEqualTo(eventId);
        assertThat(jsonPath.getString("competition")).isEqualTo("NBA");
        assertThat(jsonPath.getInt(COMPETITION_ID)).isEqualTo(1001);
        assertThat(jsonPath.getString("event_name")).isEqualTo("Los Angeles Lakers vs Golden State Warriors");

        // Validate participants
        List<Map<String, Object>> participants = jsonPath.getList(PARTICIPANTS);
        assertThat(participants).isNotEmpty().hasSize(1);

        // Validate first participant
        Map<String, Object> firstParticipant = participants.get(0);
        assertThat(firstParticipant.get("participant_name")).isEqualTo("Los Angeles Lakers");
        assertThat(firstParticipant.get("score")).isEqualTo(98);

        // Validate venue details
        Map<String, Object> venue = jsonPath.getMap("venue");
        assertThat(venue.get("venue_name")).isEqualTo("Crypto.com Arena");

        // Validate incidents
        List<Map<String, Object>> incidents = jsonPath.getList("incidents");
        assertThat(incidents).isNotEmpty().hasSize(1);
        assertThat(incidents.get(0).get("sub_participant_name")).isEqualTo("Stephen Curry");

        // Validate odds
        Map<String, Object> odds = jsonPath.getMap("odds");
        List<Map<String, Object>> markets = (List<Map<String, Object>>) odds.get("markets");
        assertThat(markets).isNotEmpty();
        assertThat(markets.get(0).get("market_name")).isEqualTo("Match Winner");
    }

}
