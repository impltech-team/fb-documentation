package io.limeup.flexbets.sport.dto.statscore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatScoreEventParticipantDTO {
    private int id;
    private String name;
    private String shortName;
    private String acronym;
    private String gender;
    private int areaId;
    private String areaName;
    private String areaCode;
    private int sportId;
    private String sportName;
    private String type;
    private int counter; // 1 = home team
    private List<StatScoreResultDTO> results;
    private List<StatScoreStatDTO> stats;
    private List<StatScoreLineupDTO> lineups;
}
