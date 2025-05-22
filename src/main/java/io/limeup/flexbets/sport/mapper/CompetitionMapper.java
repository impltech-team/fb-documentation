package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.CompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.enums.CompetitionType;
import io.limeup.flexbets.sport.model.Sport;
import io.limeup.flexbets.sport.model.enums.StatusType;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        entity.setLsId(dto.getLsId());
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

    public static List<CompetitionDTO> toDTO(List<Competition> competitionList) {
        if (competitionList == null) return Collections.emptyList();

        return competitionList.stream().map(c -> {
            CompetitionDTO dto = new CompetitionDTO();
            dto.setId(c.getExternalId());
            dto.setName(c.getName());
            dto.setType(c.getType() != null ? c.getType().name() : null);
            dto.setSportId(c.getSport() != null ? c.getSport().getExternalId() : null);
            dto.setSportName(c.getSport() != null ? c.getSport().getName() : null);
            dto.setAreaId(c.getArea() != null ? c.getArea().getExternalId() : null);
            dto.setAreaName(c.getArea() != null ? c.getArea().getName() : null);
            dto.setStatusType(c.getStatusType() != null ? c.getStatusType().name() : null);
            dto.setGender(c.getGender());

            return dto;
        }).collect(Collectors.toList());
    }

}
