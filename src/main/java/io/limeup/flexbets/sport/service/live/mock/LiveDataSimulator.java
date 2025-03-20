package io.limeup.flexbets.sport.service.live.mock;

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

@Profile("mock")
@Service
public class LiveDataSimulator {
    private final LiveWebSocketController webSocketController;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    public LiveDataSimulator(LiveWebSocketController webSocketController) {
        this.webSocketController = webSocketController;
    }

    //@Scheduled(fixedRate = 30000)
    public void sendRandomLiveUpdate() {
        try {
            Map<String, Object> update = generateRandomUpdate();
            String jsonUpdate = objectMapper.writeValueAsString(update);

            webSocketController.broadcastLiveUpdate(jsonUpdate);
            System.out.println("Sent Live Update: " + jsonUpdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> generateRandomUpdate() {
        int eventId = 3755129;
        int playedTime = 10 + random.nextInt(35);

        Map<String, Object> update = new HashMap<>();
        update.put("id", eventId);

        List<Map<String, Object>> participants = new ArrayList<>();

        // First Participant
        Map<String, Object> participant1 = new HashMap<>();
        participant1.put("id", 136929);
        participant1.put("name", "Los Angeles Lakers");
        participant1.put("played_time", playedTime);

        List<Map<String, Object>> subParticipants1 = new ArrayList<>();
        subParticipants1.add(createSubParticipant(1, "LeBron James", 7, "Under/Over Player Points", 24.5));
        subParticipants1.add(createSubParticipant(2, "Anthony Davis", 8, "Under/Over Player Assists", 5.5));

        participant1.put("subparticipants", subParticipants1);
        participants.add(participant1);

        // Second Participant
        Map<String, Object> participant2 = new HashMap<>();
        participant2.put("id", 1507);
        participant2.put("name", "Phoenix Suns");
        participant2.put("played_time", playedTime);

        List<Map<String, Object>> subParticipants2 = new ArrayList<>();
        subParticipants2.add(createSubParticipant(3, "Kevin Durant", 7, "Under/Over Player Points", 30.5));
        subParticipants2.add(createSubParticipant(4, "Devin Booker", 9, "Under/Over Player Rebounds", 4.5));
        subParticipants2.add(createSubParticipant(5, "Bradley Beal", 10, "Under/Over Player Blocks", 1.5));

        participant2.put("subparticipants", subParticipants2);
        participants.add(participant2);

        update.put("participants", participants);
        return update;
    }

    private Map<String, Object> createSubParticipant(int subParticipantId, String name, int marketId, String marketName, double line) {
        Map<String, Object> subParticipant = new HashMap<>();
        subParticipant.put("sub_participant_id", subParticipantId);
        subParticipant.put("player_name", name);

        List<Map<String, Object>> markets = new ArrayList<>();
        Map<String, Object> market = new HashMap<>();
        market.put("market_id", marketId);
        market.put("market_name", marketName);
        market.put("line", line);
        market.put("bet_type", "Over");
        market.put("status", "Open");
        market.put("price", round(random.nextDouble() * (2.25 - 1.75) + 1.75, 2)); // Random price between 1.75 - 2.25
        market.put("stat_value", random.nextInt(31) + 10); // Random stat between 10-40
        market.put("last_updated_date", getCurrentTimestamp());

        markets.add(market);
        subParticipant.put("markets", markets);

        return subParticipant;
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private double round(double value, int places) {
        return Math.round(value * Math.pow(10, places)) / Math.pow(10, places);
    }

}
