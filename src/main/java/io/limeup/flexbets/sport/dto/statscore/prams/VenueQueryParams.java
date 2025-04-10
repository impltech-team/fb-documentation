package io.limeup.flexbets.sport.dto.statscore.prams;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueQueryParams {
    private Integer sportId;
    private Integer participantId;
    private Long timestamp;
    @Min(1)
    private Integer page = 1;
    @Max(500)
    @Min(1)
    private Integer limit = 500;
}

