package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.statscore.StatScoreAreaDTO;
import io.limeup.flexbets.sport.model.Area;
import org.springframework.stereotype.Component;

@Component
public class AreaMapper {

    public Area toEntity(StatScoreAreaDTO dto) {
        if (dto == null) return null;

        Area entity = new Area();
        return updateEntity(entity, dto);
    }

    public Area updateEntity(Area entity, StatScoreAreaDTO dto) {
        if (entity == null || dto == null) {
            return entity;
        }
        entity.setExternalId(dto.getId());
        entity.setName(dto.getName());
        entity.setAreaCode(dto.getAreaCode());
        entity.setParentAreaId(dto.getParentAreaId());

        return entity;
    }

}
