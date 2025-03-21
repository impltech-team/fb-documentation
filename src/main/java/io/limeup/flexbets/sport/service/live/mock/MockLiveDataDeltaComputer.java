package io.limeup.flexbets.sport.service.live.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@Profile("mock")
public class MockLiveDataDeltaComputer {

    public Map<String, Object> computeDelta(Map<String, Object> lastState, Map<String, Object> newState) {
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

            Map<String, Object> participantDelta = new HashMap<>();
            participantDelta.put("id", newParticipant.get("id"));

            Map<String, Object> newFormation = (Map<String, Object>) newParticipant.get("team_formation");
            Map<String, Object> lastFormation = (Map<String, Object>) lastParticipant.get("team_formation");

            if (newFormation != null && !Objects.equals(newFormation, lastFormation)) {
                participantDelta.put("team_formation", newFormation);
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
                participantDelta.put("subparticipants", changedSubParticipants);
            }

            if (participantDelta.size() > 1) {
                changedParticipants.add(participantDelta);
            }
        }

        if (!changedParticipants.isEmpty()) {
            delta.put("id", newState.get("id"));
            delta.put("participants", changedParticipants);
        }

        return delta;
    }

}
