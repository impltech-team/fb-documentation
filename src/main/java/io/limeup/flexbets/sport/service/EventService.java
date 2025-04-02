package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.EventDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.model.Event;

import java.util.List;

public interface EventService extends ReadService<Event, EventDTO, Long> {
    List<EventDTO> listEvents(Integer competitionId, String dateFrom, String dateTo, List<Integer> venueIds
            , List<Integer> participantIds, String status, RequestQueryDTO requestQuery);

    EventDTO getEventById(Long eventId);
}
