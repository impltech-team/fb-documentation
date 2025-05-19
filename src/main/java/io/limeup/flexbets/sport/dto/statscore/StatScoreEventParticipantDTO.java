package io.limeup.flexbets.sport.dto.statscore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatScoreEventParticipantDTO {
    @EqualsAndHashCode.Include
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
