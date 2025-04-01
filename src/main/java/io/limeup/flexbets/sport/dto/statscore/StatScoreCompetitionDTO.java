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
public class StatScoreCompetitionDTO {
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String shortName;
    private String miniName;
    private String gender;
    private String type;
    private int areaId;
    private String areaName;
    private String areaCode;
    private int sportId;
    private String sportName;
    private String status;
    private List<StatScoreSeasonDTO> seasons;
    private StatScoreSeasonDTO season;
}
