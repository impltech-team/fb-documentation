package io.limeup.flexbets.sport.model.dto;

import io.limeup.flexbets.sport.dto.SportsDataPlayerDTO;
import io.limeup.flexbets.sport.repository.IoEventRepository;
import io.limeup.flexbets.sport.repository.IoPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SportsDataMlbImportService {

    @Qualifier("sportsDataWebClient")
    private final WebClient sportsDataWebClient;
    private final IoEventRepository repo;
    private final IoPlayerRepository playerRepository;
    private final IoEventMapper mapper;
    private final IoPlayerMapper playerMapper;

    @Transactional
    public void importScores(LocalDate date) {

//        String path = String.format(
//                "/v3/mlb/scores/json/ScoresBasicFinal/%s",
//                date
//        );
        String path = String.format(
                "https://api.sportsdata.io/v3/mlb/scores/json/ScoresBasicFinal/2025-06-15?key=52bba367bf14471bac048aa668395046");

        List<ScoreBasicDto> dtos = sportsDataWebClient.get()
                .uri(path)
                .retrieve()
                .bodyToFlux(ScoreBasicDto.class)
                .collectList()
                .block();

        if (dtos == null) return;

        for (ScoreBasicDto dto : dtos) {
            repo.findByGameId(dto.gameId())
                    .ifPresentOrElse(
                            existing -> {
                                mapper.merge(existing, dto);
                                repo.save(existing);
                            },
                            () -> repo.save(mapper.toEntity(dto))
                    );
        }
    }

    @Transactional
    public void importPlayers() {

//        String path = String.format(
//                "/v3/mlb/scores/json/ScoresBasicFinal/%s",
//                date
//        );
        String path = String.format(
                "https://api.sportsdata.io/v3/mlb/scores/json/Players?key=52bba367bf14471bac048aa668395046");

        List<SportsDataPlayerDTO> dtos = sportsDataWebClient.get()
                .uri(path)
                .retrieve()
                .bodyToFlux(SportsDataPlayerDTO.class)
                .collectList()
                .block();

        if (dtos == null) return;

        for (SportsDataPlayerDTO dto : dtos) {
            System.out.println("Received DTO: " + dto);

            if (dto.getPlayerID() == null) {
                System.out.println("⚠️  DTO has null PlayerID, skipping: " + dto);
                continue;
            }

            playerRepository.findByPlayerId(dto.getPlayerID())
                    .ifPresentOrElse(
                            existing -> {
                                playerMapper.merge(existing, dto);
                                playerRepository.save(existing);
                            },
                            () -> {
                                playerRepository.save(playerMapper.toEntity(dto));
                            }
                    );
        }

    }
}
