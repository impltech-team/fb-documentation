package io.limeup.flexbets.sport.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OddsWithStatsDTO extends OddsDTO {
    private int statValue;

    public OddsWithStatsDTO(int id, String marketName, int marketId, String line,
                            String betType, String price, String status,
                            LocalDateTime lastUpdatedDate, int statValue) {
        super(id, marketName, marketId, line, betType, price, status, lastUpdatedDate);
        this.statValue = statValue;
    }
}

