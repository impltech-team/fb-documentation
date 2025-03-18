package io.limeup.flexbets.sport.config.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class MarketsListWireMockTest extends BaseWireMockTest {

    private static final String MARKETS_LIST_ENDPOINT = "/markets/list";

    private static final String ODDS_BATCH_ENDPOINT = "/odds/batch";

    @BeforeAll
    private void setupStub() {
        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathMatching(MARKETS_LIST_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("markets_response.json")
                        .withStatus(200)));
        getWireMockServer().stubFor(WireMock.post(WireMock.urlPathMatching(ODDS_BATCH_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("odds_batch_response.json")
                        .withStatus(200)));
    }

    @Test
    @DisplayName("Verify Markets List API Response")
    public void testMarketsList() {
        Response response = given()
                .queryParam("competition_id", 1001)
                .when()
                .get(MARKETS_LIST_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        // Validate response structure
        List<Map<String, Object>> markets = jsonPath.getList("$");
        assertThat(markets).isNotEmpty().hasSize(2);

        // Validate market names
        assertThat(markets).extracting("market_name").contains("Under/Over Player Points", "Under/Over Player Rebounds");

        // Validate market types
        assertThat(markets).extracting("type").contains("participant");

        // Validate market IDs
        assertThat(markets).extracting("market_id").contains(7, 78);
    }

    @Test
    @DisplayName("Verify Batch Odds Response")
    public void testBatchOddsResponse() {
        String requestBody = """
        {
          "queries": [
            { "market_id": 7, "sub_participant_id": 101, "event_id": 107645 },
            { "market_id": 8, "sub_participant_id": 101, "event_id": 107645 },
            { "market_id": 7, "sub_participant_id": 201, "event_id": 107645 }
          ]
        }
        """;

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post(ODDS_BATCH_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> oddsList = jsonPath.getList("$");

        // Validate response structure
        assertThat(oddsList).isNotEmpty().hasSize(2);

        // Validate participants and events
        assertThat(oddsList).extracting("sub_participant_name").contains("LeBron James", "Stephen Curry");
        assertThat(oddsList).extracting("sub_participant_id").contains(101, 201);
        assertThat(oddsList).extracting("event_id").contains(107645);
        assertThat(oddsList).extracting("participant_id").contains(1, 2);


        // Validate market
        List<Map<String, Object>> oddsForLeBron = (List<Map<String, Object>>) oddsList.get(0).get("odds");
        assertThat(oddsForLeBron).extracting("market_name").contains("Under/Over Player Points", "Under/Over Player Assists");
        assertThat(oddsForLeBron).extracting("market_id").contains(7, 8);
        assertThat(oddsForLeBron).extracting("price").isNotEmpty();
        assertThat(oddsForLeBron).extracting("stat_value").isNotNull();
    }
}
