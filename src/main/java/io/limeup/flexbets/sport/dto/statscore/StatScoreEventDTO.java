package io.limeup.flexbets.sport.dto.statscore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatScoreEventDTO {
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
    private List<StatScoreEventParticipantDTO> participants;
}
