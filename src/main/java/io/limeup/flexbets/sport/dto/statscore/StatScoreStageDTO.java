package io.limeup.flexbets.sport.dto.statscore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatScoreStageDTO {
    private int id;
    private String name;
    private String startDate;
    private String endDate;
    private String showStandings;
    private int groupsNr;
    private int sort;
    private String isCurrent;
    private String hasBrackets;
    private List<StatScoreGroupDTO> groups;
    private StatScoreGroupDTO group;
}
