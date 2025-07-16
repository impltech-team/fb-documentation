package io.limeup.flexbets.sport.dto.statscore.params;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupQueryParams {
    @Min(1)
    private Integer page = 1;
    @Max(500)
    @Min(1)
    private Integer limit = 500;
    @NotNull(message = "stage_id is required")
    private Integer stageId;
    private Long timestamp;
}
