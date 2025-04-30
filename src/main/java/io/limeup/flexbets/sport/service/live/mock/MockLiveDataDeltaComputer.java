package io.limeup.flexbets.sport.service.live.mock;

import io.limeup.flexbets.sport.utils.ConstantUtils;
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

        List<Map<String, Object>> lastParticipants = (List<Map<String, Object>>) lastState.get(ConstantUtils.Mock.PARTICIPANTS);
        List<Map<String, Object>> newParticipants = (List<Map<String, Object>>) newState.get(ConstantUtils.Mock.PARTICIPANTS);

        List<Map<String, Object>> changedParticipants = new ArrayList<>();

        for (int i = 0; i < newParticipants.size(); i++) {
            participantChange(lastParticipants, newParticipants, changedParticipants, i);
        }

        if (!changedParticipants.isEmpty()) {
            delta.put("id", newState.get("id"));
            delta.put(ConstantUtils.Mock.PARTICIPANTS, changedParticipants);
        }

        return delta;
    }

    private void participantChange(List<Map<String, Object>> lastParticipants,
                                   List<Map<String, Object>> newParticipants,
                                   List<Map<String, Object>> changedParticipants, int count) {
        Map<String, Object> newParticipant = newParticipants.get(count);
        Map<String, Object> lastParticipant = lastParticipants.size() > count ? lastParticipants.get(count) : null;

        if (lastParticipant == null) {
            changedParticipants.add(newParticipant);
            return;
        }

        Map<String, Object> participantDelta = new HashMap<>();
        participantDelta.put("id", newParticipant.get("id"));

        Map<String, Object> newFormation = (Map<String, Object>) newParticipant.get(ConstantUtils.Mock.TEAM_FORMATION);
        Map<String, Object> lastFormation = (Map<String, Object>) lastParticipant.get(ConstantUtils.Mock.TEAM_FORMATION);

        if (newFormation != null && !Objects.equals(newFormation, lastFormation)) {
            participantDelta.put(ConstantUtils.Mock.TEAM_FORMATION, newFormation);
        }

        List<Map<String, Object>> newSubParticipants = (List<Map<String, Object>>) newParticipant.get(ConstantUtils.Mock.SUB_PARTICIPANTS);
        List<Map<String, Object>> lastSubParticipants = (List<Map<String, Object>>) lastParticipant.get(ConstantUtils.Mock.SUB_PARTICIPANTS);

        if (newSubParticipants != null && lastSubParticipants != null) {
            List<Map<String, Object>> changedSubParticipants = new ArrayList<>();

            for (int j = 0; j < newSubParticipants.size(); j++) {
                subParticipantChange(newSubParticipants, lastSubParticipants, changedSubParticipants, j);
            }

            if (!changedSubParticipants.isEmpty()) {
                participantDelta.put(ConstantUtils.Mock.SUB_PARTICIPANTS, changedSubParticipants);
            }
        }

        if (participantDelta.size() > 1) {
            changedParticipants.add(participantDelta);
        }
    }

    private void subParticipantChange(List<Map<String, Object>> newSubParticipants,
                                      List<Map<String, Object>> lastSubParticipants,
                                      List<Map<String, Object>> changedSubParticipants, int count) {
        Map<String, Object> newSubParticipant = newSubParticipants.get(count);
        Map<String, Object> lastSubParticipant = lastSubParticipants.size() > count ? lastSubParticipants.get(count) : null;

        if (lastSubParticipant == null) {
            changedSubParticipants.add(newSubParticipant);
            return;
        }

        List<Map<String, Object>> newMarkets = (List<Map<String, Object>>) newSubParticipant.get(ConstantUtils.Mock.MARKETS);
        List<Map<String, Object>> lastMarkets = (List<Map<String, Object>>) lastSubParticipant.get(ConstantUtils.Mock.MARKETS);

        List<Map<String, Object>> changedMarkets = new ArrayList<>();

        for (int k = 0; k < newMarkets.size(); k++) {
            marketChange(newMarkets, lastMarkets, changedMarkets, k);
        }

        if (!changedMarkets.isEmpty()) {
            Map<String, Object> changedSubParticipant = new HashMap<>();
            changedSubParticipant.put("id", newSubParticipant.get("sub_participant_id"));
            changedSubParticipant.put(ConstantUtils.Mock.MARKETS, changedMarkets);
            changedSubParticipants.add(changedSubParticipant);
        }
    }

    private void marketChange(List<Map<String, Object>> newMarkets,
                              List<Map<String, Object>> lastMarkets,
                              List<Map<String, Object>> changedMarkets, int count) {
        Map<String, Object> newMarket = newMarkets.get(count);
        Map<String, Object> lastMarket = lastMarkets.size() > count ? lastMarkets.get(count) : null;

        if (lastMarket == null) {
            changedMarkets.add(newMarket);
            return;
        }

        Map<String, Object> changedMarket = new HashMap<>();
        changedMarket.put("market_id", newMarket.get("market_id"));

        if (!Objects.equals(newMarket.get(ConstantUtils.Mock.PRICE), lastMarket.get(ConstantUtils.Mock.PRICE))) {
            changedMarket.put(ConstantUtils.Mock.PRICE, newMarket.get(ConstantUtils.Mock.PRICE));
        }

        if (!Objects.equals(newMarket.get(ConstantUtils.Mock.STAT_VALUE), lastMarket.get(ConstantUtils.Mock.STAT_VALUE))) {
            changedMarket.put(ConstantUtils.Mock.STAT_VALUE, newMarket.get(ConstantUtils.Mock.STAT_VALUE));
        }

        if (changedMarket.size() > 1) {
            changedMarkets.add(changedMarket);
        }
    }

}
