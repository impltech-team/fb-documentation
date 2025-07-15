package io.limeup.flexbets.sport.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.limeup.flexbets.sport.dto.sportsdata.IoBettingMarketResultDto;
import io.limeup.flexbets.sport.dto.sportsdata.IoBettingOutcomeResultDto;
import io.limeup.flexbets.sport.dto.statscore.params.FetchIoType;
import io.limeup.flexbets.sport.dto.statscore.params.SportIoType;
import io.limeup.flexbets.sport.mapper.*;
import io.limeup.flexbets.sport.model.IoBet;
import io.limeup.flexbets.sport.model.IoBetOutcome;
import io.limeup.flexbets.sport.model.IoFetchLog;
import io.limeup.flexbets.sport.model.dto.ScoreBasicDto;
import io.limeup.flexbets.sport.repository.sportsdataio.*;
import io.limeup.flexbets.sport.service.sportdataio.FetchLogService;
import io.limeup.flexbets.sport.service.sportdataio.SportsDataMlbImportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SportsDataMlbImportServiceTest {
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebClient sportsDataWebClient;

    @Mock private FetchLogService fetchLogService;
    @Mock private IoBetRepository betRepo;
    @Mock private IoBetOutcomeRepository betOutcomeRepo;
    @Mock private IoEventRepository ioEventRepo;
    @Mock private IoTeamRepository teamRepo;
    @Mock private IoVenueRepository venueRepo;
    @Mock private IoPlayerRepository playerRepo;
    @Mock private IoPlayerStatsRepository playerStatsRepo;
    @Mock private IoPlayerGamesStatsRepository playerGamesStatsRepo;
    @Mock private IoEventMapper eventMapper;
    @Mock private IoPlayerMapper playerMapper;
    @Mock private IoVenueMapper venueMapper;
    @Mock private IoPlayersStatsMapper playerStatsMapper;
    @Mock private IoPlayerGameStatsMapper playerGameStatsMapper;

    private ObjectMapper objectMapper;

    @InjectMocks
    private SportsDataMlbImportService realService;

    private final LocalDate testDate = LocalDate.of(2024, 6, 15);

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        realService = new SportsDataMlbImportService(sportsDataWebClient,
                ioEventRepo,
                betRepo,
                betOutcomeRepo,
                teamRepo,
                venueRepo,
                playerRepo,
                playerStatsRepo,
                playerGamesStatsRepo,
                eventMapper,
                playerMapper,
                venueMapper,
                playerStatsMapper,
                playerGameStatsMapper,
                fetchLogService,
                objectMapper);
        ReflectionTestUtils.setField(realService, "apiKey", "TEST");
    }

    @Test
    void fetchAndUpsertScores_shouldSaveUpdatedOutcomesForFinalGame() throws JsonProcessingException {
        ScoreBasicDto game = mock(ScoreBasicDto.class);
        when(game.status()).thenReturn("Final");
        when(game.gameId()).thenReturn(1L);
        when(sportsDataWebClient.get()
                .uri(anyString())
                .retrieve()
                .bodyToFlux(ScoreBasicDto.class))
                .thenReturn(Flux.just(game));

        when(fetchLogService.wasRunRecently(any(), any(), any())).thenReturn(false);

        IoBetOutcome outcome = mock(IoBetOutcome.class);
        when(outcome.getOutcomeId()).thenReturn(100L);
        when(outcome.getResultTypeId()).thenReturn(null);

        IoBet bet = mock(IoBet.class);
        when(bet.getMarketId()).thenReturn(11L);
        when(bet.getBetOutcomes()).thenReturn(List.of(outcome));
        when(betRepo.findAllByEventGameId(1L)).thenReturn(Set.of(bet));

        IoBettingOutcomeResultDto outcomeResultDto = mock(IoBettingOutcomeResultDto.class);
        when(outcomeResultDto.outcomeId()).thenReturn(100L);

        IoBettingMarketResultDto marketResult = mock(IoBettingMarketResultDto.class);
        when(marketResult.isResultSupported()).thenReturn(true);
        when(marketResult.getResults()).thenReturn(List.of(outcomeResultDto));
        var json = """
                {
                    "IsMarketResultingSupported":true,
                    "BettingOutcomeResults":[
                     {
                        "BettingOutcomeID":100,
                        "BettingResultTypeID":1,
                        "BettingResultType": "Win",
                        "ActualValue":1.5
                     }
                    ]
                }
                """;
        JsonNode jsonNode = objectMapper.readTree(json);
        when(sportsDataWebClient.get()
                .uri(anyString())
                .retrieve()
                .bodyToMono(JsonNode.class))
                .thenReturn(Mono.just(jsonNode));

        IoBetOutcome updatedOutcome = mock(IoBetOutcome.class);
        when(outcome.getResultTypeId()).thenReturn(1);
        when(outcome.getOutcomeId()).thenReturn(100L);
        when(outcome.getResultType()).thenReturn("Win");
        when(outcome.getResultValue()).thenReturn("1.5");

        realService.importScores(testDate);

        verify(betOutcomeRepo, times(6)).saveAll(List.of(updatedOutcome));
    }
}
