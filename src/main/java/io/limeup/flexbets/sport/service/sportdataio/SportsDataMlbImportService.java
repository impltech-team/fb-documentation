package io.limeup.flexbets.sport.service.sportdataio;

import io.limeup.flexbets.sport.dto.sportsdata.IoPlayerGameStatsDto;
import io.limeup.flexbets.sport.dto.sportsdata.SportsDataBettingMarketDTO;
import io.limeup.flexbets.sport.dto.sportsdata.SportsDataPlayerDTO;
import io.limeup.flexbets.sport.dto.sportsdata.SportsDataTeamDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.FetchIoType;
import io.limeup.flexbets.sport.dto.statscore.prams.SportIoType;
import io.limeup.flexbets.sport.mapper.IoBetMapper;
import io.limeup.flexbets.sport.mapper.IoTeamMapper;
import io.limeup.flexbets.sport.model.IoBet;
import io.limeup.flexbets.sport.model.IoBetOutcome;
import io.limeup.flexbets.sport.model.IoEvent;
import io.limeup.flexbets.sport.model.IoPlayersStats;
import io.limeup.flexbets.sport.model.dto.*;
import io.limeup.flexbets.sport.repository.sportsdataio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SportsDataMlbImportService {


    @Qualifier("sportsDataWebClient")
    private final WebClient sportsDataWebClient;

    private final IoEventRepository eventRepo;
    private final IoBetRepository betRepo;
    private final IoTeamRepository teamRepo;
    private final IoPlayerRepository playerRepo;
    private final IoPlayerStatsRepository playerStatsRepo;
    private final IoPlayerGamesStatsRepository playerGameStatsRepo;

    private final IoEventMapper eventMapper;
    private final IoBetMapper betMapper;
    private final IoTeamMapper teamMapper;
    private final IoPlayerMapper playerMapper;
    private final IoPlayersStatsMapper playersStatsMapper;
    private final IoPlayerGameStatsMapper playerGameStatsMapper;

    private final FetchLogService fetchLogService;


    @Value("${sportsdata.key}")
    private String apiKey;

    private static final String PLAYER_MARKET_NAME = "Player Prop";


    private final int seasonYear = Year.now().getValue();


    public void importPlayersStats() {

        var log = fetchLogService.start(FetchIoType.PLAYER_STATS, SportIoType.MLB, null);
        try {
            String url = "https://api.sportsdata.io/v3/mlb/stats/json/PlayerSeasonStats/"
                    + seasonYear + "?key=" + apiKey;

            sportsDataWebClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToFlux(IoSportsDataPlayerStatsDTO.class)

                    .doOnNext(this::upsertPlayerSeasonStat)
                    .blockLast();

            fetchLogService.finishSuccess(log);
        } catch (Exception ex) {
            fetchLogService.finishError(log, ex);
            throw ex;
        }
    }

    public void importScores(LocalDate date) {

        var log = fetchLogService.start(FetchIoType.SCORES, SportIoType.MLB, null);
        try {
            List<ScoreBasicDto> games = fetchScores(date);
            if (games != null) games.forEach(this::upsertGame);

            fetchLogService.runVoid(FetchIoType.BET, SportIoType.MLB, log,
                    () -> fetchBetMarketsForGames(games).block());

            fetchLogService.finishSuccess(log);
        } catch (Exception ex) {
            fetchLogService.finishError(log, ex);
            throw ex;
        }
    }

    public void importPlayers() {

        var log = fetchLogService.start(FetchIoType.PLAYERS, SportIoType.MLB, null);
        try {
            List<SportsDataPlayerDTO> players = fetchPlayers();
            if (players != null) {
                players.forEach(this::upsertPlayer);

                fetchLogService.runVoid(FetchIoType.PLAYER_GAME_STATS, SportIoType.MLB, log,
                        () -> Flux.fromIterable(players)
                                .map(SportsDataPlayerDTO::getPlayerID)
                                .filter(Objects::nonNull)
                                .buffer(50)
                                .delayElements(Duration.ofMillis(200))
                                .flatMap(ids -> Flux.fromIterable(ids)
                                        .flatMap(this::fetchAndUpsertPlayerGameStats, 5))
                                .blockLast());
            }
            fetchLogService.finishSuccess(log);
        } catch (Exception ex) {
            fetchLogService.finishError(log, ex);
            throw ex;
        }
    }

    @Transactional
    public void importTeams() {

        fetchLogService.runVoid(FetchIoType.TEAMS, SportIoType.MLB, null, () -> {
            String url = "https://api.sportsdata.io/v3/mlb/scores/json/teams?key=" + apiKey;
            List<SportsDataTeamDTO> dtos = sportsDataWebClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToFlux(SportsDataTeamDTO.class)

                    .collectList()
                    .block();

            if (dtos != null) dtos.forEach(this::upsertTeam);
        });
    }


    private void upsertPlayerSeasonStat(IoSportsDataPlayerStatsDTO dto) {

        if (dto.getPlayerID() == null) return;

        playerStatsRepo.findByStatId(dto.getStatID())
                .stream()
                .max(Comparator.comparing(IoPlayersStats::getUpdated))
                .ifPresentOrElse(
                        latest -> {
                            playersStatsMapper.merge(latest, dto);
                            playerStatsRepo.save(latest);
                        },
                        () -> playerStatsRepo.save(playersStatsMapper.toEntity(dto)));
    }


    private List<ScoreBasicDto> fetchScores(LocalDate date) {

        String url = "https://api.sportsdata.io/v3/mlb/scores/json/ScoresBasicFinal/"
                + date + "?key=" + apiKey;

        return sportsDataWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(ScoreBasicDto.class)

                .collectList()
                .block();
    }

    private IoEvent upsertGame(ScoreBasicDto dto) {

        return eventRepo.findByGameId(dto.gameId())
                .map(ex -> {
                    eventMapper.merge(ex, dto);
                    return eventRepo.save(ex);
                })
                .orElseGet(() -> eventRepo.save(eventMapper.toEntity(dto)));
    }


    private Mono<Void> fetchBetMarketsForGames(List<ScoreBasicDto> games) {

        if (games == null) return Mono.empty();

        return Flux.fromIterable(games)
                .delayElements(Duration.ofMillis(150))
                .flatMap(gameDto -> {
                    IoEvent game = eventRepo.findByGameId(gameDto.gameId()).orElse(null);
                    if (game == null) return Mono.empty();

                    String url = "https://api.sportsdata.io/v3/mlb/odds/json/BettingMarketsByGameID/"
                            + game.getGameId() + "/G1000?key=" + apiKey + "&include=available";

                    return sportsDataWebClient.get()
                            .uri(url)
                            .retrieve()
                            .bodyToFlux(SportsDataBettingMarketDTO.class)

                            .filter(b -> PLAYER_MARKET_NAME.equalsIgnoreCase(b.getBettingMarketType())
                                    && Boolean.TRUE.equals(b.getAnyBetsAvailable()))
                            .collectList()
                            .doOnNext(dtos -> {
                                Set<IoBet> toSave = reconcileBettingMarkets(
                                        game, dtos, betRepo.findByEventWithOutcomes(game));
                                if (!toSave.isEmpty()) betRepo.saveAll(toSave);
                            })
                            .then();
                }, 5)
                .then();
    }


    private List<SportsDataPlayerDTO> fetchPlayers() {

        String url = "https://api.sportsdata.io/v3/mlb/scores/json/Players?key=" + apiKey;
        return sportsDataWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(SportsDataPlayerDTO.class)

                .collectList()
                .block();
    }

    private void upsertPlayer(SportsDataPlayerDTO dto) {

        if (dto.getPlayerID() == null) return;

        playerRepo.findByPlayerId(dto.getPlayerID())
                .ifPresentOrElse(
                        ex -> {
                            playerMapper.merge(ex, dto);
                            playerRepo.save(ex);
                        },
                        () -> playerRepo.save(playerMapper.toEntity(dto)));
    }

    private Mono<Void> fetchAndUpsertPlayerGameStats(Long playerId) {

        String url = "https://api.sportsdata.io/v3/mlb/stats/json/PlayerGameStatsBySeason/"
                + seasonYear + "/" + playerId + "/5?key=" + apiKey;

        return sportsDataWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(IoPlayerGameStatsDto.class)

                .doOnNext(dto -> playerGameStatsRepo.findByStatId(dto.getStatId())
                        .ifPresentOrElse(
                                ex -> {
                                    playerGameStatsMapper.merge(ex, dto);
                                    playerGameStatsRepo.save(ex);
                                },
                                () -> playerGameStatsRepo.save(
                                        playerGameStatsMapper.toEntity(dto))))
                .then();
    }


    private void upsertTeam(SportsDataTeamDTO dto) {

        teamRepo.findByTeamId(dto.getTeamId())
                .ifPresentOrElse(
                        ex -> {
                            teamMapper.updateEntity(ex, dto);
                            teamRepo.save(ex);
                        },
                        () -> teamRepo.save(teamMapper.toEntity(dto)));
    }

    private Set<IoBet> reconcileBettingMarkets(IoEvent event,
                                                List<SportsDataBettingMarketDTO> incoming,
                                                Set<IoBet> db) {

        Map<Long, IoBet> dbMap = db.stream()
                .collect(Collectors.toMap(IoBet::getMarketId, it -> it));

        Set<Long> active = incoming.stream()
                .map(SportsDataBettingMarketDTO::getBettingMarketId)
                .collect(Collectors.toSet());

        Set<IoBet> toSave = new HashSet<>();

        for (var dto : incoming) {
            IoBet existing = dbMap.get(dto.getBettingMarketId());

            if (existing == null) {
                toSave.add(betMapper.toEntity(dto, event));
            } else if (!Objects.equals(existing.getUpdatedAt(), dto.getUpdated())) {
                IoBet replaced = betMapper.toEntity(dto, event);
                replaced.setBetOutcomes(reconcileBetOutcomes(
                        dto.getBettingOutcomes(), existing.getBetOutcomes(), replaced));
                toSave.add(replaced);
            }
        }

        db.stream()
                .filter(b -> !active.contains(b.getMarketId()) && b.isAnyBetsAvailable())
                .forEach(b -> {
                    b.setAnyBetsAvailable(false);
                    b.getBetOutcomes().forEach(o -> o.setAvailable(false));
                    toSave.add(b);
                });

        return toSave;
    }

    private List<IoBetOutcome> reconcileBetOutcomes(
            List<SportsDataBettingMarketDTO.BettingOutcomeDTO> incoming,
            List<IoBetOutcome> db,
            IoBet bet) {

        Map<Long, IoBetOutcome> dbMap = db.stream()
                .collect(Collectors.toMap(IoBetOutcome::getId, it -> it));

        Set<Long> active = incoming.stream()
                .map(SportsDataBettingMarketDTO.BettingOutcomeDTO::getBettingOutcomeId)
                .collect(Collectors.toSet());

        List<IoBetOutcome> toSave = new ArrayList<>();

        for (var dto : incoming) {
            IoBetOutcome ex = dbMap.get(dto.getBettingOutcomeId());

            if (ex == null) {
                toSave.add(betMapper.toBetOutcomeEntity(dto, bet));
            } else if (!Objects.equals(ex.getUpdatedAt(), dto.getUpdated())) {
                toSave.add(betMapper.updateEntity(ex, dto, bet));
            }
        }

        db.stream()
                .filter(o -> !active.contains(o.getId()) && o.isAvailable())
                .forEach(o -> {
                    o.setAvailable(false);
                    toSave.add(o);
                });

        return toSave;
    }
}
