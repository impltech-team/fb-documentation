package io.limeup.flexbets.sport.service.statscore.impl;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.limeup.flexbets.sport.config.wiremock.BaseWireMockTest;
import io.limeup.flexbets.sport.dto.statscore.ListWrapper;
import io.limeup.flexbets.sport.dto.statscore.StatScoreParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.ParticipantQueryParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.List;

class StatScoreClientImplTest extends BaseWireMockTest {

    private static final String AREAS_LIST_ENDPOINT = "/events/123/sub-participants";
    private static final String PARTICIPANTS_LIST_ENDPOINT = "/participants";

    private StatScoreClientImpl statScoreClient;

    @BeforeEach
    public void initClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:9099")
                .build();

        statScoreClient = new StatScoreClientImpl(webClient, new com.fasterxml.jackson.databind.ObjectMapper());
    }

    @BeforeAll
    void setupStub() {
        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(AREAS_LIST_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_event_sub_participants_response.json")
                        .withStatus(200)));
        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(PARTICIPANTS_LIST_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_participants_response.json")
                        .withStatus(200)));
    }

    @Test
    @DisplayName("Verify getEventSubParticipants returns Curry")
    void testGetEventSubParticipants() {
        StepVerifier.create(statScoreClient.getEventSubParticipants(123))
                .assertNext(resp -> {
                    ListWrapper<StatScoreSubParticipantDTO> wrapper = resp.getApi().getData();
                    List<StatScoreSubParticipantDTO> items = wrapper.getItems();
                    Assertions.assertEquals(1, items.size());
                    Assertions.assertEquals("Stephen Curry", items.get(0).getName());
                    Assertions.assertEquals(464938, items.get(0).getId());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getParticipants returns expected teams")
    void testGetParticipants() {
        ParticipantQueryParams params = new ParticipantQueryParams();
        params.setSportId(1);

        StepVerifier.create(statScoreClient.getParticipants(params))
                .assertNext(resp -> {
                    List<StatScoreParticipantDTO> participants = resp.getApi().getData().getItems();
                    Assertions.assertEquals(2, participants.size());

                    Assertions.assertEquals("Memphis Grizzlies", participants.get(0).getName());
                    Assertions.assertEquals("Philadelphia 76ers", participants.get(1).getName());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getParticipants with all query params")
    void testGetParticipantsWithAllParams() {
        ParticipantQueryParams params = new ParticipantQueryParams();
        params.setSportId(1);
        params.setLimit(10);
        params.setPage(2);
        params.setSeasonId(5);
        params.setAreaId(191);
        params.setType(ParticipantQueryParams.Type.TEAM);
        params.setSubtype(ParticipantQueryParams.Subtype.ATHLETE);
        params.setVirtual("no");
        params.setMultiIds("1,2,3");

        StepVerifier.create(statScoreClient.getParticipants(params))
                .assertNext(resp -> {
                    var items = resp.getApi().getData().getItems();
                    Assertions.assertFalse(items.isEmpty());
                })
                .verifyComplete();
    }

}

