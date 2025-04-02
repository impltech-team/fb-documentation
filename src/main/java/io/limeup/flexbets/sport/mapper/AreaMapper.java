package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.statscore.StatScoreAreaDTO;
import io.limeup.flexbets.sport.model.Area;
import org.springframework.stereotype.Component;

@Component
public class AreaMapper {

    public Area toEntity(StatScoreAreaDTO dto) {
        if (dto == null) return null;

        Area entity = new Area();
        entity.setId(dto.getId().longValue());
        entity.setName(dto.getName());
        entity.setCountryCode(dto.getAreaCode());
        entity.setParentAreaId(dto.getParentAreaId());
        return entity;
    }

    public StatScoreAreaDTO toDto(Area entity) {
        if (entity == null) return null;

        StatScoreAreaDTO dto = new StatScoreAreaDTO();
        dto.setId(entity.getId().intValue());
        dto.setName(entity.getName());
        dto.setAreaCode(entity.getCountryCode());
        dto.setParentAreaId(entity.getParentAreaId());
        return dto;
    }

}
