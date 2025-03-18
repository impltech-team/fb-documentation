package io.limeup.flexbets.sport.config.wiremock;

import com.atlassian.ta.wiremockpactgenerator.WireMockPactGenerator;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ParticipantWireMockTest extends BaseWireMockTest {

    private static final String PARTICIPANTS_LIST_ENDPOINT = "/participants/list";
    private static final String PARTICIPANTS_DETAILS_ENDPOINT = "/participants/";

    @BeforeAll
    private void setupStub() {
        getWireMockServer().addMockServiceRequestListener(
                WireMockPactGenerator.builder("participants-provider", "participants-consumer").build()
        );

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(PARTICIPANTS_LIST_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("participants_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathMatching(PARTICIPANTS_DETAILS_ENDPOINT + "\\d+"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("participant_response.json")
                        .withStatus(200)));
    }

    @Test
    @DisplayName("Verify Participants List Response")
    public void testParticipantsList() {
        Response response = given()
                .queryParam("competition_id", 1001)
                .when()
                .get(PARTICIPANTS_LIST_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<?> participants = jsonPath.getList("participants");

        assertThat(participants).isNotEmpty();
        assertThat(jsonPath.getInt("count")).isGreaterThan(0);
        assertThat(jsonPath.getString("participants[0].team_name")).isNotEmpty();
    }

    @Test
    @DisplayName("Verify Participants Filter by Multiple IDs (Brooklyn Nets & Milwaukee Bucks)")
    public void testParticipantsFilterByMultipleIds() {
        List<String> expectedParticipantIds = Arrays.asList("3", "4");

        Response response = given()
                .queryParam("competition_id", 1001)
                .queryParam("participant_ids", expectedParticipantIds)
                .when()
                .get(PARTICIPANTS_LIST_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> participants = jsonPath.getList("participants");
        assertThat(participants).isNotEmpty();
        List<Map<String, Object>> filteredList = participants.stream()
                .filter(participant -> expectedParticipantIds.contains(participant.get("id").toString()))
                .toList();
        assertThat(filteredList).isEqualTo(participants);
        filteredList.forEach(participant ->
                assertThat(expectedParticipantIds).contains(participant.get("id").toString())
        );
    }

    @Test
    @DisplayName("Verify Participant Details by ID")
    public void testParticipantById() {
        Response response = given()
                .when()
                .get(PARTICIPANTS_DETAILS_ENDPOINT + "2")
                .then()
                .statusCode(200)
                .body("id", equalTo(2))
                .body("team_name", equalTo("Golden State Warriors"))
                .body("acronym", equalTo("BKN"))
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("next_event.event_name")).contains("Lakers vs Warriors");
        assertThat(jsonPath.getList("historical_stats")).isNotEmpty();
    }

}
