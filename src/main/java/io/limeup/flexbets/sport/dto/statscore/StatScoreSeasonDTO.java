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
public class StatScoreSeasonDTO {
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String year;
    private String actual;
    private String statsLvl;
    private List<StatScoreStageDTO> stages;
    private StatScoreStageDTO stage;
}
