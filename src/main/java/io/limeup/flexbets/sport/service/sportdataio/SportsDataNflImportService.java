package io.limeup.flexbets.sport.service.sportdataio;

import io.limeup.flexbets.sport.constants.NflMarketTypes;
import io.limeup.flexbets.sport.dto.sportsdata.*;
import io.limeup.flexbets.sport.dto.statscore.params.FetchIoType;
import io.limeup.flexbets.sport.dto.statscore.params.SportIoType;
import io.limeup.flexbets.sport.mapper.*;
import io.limeup.flexbets.sport.model.*;
import io.limeup.flexbets.sport.model.dto.*;
import io.limeup.flexbets.sport.repository.sportsdataio.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service // This is the map key used in the controller
@RequiredArgsConstructor
public class SportsDataNflImportService {

    @Qualifier("sportsDataWebClient")
    private final WebClient sportsDataWebClient;

    private final IoEventNFLRepository eventNflRepo;
    private final IoBetNFLRepository betNflRepo;
    private final IoTeamNFLRepository teamNFLRepo;
    private final IoStadiumNFLRepository stadiumRepo;
    private final IoPlayerNFLRepository playerRepo;
    private final IoPlayerStatsNFLRepository playerStatsRepo;
    private final IoPlayerGameStatsNFLRepository playerGameStatsRepo;

    private final IoEventNFLMapper eventMapper;
    private final IoBetNFLMapper betMapper;
    private final IoTeamNFLMapper teamNFLMapper;
    private final IoStadiumNFLMapper stadiumMapper;
    private final IoPlayerNFLMapper playerMapper;
    private final IoPlayersStatsNFLMapper playersStatsMapper;
    private final IoPlayerGameStatsNFLMapper playerGameStatsMapper;

    private final FetchLogService fetchLogService;

    @Value("${sportsdata.key}")
    private String apiKey;

    private static final long cooldownMinutes = 5;
    public static final String URL = "https://api.sportsdata.io/v3/";
    public static final String SPORT_URL = "nfl/";
    private static final String PLAYER_MARKET_NAME = "Player Prop";
    private final int seasonYear = Year.now().getValue();

    @Autowired
    private TransactionTemplate transactionTemplate;

    // 🏈 Scores
    @Transactional
    public void importScores(LocalDate date) {
        if (skipIfLaunchedRecently(FetchIoType.SCORES)) return;
        fetchAndUpsertScores(date);
        fetchAndUpsertScores(date.plusDays(1));
        fetchAndUpsertScores(date.plusDays(2));
        fetchAndUpsertScores(date.plusDays(3));
        fetchAndUpsertScores(date.plusDays(4));
    }

    private void fetchAndUpsertScores(LocalDate date) {
        var log = fetchLogService.start(FetchIoType.SCORES, SportIoType.NFL);
        try {
            String url = URL + SPORT_URL + "scores/json/ScoresByDateFinal/" + "2024-11-28" + "?key=" + apiKey;
            List<ScoreNFLDto> games = sportsDataWebClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToFlux(ScoreNFLDto.class)
                    .collectList()
                    .block();

            if (games != null) games.forEach(this::upsertGame);
            fetchLogService.finishSuccess(log);
        } catch (Exception ex) {
            fetchLogService.finishError(log, ex);
            throw ex;
        }
    }

    private void upsertGame(ScoreNFLDto dto) {
        eventNflRepo.findByGameKey(dto.gameKey())
                .map(existing -> {
                    eventMapper.merge(existing, dto);
                    return eventNflRepo.save(existing);
                })
                .orElseGet(() -> eventNflRepo.save(eventMapper.toEntity(dto)));
    }

    // 🧩 Teams
    @Transactional
    public void importTeams() {
        if (skipIfLaunchedRecently(FetchIoType.TEAMS)) return;
        var log = fetchLogService.start(FetchIoType.TEAMS, SportIoType.NFL);
        try {
            String url = URL + SPORT_URL + "scores/json/teams?key=" + apiKey;
            List<SportsDataNFLTeamDTO> teams = sportsDataWebClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToFlux(SportsDataNFLTeamDTO.class)
                    .collectList()
                    .block();

            if (teams != null) teams.forEach(this::upsertTeam);
            fetchLogService.finishSuccess(log);
        } catch (Exception ex) {
            fetchLogService.finishError(log, ex);
            throw ex;
        }
    }

