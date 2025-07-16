package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "io_event_nfl", schema = "sport")
public class IoEventNFL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "global_game_id")
    private Long globalGameId;

    @Column(name = "game_key", unique = true)
    private String gameKey;

    @Column(name = "season")
    private Integer season;

    @Column(name = "season_type")
    private Integer seasonType;

    @Column(name = "week")
    private Integer week;

    @Column(name = "status")
    private String status;

    @Column(name = "day")
    private LocalDate day;

    @Column(name = "datetime")
    private LocalDateTime datetime;

    @Column(name = "datetime_utc")
    private LocalDateTime datetimeUtc;

    @Column(name = "away_team")
    private String awayTeam;

    @Column(name = "home_team")
    private String homeTeam;

    @Column(name = "away_score")
    private Integer awayScore;

    @Column(name = "home_score")
    private Integer homeScore;

    @Column(name = "away_team_id")
    private Integer awayTeamId;

    @Column(name = "home_team_id")
    private Integer homeTeamId;

    @Column(name = "global_away_team_id")
    private Long globalAwayTeamId;

    @Column(name = "global_home_team_id")
    private Long globalHomeTeamId;

    @Column(name = "stadium_id")
    private Integer stadiumId;

    @Column(name = "is_closed")
    private Boolean isClosed;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column(name = "game_end_datetime")
    private LocalDateTime gameEndDatetime;

    @Column(name = "neutral_venue")
    private Boolean neutralVenue;

    @Column(name = "quarter_description")
    private String quarterDescription;

    @Column(name = "attendance")
    private Integer attendance;

    @Column(name = "channel")
    private String channel;

    @Column(name = "point_spread")
    private Double pointSpread;

    @Column(name = "over_under")
    private Double overUnder;

    @Column(name = "referee_id")
    private Integer refereeId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IoEventNFL that = (IoEventNFL) o;
        return gameKey.equals(that.gameKey);
    }

    @Override
    public int hashCode() {
        return gameKey.hashCode();
    }

    @Override
    public String toString() {
        return "IoEventNFL{" +
                "id=" + id +
                ", gameKey='" + gameKey + '\'' +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", datetime=" + datetime +
                ", status='" + status + '\'' +
                '}';
    }
}