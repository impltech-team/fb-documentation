package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.statscore.StatScoreEventDTO;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.Venue;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Component
public class EventMapper {

    public Event toEntity(StatScoreEventDTO dto, Competition competition, Venue venue) {
        if (dto == null) {
            return null;
        }

        Event event = new Event();
        event.setId((long) dto.getId());
        event.setName(dto.getName());

        if (dto.getStartDate() != null) {
            event.setStartDate(LocalDateTime.parse(dto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }

        event.setCompetition(competition);
        event.setVenue(venue);
        event.setParticipants(new ArrayList<>());

        return event;
    }

}
