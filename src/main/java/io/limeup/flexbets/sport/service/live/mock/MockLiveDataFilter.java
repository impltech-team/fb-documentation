package io.limeup.flexbets.sport.service.live.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Profile("mock")
public class MockLiveDataFilter {

    public Map<String, Object> filterEventData(Map<String, Object> fullEventData, List<Map<String, Object>> clientSubscriptions) {
        Map<String, Object> filteredData = new HashMap<>();
        if (clientSubscriptions.size() <= 1) {
            return fullEventData;
        }
        filteredData.put("id", fullEventData.get("id"));
        filteredData.put("participants", new ArrayList<>());

        List<Map<String, Object>> participants = (List<Map<String, Object>>) fullEventData.get("participants");

        for (Map<String, Object> participant : participants) {
            List<Map<String, Object>> filteredSubParticipants = new ArrayList<>();
            List<Map<String, Object>> subParticipants = (List<Map<String, Object>>) participant.get("subparticipants");

            for (Map<String, Object> subParticipant : subParticipants) {
                int subParticipantId = (int) subParticipant.get("sub_participant_id");

                for (Map<String, Object> sub : clientSubscriptions) {
                    if (sub.containsKey("sub_participant_id") && (int) sub.get("sub_participant_id") == subParticipantId) {
                        List<Integer> requestedMarkets = (List<Integer>) sub.get("market_ids");
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
