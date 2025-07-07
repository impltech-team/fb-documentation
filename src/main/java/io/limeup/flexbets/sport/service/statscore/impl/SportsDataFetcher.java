package io.limeup.flexbets.sport.service.statscore.impl;

import io.limeup.flexbets.sport.service.sportdataio.SportsDataMlbImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SportsDataFetcher {

    private final SportsDataMlbImportService importService;

    @Scheduled(fixedDelay = 7_200_000)
    public void pollScore() {
   //       importService.importScores(LocalDate.now());
   }

    @Scheduled(fixedDelay = 7_200_000)
    public void pollPlayers() {
//        importService.importPlayers();
    }

    @Scheduled(fixedDelay = 7_200_000)
    public void importPlayerSeasonStats() {
   //     importService.importPlayerGameStats();
    }

    @Scheduled(fixedDelay = 7_200_000)
    public void pollTeams() {
    //    importService.importTeams();
    }

    @Scheduled(fixedDelay = 7_200_000)
    public void pollPlayersStats() {
     //   importService.importPlayersStats();
    }

    @Scheduled(fixedDelay = 7_200_000)
    public void pollImportBetMarkets() {
    //    importService.importBetMarkets(LocalDate.now());
    }

}
