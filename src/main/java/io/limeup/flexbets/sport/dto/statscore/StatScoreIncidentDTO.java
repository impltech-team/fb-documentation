package io.limeup.flexbets.sport.dto.statscore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatScoreIncidentDTO {
    @EqualsAndHashCode.Include
    private int id;
    private String incidentName;
    private int participantId;
    private String participantName;
    private int subParticipantId;
    private String subParticipantName;
    private int eventStatusId;
    private String eventStatusName;
    private String eventTime;
    private String info;
    private String additionalInfo;
}
