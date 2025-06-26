package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.service.sportdataio.SportsDataMlbImportService;
import io.limeup.flexbets.sport.service.statscore.impl.PrefetchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;

@RestController
@RequestMapping("/v1/data/fetch/io")
@RequiredArgsConstructor
@Validated
public class SportsDataIoPrefetchController {

    private final SportsDataMlbImportService importService;
    private final ThreadPoolTaskExecutor taskExecutor;

    @PostMapping("/players")
    public ResponseEntity<PrefetchResponse> prefetchPlayers() {
        taskExecutor.execute(importService::importPlayers);
        return ResponseEntity.accepted()
                .body(new PrefetchResponse("importPlayers", Instant.now()));
    }

    @PostMapping("/teams")
    public ResponseEntity<PrefetchResponse> prefetchTeams() {
        taskExecutor.execute(importService::importTeams);
        return ResponseEntity.accepted()
                .body(new PrefetchResponse("teams", Instant.now()));
    }

    @PostMapping("/scores")
    public ResponseEntity<PrefetchResponse> prefetchScores() {
        taskExecutor.execute(() -> importService.importScores(LocalDate.now()));
        return ResponseEntity.accepted()
                .body(new PrefetchResponse("scores", Instant.now()));
    }

    @PostMapping("/player-stats")
    public ResponseEntity<PrefetchResponse> prefetchPlayerStats() {
        taskExecutor.execute(importService::importPlayersStats);
        return ResponseEntity.accepted()
                .body(new PrefetchResponse("player-stats", Instant.now()));
    }

    @PostMapping("/player-game-stats")
    public ResponseEntity<PrefetchResponse> prefetcPlayerGameStats() {
        taskExecutor.execute(importService::importPlayerGameStats);
        return ResponseEntity.accepted()
                .body(new PrefetchResponse("player-game-stats", Instant.now()));
    }

    @PostMapping("/bet-market")
    public ResponseEntity<PrefetchResponse> prefetchBetMarkets() {
        taskExecutor.execute(() -> importService.importBetMarkets(LocalDate.now()));
        return ResponseEntity.accepted()
                .body(new PrefetchResponse("bet-markets", Instant.now()));
    }


}

