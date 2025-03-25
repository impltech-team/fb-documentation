package io.limeup.flexbets.sport.dto.statscore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatScoreStatDTO {
    private Integer id;
    private String name;
    private String shortName;
    private String value;
    private String dataType;
}
