package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OddsResponseDTO {
    private Integer subParticipantId;

    private String subParticipantName;

    private Integer participantId;

    private String participantName;

    private Integer eventId;

    private List<OddsWithStatsDTO> odds;
}
