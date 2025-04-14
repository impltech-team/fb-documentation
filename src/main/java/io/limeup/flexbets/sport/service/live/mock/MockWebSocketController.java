package io.limeup.flexbets.sport.service.live.mock;

import io.limeup.flexbets.sport.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Profile("mock")
@Component
public class MockWebSocketController extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private final Map<String, List<Map<String, Object>>> subscriptions = new ConcurrentHashMap<>();
    private final Map<Integer, Map<String, Object>> lastEventState = new ConcurrentHashMap<>();

    private final MockLiveDataSimulator liveDataSimulator;
    private final MockLiveDataFilter mockLiveDataFilter;
    private final MockLiveDataDeltaComputer mockLiveDataDeltaComputer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public MockWebSocketController(@Lazy MockLiveDataSimulator liveDataSimulator, MockLiveDataFilter mockLiveDataFilter, MockLiveDataDeltaComputer mockLiveDataDeltaComputer) {
        this.liveDataSimulator = liveDataSimulator;
        this.mockLiveDataFilter = mockLiveDataFilter;
        this.mockLiveDataDeltaComputer = mockLiveDataDeltaComputer;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        log.info("WebSocket Client Connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        JsonNode jsonNode = objectMapper.readTree(payload);

        if (jsonNode.has("action") && "subscribe".equals(jsonNode.get("action").asText())) {
            handleSubscription(session, jsonNode);
        }
    }

    private void handleSubscription(WebSocketSession session, JsonNode jsonNode) throws IOException {
        int eventId = jsonNode.get(ConstantUtils.StatScoreWebClient.EVENT_ID).asInt();
        List<Map<String, Object>> subList = new ArrayList<>();
        subList.add(Map.of(ConstantUtils.StatScoreWebClient.EVENT_ID, eventId));
        if (jsonNode.has("subscriptions")) {
            for (JsonNode sub : jsonNode.get("subscriptions")) {
                Map<String, Object> subData = new HashMap<>();
                subData.put(ConstantUtils.Mock.SUB_PARTICIPANT_ID, sub.get(ConstantUtils.Mock.SUB_PARTICIPANT_ID).asInt());
                subData.put(ConstantUtils.Mock.MARKET_IDS, objectMapper.convertValue(sub.get(ConstantUtils.Mock.MARKET_IDS), List.class));
                subList.add(subData);
            }
        }
        subscriptions.put(session.getId(), subList);
        log.info("Client subscribed: " + session.getId() + " -> " + subList);
        sendFullEventData(session, eventId, subList);
    }

    private void sendFullEventData(WebSocketSession session, int eventId, List<Map<String, Object>> subList) throws IOException {
        Map<String, Object> fullEventData = liveDataSimulator.generateRandomUpdate(eventId);
        Map<String, Object> filteredData = mockLiveDataFilter.filterEventData(fullEventData, subList);
        lastEventState.put(eventId, filteredData);
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(filteredData)));
    }

    public void broadcastLiveUpdate(String jsonUpdate) {
        Map<String, Object> fullUpdate;
        try {
            fullUpdate = objectMapper.readValue(jsonUpdate, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        for (WebSocketSession session : new HashSet<>(sessions)) {
            if (!session.isOpen()) {
                sessions.remove(session);
                continue;
            }

            List<Map<String, Object>> clientSubscriptions = subscriptions.get(session.getId());
            if (clientSubscriptions != null) {
                try {
                    int eventId = (int) clientSubscriptions.get(0).get(ConstantUtils.StatScoreWebClient.EVENT_ID);
                    Map<String, Object> lastState = lastEventState.get(eventId);
                    Map<String, Object> filteredUpdate = mockLiveDataFilter.filterEventData(fullUpdate, clientSubscriptions);

                    Map<String, Object> deltaUpdate = mockLiveDataDeltaComputer.computeDelta(lastState, filteredUpdate);
                    deltaUpdate.put("id", eventId);
                    if (!deltaUpdate.isEmpty()) {
                        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(deltaUpdate)));
                        lastEventState.put(eventId, filteredUpdate);
                        log.info("Mock Updates sent: {}", objectMapper.writeValueAsString(deltaUpdate));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