    // 🧍 Players
    public void importPlayers() {
        if (skipIfLaunchedRecently(FetchIoType.PLAYERS)) return;
        var log = fetchLogService.start(FetchIoType.PLAYERS, SportIoType.NFL);
        try {
            String url = URL + SPORT_URL + "scores/json/Players?key=" + apiKey;
            List<SportsDataNFLPlayerDTO> players = sportsDataWebClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToFlux(SportsDataNFLPlayerDTO.class)
                    .collectList()
                    .block();

            if (players != null) players.forEach(this::upsertPlayer);
            fetchLogService.finishSuccess(log);
        } catch (Exception ex) {
            fetchLogService.finishError(log, ex);
            throw ex;
        }
    }

    // 📊 Player Season Stats
    public void importPlayersStats() {
        if (skipIfLaunchedRecently(FetchIoType.PLAYER_STATS)) return;

        var log = fetchLogService.start(FetchIoType.PLAYER_STATS, SportIoType.NFL);
        try {                                                               // this need to be chnaged hardcoded year once the season is live
            String url = URL + SPORT_URL + "stats/json/PlayerSeasonStats/" + 2024 + "?key=" + apiKey;

            sportsDataWebClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToFlux(IoSportsDataNFLPlayerStatsDTO.class)
                    .flatMap(dto -> Mono.fromCallable(() -> {
                        transactionTemplate.execute(status -> {
                            upsertPlayerSeasonStat(dto, dto.getScoringDetails());
                            return null;
                        });
                        return dto;
                    }).subscribeOn(Schedulers.boundedElastic()))
                    .blockLast();

            fetchLogService.finishSuccess(log);
        } catch (Exception ex) {
            fetchLogService.finishError(log, ex);
            throw ex;
        }
    }

    private void upsertPlayerSeasonStat(IoSportsDataNFLPlayerStatsDTO dto, List<IoSportsDataNFLScoringDetailDTO> scoringDetails) {
        if (dto.getPlayerId() == null || dto.getSeason() == null) {
            return;
        }

        Optional<IoPlayerStatsNFL> existingStat = playerStatsRepo
                .findByPlayerIdAndSeason(dto.getPlayerId(), dto.getSeason());

        IoPlayerStatsNFL stat = existingStat.orElseGet(() -> playersStatsMapper.toEntity(dto));
        playersStatsMapper.merge(stat, dto);

        if (existingStat.isPresent()) {
            Hibernate.initialize(stat.getScoringDetails());
        }

        playersStatsMapper.mergeScoringDetails(stat, scoringDetails);
        playerStatsRepo.save(stat);
    }

    // 🏟️ Player Game Stats
    public void importPlayerGameStats() {
        if (skipIfLaunchedRecently(FetchIoType.PLAYER_GAME_STATS)) {
            return;
        }

        IoFetchLog log = fetchLogService.start(FetchIoType.PLAYER_GAME_STATS, SportIoType.NFL);
        final int pageSize = 200;
        final AtomicInteger pageCounter = new AtomicInteger(0);
        final AtomicInteger processedCounter = new AtomicInteger(0);
        final Logger logger = LoggerFactory.getLogger(getClass());

        Flux.<List<IoPlayerNFL>>generate(sink -> {
                    int currentPage = pageCounter.getAndIncrement();
                    List<IoPlayerNFL> chunk = playerRepo.findAll(PageRequest.of(currentPage, pageSize)).getContent();

                    if (chunk.isEmpty()) {
                        sink.complete();
                    } else {
                        sink.next(chunk);
                    }
                })
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSubscribe(sub -> logger.info("Starting import of approximately {} players...", playerRepo.count()))
                .flatMap(chunk -> Flux.fromIterable(chunk), 1)
                .flatMap(player ->
                                fetchAndUpsertPlayerGameStatsApi(player)
                                        .retryWhen(
                                                Retry.backoff(3, Duration.ofSeconds(1))
                                                        .doBeforeRetry(retry -> logger.warn("Retrying player {} (attempt {})",
                                                                player.getId(), retry.totalRetries() + 1))
                                                        .filter(throwable -> throwable instanceof Exception) // Ensure only Exceptions are retried
                                        )
                                        .onErrorResume(Exception.class, e -> {  // Explicitly handle Exception type
                                            logger.error("Failed to process player {}: {}", player.getId(), e.getMessage());
                                            return Mono.empty();
                                        }),
                        10)
                .doOnNext(stat -> {
                    int processed = processedCounter.incrementAndGet();
                })
                .doOnComplete(() -> {
                    logger.info("Completed processing {} players", processedCounter.get());
                    fetchLogService.finishSuccess(log);
                })
                .subscribe(
                        null,
                        error -> {
                            if (error instanceof Exception) {
                                logger.error("Fatal import error after {} players: {}",
                                        processedCounter.get(), error.getMessage());
                                fetchLogService.finishError(log, (Exception) error);
                            } else {
                                logger.error("Fatal non-Exception error after {} players", processedCounter.get());
                                fetchLogService.finishError(log, new RuntimeException(error));
                            }
                        }
                );
    }

