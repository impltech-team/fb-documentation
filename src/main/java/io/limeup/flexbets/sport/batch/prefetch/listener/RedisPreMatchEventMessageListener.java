package io.limeup.flexbets.sport.batch.prefetch.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.limeup.flexbets.sport.dto.trade360.Trade360BetDTO;
import io.limeup.flexbets.sport.dto.trade360.Trade360MarketDTO;
import io.limeup.flexbets.sport.model.*;
import io.limeup.flexbets.sport.repository.*;
import io.limeup.flexbets.sport.service.BetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisPreMatchEventMessageListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final LiveLsEventRepository eventRepo;
    private final LiveLsScoreboardRepository scoreboardRepo;
    private final LiveLsPeriodRepository periodRepo;
    private final LiveLsPeriodIncidentRepository incidentRepo;
    private final LiveLsStatisticRepository statRepo;
    private final LiveLsParticipantRepository participantRepo;
    private final LiveLsMarketRepository marketRepo;
    private final LiveLsBetRepository betRepo;
    private final LiveLsFixtureExtraDataRepository extraRepo;
    private final BetService betService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String json = new String(message.getBody(), StandardCharsets.UTF_8);
            JsonNode root = objectMapper.readTree(json);
            JsonNode events = root.path("Body").path("Events");

            if (!events.isArray() || events.isEmpty()) return;



            for (JsonNode eventNode : events) {
                Long fixtureId = eventNode.path("FixtureId").asLong();
                LiveLsEvent event = eventRepo.save(
                        LiveLsEvent.builder()
                                .fixtureId(fixtureId)
                                .receivedAt(LocalDateTime.now())
                                .build()
                );
                List<Trade360MarketDTO> marketBetsList = new ArrayList<>();

                JsonNode scoreboard = eventNode.path("Livescore").path("Scoreboard");
                if (!scoreboard.isMissingNode()) {
                    scoreboardRepo.save(LiveLsScoreboard.builder()
                            .fixtureId(fixtureId)
                            .lsScoreboardStatus(scoreboard.path("Status").asInt())
                            .lsScoreboardCurrentPeriod(scoreboard.path("CurrentPeriod").asInt())
                            .lsScoreboardTime(scoreboard.path("Time").asText())
                            .lsScoreboardResultPosition1(getResultValue(scoreboard, 1))
                            .lsScoreboardResultPosition2(getResultValue(scoreboard, 2))
                            .event(event)
                            .build());
                }

                JsonNode stats = eventNode.path("Livescore").path("Statistics");
                if (stats.isArray()) {
                    for (JsonNode s : stats) {
                        statRepo.save(LiveLsStatistic.builder()
                                .fixtureId(fixtureId)
                                .lsStatType(s.path("Type").asInt())
                                .lsStatResultPosition1(getResultValue(s, 1))
                                .lsStatResultPosition2(getResultValue(s, 2))
                                .event(event)
                                .build());
                    }
                }

                JsonNode periods = eventNode.path("Livescore").path("Periods");
                if (periods.isArray()) {
                    for (JsonNode p : periods) {
                        LiveLsPeriod savedPeriod = periodRepo.save(LiveLsPeriod.builder()
                                .fixtureId(fixtureId)
                                .lsPeriodType(p.path("Type").asInt())
                                .lsPeriodIsFinished(p.path("IsFinished").asBoolean())
                                .lsPeriodIsConfirmed(p.path("IsConfirmed").asBoolean())
                                .lsPeriodSequenceNumber(p.path("SequenceNumber").asInt())
                                .lsPeriodResultPosition1(getResultValue(p, 1))
                                .lsPeriodResultPosition2(getResultValue(p, 2))
                                .event(event)
                                .build());

                        JsonNode incidents = p.path("Incidents");
                        if (incidents.isArray()) {
                            for (JsonNode i : incidents) {
                                incidentRepo.save(LiveLsPeriodIncident.builder()
                                        .periodId(savedPeriod.getId())
                                        .lsIncidentPeriod(i.path("Period").asInt())
                                        .lsIncidentType(i.path("IncidentType").asInt())
                                        .lsIncidentSeconds(i.path("Seconds").asInt())
                                        .lsIncidentParticipantPosition(i.path("ParticipantPosition").asText())
                                        .lsIncidentResultPosition1(getResultValue(i, 1))
                                        .lsIncidentResultPosition2(getResultValue(i, 2))
                                        .build());
                            }
                        }
                    }
                }

                JsonNode participants = eventNode.path("Fixture").path("Participants");
                if (participants.isArray()) {
                    for (JsonNode p : participants) {
                        participantRepo.save(LiveLsParticipant.builder()
                                .id(p.path("Id").asLong())
                                .fixtureId(fixtureId)
                                .lsParticipantName(p.path("Name").asText())
                                .lsParticipantPosition(p.path("Position").asInt())
                                .event(event)
                                .build());
                    }
                }

                JsonNode extras = eventNode.path("Fixture").path("FixtureExtraData");
                if (extras.isArray()) {
                    for (JsonNode extra : extras) {
                        extraRepo.save(LiveLsFixtureExtraData.builder()
                                .fixtureId(fixtureId)
                                .name(extra.path("Name").asText())
                                .value(extra.path("Value").asText())
                                .event(event)
                                .build());
                    }
                }

                JsonNode markets = eventNode.path("Markets");
                if (markets.isArray()) {
                    for (JsonNode m : markets) {
                        Long marketId = m.path("Id").asLong();
                        String marketName = m.path("Name").asText();
                        LiveLsMarket savedMarket = marketRepo.save(LiveLsMarket.builder()
                                .fixtureId(fixtureId)
                                .marketId(marketId)
                                .marketName(marketName)
                                .marketMainLine(m.path("MainLine").asText(null))
                                .event(event)
                                .build());

                        Trade360MarketDTO trade360Market = new Trade360MarketDTO();
                        trade360Market.setId(marketId.intValue());
                        trade360Market.setName(marketName);

                        List<Trade360BetDTO> trade360Bets = new ArrayList<>();
                        trade360Market.setBets(trade360Bets);
                        marketBetsList.add(trade360Market);

                        JsonNode bets = m.path("Bets");
                        if (bets.isArray()) {
                            for (JsonNode b : bets) {
                                Long id = b.path("Id").asLong();
                                String name = b.path("Name").asText();
                                String line = b.path("Line").asText();
                                String baseLine = b.path("BaseLine").asText();
                                Integer status = b.path("Status").asInt();
                                String price = b.path("Price").asText(null);

                                betRepo.save(LiveLsBet.builder()
                                        .marketId(savedMarket.getId())
                                        .betId(id)
                                        .betName(name)
                                        .betProbability(b.path("Probability").asDouble())
                                        .betSuspensionReason(b.path("SuspensionReason").asText(null))
                                        .betCalculatedMargin(b.path("CalculatedMargin").asText(null))
                                        .betSerializedCalculatedMargin(b.path("SerializedCalculatedMargin").asText(null))
                                        .betLine(line)
                                        .betBaseLine(baseLine)
                                        .betStatus(status)
                                        .betStartPrice(b.path("StartPrice").asText(null))
                                        .betPrice(price)
                                        .betProviderId(b.path("ProviderBetId").asText(null))
                                        .betLastUpdate(parseInstant(b.path("LastUpdate").asText(null)))
                                        .betSerializedLastUpdate(b.path("SerializedLastUpdate").asText(null))
                                        .build());

                                Trade360BetDTO betDTO = Trade360BetDTO.builder()
                                        .id(id)
                                        .name(name)
                                        .participantName(b.path("PlayerName").asText(null))
                                        .line(line)
                                        .baseLine(baseLine)
                                        .status(status)
                                        .price(price)
                                        .settlement(b.path("Settlement").asInt(0))
                                        .suspensionReason(b.path("SuspensionReason").asInt(0))
                                        .lastUpdated(parseToLocalDateTime(b.path("LastUpdate").asText(null)))
                                        .build();

                                trade360Bets.add(betDTO);
                            }
                        }
                    }
                }

                if (!marketBetsList.isEmpty()) {
                    betService.updateBetsInfoFromTrade360(fixtureId, marketBetsList);
                }
            }

        } catch (Exception e) {
            log.error("\u274C Error parsing PreMatch event", e);
        }
    }

    private Integer getResultValue(JsonNode node, int position) {
        JsonNode results = node.path("Results");
        if (results.isArray()) {
            for (JsonNode r : results) {
                if (r.path("Position").asText().equals(String.valueOf(position))) {
                    return r.path("Value").asInt();
                }
            }
        }
        return null;
    }

    private Instant parseInstant(String text) {
        try {
            return text != null ? Instant.parse(text) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDateTime parseToLocalDateTime(String isoString) {
        if (isoString == null || isoString.isBlank()) {
            return null;
        }
        return OffsetDateTime.parse(isoString).toLocalDateTime();
    }
}
