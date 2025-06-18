package io.limeup.flexbets.sport.model.dto;

import io.limeup.flexbets.sport.dto.sportsdata.SportsDataBettingMarketDTO;
import io.limeup.flexbets.sport.dto.sportsdata.SportsDataPlayerDTO;
import io.limeup.flexbets.sport.dto.sportsdata.SportsDataTeamDTO;
import io.limeup.flexbets.sport.mapper.IoBetMapper;
import io.limeup.flexbets.sport.mapper.IoTeamMapper;
import io.limeup.flexbets.sport.model.IoBet;
import io.limeup.flexbets.sport.model.IoBetOutcome;
import io.limeup.flexbets.sport.model.IoEvent;
import io.limeup.flexbets.sport.repository.sportsdataio.IoBetRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoEventRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoPlayerRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SportsDataMlbImportService {

    @Qualifier("sportsDataWebClient")
    private final WebClient sportsDataWebClient;
    private final IoEventRepository repo;
    private final IoPlayerRepository playerRepository;
    private final IoTeamRepository teamRepository;
    private final IoBetRepository betRepository;
    private final IoEventMapper mapper;
    private final IoPlayerMapper playerMapper;

    private static final String PLAYER_MARKET_NAME = "Player Prop";

    @Transactional
    public void importScores(LocalDate date) {
        System.out.printf("Import scores from SportsDataMLB has started for date - %s", date);
        long start = System.currentTimeMillis();
//        String path = String.format(
//                "/v3/mlb/scores/json/ScoresBasicFinal/%s",
//                date
//        );
        String scorePath = String.format(
                "https://api.sportsdata.io/v3/mlb/scores/json/ScoresBasicFinal/2025-06-17?key=52bba367bf14471bac048aa668395046");

        List<ScoreBasicDto> gameDtos = sportsDataWebClient.get()
                .uri(scorePath)
                .retrieve()
                .bodyToFlux(ScoreBasicDto.class)
                .collectList()
                .block();

        if (gameDtos == null) return;

        for (ScoreBasicDto dto : gameDtos) {
            Optional<IoEvent> gameOptional = repo.findByGameId(dto.gameId());
            IoEvent game;

            if (gameOptional.isPresent()) {
                IoEvent existing = gameOptional.get();
                mapper.merge(existing, dto);
                repo.save(existing);
                game = existing;
            } else {
                game = repo.save(mapper.toEntity(dto));
            }

            String betMarketPath = String.format(
                    "https://api.sportsdata.io/v3/mlb/odds/json/BettingMarketsByGameID/%s/G1000?key=52bba367bf14471bac048aa668395046&include=available",
                    game.getGameId());

            List<SportsDataBettingMarketDTO> updateBetMarketDtoList = sportsDataWebClient.get()
                    .uri(betMarketPath)
                    .retrieve()
                    .bodyToFlux(SportsDataBettingMarketDTO.class)
                    .collectList()
                    .block();

            if (updateBetMarketDtoList == null) return;

            List<IoBet> existingBetMarkets = betRepository.findByEvent(game);

            updateBetMarketDtoList = updateBetMarketDtoList.stream()
                    .filter(betMarket -> betMarket.getBettingMarketType().equalsIgnoreCase(PLAYER_MARKET_NAME)
                            && betMarket.getAnyBetsAvailable())
                    .toList();

            List<IoBet> betsToSave = reconcileBettingMarkets(game, updateBetMarketDtoList, existingBetMarkets);
            if(!betsToSave.isEmpty()) betRepository.saveAll(betsToSave);

            long durationMillis = System.currentTimeMillis() - start;
            long seconds = durationMillis / 1000;
            long minutes = seconds / 60;
            String formattedDuration = String.format("%d minute %d second", minutes, seconds);

            System.out.printf("Import scores from SportsDataMLB has finished for date - %s in %s", date, formattedDuration);
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

    @Transactional
    public void importTeams() {

//        String path = String.format(
//                "/v3/mlb/scores/json/ScoresBasicFinal/%s",
//                date
//        );
        String path = String.format(
                "https://api.sportsdata.io/v3/mlb/scores/json/teams?key=52bba367bf14471bac048aa668395046");

        List<SportsDataTeamDTO> dtos = sportsDataWebClient.get()
                .uri(path)
                .retrieve()
                .bodyToFlux(SportsDataTeamDTO.class)
                .collectList()
                .block();

        if (dtos == null) return;

        for (SportsDataTeamDTO dto : dtos) {
            teamRepository.findByTeamId(dto.getTeamId())
                    .ifPresentOrElse(
                            existing -> {
                                IoTeamMapper.updateEntity(existing, dto);
                                teamRepository.save(existing);
                            },
                            () -> {
                                teamRepository.save(IoTeamMapper.toEntity(dto));
                            }
                    );
        }

    }

    private List<IoBet> reconcileBettingMarkets(IoEvent event, List<SportsDataBettingMarketDTO> updateList, List<IoBet> dbList) {
        Map<Long, IoBet> dbBetMap = dbList.stream()
                .collect(Collectors.toMap(IoBet::getMarketId, Function.identity()));

        Set<Long> activeMarketIds = new HashSet<>(updateList.size());

        List<IoBet> betsToSave = new ArrayList<>();

        for (SportsDataBettingMarketDTO updatedDto : updateList) {
            Long id = updatedDto.getBettingMarketId();
            activeMarketIds.add(id);
            IoBet bet = dbBetMap.get(id);

            if (bet == null) {
                bet = IoBetMapper.toEntity(updatedDto, event);
                betsToSave.add(bet);
            } else if (!Objects.equals(bet.getUpdatedAt(), updatedDto.getUpdated())) {
                bet = IoBetMapper.toEntity(updatedDto, event);
                List<IoBetOutcome> updateBetOutcomesList = reconcileBetOutcomes(updatedDto.getBettingOutcomes(), bet.getBetOutcomes(), bet);
                bet.setBetOutcomes(updateBetOutcomesList);
                betsToSave.add(bet);
            }
        }

        for (IoBet dbEntity : dbList) {
            if (!activeMarketIds.contains(dbEntity.getId()) && dbEntity.isAnyBetsAvailable()) {
                dbEntity.setAnyBetsAvailable(false);
                dbEntity.getBetOutcomes().forEach(outcome -> outcome.setAvailable(false));
                betsToSave.add(dbEntity);
            }
        }

        return betsToSave;
    }

    private List<IoBetOutcome> reconcileBetOutcomes(List<SportsDataBettingMarketDTO.BettingOutcomeDTO> updatedList,
                                                    List<IoBetOutcome> dbList, IoBet bet) {
        Map<Long, IoBetOutcome> dbMap = dbList.stream()
                .collect(Collectors.toMap(IoBetOutcome::getId, Function.identity()));

        Set<Long> activeOutcomesIds = new HashSet<>(updatedList.size());
        List<IoBetOutcome> outcomesToSave = new ArrayList<>();

        for (SportsDataBettingMarketDTO.BettingOutcomeDTO updated : updatedList) {
            Long id = updated.getBettingOutcomeId();
            activeOutcomesIds.add(id);
            IoBetOutcome existing = dbMap.get(id);

            if (existing == null) {
                outcomesToSave.add(IoBetMapper.toBetOutcomeEntity(updated, bet));
            } else if (!Objects.equals(existing.getUpdatedAt(), updated.getUpdated())) {
                outcomesToSave.add(IoBetMapper.updateEntity(existing, updated, bet));
            }
        }

        for (IoBetOutcome db : dbList) {
            if (!activeOutcomesIds.contains(db.getId()) && db.isAvailable()) {
                db.setAvailable(false);
                outcomesToSave.add(db);
            }
        }

        return outcomesToSave;
    }
}
