package io.limeup.flexbets.sport.config.wiremock;

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

public class AreasWireMockTest extends BaseWireMockTest {

    private static final String AREAS_LIST_ENDPOINT = "/areas/list";

    @BeforeAll
    public void setupStub() {
        getWireMockServer().addMockServiceRequestListener(
                WireMockPactGenerator.builder("areas-provider", "areas-consumer").build()
        );

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(AREAS_LIST_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBodyFile("areas_response.json")
                        .withStatus(200)));
    }

    @Test
    @DisplayName("Verify Areas List API Response")
    public void testAreasList() {
        Response response = given()
                .when()
                .get(AREAS_LIST_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        // Validate main response structure
        assertThat(jsonPath.getInt("count")).isEqualTo(2);
        assertThat(jsonPath.getInt("page")).isEqualTo(1);
        assertThat(jsonPath.getInt("total_pages")).isEqualTo(1);

        // Validate areas list
        List<Map<String, Object>> areas = jsonPath.getList("areas");
        assertThat(areas).isNotEmpty().hasSize(2);

        // Validate first area (United States)
        Map<String, Object> firstArea = areas.get(0);
        assertThat(firstArea.get("name")).isEqualTo("United States");
        assertThat(firstArea.get("area_code")).isEqualTo("US");

        // Validate second area (Europe)
        Map<String, Object> secondArea = areas.get(1);
        assertThat(secondArea.get("name")).isEqualTo("Europe");
        assertThat(secondArea.get("area_code")).isNull();
    }
}
