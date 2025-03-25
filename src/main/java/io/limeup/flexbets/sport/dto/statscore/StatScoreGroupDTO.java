package io.limeup.flexbets.sport.dto.statscore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatScoreGroupDTO {
    private int id;
    private String name;
    private List<StatScoreEventDTO> events;
    private StatScoreEventDTO event;
}