    private Flux<IoPlayerGameStatsNFL> fetchAndUpsertPlayerGameStatsApi(IoPlayerNFL player) {
        String url = URL + SPORT_URL + "stats/json/PlayerGameStatsBySeason/"
                + 2024 + "/" + player.getPlayerId() + "/5?key=" + apiKey;

        return sportsDataWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(IoPlayerGameStatsNFLDto.class)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(dto -> Mono.fromCallable(() -> {
                    // Lookup by playerId and gameKey instead of statId
                    Optional<IoPlayerGameStatsNFL> existingOpt = playerGameStatsRepo
                            .findByPlayerIdAndGameKey(player.getPlayerId(), dto.getGameKey());

                    if (existingOpt.isPresent()) {
                        // Merge with existing entity
                        IoPlayerGameStatsNFL existing = existingOpt.get();
                        playerGameStatsMapper.merge(existing, dto);
                        return playerGameStatsRepo.save(existing);
                    } else {
                        // Create new entity
                        IoPlayerGameStatsNFL newEntity = playerGameStatsMapper.toEntity(dto);
                        newEntity.setPlayerId(player.getPlayerId()); // Ensure playerId is set
                        return playerGameStatsRepo.save(newEntity);
                    }
                }))
                .onErrorResume(e -> {
                    log.error("Error processing game stats for player {}: {}", player.getPlayerId(), e.getMessage());
                    return Mono.empty();
                });
    }

    // 💰 Betting Markets
    @Transactional
    public void importNflBetMarkets(LocalDate date) {
        if (skipIfLaunchedRecently(FetchIoType.BET)) {
            return;
        }

        var log = fetchLogService.start(FetchIoType.BET, SportIoType.NFL);
        try {
            processMarketsForDate(date);
            processMarketsForDate(date.plusDays(1));
            processMarketsForDate(date.plusDays(2));
            processMarketsForDate(date.plusDays(3));
            processMarketsForDate(date.plusDays(4));
            fetchLogService.finishSuccess(log);
        } catch (Exception ex) {
            fetchLogService.finishError(log, ex);
            throw ex;
        }
    }

    private void processMarketsForDate(LocalDate date) {
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = from.plusDays(1);

        eventNflRepo.findAllByDatetimeUtcBetween(from, to)
                .forEach(event -> {
                    if (event.getGlobalGameId() != null) {
                        fetchAndProcessNflMarketsForGame(event);
                    }
                });
    }

    private void fetchAndProcessNflMarketsForGame(IoEventNFL event) {
        String url = URL + SPORT_URL + "odds/json/BettingMarketsByGameID/" + event.getGlobalGameId() + "/G1000?key=" + apiKey;

        sportsDataWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(SportsDataBettingMarketDTO.class)
                .filter(this::isRelevantNflMarket)
                .collectList()
                .publishOn(Schedulers.boundedElastic())
                .subscribe(markets -> {
                    Set<IoBetNFL> existingBets = betNflRepo.findByEventIdAndMarketTypes(
                            event.getId(),
                            NflMarketTypes.PLAYER_MARKETS
                    );

                    Set<IoBetNFL> updatedBets = reconcileNflMarkets(event, markets, existingBets);
                    if (!updatedBets.isEmpty()) {
                        betNflRepo.saveAll(updatedBets);
                    }
                });
    }

    private boolean isRelevantNflMarket(SportsDataBettingMarketDTO dto) {
        return (NflMarketTypes.PLAYER_MARKETS.contains(dto.getBettingMarketType()) ||
                NflMarketTypes.TEAM_MARKETS.contains(dto.getBettingMarketType())) &&
                Boolean.FALSE.equals(dto.getAnyBetsAvailable());
    }

    public Set<IoBetNFL> reconcileNflMarkets(IoEventNFL event,
                                             List<SportsDataBettingMarketDTO> incomingMarkets,
                                             Set<IoBetNFL> existingBets) {
        // Create maps for existing data
        Map<Long, IoBetNFL> existingBetMap = existingBets.stream()
                .collect(Collectors.toMap(IoBetNFL::getMarketId, b -> b));

        Set<Long> activeMarketIds = incomingMarkets.stream()
                .map(SportsDataBettingMarketDTO::getBettingMarketId)
                .collect(Collectors.toSet());

        Set<IoBetNFL> betsToSave = new HashSet<>();

        // Process incoming markets
        for (SportsDataBettingMarketDTO dto : incomingMarkets) {
            IoBetNFL existingBet = existingBetMap.get(dto.getBettingMarketId());

            if (existingBet == null) {
                // New market
                betsToSave.add(betMapper.toEntity(dto, event));
            } else if (!Objects.equals(existingBet.getUpdatedAt(), dto.getUpdated())) {
                // Updated market
                betMapper.updateEntity(existingBet, dto);
                betsToSave.add(existingBet);
            }
        }

        // Handle markets that are no longer active
        existingBets.stream()
                .filter(b -> !activeMarketIds.contains(b.getMarketId()) && b.isAnyBetsAvailable())
                .forEach(b -> {
                    b.setAnyBetsAvailable(false);
                    b.getBettingOutcomes().forEach(o -> o.setAvailable(false));
                    betsToSave.add(b);
                });

        return betsToSave;
    }

