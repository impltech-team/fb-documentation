package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalStatDTO {
    private String statName;
    private Double average;
    private Integer count;
    private Integer maxValue;
    private Integer minValue;
    private List<EventStatisticDTO> eventStatistics;
}
