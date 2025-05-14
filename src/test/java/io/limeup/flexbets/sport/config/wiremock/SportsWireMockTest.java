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
public class SportsWireMockTest extends BaseWireMockTest {

    private static final String SPORTS_LIST_ENDPOINT = "/sports/list";
    private static final String SPORTS_DETAILS_ENDPOINT = "/sports/";

    @BeforeAll
    private void setupStub() {
        getWireMockServer().addMockServiceRequestListener(
                WireMockPactGenerator.builder("sports-provider", "sports-consumer").build()
        );

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(SPORTS_LIST_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBodyFile("sports_response.json")
                        .withStatus(200)));
        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathMatching(SPORTS_DETAILS_ENDPOINT + "\\d+"))
                .willReturn(WireMock.aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBodyFile("sport_response.json")
                        .withStatus(200)));
    }

    @Test
    @DisplayName("Verify Sports List API Response")
    public void testSportsList() {
        Response response = given()
                .when()
                .get(SPORTS_LIST_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        // Validate main response structure
        assertThat(jsonPath.getInt("count")).isEqualTo(2);
        assertThat(jsonPath.getInt("page")).isEqualTo(1);
        assertThat(jsonPath.getInt("total_pages")).isEqualTo(1);

        // Validate sports list
        List<Map<String, Object>> sports = jsonPath.getList(SPORTS);
        assertThat(sports).isNotEmpty().hasSize(2);

        // Validate first sport (Soccer)
        Map<String, Object> firstSport = sports.get(0);
        assertThat(firstSport.get("name")).isEqualTo(SOCCER);
        assertThat(firstSport.get("id")).isEqualTo(1);

        // Validate second sport (Basketball)
        Map<String, Object> secondSport = sports.get(1);
        assertThat(secondSport.get("name")).isEqualTo(BASKETBALL);
        assertThat(secondSport.get("id")).isEqualTo(2);
    }

    @Test
    @DisplayName("Filter Sports by ID (Basketball)")
    public void testFilterSportsById() {
        Response response = given()
                .queryParam("sport_ids", "2")
                .when()
                .get(SPORTS_LIST_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> sports = jsonPath.getList(SPORTS);

        // Ensure only Basketball is returned
        assertThat(sports).isNotEmpty().hasSize(1);
        assertThat(sports.get(0).get("name")).isEqualTo(BASKETBALL);
        assertThat(sports.get(0).get("id")).isEqualTo(2);
    }

    @Test
    @DisplayName("Filter Sports by Name (Soccer)")
    public void testFilterSportsByName() {
        Response response = given()
                .queryParam("name", SOCCER)
                .when()
                .get(SPORTS_LIST_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> sports = jsonPath.getList(SPORTS);

        // Ensure only Soccer is returned
        assertThat(sports).isNotEmpty().hasSize(1);
        assertThat(sports.get(0).get("name")).isEqualTo(SOCCER);
        assertThat(sports.get(0).get("id")).isEqualTo(1);
    }

    @Test
    @DisplayName("Verify Sports Details API Response")
    public void testSportsDetails() {
        Response response = given()
                .when()
                .get(SPORTS_DETAILS_ENDPOINT + 1)
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        // Validate general details
        assertThat(jsonPath.getInt("id")).isEqualTo(1);
        assertThat(jsonPath.getString("name")).isEqualTo(BASKETBALL);

        // Validate statuses
        List<Map<String, Object>> statuses = jsonPath.getList("statuses");
        assertThat(statuses).isNotEmpty().hasSize(4);
        assertThat(statuses).extracting("status").contains("Scheduled", "In Progress", "Finished", "Canceled");

        // Validate result types
        List<Map<String, Object>> resultTypes = jsonPath.getList("result_types");
        assertThat(resultTypes).isNotEmpty().hasSize(4);
        assertThat(resultTypes).extracting("type").contains("Regular Time", "First Half", "Second Quarter", "Overtime");

        // Validate event statistics
        List<Map<String, Object>> eventStatistics = jsonPath.getList("event_statistics");
        assertThat(eventStatistics).isNotEmpty().hasSize(3);
        assertThat(eventStatistics).extracting(STAT_NAME).contains("Total Points", "Total Fouls", "Possession");

        // Validate team statistics
        List<Map<String, Object>> teamStatistics = jsonPath.getList("team_statistics");
        assertThat(teamStatistics).isNotEmpty().hasSize(4);
        assertThat(teamStatistics).extracting(STAT_NAME).contains("Points", "Rebounds", "Assists", "Turnovers");

        // Validate player statistics
        List<Map<String, Object>> playerStatistics = jsonPath.getList("player_statistics");
        assertThat(playerStatistics).isNotEmpty().hasSize(4);
        assertThat(playerStatistics).extracting(STAT_NAME).contains("Points", "Assists", "Rebounds", "Field Goal Percentage");

        // Validate incidents
        List<Map<String, Object>> incidents = jsonPath.getList("incidents");
        assertThat(incidents).isNotEmpty().hasSize(3);
        assertThat(incidents).extracting("incident_type").contains("3PT Made", "Foul Committed", "Substitution");
    }
}
