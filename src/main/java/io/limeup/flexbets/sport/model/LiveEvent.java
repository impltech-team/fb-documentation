package io.limeup.flexbets.sport.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "live_event")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LiveEvent {

    @Id
    private Long id;
    @Column(nullable = false)
    private UUID uuid;
    private String type;
    private Integer source;
    private Long ut;
    @Column(name = "event_data_id")
    private Long eventDataId;
    private Long lsId;
    private String action;
    private LocalDateTime startDate;
    private Boolean ftOnly;
    private String coverageType;
    private Integer statusId;
    private String statusName;
    private Integer sportId;
    private String sportName;
    private Integer day;
    private Boolean neutralVenue;
    private String itemStatus;
    private String clockTime;
    private String clockStatus;
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
    private Boolean scoutsfeed;
    private String latency;
    private String eventStatsLvl;
    private String eventStatsLive;
    private String eventStatsAfter;
    private Boolean verifiedResult;
    private Boolean isStatsVerified;
    private Boolean isCoverageLimited;
    private String playedTime;
    private Integer refereeId;
    private String participantsIdStatsModified;

}