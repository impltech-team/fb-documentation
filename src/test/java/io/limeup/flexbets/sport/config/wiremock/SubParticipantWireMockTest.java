package io.limeup.flexbets.sport.config.wiremock;

import com.atlassian.ta.wiremockpactgenerator.WireMockPactGenerator;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.assertj.core.api.Assertions.assertThat;

public class SubParticipantWireMockTest extends BaseWireMockTest {


    private static final String SUB_PARTICIPANTS_LIST_ENDPOINT = "/sub-participants/list";
    private static final String SUB_PARTICIPANTS_DETAILS_ENDPOINT = "/sub-participants/";

    @BeforeAll
    private void setupStub() {
        getWireMockServer().addMockServiceRequestListener(
                WireMockPactGenerator.builder("sub-participants-provider", "sub-participants-consumer").build()
        );

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(SUB_PARTICIPANTS_LIST_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("sub_participants_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathMatching(SUB_PARTICIPANTS_DETAILS_ENDPOINT + "\\d+"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("sub_participant_response.json")
                        .withStatus(200)));
    }

    @Test
    @DisplayName("Verify Sub-Participants List Response")
    public void testSubParticipantsList() {
        given()
                .queryParam("competition_id", 1001)
                .when()
                .get(SUB_PARTICIPANTS_LIST_ENDPOINT)
                .then()
                .statusCode(200)
                .body("count", equalTo(2))
                .body("sub_participants.size()", equalTo(2))

                .body("sub_participants[0].id", equalTo(1))
                .body("sub_participants[0].competition_id", equalTo(1001))
                .body("sub_participants[0].player_name", equalTo("LeBron James"))
                .body("sub_participants[0].team_name", equalTo("Los Angeles Lakers"))
                .body("sub_participants[0].position", equalTo("Forward"))
                .body("sub_participants[0].next_event", notNullValue())
                .body("sub_participants[0].historical_stats[0].stat_name", equalTo("Points"))
                .body("sub_participants[0].odds[0].market_name", equalTo("Under/Over Player Assists"))

                .body("sub_participants[1].id", equalTo(104))
                .body("sub_participants[0].competition_id", equalTo(1001))
                .body("sub_participants[1].player_name", equalTo("Nic Claxton"))
                .body("sub_participants[1].team_name", equalTo("Brooklyn Nets"))
                .body("sub_participants[1].position", equalTo("Center"))
                .body("sub_participants[1].next_event", notNullValue())
                .body("sub_participants[1].historical_stats[0].stat_name", equalTo("Points"))
                .body("sub_participants[1].odds[0].market_name", equalTo("Under/Over Player Blocks"));
    }

    @Test
    @DisplayName("Verify Sub-Participants Filter by Multiple Positions (Forward & Center)")
    public void testSubParticipantsFilterByMultiplePositions() {
        Response response = given()
                .queryParam("competition_id", 1001)
                .queryParam("positions", Arrays.asList("Forward")) // Pass multiple positions
                .when()
                .get(SUB_PARTICIPANTS_LIST_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> subParticipants = jsonPath.getList("sub_participants");
        assertThat(subParticipants).isNotEmpty();
        List<String> expectedPositions = Arrays.asList("Forward", "Center");
        List<Map<String, Object>> filteredList = subParticipants.stream()
                .filter(subParticipant -> expectedPositions.contains(subParticipant.get("position")))
                .toList();

        assertThat(filteredList).isEqualTo(subParticipants);

        filteredList.forEach(subParticipant ->
                assertThat(expectedPositions).contains((String) subParticipant.get("position"))
        );
    }

    @Test
    @DisplayName("Verify Sub-Participants Filtering by Position, Participant ID, Market ID, and Search Filter")
    public void testSubParticipantsFiltering() {

        List<String> expectedPositions = Arrays.asList("Forward", "Center");
        List<Integer> expectedParticipantIds = Arrays.asList(1, 3);
        int expectedMarketId = 3;
        String expectedSearchFilter = "Brooklyn";

        Response response = given()
                .queryParam("competition_id", 1001)
                .queryParam("positions", expectedPositions)
                .queryParam("participant_ids", expectedParticipantIds)
                .queryParam("market_id", expectedMarketId)
                .queryParam("filter", expectedSearchFilter)
                .when()
                .get("/sub-participants/list")
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> subParticipants = jsonPath.getList("sub_participants");

        assertThat(subParticipants).isNotEmpty();

        List<Map<String, Object>> filteredList = subParticipants.stream()
                .filter(subParticipant -> expectedPositions.contains(subParticipant.get("position"))) // Position filter
                .filter(subParticipant -> expectedParticipantIds.contains(Integer.parseInt(subParticipant.get("participant_id").toString()))) // Participant ID filter
                .filter(subParticipant ->
                        subParticipant.containsKey("odds") && ((List<Map<String, Object>>) subParticipant.get("odds"))
                                .stream().anyMatch(odd -> Integer.parseInt(odd.get("market_id").toString()) == expectedMarketId) // Market ID filter
                )
                .filter(subParticipant ->
                        subParticipant.get("player_name").toString().contains(expectedSearchFilter) ||
                                subParticipant.get("team_name").toString().contains(expectedSearchFilter) ||
                                subParticipant.get("position").toString().contains(expectedSearchFilter) // General filter
                )
                .collect(Collectors.toList());

        assertThat(filteredList.size()).isEqualTo(1);

        filteredList.forEach(subParticipant -> {
            assertThat(expectedPositions).contains((String) subParticipant.get("position"));
            assertThat(expectedParticipantIds).contains(Integer.parseInt(subParticipant.get("participant_id").toString()));

            List<Map<String, Object>> odds = (List<Map<String, Object>>) subParticipant.get("odds");
            assertThat(odds).anyMatch(odd -> Integer.parseInt(odd.get("market_id").toString()) == expectedMarketId);

            assertThat(
                    subParticipant.get("player_name").toString().contains(expectedSearchFilter) ||
                            subParticipant.get("team_name").toString().contains(expectedSearchFilter) ||
                            subParticipant.get("position").toString().contains(expectedSearchFilter)
            ).isTrue().as("At least one field should contain the filter keyword");
        });
    }

    @Test
    @DisplayName("Verify Sub-Participant Details by ID")
    public void testSubParticipantById() {
        Response response = given()
                .when()
                .get(SUB_PARTICIPANTS_DETAILS_ENDPOINT + "1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("player_name", equalTo("LeBron James"))
                .body("team", equalTo("Los Angeles Lakers"))
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("position")).isEqualTo("Forward");
        assertThat(jsonPath.getString("next_event.event_name")).contains("Los Angeles Lakers vs Golden State Warriors");
        assertThat(jsonPath.getList("historical_stats")).isNotEmpty();
    }
}

