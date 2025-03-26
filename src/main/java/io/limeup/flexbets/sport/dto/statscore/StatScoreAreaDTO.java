package io.limeup.flexbets.sport.dto.statscore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatScoreAreaDTO {
    private Integer id;
    private String areaCode;
    private String name;
    private Integer parentAreaId;
    private Long ut;
}
