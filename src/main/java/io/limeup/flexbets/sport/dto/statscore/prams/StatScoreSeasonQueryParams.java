package io.limeup.flexbets.sport.dto.statscore.prams;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatScoreSeasonQueryParams {
    private String lang;
    @Min(1)
    private Integer page = 1;
    @Max(500)
    @Min(1)
    private Integer limit = 500;
    private Integer sportId;
    private String competitionId;
    private Integer participantId;
    private String multiIds;
    private String year;
    private String sortType;
    private String sortOrder;
    private Integer areaId;
    private Long timestamp;
}
