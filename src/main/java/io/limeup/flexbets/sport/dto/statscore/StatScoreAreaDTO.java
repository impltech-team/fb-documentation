package io.limeup.flexbets.sport.dto.statscore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatScoreAreaDTO {
    @EqualsAndHashCode.Include
    private Integer id;
    private String areaCode;
    private String name;
    private Integer parentAreaId;
    private Long ut;
}
