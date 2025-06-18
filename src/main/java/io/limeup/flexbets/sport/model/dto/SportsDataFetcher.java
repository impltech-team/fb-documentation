package io.limeup.flexbets.sport.model.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SportsDataFetcher {

    private final SportsDataMlbImportService importService;

    @Scheduled(fixedDelay = 500_000)       // або cron = "*/5 * * * * *"
    public void poll() {
        importService.importScores(LocalDate.now());
    }

    @Scheduled(fixedDelay = 500_000)       // або cron = "*/5 * * * * *"
    public void pollPlayers() {
        importService.importPlayers();
    }

    @Scheduled(fixedDelay = 500_000)       // або cron = "*/5 * * * * *"
    public void pollTeams() {
        importService.importTeams();
    }
}
