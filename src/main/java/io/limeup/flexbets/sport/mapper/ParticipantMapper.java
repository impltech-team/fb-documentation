package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.statscore.StatScoreEventParticipantDTO;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Participant;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ParticipantMapper {

    public Participant toEntity(StatScoreEventParticipantDTO dto, Competition competition) {
        Participant entity = new Participant();
        return updateEntity(entity, dto, competition);
    }

    public Participant updateEntity(Participant entity, StatScoreEventParticipantDTO dto, Competition competition) {
        if (dto == null || entity == null) return entity;
        entity.setExternalId(dto.getId());
        entity.setTeamName(dto.getName());
        entity.setAcronym(dto.getAcronym());
        entity.setCompetition(competition);
        entity.setType(dto.getType());
        entity.setHistoricalStats(new ArrayList<>());
        return entity;
    }
}

