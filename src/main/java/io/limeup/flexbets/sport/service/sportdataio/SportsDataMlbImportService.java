package io.limeup.flexbets.sport.service.sportdataio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.limeup.flexbets.sport.dto.sportsdata.*;
import io.limeup.flexbets.sport.dto.statscore.params.FetchIoType;
import io.limeup.flexbets.sport.dto.statscore.params.SportIoType;
import io.limeup.flexbets.sport.mapper.*;
import io.limeup.flexbets.sport.model.*;
import io.limeup.flexbets.sport.model.dto.*;
import io.limeup.flexbets.sport.model.enums.IoEventStatus;
import io.limeup.flexbets.sport.repository.sportsdataio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class SportsDataMlbImportService {

    @Qualifier("sportsDataWebClient")
    private final WebClient sportsDataWebClient;

    private final IoEventRepository eventRepo;
    private final IoBetRepository betRepo;
    private final IoBetOutcomeRepository betOutcomeRepo;
    private final IoTeamRepository teamRepo;
    private final IoVenueRepository venueReposirory;
    private final IoPlayerRepository playerRepo;
    private final IoPlayerStatsRepository playerStatsRepo;
    private final IoPlayerGamesStatsRepository playerGameStatsRepo;

    private final IoEventMapper eventMapper;
    private final IoPlayerMapper playerMapper;
    private final IoVenueMapper ioVenueMapper;
    private final IoPlayersStatsMapper playersStatsMapper;
    private final IoPlayerGameStatsMapper playerGameStatsMapper;

    private final FetchLogService fetchLogService;

    private final ObjectMapper objectMapper;


    @Value("${sportsdata.key}")
    private String apiKey;

    private static final long cooldownMinutes = 5;
    public static final String URL = "https://api.sportsdata.io/v3/";
    public static final String SPORT_URL = "mlb/";

    private static final String PLAYER_MARKET_NAME = "Player Prop";
    private static final String TEAM_MARKET_NAME = "Team Prop";
    private final int seasonYear = Year.now().getValue();


    @Transactional
    public void importTeams() {
        if (skipIfLaunchedRecently(FetchIoType.TEAMS)) return;
        fetchAndUpsertTeams();
    }

    @Transactional
    public void importBetMarkets(LocalDate date) {
        if (skipIfLaunchedRecently(FetchIoType.BET)) return;
        queryGamesFromDbAndUpdateMarkets(date.minusDays(1));
        queryGamesFromDbAndUpdateMarkets(date);
        queryGamesFromDbAndUpdateMarkets(date.plusDays(1));
        queryGamesFromDbAndUpdateMarkets(date.plusDays(2));
        queryGamesFromDbAndUpdateMarkets(date.plusDays(3));
        queryGamesFromDbAndUpdateMarkets(date.plusDays(4));
    }

    public void importVenue() {
        if (skipIfLaunchedRecently(FetchIoType.VENUE)) return;
        fetchAndUpsertVenue();
    }

    private void fetchAndUpsertVenue() {
        var log = fetchLogService.start(FetchIoType.VENUE, SportIoType.MLB);
        try {
            String url = URL + SPORT_URL + "scores/json/Stadiums?key=" + apiKey;
            List<VenueImportDTO> dtos = sportsDataWebClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToFlux(VenueImportDTO.class)
                    .collectList()
                    .block();

            if (dtos != null) dtos.forEach(this::upsertVenue);
            fetchLogService.finishSuccess(log);
        } catch (Exception ex) {
            fetchLogService.finishError(log, ex);
            throw ex;
        }
    }

    private void upsertVenue(VenueImportDTO dto) {
        venueReposirory.findByStadiumId(dto.getStadiumId())
                .ifPresentOrElse(
                        ex -> {
                            ioVenueMapper.updateEntity(ex, dto);
                            venueReposirory.save(ex);
                        },
                        () -> venueReposirory.save(ioVenueMapper.toEntity(dto)));

    }

    private void queryGamesFromDbAndUpdateMarkets(LocalDate date) {
        var log = fetchLogService.start(FetchIoType.BET, SportIoType.MLB);
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = from.plusDays(1);
        try {
            eventRepo.findAllByDatetimeUtcBetween(from, to)
                    .stream()
                    .map(IoEvent::getGameId)
                    .filter(Objects::nonNull)
                    .forEach(this::fetchAndUpsertMarketsForGame);
            fetchLogService.finishSuccess(log);
        } catch (Exception ex) {
            fetchLogService.finishError(log, ex);
            throw ex;
        }
    }

    public void importPlayerGameStats() {
        if (skipIfLaunchedRecently(FetchIoType.PLAYER_GAME_STATS)) return;
        queryPlayersFromDbAndUpdateStats();
    }

    private void queryPlayersFromDbAndUpdateStats() {
        var log = fetchLogService.start(FetchIoType.PLAYER_GAME_STATS, SportIoType.MLB);
        try {
            playerRepo.findAll()
                    .forEach(this::fetchAndUpsertPlayerGameStatsApi);
            fetchLogService.finishSuccess(log);
        } catch (Exception ex) {
            fetchLogService.finishError(log, ex);
            throw ex;
        }
    }

    private void fetchAndUpsertMarketsForGame(Long gameId) {
        String url = URL + SPORT_URL + "odds/json/BettingMarketsByGameID/" + gameId + "?include=available&key=" + apiKey;
        sportsDataWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(SportsDataBettingMarketDTO.class)
                .filter(this::isPlayerMarketWithBets)
                .collectList()
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(dtos -> {
                    IoEvent event = eventRepo.findByGameId(gameId).orElse(null);
                    if (event == null) return;
                    Set<IoBet> toSave = reconcileBettingMarkets(event, dtos, betRepo.findByEventWithOutcomes(event));
                    if (!toSave.isEmpty()) betRepo.saveAll(toSave);
                })
                .block();
    }

    private boolean isPlayerMarketWithBets(SportsDataBettingMarketDTO dto) {
        return (PLAYER_MARKET_NAME.equalsIgnoreCase(dto.getBettingMarketType()) ||
                TEAM_MARKET_NAME.equalsIgnoreCase(dto.getBettingMarketType()))                ;
    }

    private void fetchAndUpsertTeams() {
        var log = fetchLogService.start(FetchIoType.TEAMS, SportIoType.MLB);
        try {
            String url = URL + SPORT_URL + "scores/json/Allteams?key=" + apiKey;
            List<SportsDataTeamDTO> dtos = sportsDataWebClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToFlux(SportsDataTeamDTO.class)
                    .collectList()
                    .block();

            if (dtos != null) dtos.forEach(this::upsertTeam);
            fetchLogService.finishSuccess(log);
        } catch (Exception ex) {
            fetchLogService.finishError(log, ex);
            throw ex;
        }
    }

    public void importPlayersStats() {
        if (skipIfLaunchedRecently(FetchIoType.PLAYER_STATS)) return;
        fetchAndUpsertPlayerSeasonStats();
    }

    private void fetchAndUpsertPlayerSeasonStats() {
        var log = fetchLogService.start(FetchIoType.PLAYER_STATS, SportIoType.MLB);
        try {
            String url = URL + SPORT_URL + "stats/json/PlayerSeasonStats/" + seasonYear + "?key=" + apiKey;
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
        if (skipIfLaunchedRecently(FetchIoType.SCORES)) return;
        fetchAndUpsertScores(date.minusDays(1));
        fetchAndUpsertScores(date);
        fetchAndUpsertScores(date.plusDays(1));
        fetchAndUpsertScores(date.plusDays(2));
        fetchAndUpsertScores(date.plusDays(3));
        fetchAndUpsertScores(date.plusDays(4));
    }

    private void fetchAndUpsertScores(LocalDate date) {
        var log = fetchLogService.start(FetchIoType.SCORES, SportIoType.MLB);
        try {
            List<ScoreBasicDto> games = fetchScoresFromApi(date);
            if (games != null) games.forEach(game -> {
                upsertGame(game);

                if(IoEventStatus.FINAL.getName().equals(game.status())){
                    Map<Long, IoBet> marketBetsMap = betRepo.findAllByEventGameId(game.gameId()).stream()
                            .collect(Collectors.toMap(IoBet::getMarketId, Function.identity()));
                    List<IoBetOutcome> betOutcomesToUpdate = collectBetOutcomesToUpdate(marketBetsMap);

                    if (!betOutcomesToUpdate.isEmpty()) {
                        betOutcomeRepo.saveAll(betOutcomesToUpdate);
                    }
                }

            });
            fetchLogService.finishSuccess(log);
        } catch (Exception ex) {
            fetchLogService.finishError(log, ex);
            throw ex;
        }
    }

    private List<ScoreBasicDto> fetchScoresFromApi(LocalDate date) {
        String url = URL + SPORT_URL + "scores/json/ScoresBasicFinal/" + date + "?key=" + apiKey;
        return sportsDataWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(ScoreBasicDto.class)
                .collectList()
                .block();
    }

    public void importPlayers() {
        if (skipIfLaunchedRecently(FetchIoType.PLAYERS)) return;
        fetchAndUpsertPlayers();
    }

    private void fetchAndUpsertPlayers() {
        var log = fetchLogService.start(FetchIoType.PLAYERS, SportIoType.MLB);
        try {
            List<SportsDataPlayerDTO> players = fetchPlayersFromApi();
            if (players != null) players.forEach(this::upsertPlayer);
            fetchLogService.finishSuccess(log);
        } catch (Exception ex) {
            fetchLogService.finishError(log, ex);
            throw ex;
        }
    }


    private List<SportsDataPlayerDTO> fetchPlayersFromApi() {
        String url = URL + SPORT_URL + "scores/json/Players?key=" + apiKey;
        return sportsDataWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(SportsDataPlayerDTO.class)
                .collectList()
                .block();
    }


    private boolean skipIfLaunchedRecently(FetchIoType type) {
        Duration window = Duration.ofMinutes(cooldownMinutes);
        if (fetchLogService.wasRunRecently(type, SportIoType.MLB, window)) {
            log.warn("[{}] Skipped – already ran within {}", type, window);
            return true;
        }
        return false;
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


    private void upsertGame(ScoreBasicDto dto) {
        eventRepo.findByGameId(dto.gameId())
                .map(ex -> {
                    eventMapper.merge(ex, dto);
                    return eventRepo.save(ex);
                })
                .orElseGet(() -> eventRepo.save(eventMapper.toEntity(dto)));
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

    private Flux<IoPlayerGameStats> fetchAndUpsertPlayerGameStatsApi(IoPlayer player) {
        String url = URL + SPORT_URL + "stats/json/PlayerGameStatsBySeason/"
                + seasonYear + "/" + player.getPlayerId() + "/5?key=" + apiKey;

        return sportsDataWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(IoPlayerGameStatsDto.class)
                .publishOn(Schedulers.boundedElastic())
                .map(dto -> playerGameStatsRepo.findByStatId(dto.getStatId())
                        .map(ex -> {
                            playerGameStatsMapper.merge(ex, dto);
                            return ex;
                        })
                        .orElseGet(() -> playerGameStatsMapper.toEntity(dto))
                );
    }

    private void upsertTeam(SportsDataTeamDTO dto) {
        teamRepo.findByTeamId(dto.getTeamId())
                .ifPresentOrElse(
                        ex -> {
                            IoTeamMapper.updateEntity(ex, dto);
                            teamRepo.save(ex);
                        },
                        () -> teamRepo.save(IoTeamMapper.toEntity(dto)));
    }

    private IoBettingMarketResultDto fetchBettingMarketResultFromApi(Long marketId) {
        String url = URL + SPORT_URL + "odds/json/BettingMarketResults/" + marketId + "?key=" + apiKey;

        JsonNode bettingMarketResult = sportsDataWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (bettingMarketResult == null || bettingMarketResult.isEmpty()) {
            return null;
        }

        try {
            return objectMapper.readerFor(IoBettingMarketResultDto.class).readValue(bettingMarketResult);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse response from Betting Results - by Market SportsDataIo API endpoint", e);
        }
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
                toSave.add(IoBetMapper.toEntity(dto, event));
            } else if (!Objects.equals(existing.getUpdatedAt(), dto.getUpdated())) {
                IoBet replaced = IoBetMapper.toEntity(dto, event);
                replaced.setBetOutcomes(reconcileBetOutcomes(
                        dto.getBettingOutcomes(), existing.getBetOutcomes(), replaced));
                toSave.add(replaced);
            }
        }

        db.stream()
                .filter(b -> !active.contains(b.getMarketId()) && b.isAnyBetsAvailable())
                .forEach(b -> {
                    b.setAnyBetsAvailable(false);
                    b.setUpdatedAt(LocalDateTime.now());
                    b.getBetOutcomes().forEach(o -> {
                        o.setAvailable(false);
                        o.setUpdatedAt(LocalDateTime.now());
                    });
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
                toSave.add(IoBetMapper.toBetOutcomeEntity(dto, bet));
            } else if (!Objects.equals(ex.getUpdatedAt(), dto.getUpdated())) {
                toSave.add(IoBetMapper.updateEntity(ex, dto, bet));
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

    public void importScoresRange(LocalDate from, LocalDate to) {
        if (skipIfLaunchedRecently(FetchIoType.SCORES)) return;

        for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {
            fetchAndUpsertScores(date);
        }
    }

    public void importBetsRange(LocalDate from, LocalDate to) {
        if (skipIfLaunchedRecently(FetchIoType.BET)) return;

        for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {
            queryGamesFromDbAndUpdateMarkets(date);
        }
    }

    private List<IoBetOutcome> collectBetOutcomesToUpdate(Map<Long, IoBet> marketBetsMap) {
        List<IoBetOutcome> updatedOutcomes = new ArrayList<>();

        for (Map.Entry<Long, IoBet> entry : marketBetsMap.entrySet()) {
            Long marketId = entry.getKey();
            IoBet ioBet = entry.getValue();

            List<IoBetOutcome> pendingOutcomes = ioBet.getBetOutcomes().stream()
                    .filter(o -> o.getResultTypeId() == null)
                    .toList();

            if (pendingOutcomes.isEmpty()) continue;

            IoBettingMarketResultDto result = fetchBettingMarketResultFromApi(marketId);
            if (result == null || !result.isResultSupported()) continue;

            Map<Long, IoBettingOutcomeResultDto> resultMap = result.getResults().stream()
                    .collect(Collectors.toMap(IoBettingOutcomeResultDto::outcomeId, Function.identity()));

            for (IoBetOutcome outcome : pendingOutcomes) {
                IoBettingOutcomeResultDto outcomeResult = resultMap.get(outcome.getOutcomeId());
                if (outcomeResult != null) {
                    updatedOutcomes.add(IoBetMapper.addResultData(outcome, outcomeResult));
                }
            }
        }

        return updatedOutcomes;
    }
}
