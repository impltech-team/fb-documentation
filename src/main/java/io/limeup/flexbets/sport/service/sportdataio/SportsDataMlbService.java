package io.limeup.flexbets.sport.service.sportdataio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.limeup.flexbets.sport.dto.sportsdata.MLBDataPlayerGameDTO;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class SportsDataMlbService {

    @Qualifier("sportsDataWebClient")
    private final WebClient sportsDataWebClient;

    private final ObjectMapper objectMapper;


    @Value("${sportsdata.key}")
    private String apiKey;

    public static final String URL = "https://api.sportsdata.io/v3/";
    public static final String SPORT_URL = "mlb/";


    public MLBDataPlayerGameDTO getPlayerGameDataByGameIdAndPlayerId(Integer gameId, Long playerId) {
        var apiResponse = fetchPlayerGameStatsDataFromApi(gameId);

        if (CollectionUtils.isEmpty(apiResponse)) {
            throw new FlexBetsSportNotFoundException(String.format("Can not find game with id - %s.", gameId));
        }

        return apiResponse.stream()
                .filter(playerGame -> playerId.equals(playerGame.getPlayerId()))
                .findFirst()
                .orElseThrow(() -> new FlexBetsSportNotFoundException(String.format("Can not find game data for player with id - %s in game with id - %s", playerId, gameId)));
    }

    private List<MLBDataPlayerGameDTO> fetchPlayerGameStatsDataFromApi(Integer gameId) {
        String url = URL + SPORT_URL + "stats/json/BoxScore/" + gameId + "?key=" + apiKey;

        JsonNode rootNode = sportsDataWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (rootNode == null || rootNode.isEmpty()) {
            return Collections.emptyList();
        }

        JsonNode playerGamesNode = rootNode.get("PlayerGames");
        if (playerGamesNode == null || !playerGamesNode.isArray()) {
            return Collections.emptyList();
        }

        try {
            return objectMapper.readerForListOf(MLBDataPlayerGameDTO.class).readValue(playerGamesNode);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse response from Live Player Data SportsDataIo API endpoint", e);
        }
    }
}
