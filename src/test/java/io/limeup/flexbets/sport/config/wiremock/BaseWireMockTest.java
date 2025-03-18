package io.limeup.flexbets.sport.config.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseWireMockTest {

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
