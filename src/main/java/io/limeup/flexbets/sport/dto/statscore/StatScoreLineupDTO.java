package io.limeup.flexbets.sport.dto.statscore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatScoreLineupDTO {
    @EqualsAndHashCode.Include
    private Integer id;
    private String type;
    private String bench;
    private Integer participantId;
    private String participantName;
    private Integer participantAreaId;
    private String shirtNr;
    private String participantSlug;
}
