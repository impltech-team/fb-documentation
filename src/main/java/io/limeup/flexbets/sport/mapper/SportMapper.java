package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.statscore.StatScoreSportDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportLiteDTO;
import io.limeup.flexbets.sport.model.Sport;
import org.springframework.stereotype.Component;

@Component
public class SportMapper {

    public Sport toEntity(StatScoreSportLiteDTO dto) {
        if (dto == null) return null;

        Sport entity = new Sport();
        entity.setId(dto.getId().longValue());
        entity.setName(dto.getName());
        return entity;
    }

    public Sport toEntity(StatScoreSportDTO dto) {
        if (dto == null) return null;

        Sport entity = new Sport();
        entity.setId(dto.getId().longValue());
        entity.setName(dto.getName());
        return entity;
    }

    public StatScoreSportDTO toDto(Sport entity) {
        if (entity == null) return null;

        StatScoreSportDTO dto = new StatScoreSportDTO();
        dto.setId(entity.getId().intValue());
        dto.setName(entity.getName());
        return dto;
    }

}
