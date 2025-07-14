package io.limeup.flexbets.sport.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ScoreNFLDto(
        @JsonProperty("GlobalGameID") Long globalGameId,
        @JsonProperty("GameKey") String gameKey,
        @JsonProperty("Season") Integer season,
        @JsonProperty("SeasonType") Integer seasonType,
        @JsonProperty("Week") Integer week,
        @JsonProperty("Status") String status,
        @JsonProperty("Day") LocalDate day,
        @JsonProperty("DateTime") LocalDateTime dateTime,
        @JsonProperty("DateTimeUTC") LocalDateTime dateTimeUtc,
        @JsonProperty("AwayTeam") String awayTeam,
        @JsonProperty("HomeTeam") String homeTeam,
        @JsonProperty("AwayScore") Integer awayScore,
        @JsonProperty("HomeScore") Integer homeScore,
        @JsonProperty("AwayTeamID") Integer awayTeamId,
        @JsonProperty("HomeTeamID") Integer homeTeamId,
        @JsonProperty("GlobalAwayTeamID") Long globalAwayTeamId,
        @JsonProperty("GlobalHomeTeamID") Long globalHomeTeamId,
        @JsonProperty("StadiumID") Integer stadiumId,
        @JsonProperty("IsClosed") Boolean isClosed,
        @JsonProperty("LastUpdated") LocalDateTime lastUpdated,
        @JsonProperty("GameEndDateTime") LocalDateTime gameEndDateTime,
        @JsonProperty("NeutralVenue") Boolean neutralVenue,
        @JsonProperty("QuarterDescription") String quarterDescription,
        @JsonProperty("Attendance") Integer attendance,
        @JsonProperty("Channel") String channel,
        @JsonProperty("PointSpread") Double pointSpread,
        @JsonProperty("OverUnder") Double overUnder,
        @JsonProperty("RefereeID") Integer refereeId
) {}
