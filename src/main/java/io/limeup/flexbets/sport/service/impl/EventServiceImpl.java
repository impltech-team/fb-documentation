package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.EventDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Override
    public List<EventDTO> listEvents(Integer competitionId, String dateFrom, String dateTo, List<Integer> venueIds, List<Integer> participantIds, RequestQueryDTO requestQuery) {
        return null;
    }

    @Override
    public EventDTO getEventById(Long eventId) {
        return null;
    }
}
