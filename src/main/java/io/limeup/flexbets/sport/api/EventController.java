package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.EventDTO;
import io.limeup.flexbets.sport.dto.FullEventDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("/list")
    public ResponseEntity<PaginatedResponse<EventDTO>> listEvents(
            @RequestParam(name = "competition_id") Integer competitionId,
            @RequestParam(required = false, name = "date_from") LocalDateTime dateFrom,
            @RequestParam(required = false, name = "date_to") LocalDateTime dateTo,
            @RequestParam(required = false, name = "venue_ids") List<Integer> venueIds,
            @RequestParam(required = false, name = "participant_ids") List<Integer> participantIds,
            @RequestParam(required = false, name = "status") String status,
            @ParameterObject RequestQueryDTO requestQuery) {
        return ResponseEntity.ok(eventService.listEvents(
                competitionId, dateFrom, dateTo, venueIds, participantIds, status, requestQuery));
    }

    @GetMapping("/{event_id}")
    public ResponseEntity<FullEventDTO> getEventById(@PathVariable("event_id") Integer eventId) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }
}
