package io.limeup.flexbets.sport.dto.statscore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatScoreStageDTO {
    @EqualsAndHashCode.Include
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
