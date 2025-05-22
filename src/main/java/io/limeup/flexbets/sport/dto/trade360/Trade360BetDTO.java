package io.limeup.flexbets.sport.dto.trade360;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trade360BetDTO {
    private Long id;
    private String name;
    private String participantName;
    private String line;
    private String baseLine;
    private String price;
    private Integer status;
    private Integer settlement;
    private Integer suspensionReason;
    private Instant lastUpdated;
}
