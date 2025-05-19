package io.limeup.flexbets.sport.service.live;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveEventDTO {
    private Long id;
    private String action;
    private String startDate;
    private String ftOnly;
    private String coverageType;
    private Integer statusId;
    private String statusName;
    private Integer sportId;
    private String sportName;
    private Integer day;
    private String neutralVenue;
    private String itemStatus;

    private Integer areaId;
    private String areaName;
    private Integer competitionId;
    private String competitionName;
    private Integer seasonId;
    private Integer stageId;
    private String stageName;
    private Integer groupId;
    private Integer tourId;
    private String tourName;
    private String gender;
    private String betStatus;
    private String betCards;
    private String betCorners;
    private String relationStatus;
    private String statusType;
    private String name;
    private Integer roundId;
    private String roundName;
    private String playedTime;
    private List<LiveParticipantDTO> participants;

    // private String clockTime;       // add later
    // private String clockStatus;     // add later

}