package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.statscore.StatScoreAreaDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreBracketDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportLiteDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreVenueDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.AreaQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.ParticipantQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.SportQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.VenueQueryParams;
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
    public ResponseEntity<PaginatedResponse<StatScoreParticipantDTO>> listParticipants(@Valid ParticipantQueryParams participantQueryParams) {
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

    @GetMapping("/area/list")
    public ResponseEntity<PaginatedResponse<StatScoreAreaDTO>> listAreas(@Valid AreaQueryParams query) {
        return ResponseEntity.ok(statScoreProxyService.listAreas(query));
    }

    @GetMapping("/sport/list")
    public ResponseEntity<PaginatedResponse<StatScoreSportLiteDTO>> listSports(@Valid SportQueryParams query) {
        return ResponseEntity.ok(statScoreProxyService.listSports(query));
    }

    @GetMapping("/sport/{sportId}")
    public ResponseEntity<StatScoreSportDTO> getSport(@PathVariable Integer sportId) {
        return ResponseEntity.ok(statScoreProxyService.getSportById(sportId));
    }

    @GetMapping("/venue/list")
    public ResponseEntity<PaginatedResponse<StatScoreVenueDTO>> listSports(@Valid VenueQueryParams query) {
        return ResponseEntity.ok(statScoreProxyService.listVenues(query));
    }

    @GetMapping("/venue/{venueId}")
    public ResponseEntity<StatScoreVenueDTO> getVenue(@PathVariable Integer venueId) {
        return ResponseEntity.ok(statScoreProxyService.getVenueById(venueId));
    }

    @GetMapping("/brackets/{stageId}")
    public ResponseEntity<PaginatedResponse<StatScoreBracketDTO>> listBracketsById(@PathVariable Integer stageId) {
        return ResponseEntity.ok(statScoreProxyService.listBracketsByStageId(stageId));
    }
}
