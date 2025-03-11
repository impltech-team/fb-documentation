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
    private double average;
    private int count;
    private int maxValue;
    private int minValue;
    private List<EventStatisticDTO> eventStatistics;
}
