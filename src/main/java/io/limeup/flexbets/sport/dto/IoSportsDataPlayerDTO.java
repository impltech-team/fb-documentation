package io.limeup.flexbets.sport.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class IoSportsDataPlayerDTO {

    @JsonProperty("PlayerID")
    private Long playerID;

    @JsonProperty("SportsDataID")
    private String sportsDataID;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("TeamID")
    private Integer teamID;

    @JsonProperty("Team")
    private String team;

    @JsonProperty("Jersey")
    private Integer jersey;

    @JsonProperty("PositionCategory")
    private String positionCategory;

    @JsonProperty("Position")
    private String position;

    @JsonProperty("MLBAMID")
    private Long mlbamid;

    @JsonProperty("FirstName")
    private String firstName;

    @JsonProperty("LastName")
    private String lastName;

    @JsonProperty("BatHand")
    private String batHand;

    @JsonProperty("ThrowHand")
    private String throwHand;

    @JsonProperty("Height")
    private Integer height;

    @JsonProperty("Weight")
    private Integer weight;

    @JsonProperty("BirthDate")
    private LocalDate birthDate;

    @JsonProperty("BirthCity")
    private String birthCity;

    @JsonProperty("BirthState")
    private String birthState;

    @JsonProperty("BirthCountry")
    private String birthCountry;

    @JsonProperty("HighSchool")
    private String highSchool;

    @JsonProperty("College")
    private String college;

    @JsonProperty("ProDebut")
    private LocalDate proDebut;

    @JsonProperty("Salary")
    private String salary;

    @JsonProperty("PhotoUrl")
    private String photoUrl;

    @JsonProperty("SportRadarPlayerID")
    private String sportRadarPlayerID;

    @JsonProperty("RotoworldPlayerID")
    private String rotoworldPlayerID;

    @JsonProperty("RotoWirePlayerID")
    private String rotoWirePlayerID;

    @JsonProperty("FantasyAlarmPlayerID")
    private String fantasyAlarmPlayerID;

    @JsonProperty("StatsPlayerID")
    private String statsPlayerID;

    @JsonProperty("SportsDirectPlayerID")
    private String sportsDirectPlayerID;

    @JsonProperty("XmlTeamPlayerID")
    private String xmlTeamPlayerID;

    @JsonProperty("InjuryStatus")
    private String injuryStatus;

    @JsonProperty("InjuryBodyPart")
    private String injuryBodyPart;

    @JsonProperty("InjuryStartDate")
    private LocalDate injuryStartDate;

    @JsonProperty("InjuryNotes")
    private String injuryNotes;

    @JsonProperty("FanDuelPlayerID")
    private String fanDuelPlayerID;

    @JsonProperty("DraftKingsPlayerID")
    private String draftKingsPlayerID;

    @JsonProperty("YahooPlayerID")
    private String yahooPlayerID;

    @JsonProperty("UpcomingGameID")
    private String upcomingGameID;

    @JsonProperty("FanDuelName")
    private String fanDuelName;

    @JsonProperty("DraftKingsName")
    private String draftKingsName;

    @JsonProperty("YahooName")
    private String yahooName;

    @JsonProperty("GlobalTeamID")
    private Long globalTeamID;

    @JsonProperty("FantasyDraftName")
    private String fantasyDraftName;

    @JsonProperty("FantasyDraftPlayerID")
    private String fantasyDraftPlayerID;

    @JsonProperty("Experience")
    private String experience;

    @JsonProperty("UsaTodayPlayerID")
    private String usaTodayPlayerID;

    @JsonProperty("UsaTodayHeadshotUrl")
    private String usaTodayHeadshotUrl;

    @JsonProperty("UsaTodayHeadshotNoBackgroundUrl")
    private String usaTodayHeadshotNoBackgroundUrl;

    @JsonProperty("UsaTodayHeadshotUpdated")
    private LocalDateTime usaTodayHeadshotUpdated;

    @JsonProperty("UsaTodayHeadshotNoBackgroundUpdated")
    private LocalDateTime usaTodayHeadshotNoBackgroundUpdated;
}

