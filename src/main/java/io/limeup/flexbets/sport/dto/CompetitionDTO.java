package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionDTO {
    private Integer id;
    private String name;
    private String type;
    private Integer sportId;
    private String sportName;
    private Integer areaId;
    private String areaName;
    private String statusType;
    private String gender;
}
