package io.limeup.flexbets.sport.config.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseWireMockTest {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String REGULAR_SEASON = "Regular Season";
    public static final String SEASON = "NBA 2024/25";
    public static final String VENUE = "Staples Center";
    public static final String BASKETBALL = "Basketball";
    public static final String SOCCER = "Soccer";
    public static final String ACTIVE = "active";
    public static final String FINISHED = "finished";
    public static final String FROM_TV = "from_tv";
    public static final String PLAYER_NAME = "player_name";
    public static final String MARKET_ID = "market_id";
    public static final String POSITION = "position";
    public static final String CENTER = "Center";
    public static final String FORWARD = "Forward";
    public static final String COMPETITION_ID = "competition_id";
    public static final String STAT_NAME = "stat_name";
    public static final String SPORTS = "sports";
    public static final String PARTICIPANTS = "participants";

    private WireMockServer wireMockServer;

    public WireMockServer getWireMockServer() {
        return wireMockServer;
    }

    @BeforeAll
    public void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig()
                .port(9099)
                .usingFilesUnderDirectory("src/test/resources"));
        wireMockServer.start();

        RestAssured.baseURI = "http://localhost:9099";
    }

}
