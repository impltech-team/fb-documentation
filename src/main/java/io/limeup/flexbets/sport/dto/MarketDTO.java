package io.limeup.flexbets.sport.dto;

import io.limeup.flexbets.sport.model.MarketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketDTO {

    private Integer id;

    private Integer competitionId;

    private MarketType marketType;

    private String marketName;

    private List<String> linkedStats;

    private boolean enabled;
}
