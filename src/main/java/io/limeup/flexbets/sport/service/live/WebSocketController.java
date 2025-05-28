package io.limeup.flexbets.sport.service.live;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.limeup.flexbets.sport.model.*;
import io.limeup.flexbets.sport.repository.*;
import io.limeup.flexbets.sport.utils.ConstantUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WebSocketController extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private final Map<String, Integer> subscriptions = new ConcurrentHashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    private final LiveEventRepository eventRepo;
    private final LiveLsParticipantRepository participantRepo;
    private final LiveParticipantRepository baseRepo;
    private final EventNormalizer eventNormalizer;
    private final LiveLsScoreboardRepository scoreboardRepo;
    private final LiveLsPeriodRepository periodRepo;
    private final LiveLsPeriodIncidentRepository incidentRepo;
    private final LiveLsStatisticRepository statRepo;
    private final LiveLsFixtureExtraDataRepository extraRepo;
    private final LiveLsMarketRepository marketRepo;
    private final LiveLsBetRepository betRepo;


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

    protected void handleSubscription(WebSocketSession session, JsonNode jsonNode) throws IOException {
        int lsId = jsonNode.path(ConstantUtils.StatScoreWebClient.EVENT_ID).asInt();
        subscriptions.put(session.getId(), lsId);
        sendFullEventToSession(session, lsId);
    }

    private void sendFullEventToSession(WebSocketSession session, long lsId) {
        try {
            Optional<LiveEvent> evOpt = eventRepo.findByLsId(lsId);
            LiveEvent ev = evOpt.get();

            List<LiveParticipant> baseParts = new ArrayList<>();
            if (evOpt.isEmpty()) {
                baseParts = baseRepo.findByEventId(ev.getId());
            }
           List<LiveLsPeriod> periods = periodRepo.findByFixtureId(lsId);
            List<LiveLsScoreboard> score = scoreboardRepo.findByFixtureId(lsId);
            List<LiveLsPeriodIncident> incidents = incidentRepo.findByPeriodIdIn(
            periods.stream().map(LiveLsPeriod::getId).toList());
            List<LiveLsStatistic> stats = statRepo.findByFixtureId(lsId);
            List<LiveLsParticipant> parts = participantRepo.findByFixtureId(lsId);

            List<LiveLsMarket> markets = marketRepo.findByFixtureId(ev.getLsId());
            List<LiveLsBet> bets = betRepo.findByMarketIdIn(
                    markets.stream().map(LiveLsMarket::getId).toList());

            Map<String, Object> fullDto = eventNormalizer.normalizeFull(
                    ev, score,  incidents, stats, parts, baseParts, markets, bets);

            String payload = objectMapper.writeValueAsString(fullDto);
            session.sendMessage(new TextMessage(payload));
            log.info("📤 Pushed full event lsId={} to session {}", lsId, session.getId());

        } catch (Exception ex) {
            log.error("❌ Error pushing full event for lsId {}", lsId, ex);
        }
    }
}




