package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.EventDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.repository.EventRepository;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.EventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class EventServiceImpl extends ExternalIdReadServiceImpl<Event, EventDTO, Long> implements EventService {

    protected EventServiceImpl(EventRepository repository) {
        super(repository);
    }

    @Override
    public List<EventDTO> listEvents(Integer competitionId, String dateFrom, String dateTo, List<Integer> venueIds
            , List<Integer> participantIds, String status, RequestQueryDTO requestQuery) {
        return null;
    }

    @Override
    public EventDTO getEventById(Long eventId) {
        return null;
    }
}
