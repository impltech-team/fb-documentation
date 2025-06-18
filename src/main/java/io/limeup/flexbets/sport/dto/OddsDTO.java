package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OddsDTO {
    private long id;
    private String marketName;
    private int marketId;
    private String line;
    private String betType;
    private String price;
    private String status;
    private LocalDateTime lastUpdatedDate;
}
