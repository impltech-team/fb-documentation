package io.limeup.flexbets.sport.service.live.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MockWebSocketControllerTest {

    private MockWebSocketController controller;
    private MockLiveDataSimulator simulator;
    private MockLiveDataFilter filter;
    private MockLiveDataDeltaComputer deltaComputer;
    private WebSocketSession session;

    @BeforeEach
    void setUp() {
        simulator = mock(MockLiveDataSimulator.class);
        filter = mock(MockLiveDataFilter.class);
        deltaComputer = mock(MockLiveDataDeltaComputer.class);
        session = mock(WebSocketSession.class);
        controller = new MockWebSocketController(simulator, filter, deltaComputer);
    }

    @Test
    void afterConnectionEstablishedShouldAddSession() {
        when(session.getId()).thenReturn("test-session");
        controller.afterConnectionEstablished(session);
    }

    @Test
    void handleTextMessageShouldSubscribeClient() throws Exception {
        when(session.getId()).thenReturn("test-session");

        String payload = """
                {
                  "action": "subscribe",
                  "event_id": 101,
                  "subscriptions": [
                    {
                      "sub_participant_id": 1,
                      "market_ids": [10, 20]
                    }
                  ]
                }
                """;

        Map<String, Object> fullEventData = Map.of("id", 101);
        when(simulator.generateRandomUpdate(eq(101))).thenReturn(fullEventData);
        when(filter.filterEventData(any(), any())).thenReturn(fullEventData);

        controller.handleTextMessage(session, new TextMessage(payload));

        verify(session).sendMessage(any(TextMessage.class));
    }

    @Test
    void broadcastLiveUpdateShouldSendDeltaIfSubscribed() throws Exception {
        WebSocketSession session = mock(WebSocketSession.class);
        when(session.getId()).thenReturn("test-session");
        when(session.isOpen()).thenReturn(true);

        controller.afterConnectionEstablished(session);

        String subscribePayload = """
        {
          "action": "subscribe",
          "event_id": 123,
          "subscriptions": [
            {
              "sub_participant_id": 1,
              "market_ids": [10]
            }
          ]
        }
        """;

        Map<String, Object> fullData = Map.of("id", 123);
        when(simulator.generateRandomUpdate(eq(123))).thenReturn(fullData);
        when(filter.filterEventData(any(), any())).thenReturn(fullData);
        when(deltaComputer.computeDelta(any(), any()))
                .thenReturn(new HashMap<>(Map.of("price", 2.0)));

        controller.handleTextMessage(session, new TextMessage(subscribePayload));
        controller.broadcastLiveUpdate("{\"id\":123}");

        verify(session, times(2)).sendMessage(any(TextMessage.class));
    }
}

