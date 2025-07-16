package io.limeup.flexbets.sport.dto.statscore.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandingByIdQueryParams {
    private String lang;
    private Integer subParticipantId;
    private String participantId;
}

