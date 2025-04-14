package io.limeup.flexbets.sport.service.live.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Profile("mock")
@Service
public class MockLiveDataSimulator {
    private final MockWebSocketController webSocketController;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    public MockLiveDataSimulator(MockWebSocketController webSocketController) {
        this.webSocketController = webSocketController;
    }

    @Scheduled(fixedRateString = "${scheduler.mock-live-update-interval}")
    public void sendRandomLiveUpdate() {
        try {
            Map<String, Object> update = generateRandomUpdate(random.nextInt());
            String jsonUpdate = objectMapper.writeValueAsString(update);

            webSocketController.broadcastLiveUpdate(jsonUpdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> generateRandomUpdate(Integer eventId) {
        int playedTime = 10 + random.nextInt(35);

        Map<String, Object> update = new HashMap<>();
        update.put("id", eventId);

        List<Map<String, Object>> participants = new ArrayList<>();

        // First Participant
        Map<String, Object> participant1 = new HashMap<>();
        participant1.put("id", 136929);
        participant1.put("name", "Los Angeles Lakers");
        participant1.put("played_time", playedTime);
        participant1.put("team_formation", createFormation());

        List<Map<String, Object>> subParticipants1 = new ArrayList<>();
        subParticipants1.add(createSubParticipant(1, "LeBron James"));
        subParticipants1.add(createSubParticipant(2, "Anthony Davis"));

        participant1.put("subparticipants", subParticipants1);
        participants.add(participant1);

        // Second Participant
        Map<String, Object> participant2 = new HashMap<>();
        participant2.put("id", 1507);
        participant2.put("name", "Phoenix Suns");
        participant2.put("played_time", playedTime);

        List<Map<String, Object>> subParticipants2 = new ArrayList<>();
        subParticipants2.add(createSubParticipant(3, "Kevin Durant"));
        subParticipants2.add(createSubParticipant(4, "Devin Booker"));
        subParticipants2.add(createSubParticipant(5, "Bradley Beal"));

        participant2.put("subparticipants", subParticipants2);
        participants.add(participant2);

        update.put("participants", participants);
        return update;
    }

    private Map<String, Object> createFormation() {
        Map<String, Object> formation = new HashMap<>();
        List<String> basketballFormations = List.of(
                "1-2-2",
                "2-1-2",
                "3-2",
                "1-3-1",
                "2-3"
        );

        formation.put("id", 50000 + random.nextInt(10000));

        String oldFormation = basketballFormations.get(random.nextInt(basketballFormations.size()));
        String newFormation = basketballFormations.get(random.nextInt(basketballFormations.size()));


        formation.put("old_formation", oldFormation);
        formation.put("new_formation", newFormation);

        return formation;
    }

    private Map<String, Object> createSubParticipant(int subParticipantId, String name) {
        Map<String, Object> subParticipant = new HashMap<>();
        subParticipant.put("sub_participant_id", subParticipantId);
        subParticipant.put("player_name", name);

        List<Map<String, Object>> markets = new ArrayList<>();
        markets.add(createMarket(1, "Under/Over Player Points", 30.5));
        markets.add(createMarket(2, "Under/Over Player Rebounds", 4.5));
        markets.add(createMarket(3, "Under/Over Player Blocks", 3.5));
        subParticipant.put("markets", markets);

        return subParticipant;
    }

    private Map<String, Object> createMarket(int marketId, String marketName, double line) {
        Map<String, Object> market = new HashMap<>();
        market.put("market_id", marketId);
        market.put("market_name", marketName);
        market.put("line", line);
        market.put("bet_type", "Over");
        market.put("status", "Open");
        market.put("price", round(random.nextDouble() * (2.25 - 1.75) + 1.75, 2)); // Random price between 1.75 - 2.25
        market.put("stat_value", random.nextInt(31) + 10); // Random stat between 10-40
        market.put("last_updated_date", getCurrentTimestamp());
        return market;
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private double round(double value, int places) {
        return Math.round(value * Math.pow(10, places)) / Math.pow(10, places);
    }

}
