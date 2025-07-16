package io.limeup.flexbets.sport.dto.statscore.params;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SportQueryParams {
    @Min(1)
    private Integer page = 1;
    @Max(500)
    @Min(1)
    private Integer limit = 100;
    private Long timestamp;
}
