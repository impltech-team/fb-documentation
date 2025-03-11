package io.limeup.flexbets.sport.config.stub;

import com.atlassian.ta.wiremockpactgenerator.WireMockPactGenerator;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CompetitionsWireMockTest extends BaseWireMockTest {

    private static final String COMPETITIONS_LIST_ENDPOINT = "/competitions/list";

    @BeforeAll
    public void setupStub() {
        getWireMockServer().addMockServiceRequestListener(
                WireMockPactGenerator.builder("competitions-provider", "competitions-consumer").build()
        );

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(COMPETITIONS_LIST_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("competitions_response.json")
                        .withStatus(200)));
    }

    @Test
    @DisplayName("Verify Competitions List API Response")
    public void testCompetitionsList() {
        Response response = given()
                .when()
                .get(COMPETITIONS_LIST_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        // Validate main response structure
        assertThat(jsonPath.getInt("count")).isEqualTo(2);
        assertThat(jsonPath.getInt("page")).isEqualTo(1);
        assertThat(jsonPath.getInt("total_pages")).isEqualTo(1);

        // Validate competitions list
        List<Map<String, Object>> competitions = jsonPath.getList("competitions");
        assertThat(competitions).isNotEmpty().hasSize(2);

        // Validate first competition (NBA)
        Map<String, Object> firstCompetition = competitions.get(0);
        assertThat(firstCompetition.get("name")).isEqualTo("NBA");
        assertThat(firstCompetition.get("type")).isEqualTo("country_league");
        assertThat(firstCompetition.get("sport_name")).isEqualTo("Basketball");
        assertThat(firstCompetition.get("area_name")).isEqualTo("United States");
        assertThat(firstCompetition.get("status_type")).isEqualTo("active");

        // Validate second competition (UEFA Champions League)
        Map<String, Object> secondCompetition = competitions.get(1);
        assertThat(secondCompetition.get("name")).isEqualTo("UEFA Champions League");
        assertThat(secondCompetition.get("type")).isEqualTo("international_clubs");
        assertThat(secondCompetition.get("sport_name")).isEqualTo("Soccer");
        assertThat(secondCompetition.get("area_name")).isEqualTo("Europe");
        assertThat(secondCompetition.get("status_type")).isEqualTo("active");
    }
}
