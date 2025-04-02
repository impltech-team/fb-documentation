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

    public static Competition toEntity(StatScoreCompetitionDTO dto, Sport sport, Area area) {
        Competition competition = new Competition();

        competition.setId((long) dto.getId());
        competition.setName(dto.getName());
        competition.setGender(dto.getGender());
        competition.setSport(sport);
        competition.setArea(area);

        if (dto.getType() != null) {
            try {
                competition.setType(CompetitionType.valueOf(dto.getType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                competition.setType(CompetitionType.UNDEFINED);
            }
        }

        if (dto.getStatus() != null) {
            try {
                competition.setStatusType(StatusType.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                competition.setStatusType(null);
            }
        }

        return competition;
    }

}
