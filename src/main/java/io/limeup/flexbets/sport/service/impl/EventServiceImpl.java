package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.EventDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SportDTO;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.Sport;
import io.limeup.flexbets.sport.service.AbstractReadService;
import io.limeup.flexbets.sport.service.EventService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl extends AbstractReadService<Event, EventDTO, Long> implements EventService {

    protected EventServiceImpl(JpaRepository<Event, Long> repository) {
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
