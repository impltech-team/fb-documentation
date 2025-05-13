package io.limeup.flexbets.sport.service.live.mock;

import io.limeup.flexbets.sport.utils.ConstantUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class MockLiveDataDeltaComputerTest {

    private MockLiveDataDeltaComputer deltaComputer;

    @BeforeEach
    void setUp() {
        deltaComputer = new MockLiveDataDeltaComputer();
    }

    @Test
    void computeDeltaWhenLastStateIsNullShouldReturnNewState() {
        Map<String, Object> newState = new HashMap<>();
        newState.put(ConstantUtils.Mock.ID, 1);
        newState.put(ConstantUtils.Mock.PARTICIPANTS, List.of(Map.of(ConstantUtils.Mock.ID, 1)));

        Map<String, Object> result = deltaComputer.computeDelta(null, newState);

        assertThat(result).isEqualTo(newState);
    }

    @Test
    void computeDeltaWhenParticipantFormationChangedShouldReturnDelta() {
        Map<String, Object> lastState = Map.of(
                ConstantUtils.Mock.ID, 1,
                ConstantUtils.Mock.PARTICIPANTS, List.of(
                Map.of(
                        ConstantUtils.Mock.ID, 1,
                        ConstantUtils.Mock.TEAM_FORMATION, Map.of(ConstantUtils.Mock.FORMATION, "4-4-2"),
                        ConstantUtils.Mock.SUB_PARTICIPANTS, List.of()
                )
            )
        );

        Map<String, Object> newState = Map.of(
                ConstantUtils.Mock.ID, 1,
                ConstantUtils.Mock.PARTICIPANTS, List.of(
                Map.of(
                        ConstantUtils.Mock.ID, 1,
                        ConstantUtils.Mock.TEAM_FORMATION, Map.of(ConstantUtils.Mock.FORMATION, "3-5-2"),
                        ConstantUtils.Mock.SUB_PARTICIPANTS, List.of()
                )
            )
        );

        Map<String, Object> result = deltaComputer.computeDelta(lastState, newState);

        assertThat(result).containsKey(ConstantUtils.Mock.PARTICIPANTS);
        List<Map<String, Object>> participants = (List<Map<String, Object>>) result.get(ConstantUtils.Mock.PARTICIPANTS);
        assertThat(participants).hasSize(1);
        assertThat(participants.get(0)).containsEntry(ConstantUtils.Mock.TEAM_FORMATION, Map.of(ConstantUtils.Mock.FORMATION, "3-5-2"));
    }

    @Test
    void computeDeltaWhenSubParticipantMarketChangedShouldReturnDelta() {
        Map<String, Object> lastState = Map.of(
                ConstantUtils.Mock.ID, 1,
                ConstantUtils.Mock.PARTICIPANTS, List.of(
                Map.of(
                        ConstantUtils.Mock.ID, 1,
                        ConstantUtils.Mock.TEAM_FORMATION, Map.of(),
                        ConstantUtils.Mock.SUB_PARTICIPANTS, List.of(
                        Map.of(
                                ConstantUtils.Mock.SUB_PARTICIPANT_ID, 10,
                                ConstantUtils.Mock.MARKETS, List.of(
                                    Map.of(ConstantUtils.Mock.MARKET_ID, 100, ConstantUtils.Mock.PRICE, 1.5, ConstantUtils.Mock.STAT_VALUE, 2)
                            )
                        )
                    )
                )
            )
        );

        Map<String, Object> newState = Map.of(
                ConstantUtils.Mock.ID, 1,
                ConstantUtils.Mock.PARTICIPANTS, List.of(
                Map.of(
                        ConstantUtils.Mock.ID, 1,
                        ConstantUtils.Mock.TEAM_FORMATION, Map.of(),
                        ConstantUtils.Mock.SUB_PARTICIPANTS, List.of(
                        Map.of(
                                ConstantUtils.Mock.SUB_PARTICIPANT_ID, 10,
                                ConstantUtils.Mock.MARKETS, List.of(
                                    Map.of(ConstantUtils.Mock.MARKET_ID, 100, ConstantUtils.Mock.PRICE, 2.0, ConstantUtils.Mock.STAT_VALUE, 2)
                            )
                        )
                    )
                )
            )
        );

        Map<String, Object> result = deltaComputer.computeDelta(lastState, newState);

        assertThat(result).containsKey(ConstantUtils.Mock.PARTICIPANTS);
        List<Map<String, Object>> participants = (List<Map<String, Object>>) result.get(ConstantUtils.Mock.PARTICIPANTS);
        Map<String, Object> subParticipant = (Map<String, Object>) ((List<?>) participants.get(0).get(ConstantUtils.Mock.SUB_PARTICIPANTS)).get(0);
        List<Map<String, Object>> changedMarkets = (List<Map<String, Object>>) subParticipant.get(ConstantUtils.Mock.MARKETS);

        assertThat(changedMarkets).hasSize(1);
        assertThat(changedMarkets.get(0))
            .containsEntry(ConstantUtils.Mock.MARKET_ID, 100)
            .containsEntry(ConstantUtils.Mock.PRICE, 2.0);
    }

    @Test
    void computeDeltaWhenNoChangesShouldReturnEmptyDelta() {
        Map<String, Object> lastState = Map.of(
                ConstantUtils.Mock.ID, 1,
                ConstantUtils.Mock.PARTICIPANTS, List.of(
                Map.of(
                        ConstantUtils.Mock.ID, 1,
                        ConstantUtils.Mock.TEAM_FORMATION, Map.of(ConstantUtils.Mock.FORMATION, "4-4-2"),
                        ConstantUtils.Mock.SUB_PARTICIPANTS, List.of(
                        Map.of(
                                ConstantUtils.Mock.SUB_PARTICIPANT_ID, 10,
                                ConstantUtils.Mock.MARKETS, List.of(
                                    Map.of(ConstantUtils.Mock.MARKET_ID, 100, ConstantUtils.Mock.PRICE, 1.5, ConstantUtils.Mock.STAT_VALUE, 2)
                            )
                        )
                    )
                )
            )
        );

        Map<String, Object> newState = lastState;

        Map<String, Object> result = deltaComputer.computeDelta(lastState, newState);

        assertThat(result).isEmpty();
    }

    @Test
    void computeDeltaWhenNewParticipantAddedShouldReturnFullParticipant() {
        Map<String, Object> lastState = Map.of(
                ConstantUtils.Mock.ID, 1,
                ConstantUtils.Mock.PARTICIPANTS, List.of()
        );

        Map<String, Object> newParticipant = Map.of(
                ConstantUtils.Mock.ID, 2,
                ConstantUtils.Mock.TEAM_FORMATION, Map.of(),
                ConstantUtils.Mock.SUB_PARTICIPANTS, List.of()
        );

        Map<String, Object> newState = Map.of(
                ConstantUtils.Mock.ID, 1,
                ConstantUtils.Mock.PARTICIPANTS, List.of(newParticipant)
        );

        Map<String, Object> result = deltaComputer.computeDelta(lastState, newState);

        assertThat(result).containsKey(ConstantUtils.Mock.PARTICIPANTS);
        List<Map<String, Object>> participants = (List<Map<String, Object>>) result.get(ConstantUtils.Mock.PARTICIPANTS);
        assertThat(participants).containsExactly(newParticipant);
    }
}