    private boolean skipIfLaunchedRecently(FetchIoType type) {
        Duration window = Duration.ofMinutes(cooldownMinutes);
        if (fetchLogService.wasRunRecently(type, SportIoType.NFL, window)) {
            log.warn("[{}] Skipped – already ran within {}", type, window);
            return true;
        }
        return false;
    }

    private void upsertTeam(SportsDataNFLTeamDTO dto) {
        IoStadiumNFL stadium = resolveStadium(dto.getStadiumDetails());

        IoTeamNFL team = teamNFLRepo.findByTeamId(dto.getTeamId())
                .map(existing -> {
                    teamNFLMapper.updateEntity(existing, dto);
                    existing.setStadium(stadium);
                    return existing;
                })
                .orElseGet(() -> {
                    IoTeamNFL newTeam = teamNFLMapper.toEntity(dto);
                    newTeam.setStadium(stadium);
                    return newTeam;
                });

        teamNFLRepo.save(team);
    }

    @Transactional
    private IoStadiumNFL resolveStadium(SportsDataNFLStadiumDTO stadiumDTO) {
        if (stadiumDTO == null) return null;

        try {
            return stadiumRepo.findByStadiumId(stadiumDTO.getStadiumID())
                    .map(existing -> {
                        stadiumMapper.updateEntity(existing, stadiumDTO);
                        return stadiumRepo.save(existing);
                    })
                    .orElseGet(() -> stadiumRepo.save(stadiumMapper.toEntity(stadiumDTO)));

        } catch (DataIntegrityViolationException e) {
            // In case another thread inserts the same stadium concurrently
            return stadiumRepo.findByStadiumId(stadiumDTO.getStadiumID())
                    .map(existing -> {
                        stadiumMapper.updateEntity(existing, stadiumDTO);
                        return stadiumRepo.save(existing);
                    })
                    .orElseThrow(() -> new RuntimeException("Stadium insert failed and was not found on retry", e));
        }
    }

    private void upsertPlayer(SportsDataNFLPlayerDTO dto) {
        if (dto.getPlayerID() == null) return;
        playerRepo.findByPlayerId(dto.getPlayerID())
                .ifPresentOrElse(
                        ex -> {
                            playerMapper.merge(ex, dto);
                            playerRepo.save(ex);
                        },
                        () -> playerRepo.save(playerMapper.toEntity(dto))
                );
    }







    public void importVenue() {
        if (skipIfLaunchedRecently(FetchIoType.VENUE)) return;
        fetchAndUpsertVenue();
    }

    private void fetchAndUpsertVenue() {
        var log = fetchLogService.start(FetchIoType.VENUE, SportIoType.NFL);
        try {
            String url = URL + SPORT_URL + "scores/json/Stadiums?key=" + apiKey;
            List<SportsDataNFLStadiumDTO> dtos = sportsDataWebClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToFlux(SportsDataNFLStadiumDTO.class)
                    .collectList()
                    .block();

            if (dtos != null) dtos.forEach(this::upsertVenue);
            fetchLogService.finishSuccess(log);
        } catch (Exception ex) {
            fetchLogService.finishError(log, ex);
            throw ex;
        }
    }

    @Transactional
    private void upsertVenue(SportsDataNFLStadiumDTO dto) {
        try {
            stadiumRepo.findByStadiumId(dto.getStadiumID())
                    .ifPresentOrElse(
                            ex -> {
                                stadiumMapper.updateEntity(ex, dto);
                                stadiumRepo.save(ex);
                            },
                            () -> stadiumRepo.save(stadiumMapper.toEntity(dto))
                    );
        } catch (DataIntegrityViolationException e) {
            // In case another thread inserted the record
            stadiumRepo.findByStadiumId(dto.getStadiumID()).ifPresent(ex -> {
                stadiumMapper.updateEntity(ex, dto);
                stadiumRepo.save(ex);
            });
        }
    }
}
