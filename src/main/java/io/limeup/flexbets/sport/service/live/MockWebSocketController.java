package io.limeup.flexbets.sport.service.live;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.limeup.flexbets.sport.model.*;
import io.limeup.flexbets.sport.repository.*;
import io.limeup.flexbets.sport.utils.ConstantUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class MockWebSocketController extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private final Map<String, List<Map<String, Object>>> subscriptions = new ConcurrentHashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    private final LiveEventRepository eventRepo;
    private final LiveParticipantRepository participantRepo;
    private final LiveParticipantResultRepository resultRepo;
    private final EventNormalizer eventNormalizer;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        log.info("WebSocket Client Connected: {}", session.getId());
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
        log.info("Client subscribed: {} -> {}", session.getId(), subList);
        sendEventFromDbToSession(session, eventId);
    }

    private void sendEventFromDbToSession(WebSocketSession session, int eventId) {
        try {
            List<LiveEvent> eventOpt = eventRepo.findByEventDataId((long) eventId);
            if (eventOpt.isEmpty()) return;

            List<LiveParticipant> participentOpt = participantRepo.findByEvent(eventOpt.getFirst());
            List<LiveParticipantResult> resultList = new ArrayList<>() ;
            for (LiveParticipant p :participentOpt) {
                resultList.addAll(resultRepo.findByParticipant(p));
            }

            JsonNode eventJson = objectMapper.valueToTree(eventOpt.getFirst());
            JsonNode rawJson = objectMapper.valueToTree(participentOpt);
            JsonNode resultJson = objectMapper.valueToTree(resultList);

            LiveEventDTO normalize = eventNormalizer.normalize(eventJson, rawJson,resultJson);

            String payload = objectMapper.writeValueAsString(normalize);

            if (session.isOpen()) {
                session.sendMessage(new TextMessage(payload));
                log.info("\uD83D\uDCE4 Sent DB event {} to WebSocket {}", eventId, session.getId());
            }

        } catch (Exception e) {
            log.error(" Failed to send event from DB to session", e);
        }
    }

    public void pushEventFromDb(Long eventId) {
        try {
            boolean hasSubscribers = sessions.stream()
                    .filter(WebSocketSession::isOpen)
                    .anyMatch(session -> {
                        List<Map<String, Object>> subList = subscriptions.get(session.getId());
                        if (subList == null || subList.isEmpty()) return false;
                        Integer subscribedEventId = (Integer) subList.get(0).get(ConstantUtils.StatScoreWebClient.EVENT_ID);
                        return subscribedEventId != null && subscribedEventId.equals(eventId.intValue());
                    });

            if (!hasSubscribers) {
                log.info("\uD83D\uDD15 No active subscriptions for event {}", eventId);
                return;
            }

            Optional<LiveEvent> eventOpt = eventRepo.findById(eventId);
            if (eventOpt.isEmpty()) return;
            List<LiveParticipant> participentOpt = participantRepo.findByEvent(eventOpt.get());
            List<LiveParticipantResult> resultList = new ArrayList<>() ;
            for (LiveParticipant p :participentOpt) {
                resultList.addAll(resultRepo.findByParticipant(p));
            }

            JsonNode eventJson = objectMapper.valueToTree(eventOpt.get());
            JsonNode rawJson = objectMapper.valueToTree(participentOpt);
            JsonNode resultJson = objectMapper.valueToTree(resultList);


            LiveEventDTO normalize = eventNormalizer.normalize(eventJson, rawJson,resultJson);
            String payload = objectMapper.writeValueAsString(normalize);

            for (WebSocketSession session : sessions) {
                List<Map<String, Object>> subList = subscriptions.get(session.getId());
                if (subList == null || subList.isEmpty()) continue;

                int subscribedEventId = (int) subList.get(0).get(ConstantUtils.StatScoreWebClient.EVENT_ID);
                if (subscribedEventId == eventId.intValue() && session.isOpen()) {
                    session.sendMessage(new TextMessage(payload));
                    log.info("\uD83D\uDCE4 Sent DB event {} to WebSocket {}", eventId, session.getId());
                }
            }

        } catch (Exception e) {
            log.error("\u274C Failed to send DB event to WebSocket", e);
        }
    }
}
