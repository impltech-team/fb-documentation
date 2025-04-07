package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.statscore.StatScoreVenueDTO;
import io.limeup.flexbets.sport.model.Venue;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class VenueMapper {

    public Venue toEntity(StatScoreVenueDTO dto) {
        if (dto == null) return null;

        Venue entity = new Venue();
        entity.setExternalId(dto.getId());
        entity.setName(dto.getName());
        entity.setCity(dto.getCity());
        entity.setCountry(dto.getCountry());
        entity.setLat(dto.getLat());
        entity.setLng(dto.getLng());
        if (dto.getOpened() != null) {
            try {
                entity.setOpened(LocalDate.parse(dto.getOpened()));
            } catch (DateTimeParseException e) {
                entity.setOpened(null);
            }
        }
        entity.setShortName(dto.getShortName());
        entity.setStatus(dto.getStatus());
        entity.setUrl(dto.getUrl());
        return entity;
    }

}
