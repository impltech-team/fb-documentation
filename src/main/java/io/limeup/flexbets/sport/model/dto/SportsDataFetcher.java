package io.limeup.flexbets.sport.model.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SportsDataFetcher {

    private final SportsDataMlbImportService importService;

    @Scheduled(fixedDelay = 7_200_000)
    public void poll() {
        importService.importScores(LocalDate.now());
    }

    @Scheduled(fixedDelay = 7_200_000)
    public void pollPlayers() {
        importService.importPlayers();
    }

    @Scheduled(fixedDelay = 7_200_000)
    public void pollTeams() {
        importService.importTeams();
    }

    @Scheduled(fixedDelay = 700_000)
    public void pollPlayersStats() {
        importService.importPlayersStats();
    }
}
