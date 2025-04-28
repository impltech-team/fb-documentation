package io.limeup.flexbets.sport.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestQueryDTO {
    @Min(value = 1, message = "Must be greater than or equal to 1")
    private int page = 1;

    @Min(value = 1, message = "Must be greater than or equal to 1")
    @Max(value = 100, message = "Must be less than or equal to 100")
    private int pageSize = 25;

    private String sortBy;

    @Pattern(regexp = "asc|desc", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Parameter sort_order must be 'asc' or 'desc'")
    private String sortOrder = "asc";

    private String filter;
}
