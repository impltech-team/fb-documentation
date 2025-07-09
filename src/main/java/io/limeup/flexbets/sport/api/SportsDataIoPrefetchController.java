package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.service.sportdataio.SportsDataMlbImportService;
import io.limeup.flexbets.sport.service.statscore.impl.PrefetchResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
                .body(new PrefetchResponse("io/players", Instant.now()));
    }

    @PostMapping("/teams")
    public ResponseEntity<PrefetchResponse> prefetchTeams() {
        taskExecutor.execute(importService::importTeams);
        return ResponseEntity.accepted()
                .body(new PrefetchResponse("io/teams", Instant.now()));
    }

    @PostMapping("/scores")
    public ResponseEntity<PrefetchResponse> prefetchScores() {
        taskExecutor.execute(() -> importService.importScores(LocalDate.now()));
        return ResponseEntity.accepted()
                .body(new PrefetchResponse("io/scores", Instant.now()));
    }

    @PostMapping("/player-stats")
    public ResponseEntity<PrefetchResponse> prefetchPlayerStats() {
        taskExecutor.execute(importService::importPlayersStats);
        return ResponseEntity.accepted()
                .body(new PrefetchResponse("io/player-stats", Instant.now()));
    }

    @PostMapping("/player-game-stats")
    public ResponseEntity<PrefetchResponse> prefetchPlayerGameStats() {
        taskExecutor.execute(importService::importPlayerGameStats);
        return ResponseEntity.accepted()
                .body(new PrefetchResponse("io/player-game-stats", Instant.now()));
    }

    @PostMapping("/bet-markets")
    public ResponseEntity<PrefetchResponse> prefetchBetMarkets() {
        taskExecutor.execute(() -> importService.importBetMarkets(LocalDate.now()));
        return ResponseEntity.accepted()
                .body(new PrefetchResponse("io/bet-markets", Instant.now()));
    }

    @PostMapping("/venue")
    public ResponseEntity<PrefetchResponse> prefetchVenue() {
        taskExecutor.execute(importService::importVenue);
        return ResponseEntity.accepted()
                .body(new PrefetchResponse("io/venue", Instant.now()));
    }

    @Hidden
    @PostMapping("/allData")
    public ResponseEntity<PrefetchResponse> prefetchAllData() {
        taskExecutor.execute(importService::importPlayers);
        taskExecutor.execute(importService::importTeams);
        taskExecutor.execute(() -> importService.importScores(LocalDate.now()));
        taskExecutor.execute(importService::importVenue);
        taskExecutor.execute(importService::importPlayersStats);
        taskExecutor.execute(importService::importPlayerGameStats);
        taskExecutor.execute(() -> importService.importBetMarkets(LocalDate.now()));
        taskExecutor.execute(importService::importVenue);

        return ResponseEntity.accepted()
                .body(new PrefetchResponse("all", Instant.now()));
    }

    @Hidden
    @PostMapping("/scores/range")
    public ResponseEntity<PrefetchResponse> prefetchScoresRange(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        taskExecutor.execute(() -> importService.importScoresRange(from, to));
        return ResponseEntity.accepted()
                .body(new PrefetchResponse("io/scores/range", Instant.now()));
    }

    @Hidden
    @PostMapping("/bet-markets/range")
    public ResponseEntity<PrefetchResponse> prefetchBetsRange(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        taskExecutor.execute(() -> importService.importBetsRange(from, to));
        return ResponseEntity.accepted()
                .body(new PrefetchResponse("io/bets/range", Instant.now()));
    }


}

