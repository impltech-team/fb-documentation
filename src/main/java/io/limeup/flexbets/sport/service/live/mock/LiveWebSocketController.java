package io.limeup.flexbets.sport.service.live.mock;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Profile("mock")
@Component
public class LiveWebSocketController extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private final Map<String, List<Map<String, Object>>> subscriptions = new ConcurrentHashMap<>();
    private final Map<Integer, Map<String, Object>> lastEventState = new ConcurrentHashMap<>();

    @Autowired
    @Lazy
    private LiveDataSimulator liveDataSimulator;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("✅ WebSocket Client Connected: " + session.getId());
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
        int eventId = jsonNode.get("event_id").asInt();
        List<Map<String, Object>> subList = new ArrayList<>();

        for (JsonNode sub : jsonNode.get("subscriptions")) {
            Map<String, Object> subData = new HashMap<>();
            subData.put("sub_participant_id", sub.get("sub_participant_id").asInt());
            subData.put("markets", objectMapper.convertValue(sub.get("markets"), List.class));
            subList.add(subData);
        }

        subscriptions.put(session.getId(), subList);
        System.out.println("📡 Client subscribed: " + session.getId() + " -> " + subList);

        sendFullEventData(session, eventId, subList);
    }

    private void sendFullEventData(WebSocketSession session, int eventId, List<Map<String, Object>> subList) throws IOException {
        Map<String, Object> fullEventData = liveDataSimulator.generateRandomUpdate();
        Map<String, Object> filteredData = filterEventData(fullEventData, subList);
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

        int eventId = (int) fullUpdate.get("id");

        for (WebSocketSession session : new HashSet<>(sessions)) {
            if (!session.isOpen()) {
                sessions.remove(session);
                continue;
            }

            List<Map<String, Object>> clientSubscriptions = subscriptions.get(session.getId());
            if (clientSubscriptions != null) {
                try {
                    Map<String, Object> lastState = lastEventState.get(eventId);
                    Map<String, Object> filteredUpdate = filterEventData(fullUpdate, clientSubscriptions);

                    Map<String, Object> deltaUpdate = computeDelta(lastState, filteredUpdate);

                    if (!deltaUpdate.isEmpty()) {
                        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(deltaUpdate)));
                        lastEventState.put(eventId, filteredUpdate);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Map<String, Object> computeDelta(Map<String, Object> lastState, Map<String, Object> newState) {
        Map<String, Object> delta = new HashMap<>();
        if (lastState == null) {
            return newState; // First update, send full data
        }

        List<Map<String, Object>> lastParticipants = (List<Map<String, Object>>) lastState.get("participants");
        List<Map<String, Object>> newParticipants = (List<Map<String, Object>>) newState.get("participants");

        List<Map<String, Object>> changedParticipants = new ArrayList<>();

        for (int i = 0; i < newParticipants.size(); i++) {
            Map<String, Object> newParticipant = newParticipants.get(i);
            Map<String, Object> lastParticipant = lastParticipants.size() > i ? lastParticipants.get(i) : null;

            if (lastParticipant == null) {
                changedParticipants.add(newParticipant);
                continue;
            }

            List<Map<String, Object>> newSubParticipants = (List<Map<String, Object>>) newParticipant.get("subparticipants");
            List<Map<String, Object>> lastSubParticipants = (List<Map<String, Object>>) lastParticipant.get("subparticipants");

            List<Map<String, Object>> changedSubParticipants = new ArrayList<>();

            for (int j = 0; j < newSubParticipants.size(); j++) {
                Map<String, Object> newSubParticipant = newSubParticipants.get(j);
                Map<String, Object> lastSubParticipant = lastSubParticipants.size() > j ? lastSubParticipants.get(j) : null;

                if (lastSubParticipant == null) {
                    changedSubParticipants.add(newSubParticipant);
                    continue;
                }

                List<Map<String, Object>> newMarkets = (List<Map<String, Object>>) newSubParticipant.get("markets");
                List<Map<String, Object>> lastMarkets = (List<Map<String, Object>>) lastSubParticipant.get("markets");

                List<Map<String, Object>> changedMarkets = new ArrayList<>();

                for (int k = 0; k < newMarkets.size(); k++) {
                    Map<String, Object> newMarket = newMarkets.get(k);
                    Map<String, Object> lastMarket = lastMarkets.size() > k ? lastMarkets.get(k) : null;

                    if (lastMarket == null) {
                        changedMarkets.add(newMarket);
                        continue;
                    }

                    Map<String, Object> changedMarket = new HashMap<>();
                    changedMarket.put("market_id", newMarket.get("market_id"));

                    if (!Objects.equals(newMarket.get("price"), lastMarket.get("price"))) {
                        changedMarket.put("price", newMarket.get("price"));
                    }

                    if (!Objects.equals(newMarket.get("stat_value"), lastMarket.get("stat_value"))) {
                        changedMarket.put("stat_value", newMarket.get("stat_value"));
                    }

                    if (changedMarket.size() > 1) {
                        changedMarkets.add(changedMarket);
                    }
                }

                if (!changedMarkets.isEmpty()) {
                    Map<String, Object> changedSubParticipant = new HashMap<>();
                    changedSubParticipant.put("id", newSubParticipant.get("sub_participant_id"));
                    changedSubParticipant.put("markets", changedMarkets);
                    changedSubParticipants.add(changedSubParticipant);
                }
            }

            if (!changedSubParticipants.isEmpty()) {
                Map<String, Object> changedParticipant = new HashMap<>();
                changedParticipant.put("id", newParticipant.get("id"));
                changedParticipant.put("subparticipants", changedSubParticipants);
                changedParticipants.add(changedParticipant);
            }
        }

        if (!changedParticipants.isEmpty()) {
            delta.put("id", newState.get("id"));
            delta.put("participants", changedParticipants);
        }

        return delta;
    }

    private Map<String, Object> filterEventData(Map<String, Object> fullEventData, List<Map<String, Object>> clientSubscriptions) {
        Map<String, Object> filteredData = new HashMap<>();
        filteredData.put("id", fullEventData.get("id"));
        filteredData.put("participants", new ArrayList<>());

        List<Map<String, Object>> participants = (List<Map<String, Object>>) fullEventData.get("participants");

        for (Map<String, Object> participant : participants) {
            List<Map<String, Object>> filteredSubParticipants = new ArrayList<>();
            List<Map<String, Object>> subParticipants = (List<Map<String, Object>>) participant.get("subparticipants");

            for (Map<String, Object> subParticipant : subParticipants) {
                int subParticipantId = (int) subParticipant.get("sub_participant_id");

                for (Map<String, Object> sub : clientSubscriptions) {
                    if ((int) sub.get("sub_participant_id") == subParticipantId) {
                        List<Integer> requestedMarkets = (List<Integer>) sub.get("markets");
                        List<Map<String, Object>> filteredMarkets = new ArrayList<>();

                        List<Map<String, Object>> markets = (List<Map<String, Object>>) subParticipant.get("markets");

                        for (Map<String, Object> market : markets) {
                            if (requestedMarkets.contains(market.get("market_id"))) {
                                filteredMarkets.add(market);
                            }
                        }

                        if (!filteredMarkets.isEmpty()) {
                            Map<String, Object> filteredSub = new HashMap<>(subParticipant);
                            filteredSub.put("markets", filteredMarkets);
                            filteredSubParticipants.add(filteredSub);
                        }
                    }
                }
            }

            if (!filteredSubParticipants.isEmpty()) {
                Map<String, Object> filteredParticipant = new HashMap<>(participant);
                filteredParticipant.put("subparticipants", filteredSubParticipants);
                ((List<Map<String, Object>>) filteredData.get("participants")).add(filteredParticipant);
            }
        }

        return filteredData;
    }
}
