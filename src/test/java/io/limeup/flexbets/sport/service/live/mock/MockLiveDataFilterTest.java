package io.limeup.flexbets.sport.service.live.mock;

import io.limeup.flexbets.sport.utils.ConstantUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class MockLiveDataFilterTest {

    private MockLiveDataFilter mockLiveDataFilter;

    @BeforeEach
    void setUp() {
        mockLiveDataFilter = new MockLiveDataFilter();
    }

    @Test
    void filterEventDataWhenSingleSubscriptionShouldReturnFullData() {
        Map<String, Object> fullEventData = createFullEventData();
        List<Map<String, Object>> subList = new ArrayList<>();
        subList.add(Map.of(ConstantUtils.StatScoreWebClient.EVENT_ID, 1));

        Map<String, Object> result = mockLiveDataFilter.filterEventData(fullEventData, subList);

        assertThat(result).isEqualTo(fullEventData);
    }

    @Test
    void filterEventDataWhenMultipleSubscriptionsShouldFilterMarkets() {
        Map<String, Object> fullEventData = createFullEventData();
        List<Map<String, Object>> subscriptions = List.of(
            Map.of(ConstantUtils.StatScoreWebClient.EVENT_ID, 1),
            Map.of(
                ConstantUtils.Mock.SUB_PARTICIPANT_ID, 1,
                ConstantUtils.Mock.MARKET_IDS, List.of(101)
            ),
            Map.of(
                ConstantUtils.Mock.SUB_PARTICIPANT_ID, 2,
                ConstantUtils.Mock.MARKET_IDS, List.of(202)
            )
        );

        Map<String, Object> result = mockLiveDataFilter.filterEventData(fullEventData, subscriptions);

        List<Map<String, Object>> participants = (List<Map<String, Object>>) result.get(ConstantUtils.Mock.PARTICIPANTS);

        assertThat(participants).hasSize(2);

        Map<String, Object> participant1 = participants.get(0);
        List<Map<String, Object>> subParticipants1 = (List<Map<String, Object>>) participant1.get(ConstantUtils.Mock.SUB_PARTICIPANTS);
        assertThat(subParticipants1).hasSize(1);
        assertThat(((List<Map<String, Object>>) subParticipants1.get(0).get(ConstantUtils.Mock.MARKETS))).hasSize(1);
        assertThat(((List<Map<String, Object>>) subParticipants1.get(0).get(ConstantUtils.Mock.MARKETS)).get(0).get(ConstantUtils.Mock.MARKET_ID)).isEqualTo(101);

        Map<String, Object> participant2 = participants.get(1);
        List<Map<String, Object>> subParticipants2 = (List<Map<String, Object>>) participant2.get(ConstantUtils.Mock.SUB_PARTICIPANTS);
        assertThat(subParticipants2).hasSize(1);
        assertThat(((List<Map<String, Object>>) subParticipants2.get(0).get(ConstantUtils.Mock.MARKETS))).hasSize(1);
        assertThat(((List<Map<String, Object>>) subParticipants2.get(0).get(ConstantUtils.Mock.MARKETS)).get(0).get(ConstantUtils.Mock.MARKET_ID)).isEqualTo(202);
    }

    @Test
    void filterEventDataWhenNoMatchingSubParticipantsShouldReturnEmptyParticipants() {
        Map<String, Object> fullEventData = createFullEventData();
        List<Map<String, Object>> subscriptions = List.of(
            Map.of(ConstantUtils.StatScoreWebClient.EVENT_ID, 1),
            Map.of(
                ConstantUtils.Mock.SUB_PARTICIPANT_ID, 999,
                ConstantUtils.Mock.MARKET_IDS, List.of(999)
            )
        );

        Map<String, Object> result = mockLiveDataFilter.filterEventData(fullEventData, subscriptions);

        assertThat(result.get(ConstantUtils.Mock.PARTICIPANTS)).asList().isEmpty();
    }

    @Test
    void filterEventDataWhenRequestedMarketNotFoundShouldReturnEmptyParticipants() {
        Map<String, Object> fullEventData = createFullEventData();
        List<Map<String, Object>> subscriptions = List.of(
            Map.of(ConstantUtils.StatScoreWebClient.EVENT_ID, 1),
            Map.of(
                ConstantUtils.Mock.SUB_PARTICIPANT_ID, 1,
                ConstantUtils.Mock.MARKET_IDS, List.of(999)
            )
        );

        Map<String, Object> result = mockLiveDataFilter.filterEventData(fullEventData, subscriptions);

        assertThat(result.get(ConstantUtils.Mock.PARTICIPANTS)).asList().isEmpty();
    }

    private Map<String, Object> createFullEventData() {
        Map<String, Object> subParticipant1 = Map.of(
            ConstantUtils.Mock.SUB_PARTICIPANT_ID, 1,
            ConstantUtils.Mock.MARKETS, List.of(
                Map.of(ConstantUtils.Mock.MARKET_ID, 101),
                Map.of(ConstantUtils.Mock.MARKET_ID, 102)
            )
        );

        Map<String, Object> subParticipant2 = Map.of(
            ConstantUtils.Mock.SUB_PARTICIPANT_ID, 2,
            ConstantUtils.Mock.MARKETS, List.of(
                Map.of(ConstantUtils.Mock.MARKET_ID, 201),
                Map.of(ConstantUtils.Mock.MARKET_ID, 202)
            )
        );

        Map<String, Object> participant1 = Map.of(
            ConstantUtils.Mock.ID, 1,
            ConstantUtils.Mock.SUB_PARTICIPANTS, List.of(subParticipant1)
        );

        Map<String, Object> participant2 = Map.of(
            ConstantUtils.Mock.ID, 2,
            ConstantUtils.Mock.SUB_PARTICIPANTS, List.of(subParticipant2)
        );

        return Map.of(
            ConstantUtils.Mock.ID, 123,
            ConstantUtils.Mock.PARTICIPANTS, List.of(participant1, participant2)
        );
    }
}

