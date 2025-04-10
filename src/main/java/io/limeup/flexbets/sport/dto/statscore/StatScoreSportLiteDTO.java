package io.limeup.flexbets.sport.dto.statscore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatScoreSportLiteDTO {
    @EqualsAndHashCode.Include
    private Integer id;
    private Integer lsId;
    private String name;
    private String url;
    private String active;
    private String hasTimer;
}
