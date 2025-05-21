package io.limeup.flexbets.sport.dto.statscore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatScoreEventDTO {
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String relationStatus;
    private String startDate;
    private String ftOnly;
    private String coverageType;
    private String scoutsfeed;
    private int statusId;
    private String statusName;
    private String statusType;
    private int sportId;
    private String sportName;
    private String clockStatus;
    private Integer clockTime;
    private String betStatus;
    private String betCards;
    private String betCorners;
    private String itemStatus;
    private String verifiedResult;
    private String roundName;
    private int competitionId;
    private String competitionShortName;
    private int venueId;
    private List<StatScoreEventParticipantDTO> participants;
    private List<StatScoreIncidentDTO> eventIncidents;
    private Long lsId;
}
