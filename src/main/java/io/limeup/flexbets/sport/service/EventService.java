package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.EventDTO;
import io.limeup.flexbets.sport.dto.FullEventDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService extends ExternalIdReadService<Event, EventDTO, Long> {
    PaginatedResponse<EventDTO> listEvents(Integer competitionId, LocalDateTime dateFrom, LocalDateTime dateTo, List<Integer> venueIds
            , List<Integer> participantIds, String status, RequestQueryDTO requestQuery);

    FullEventDTO getEventById(Integer eventId);
}
