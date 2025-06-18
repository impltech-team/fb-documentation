//package io.limeup.flexbets.sport.model.dto;
//
//import io.limeup.flexbets.sport.repository.sportsdataio.IoEventRepository;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.time.Duration;
//import java.time.LocalDate;
//
//@Service
//@RequiredArgsConstructor
//public class SportsDataReactivePoller {
//
//    private final WebClient sportsDataWebClient;
//    private final IoEventRepository repo;
//    private final IoEventMapper mapper;
//
//    @PostConstruct
//    public void start() {
//        Flux.interval(Duration.ZERO, Duration.ofSeconds(5))
//                .flatMap(tick -> requestAndSave())
//                .subscribe();
//    }
//
//
//    private Mono<Void> requestAndSave() {
//
//        String path = String.format(
//                "/v3/mlb/scores/json/ScoresBasicFinal/%s",
//                LocalDate.now()
//        );
//
//        return sportsDataWebClient.get()
//                .uri(path)
//                .retrieve()
//                .bodyToFlux(ScoreBasicDto.class)                // Flux<ScoreBasicDto>
//
//                /* -------- upsert для кожного DTO -------- */
//                .flatMap(dto ->
//                        repo.findByGameId(dto.gameId())             // Mono<IoEvent>
//                                .map(existing -> {                      // ─ merge
//                                    mapper.merge(existing, dto);
//                                    return existing;                    // ← ОБОВʼЯЗКОВО!
//                                })
//                                .switchIfEmpty(                         // ─ create
//                                        Mono.fromCallable(() -> mapper.toEntity(dto))
//                                )
//                )
//
//                /* -------- одразу зберігаємо -------- */
//                .flatMap(repo::save)                            // Mono<IoEvent>
//
//                /* -------- сигнал завершення -------- */
//                .then();                                        // Mono<Void>
//    }
//
//
//
//}
