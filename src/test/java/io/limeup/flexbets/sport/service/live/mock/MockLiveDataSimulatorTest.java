package io.limeup.flexbets.sport.service.live.mock;

import io.limeup.flexbets.sport.utils.ConstantUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class MockLiveDataSimulatorTest {

    @Mock
    private MockWebSocketController webSocketController;

    private MockLiveDataSimulator simulator;

    @BeforeEach
    void setup() {
        simulator = new MockLiveDataSimulator(webSocketController);
    }

    @Test
    void generateRandomUpdateShouldContainParticipantsWithMarkets() {
        Map<String, Object> result = simulator.generateRandomUpdate(42);

        assertThat(result).containsKey(ConstantUtils.Mock.ID).containsKey(ConstantUtils.Mock.PARTICIPANTS);

        List<Map<String, Object>> participants = (List<Map<String, Object>>) result.get(ConstantUtils.Mock.PARTICIPANTS);
        assertThat(participants).hasSize(2);

        for (Map<String, Object> participant : participants) {
            assertThat(participant).containsKeys(ConstantUtils.Mock.ID, "name", ConstantUtils.Mock.SUB_PARTICIPANTS);
            List<Map<String, Object>> subs = (List<Map<String, Object>>) participant.get(ConstantUtils.Mock.SUB_PARTICIPANTS);
            assertThat(subs).isNotEmpty();

            for (Map<String, Object> sub : subs) {
                assertThat(sub).containsKeys(ConstantUtils.Mock.SUB_PARTICIPANT_ID, "player_name", ConstantUtils.Mock.MARKETS);
                List<Map<String, Object>> markets = (List<Map<String, Object>>) sub.get(ConstantUtils.Mock.MARKETS);
                assertThat(markets).isNotEmpty();

                for (Map<String, Object> market : markets) {
                    assertThat(market).containsKeys("market_id", "market_name", "line", "bet_type", "status", "price", "stat_value", "last_updated_date");
                }
            }
        }
    }
}

