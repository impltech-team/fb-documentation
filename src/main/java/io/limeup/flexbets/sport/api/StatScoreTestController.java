package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.service.statscore.StatScoreClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/test/statscore")
public class StatScoreTestController {

    private final StatScoreClient statScoreClient;

    @GetMapping("/{eventId}/sub-participants")
    public Mono<String> getSubParticipants(@PathVariable String eventId) {
        Mono<String> result = statScoreClient.getEventSubParticipants(eventId);
        for (int i = 0; i < 1000; i++) {
            log.info(i + " calling /events/{eventId}/sub-participants");
            statScoreClient.getEventSubParticipants(eventId).block();
        }
        return result;
    }

}
