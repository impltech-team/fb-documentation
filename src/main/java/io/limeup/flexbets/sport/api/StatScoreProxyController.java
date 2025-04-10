package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.SingleRootItemPaginatedResponse;
import io.limeup.flexbets.sport.dto.statscore.StatScoreAreaDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreBracketDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportLiteDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreStandingDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreVenueDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.AreaQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.CompetitionQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.GroupQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.ParticipantQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.SportQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StandingByIdQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StandingQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.SeasonQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StageQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.VenueQueryParams;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
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
@RequestMapping("/v1/proxy/statscore")
public class StatScoreProxyController {

    private final StatScoreProxyService statScoreProxyService;

    @GetMapping("/{event_id}/sub-participants")
    public ResponseEntity<PaginatedResponse<StatScoreSubParticipantDTO>> getSubParticipants(@PathVariable("event_id") Integer eventId) {
        return ResponseEntity.ok(statScoreProxyService.listEventSubParticipants(
                eventId, false));
    }

    @GetMapping("/{participant_id}/squad")
    public ResponseEntity<PaginatedResponse<StatScoreSubParticipantDTO>> getSquadSubParticipants(@PathVariable("participant_id") Integer participantId
            , @RequestParam(required = false) Integer seasonId) {
        return ResponseEntity.ok(statScoreProxyService.listSquadSubParticipants(
                participantId, seasonId, false));
    }

    @GetMapping("/participant/{participant_id}")
    public ResponseEntity<StatScoreParticipantDTO> getParticipant(@PathVariable("participant_id") Integer participantId) {
        return ResponseEntity.ok(statScoreProxyService.getParticipantById(participantId, false));
    }

    @GetMapping("/participant/list")
    public ResponseEntity<PaginatedResponse<StatScoreParticipantDTO>> listParticipants(@Valid ParticipantQueryParams participantQueryParams) {
        return ResponseEntity.ok(statScoreProxyService.listParticipants(participantQueryParams, false));
    }

    @GetMapping("/event/list")
    public ResponseEntity<PaginatedResponse<StatScoreCompetitionDTO>> listEvents(@Valid EventQueryParams query) {
        return ResponseEntity.ok(statScoreProxyService.listEvents(query, false));
    }

    @GetMapping("/event/{event_id}")
    public ResponseEntity<StatScoreCompetitionDTO> getEvent(@PathVariable("event_id") Integer eventId) {
        return ResponseEntity.ok(statScoreProxyService.getEventById(eventId, false));
    }

    @GetMapping("/area/list")
    public ResponseEntity<PaginatedResponse<StatScoreAreaDTO>> listAreas(@Valid AreaQueryParams query) {
        return ResponseEntity.ok(statScoreProxyService.listAreas(query, false));
    }

    @GetMapping("/sport/list")
    public ResponseEntity<PaginatedResponse<StatScoreSportLiteDTO>> listSports(@Valid SportQueryParams query) {
        return ResponseEntity.ok(statScoreProxyService.listSports(query, false));
    }

    @GetMapping("/sport/{sportId}")
    public ResponseEntity<StatScoreSportDTO> getSport(@PathVariable Integer sportId) {
        return ResponseEntity.ok(statScoreProxyService.getSportById(sportId, false));
    }

    @GetMapping("/venue/list")
    public ResponseEntity<PaginatedResponse<StatScoreVenueDTO>> listSports(@Valid VenueQueryParams query) {
        return ResponseEntity.ok(statScoreProxyService.listVenues(query, false));
    }

    @GetMapping("/venue/{venueId}")
    public ResponseEntity<StatScoreVenueDTO> getVenue(@PathVariable Integer venueId) {
        return ResponseEntity.ok(statScoreProxyService.getVenueById(venueId, false));
    }

    @GetMapping("/brackets/{stageId}")
    public ResponseEntity<PaginatedResponse<StatScoreBracketDTO>> listBracketsById(@PathVariable Integer stageId) {
        return ResponseEntity.ok(statScoreProxyService.listBracketsByStageId(stageId, false));
    }

    @GetMapping("/group/list")
    public ResponseEntity<SingleRootItemPaginatedResponse<StatScoreCompetitionDTO>> listGroups(@Valid GroupQueryParams query) {
        return ResponseEntity.ok(statScoreProxyService.listGroups(query, false));
    }

    @GetMapping("/season/list")
    public ResponseEntity<PaginatedResponse<StatScoreCompetitionDTO>> listSeasons(SeasonQueryParams query) {
        return ResponseEntity.ok(statScoreProxyService.listSeasons(query, false));
    }

    @GetMapping("/season/{seasonId}")
    public ResponseEntity<StatScoreCompetitionDTO> getSeason(@PathVariable Integer seasonId) {
        return ResponseEntity.ok(statScoreProxyService.getSeasonById(seasonId, false));
    }

    @GetMapping("/stage/list")
    public ResponseEntity<SingleRootItemPaginatedResponse<StatScoreCompetitionDTO>> listStages(StageQueryParams query) {
        return ResponseEntity.ok(statScoreProxyService.listStages(query, false));
    }

    @GetMapping("/stage/{stageId}")
    public ResponseEntity<StatScoreCompetitionDTO> getStage(@PathVariable Integer stageId) {
        return ResponseEntity.ok(statScoreProxyService.getStageById(stageId, false));
    }

    @GetMapping("/standing/list")
    public ResponseEntity<PaginatedResponse<StatScoreStandingDTO>> listStandings(StandingQueryParams query) {
        return ResponseEntity.ok(statScoreProxyService.listStandings(query, false));
    }

    @GetMapping("/standing/{standingId}")
    public ResponseEntity<StatScoreStandingDTO> getStanding(@PathVariable Integer standingId, StandingByIdQueryParams query) {
        return ResponseEntity.ok(statScoreProxyService.getStanding(standingId, query, false));
    }

    @GetMapping("/competition/list")
    public ResponseEntity<PaginatedResponse<StatScoreCompetitionDTO>> listCompetitions(CompetitionQueryParams query) {
        return ResponseEntity.ok(statScoreProxyService.listCompetitions(query, false));
    }

    @GetMapping("/competition/{competitionId}")
    public ResponseEntity<StatScoreCompetitionDTO> getStanding(@PathVariable Integer competitionId) {
        return ResponseEntity.ok(statScoreProxyService.getCompetition(competitionId, false));
    }
}
