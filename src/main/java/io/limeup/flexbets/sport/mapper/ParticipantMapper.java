package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.statscore.StatScoreEventParticipantDTO;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Participant;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ParticipantMapper {
    public Participant toEntity(StatScoreEventParticipantDTO dto, Competition competition) {
        Participant p = new Participant();
        p.setId((long) dto.getId());
        p.setTeamName(dto.getName());
        p.setAcronym(dto.getAcronym());
        p.setCompetition(competition);
        p.setType(dto.getType());
        p.setHistoricalStats(new ArrayList<>());
        return p;
    }
}

