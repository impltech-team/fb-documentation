package io.limeup.flexbets.sport.dto.sportsdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SportsDataNFLPlayerDTO {

    @JsonProperty("PlayerID")
    private Long playerID;

    @JsonProperty("FirstName")
    private String firstName;

    @JsonProperty("LastName")
    private String lastName;

    @JsonProperty("Position")
    private String position;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Height")
    private String height; // NOTE: It's in format 5'10"

    @JsonProperty("Weight")
    private Integer weight;

    @JsonProperty("BirthDate")
    private LocalDate birthDate;

    @JsonProperty("College")
    private String college;

    @JsonProperty("Experience")
    private Integer experience;

    @JsonProperty("FantasyPosition")
    private String fantasyPosition;

    @JsonProperty("Active")
    private Boolean active;

    @JsonProperty("PositionCategory")
    private String positionCategory;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Age")
    private Integer age;

    @JsonProperty("ExperienceString")
    private String experienceString;

    @JsonProperty("BirthDateString")
    private String birthDateString;

    @JsonProperty("PhotoUrl")
    private String photoUrl;

    @JsonProperty("ByeWeek")
    private Integer byeWeek;

    @JsonProperty("UpcomingGameOpponent")
    private String upcomingGameOpponent;

    @JsonProperty("UpcomingGameWeek")
    private Integer upcomingGameWeek;

    @JsonProperty("ShortName")
    private String shortName;

    @JsonProperty("AverageDraftPosition")
    private Double averageDraftPosition;

    @JsonProperty("DepthPositionCategory")
    private String depthPositionCategory;

    @JsonProperty("DepthPosition")
    private String depthPosition;

    @JsonProperty("DepthOrder")
    private Integer depthOrder;

    @JsonProperty("DepthDisplayOrder")
    private Integer depthDisplayOrder;

    @JsonProperty("CurrentTeam")
    private String currentTeam;

    @JsonProperty("CollegeDraftTeam")
    private String collegeDraftTeam;

    @JsonProperty("CollegeDraftYear")
    private Integer collegeDraftYear;

    @JsonProperty("CollegeDraftRound")
    private Integer collegeDraftRound;

    @JsonProperty("CollegeDraftPick")
    private Integer collegeDraftPick;

    @JsonProperty("IsUndraftedFreeAgent")
    private Boolean isUndraftedFreeAgent;

    @JsonProperty("HeightFeet")
    private Integer heightFeet;

    @JsonProperty("HeightInches")
    private Integer heightInches;

    @JsonProperty("UpcomingOpponentRank")
    private Integer upcomingOpponentRank;

    @JsonProperty("UpcomingOpponentPositionRank")
    private Integer upcomingOpponentPositionRank;

    @JsonProperty("CurrentStatus")
    private String currentStatus;

    @JsonProperty("UpcomingSalary")
    private Integer upcomingSalary;

    @JsonProperty("FantasyAlarmPlayerID")
    private Integer fantasyAlarmPlayerID;

    @JsonProperty("SportRadarPlayerID")
    private String sportRadarPlayerID;

    @JsonProperty("RotoworldPlayerID")
    private Integer rotoworldPlayerID;

    @JsonProperty("RotoWirePlayerID")
    private Integer rotoWirePlayerID;

    @JsonProperty("StatsPlayerID")
    private Integer statsPlayerID;

    @JsonProperty("SportsDirectPlayerID")
    private Integer sportsDirectPlayerID;

    @JsonProperty("XmlTeamPlayerID")
    private Integer xmlTeamPlayerID;

    @JsonProperty("FanDuelPlayerID")
    private String fanDuelPlayerID;

    @JsonProperty("DraftKingsPlayerID")
    private String draftKingsPlayerID;

    @JsonProperty("YahooPlayerID")
    private String yahooPlayerID;

    @JsonProperty("InjuryStatus")
    private String injuryStatus;

    @JsonProperty("InjuryBodyPart")
    private String injuryBodyPart;

    @JsonProperty("InjuryStartDate")
    private String injuryStartDate;

    @JsonProperty("InjuryNotes")
    private String injuryNotes;

    @JsonProperty("FanDuelName")
    private String fanDuelName;

    @JsonProperty("DraftKingsName")
    private String draftKingsName;

    @JsonProperty("YahooName")
    private String yahooName;

    @JsonProperty("FantasyPositionDepthOrder")
    private Integer fantasyPositionDepthOrder;

    @JsonProperty("InjuryPractice")
    private String injuryPractice;

    @JsonProperty("InjuryPracticeDescription")
    private String injuryPracticeDescription;

    @JsonProperty("DeclaredInactive")
    private Boolean declaredInactive;

    @JsonProperty("UpcomingFanDuelSalary")
    private Integer upcomingFanDuelSalary;

    @JsonProperty("UpcomingDraftKingsSalary")
    private Integer upcomingDraftKingsSalary;

    @JsonProperty("UpcomingYahooSalary")
    private Integer upcomingYahooSalary;

    @JsonProperty("TeamID")
    private Integer teamID;

    @JsonProperty("GlobalTeamID")
    private Long globalTeamID;

    @JsonProperty("FantasyDraftPlayerID")
    private String fantasyDraftPlayerID;

    @JsonProperty("FantasyDraftName")
    private String fantasyDraftName;

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
