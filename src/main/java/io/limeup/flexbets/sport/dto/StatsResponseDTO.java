package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponseDTO {
    private Integer subParticipantId;

    private String subParticipantName;

    private Integer participantId;

    private String participantName;

    private Integer eventId;

    private Integer statId;

    private LocalDateTime lastUpdatedDate;

    private BigDecimal statValue;
}
