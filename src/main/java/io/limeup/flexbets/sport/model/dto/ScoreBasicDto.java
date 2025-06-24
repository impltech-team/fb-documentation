package io.limeup.flexbets.sport.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ScoreBasicDto(
        @JsonProperty("GlobalGameID")      Long  globalGameId,
        @JsonProperty("GameID")            Long  gameId,
        @JsonProperty("Season")            Integer season,
        @JsonProperty("SeasonType")        Integer seasonType,
        @JsonProperty("Status")            String  status,
        @JsonProperty("Day")               LocalDate day,
        @JsonProperty("DateTime")          LocalDateTime dateTime,
        @JsonProperty("DateTimeUTC")       LocalDateTime dateTimeUtc,
        @JsonProperty("AwayTeam")          String awayTeam,
        @JsonProperty("HomeTeam")          String homeTeam,
        @JsonProperty("AwayTeamID")        Integer awayTeamId,
        @JsonProperty("HomeTeamID")        Integer homeTeamId,
        @JsonProperty("GlobalAwayTeamID")  Long globalAwayTeamId,
        @JsonProperty("GlobalHomeTeamID")  Long globalHomeTeamId,
        @JsonProperty("StadiumID")         Integer stadiumId,
        @JsonProperty("IsClosed")          Boolean isClosed,
        @JsonProperty("Updated")           LocalDateTime updated,
        @JsonProperty("AwayTeamRuns")      Integer awayTeamRuns,
        @JsonProperty("HomeTeamRuns")      Integer homeTeamRuns,
        @JsonProperty("AwayTeamHits")      Integer awayTeamHits,
        @JsonProperty("HomeTeamHits")      Integer homeTeamHits,
        @JsonProperty("AwayTeamErrors")    Integer awayTeamErrors,
        @JsonProperty("HomeTeamErrors")    Integer homeTeamErrors,
        @JsonProperty("GameEndDateTime")   LocalDateTime gameEndDateTime,
        @JsonProperty("NeutralVenue")      Boolean neutralVenue
) {}
