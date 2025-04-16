package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.statscore.StatScoreEventDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSeasonDTO;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.Venue;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Component
public class EventMapper {

    public Event toEntity(StatScoreEventDTO dto, Competition competition, Venue venue, StatScoreSeasonDTO season) {
        if (dto == null) {
            return null;
        }

        Event entity = new Event();
        return updateEntity(entity, dto, competition, venue, season);
    }

    public Event updateEntity(Event entity, StatScoreEventDTO dto, Competition competition, Venue venue, StatScoreSeasonDTO season) {
        if (entity == null || dto == null) {
            return entity;
        }
        entity.setExternalId(dto.getId());
        entity.setName(dto.getName());

        if (dto.getStartDate() != null) {
            entity.setStartDate(LocalDateTime.parse(dto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }

        entity.setCompetition(competition);
        entity.setVenue(venue);
        entity.setSeasonExternalId(season.getId());
        entity.setSeasonName(season.getName());
        return entity;
    }

}
