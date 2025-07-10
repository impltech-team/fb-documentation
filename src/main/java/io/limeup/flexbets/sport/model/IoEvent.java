package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "io_event", schema = "sport")
public class IoEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "global_game_id")
    private Long globalGameId;

    @Column(name = "game_id")
    private Long gameId;

    private Integer season;
    private Integer seasonType;
    private String  status;

    private LocalDate      day;
    private LocalDateTime  datetime;
    @Column(name = "datetime_utc")
    private LocalDateTime  datetimeUtc;

    private String  awayTeam;
    private String  homeTeam;
    private Integer awayTeamId;
    private Integer homeTeamId;
    private Long    globalAwayTeamId;
    private Long    globalHomeTeamId;

    private Integer        stadiumId;
    private Boolean        isClosed;
    private LocalDateTime  updated;

    private Integer awayTeamRuns;
    private Integer homeTeamRuns;
    private Integer awayTeamHits;
    private Integer homeTeamHits;
    private Integer awayTeamErrors;
    private Integer homeTeamErrors;

    private LocalDateTime gameEndDatetime;
    private Boolean       neutralVenue;


    //this data and below get null from api
    private String attendance;
    private String inning;
    private String inningHalf;

    @Column(name = "rescheduled_game_id")
    private String rescheduledGameId;

    @Column(name = "rescheduled_from_game_id")
    private String rescheduledFromGameId;

    @Column(name = "suspension_resume_day")
    private String suspensionResumeDay;

    @Column(name = "suspension_resume_datetime")
    private String suspensionResumeDateTime;

    @Column(name = "series_info", columnDefinition = "TEXT")
    private String seriesInfo;

}
