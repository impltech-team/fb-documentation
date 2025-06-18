package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricalStatDTO {
    private String statName;
    private BigDecimal average;
    private Integer count;
    private Integer maxValue;
    private Integer minValue;
    private List<EventStatisticDTO> eventStatistics;
}
