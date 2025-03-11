package io.limeup.flexbets.sport.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestQueryDTO {
    @Min(1)
    private int page = 1;

    @Min(1)
    @Max(100)
    private int pageSize = 50;

    private String sortBy = "id";

    private String sortOrder = "asc";

    private String filter;
}
