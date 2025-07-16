package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.EventDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.service.EventService;
import io.limeup.flexbets.sport.service.resolver.EventServiceResolver;
import io.limeup.flexbets.sport.validator.PositiveList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/events")
@RequiredArgsConstructor
@Validated
public class EventController {
    private final EventServiceResolver serviceResolver;

    @GetMapping("/list")
    public ResponseEntity<PaginatedResponse<EventDTO>> listEvents(
            @RequestParam(name = "competition_id") Integer competitionId,
            @RequestParam(required = false, name = "date_from")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(required = false, name = "date_to")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @PositiveList
            @RequestParam(required = false, name = "venue_ids") List<Integer> venueIds,
            @PositiveList
            @RequestParam(required = false, name = "participant_ids") List<Integer> participantIds,
            @RequestParam(required = false, name = "status") String status,
            @ParameterObject @Valid RequestQueryDTO requestQuery) {
        EventService eventService = serviceResolver.resolve(competitionId.toString());
        return ResponseEntity.ok(eventService.listEvents(
                competitionId, dateFrom, dateTo, venueIds, participantIds, status, requestQuery));
    }

    @GetMapping("/{event_id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable("event_id") Integer eventId,
                                                     @RequestParam(name = "competition_id") Integer competitionId) {
        EventService eventService = serviceResolver.resolve(competitionId.toString());
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }
}
