package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.EventDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService  {
    PaginatedResponse<EventDTO> listEvents(Integer competitionId, LocalDateTime dateFrom, LocalDateTime dateTo, List<Integer> venueIds
            , List<Integer> participantIds, String status, RequestQueryDTO requestQuery);

    EventDTO getEventById(Integer eventId);

}
