package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentDTO {
    private String time;
    private int participantId;
    private String participantName;
    private int subParticipantId;
    private String subParticipantName;
    private String type;
}
