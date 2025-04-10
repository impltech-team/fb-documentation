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
        return updateEntity(entity, dto);
    }

    public Sport toEntity(StatScoreSportDTO dto) {
        if (dto == null) return null;

        Sport entity = new Sport();
        return updateEntity(entity, dto);
    }

    public Sport updateEntity(Sport entity, StatScoreSportLiteDTO dto) {
        if (entity == null || dto == null) {
            return entity;
        }
        entity.setExternalId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public Sport updateEntity(Sport entity, StatScoreSportDTO dto) {
        if (entity == null || dto == null) {
            return entity;
        }
        entity.setExternalId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

}
