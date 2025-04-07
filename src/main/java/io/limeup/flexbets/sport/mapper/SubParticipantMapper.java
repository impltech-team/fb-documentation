package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Participant;
import io.limeup.flexbets.sport.model.SubParticipant;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class SubParticipantMapper {

    public SubParticipant toEntity(StatScoreSubParticipantDTO dto, Area area, Participant participant, Competition competition) {
        if (dto == null) return null;

        SubParticipant entity = new SubParticipant();
        return updateEntity(entity, dto, area, participant, competition);
    }

    public SubParticipant updateEntity(SubParticipant entity, StatScoreSubParticipantDTO dto, Area area, Participant participant, Competition competition) {
        if (dto == null || entity == null) return entity;
        entity.setExternalId(dto.getId());
        entity.setPlayerName(dto.getName());
        entity.setShirtNumber(dto.getShirtNr());
        entity.setAvatarUrl(dto.getLogo());
        entity.setGender(dto.getGender());

        if (dto.getDetails() != null) {
            entity.setWeight(dto.getDetails().getWeight());
            entity.setHeight(dto.getDetails().getHeight());
            entity.setPosition(dto.getDetails().getPositionName());

            if (dto.getDetails().getBirthdate() != null) {
                try {
                    entity.setBirthDate(LocalDate.parse(dto.getDetails().getBirthdate()));
                } catch (DateTimeParseException e) {
                    entity.setBirthDate(null);
                }
            }
        }

        entity.setArea(area);
        if (participant != null) {
            entity.setParticipant(participant);
        }
        entity.setCompetition(competition);
        return entity;
    }


}
