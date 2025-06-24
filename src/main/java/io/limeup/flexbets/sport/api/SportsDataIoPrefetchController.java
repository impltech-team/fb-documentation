package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.service.sportdataio.SportsDataMlbImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/data/fetch/io")
@RequiredArgsConstructor
@Validated
public class SportsDataIoPrefetchController {

    private final SportsDataMlbImportService importService;
    private final ThreadPoolTaskExecutor taskExecutor;

    @PostMapping("/players")
    public ResponseEntity<Void> prefetchPlayers() {
        //      taskExecutor.execute(importService::importPlayers);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/teams")
    public ResponseEntity<Void> prefetchTeams() {
     //   taskExecutor.execute(importService::importTeams);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/scores")
    public ResponseEntity<Void> prefetchScores() {
    //    taskExecutor.execute(() -> importService.importScores(LocalDate.now()));
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/player-stats")
    public ResponseEntity<Void> prefetchPlayerStats() {
     //   taskExecutor.execute(importService::importPlayersStats);
        return ResponseEntity.accepted().build();
    }
}

