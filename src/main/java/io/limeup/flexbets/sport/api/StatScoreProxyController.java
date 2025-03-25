package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.ParticipantQueryParams;
import io.limeup.flexbets.sport.service.StatScoreProxyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/proxy/statscore")
public class StatScoreProxyController {

    private final StatScoreProxyService statScoreProxyService;

    @GetMapping("/{event_id}/sub-participants")
    public ResponseEntity<PaginatedResponse<StatScoreSubParticipantDTO>> getSubParticipants(@PathVariable("event_id") Integer eventId) {
        return ResponseEntity.ok(statScoreProxyService.listEventSubParticipants(
                eventId));
    }

    @GetMapping("/{participant_id}/squad")
    public ResponseEntity<PaginatedResponse<StatScoreSubParticipantDTO>> getSquadSubParticipants(@PathVariable("participant_id") Integer participantId
            , @RequestParam(required = false) Integer seasonId) {
        return ResponseEntity.ok(statScoreProxyService.listSquadSubParticipants(
                participantId, seasonId));
    }

    @GetMapping("/participant/{participant_id}")
    public ResponseEntity<StatScoreParticipantDTO> getParticipant(@PathVariable("participant_id") Integer participantId) {
        return ResponseEntity.ok(statScoreProxyService.getParticipantById(participantId));
    }

    @GetMapping("/participant/list")
    public ResponseEntity<PaginatedResponse<StatScoreParticipantDTO>> listParticipants(ParticipantQueryParams participantQueryParams) {
        return ResponseEntity.ok(statScoreProxyService.listParticipants(participantQueryParams));
    }

    @GetMapping("/event/list")
    public ResponseEntity<PaginatedResponse<StatScoreCompetitionDTO>> listEvents(@Valid EventQueryParams query) {
        return ResponseEntity.ok(statScoreProxyService.listEvents(query));
    }

    @GetMapping("/event/{event_id}")
    public ResponseEntity<StatScoreCompetitionDTO> getEvent(@PathVariable("event_id") Integer eventId) {
        return ResponseEntity.ok(statScoreProxyService.getEventById(eventId));
    }

}
