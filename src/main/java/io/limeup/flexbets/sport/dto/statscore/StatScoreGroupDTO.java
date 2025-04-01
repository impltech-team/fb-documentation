package io.limeup.flexbets.sport.dto.statscore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatScoreGroupDTO {
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private List<StatScoreEventDTO> events;
    private StatScoreEventDTO event;
}
