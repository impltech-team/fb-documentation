package io.limeup.flexbets.sport.service.live.mock;

import io.limeup.flexbets.sport.utils.ConstantUtils;
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
        filteredData.put(ConstantUtils.Mock.PARTICIPANTS, new ArrayList<>());

        List<Map<String, Object>> participants = (List<Map<String, Object>>) fullEventData.get(ConstantUtils.Mock.PARTICIPANTS);

        for (Map<String, Object> participant : participants) {
            List<Map<String, Object>> filteredSubParticipants = new ArrayList<>();
            List<Map<String, Object>> subParticipants = (List<Map<String, Object>>) participant.get(ConstantUtils.Mock.SUB_PARTICIPANTS);

            for (Map<String, Object> subParticipant : subParticipants) {
                int subParticipantId = (int) subParticipant.get(ConstantUtils.StatScoreWebClient.SUB_PARTICIPANT_ID);

                for (Map<String, Object> sub : clientSubscriptions) {
                    filterByMarkets(filteredSubParticipants, subParticipant, subParticipantId, sub);
                }
            }

            if (!filteredSubParticipants.isEmpty()) {
                Map<String, Object> filteredParticipant = new HashMap<>(participant);
                filteredParticipant.put(ConstantUtils.Mock.SUB_PARTICIPANTS, filteredSubParticipants);
                ((List<Map<String, Object>>) filteredData.get(ConstantUtils.Mock.PARTICIPANTS)).add(filteredParticipant);
            }
        }

        return filteredData;
    }

    private void filterByMarkets(List<Map<String, Object>> filteredSubParticipants, Map<String, Object> subParticipant, int subParticipantId, Map<String, Object> sub) {
        if (sub.containsKey(ConstantUtils.StatScoreWebClient.SUB_PARTICIPANT_ID) && (int) sub.get(ConstantUtils.Mock.SUB_PARTICIPANT_ID) == subParticipantId) {
            List<Integer> requestedMarkets = (List<Integer>) sub.get(ConstantUtils.Mock.MARKET_IDS);
            List<Map<String, Object>> filteredMarkets = new ArrayList<>();

            List<Map<String, Object>> markets = (List<Map<String, Object>>) subParticipant.get(ConstantUtils.Mock.MARKETS);

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
