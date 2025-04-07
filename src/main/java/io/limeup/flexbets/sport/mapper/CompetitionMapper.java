package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.CompetitionType;
import io.limeup.flexbets.sport.model.Sport;
import io.limeup.flexbets.sport.model.StatusType;
import org.springframework.stereotype.Component;

@Component
public class CompetitionMapper {

    public Competition toEntity(StatScoreCompetitionDTO dto, Sport sport, Area area) {
        Competition entity = new Competition();

        return updateEntity(entity, dto, sport, area);
    }

    public Competition updateEntity(Competition entity, StatScoreCompetitionDTO dto, Sport sport, Area area) {
        if (entity == null || dto == null) {
            return entity;
        }
        entity.setExternalId(dto.getId());
        entity.setName(dto.getName());
        entity.setGender(dto.getGender());
        entity.setSport(sport);
        entity.setArea(area);

        if (dto.getType() != null) {
            try {
                entity.setType(CompetitionType.valueOf(dto.getType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                entity.setType(CompetitionType.UNDEFINED);
            }
        }

        if (dto.getStatus() != null) {
            try {
                entity.setStatusType(StatusType.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                entity.setStatusType(null);
            }
        }

        return entity;
    }

}
