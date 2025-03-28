package io.limeup.flexbets.sport.dto.statscore.prams;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandingQueryParams {
    private String lang;
    @Min(1)
    private Integer page = 1;
    @Max(500)
    @Min(1)
    private Integer limit = 500;
    private String objectType;
    private Integer objectId;
    private Integer typeId;
    private String subtype;
    private Integer sportId;
    private Integer competitionId;
    private Integer seasonId;
    private Integer stageId;
    private Long timestamp;
    private String itemStatus;
}
