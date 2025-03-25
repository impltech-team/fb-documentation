package io.limeup.flexbets.sport.dto.statscore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatScoreSeasonDTO {
    private int id;
    private String name;
    private String year;
    private String actual;
    private String statsLvl;
    private List<StatScoreStageDTO> stages;
    private StatScoreStageDTO stage;
}
