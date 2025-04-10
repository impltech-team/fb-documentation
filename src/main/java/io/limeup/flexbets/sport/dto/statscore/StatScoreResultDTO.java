package io.limeup.flexbets.sport.dto.statscore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatScoreResultDTO {
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String shortName;
    private String value;
    private String dataType;
}
