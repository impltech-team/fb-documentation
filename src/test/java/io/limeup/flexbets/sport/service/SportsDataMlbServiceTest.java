package io.limeup.flexbets.sport.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.service.sportdataio.SportsDataMlbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SportsDataMlbServiceTest {
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebClient sportsDataWebClient;

    private ObjectMapper objectMapper;

    @InjectMocks
    private SportsDataMlbService service;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        service = new SportsDataMlbService(sportsDataWebClient, objectMapper);
        ReflectionTestUtils.setField(service, "apiKey", "TEST");
    }

    @Test
    void getPlayerGameDataByGameIdAndPlayerId_returnMatchingPlayer() throws Exception {
        JsonNode json = objectMapper.readTree("{\"PlayerGames\":[{\"PlayerID\":10}]}");
        when(sportsDataWebClient.get()
                .uri(anyString())
                .retrieve()
                .bodyToMono(JsonNode.class))
                .thenReturn(Mono.just(json));

        var dto = service.getPlayerGameDataByGameIdAndPlayerId(1, 10L);

        assertThat(dto).isNotNull();
        assertThat(dto.getPlayerId()).isEqualTo(10L);
    }

    @Test
    void getPlayerGameDataByGameIdAndPlayerId_gameNotStartedShouldThrow() throws Exception {
        JsonNode json = objectMapper.readTree("{\"PlayerGames\":[]}");
        when(sportsDataWebClient.get()
                .uri(anyString())
                .retrieve()
                .bodyToMono(JsonNode.class))
                .thenReturn(Mono.just(json));

        assertThrows(FlexBetsSportNotFoundException.class,
                () -> service.getPlayerGameDataByGameIdAndPlayerId(1, 10L));
    }

    @Test
    void getPlayerGameDataByGameIdAndPlayerId_playerNotFoundShouldThrow() throws Exception {
        JsonNode json = objectMapper.readTree("{\"PlayerGames\":[{\"PlayerID\":11}]}");
        when(sportsDataWebClient.get()
                .uri(anyString())
                .retrieve()
                .bodyToMono(JsonNode.class))
                .thenReturn(Mono.just(json));

        assertThrows(FlexBetsSportNotFoundException.class,
                () -> service.getPlayerGameDataByGameIdAndPlayerId(1, 10L));
    }

    @Test
    void getPlayerGameDataByGameIdAndPlayerId_parsingFailsShouldThrowRuntime() throws Exception {
        JsonNode json = objectMapper.readTree("{\"PlayerGames\":[\"invalid\"]}");
        when(sportsDataWebClient.get()
                .uri(anyString())
                .retrieve()
                .bodyToMono(JsonNode.class))
                .thenReturn(Mono.just(json));

        assertThrows(RuntimeException.class,
                () -> service.getPlayerGameDataByGameIdAndPlayerId(1, 10L));
    }

    @Test
    void getPlayerGameDataByGameIdAndPlayerId_notExistingGameShouldThrow() throws Exception {
        JsonNode json = objectMapper.readTree("");
        when(sportsDataWebClient.get()
                .uri(anyString())
                .retrieve()
                .bodyToMono(JsonNode.class))
                .thenReturn(Mono.just(json));

        assertThrows(FlexBetsSportNotFoundException.class,
                () -> service.getPlayerGameDataByGameIdAndPlayerId(1, 10L));
    }
}
