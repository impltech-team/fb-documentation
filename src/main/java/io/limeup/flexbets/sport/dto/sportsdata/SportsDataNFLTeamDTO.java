package io.limeup.flexbets.sport.dto.sportsdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SportsDataNFLTeamDTO {

    @JsonProperty("TeamID")
    private Long teamId;

    @JsonProperty("Key")
    private String key;

    @JsonProperty("PlayerID")
    private Long playerId;

    @JsonProperty("City")
    private String city;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Conference")
    private String conference;

    @JsonProperty("Division")
    private String division;

    @JsonProperty("FullName")
    private String fullName;

    @JsonProperty("StadiumID")
    private Integer stadiumId;

    @JsonProperty("ByeWeek")
    private Integer byeWeek;

    @JsonProperty("AverageDraftPosition")
    private Double averageDraftPosition;

    @JsonProperty("AverageDraftPositionPPR")
    private Double averageDraftPositionPpr;

    @JsonProperty("HeadCoach")
    private String headCoach;

    @JsonProperty("OffensiveCoordinator")
    private String offensiveCoordinator;

    @JsonProperty("DefensiveCoordinator")
    private String defensiveCoordinator;

    @JsonProperty("SpecialTeamsCoach")
    private String specialTeamsCoach;

    @JsonProperty("OffensiveScheme")
    private String offensiveScheme;

    @JsonProperty("DefensiveScheme")
    private String defensiveScheme;

    @JsonProperty("UpcomingSalary")
    private Double upcomingSalary;

    @JsonProperty("UpcomingOpponent")
    private String upcomingOpponent;

    @JsonProperty("UpcomingOpponentRank")
    private Integer upcomingOpponentRank;

    @JsonProperty("UpcomingOpponentPositionRank")
    private Integer upcomingOpponentPositionRank;

    @JsonProperty("UpcomingFanDuelSalary")
    private Double upcomingFanDuelSalary;

    @JsonProperty("UpcomingDraftKingsSalary")
    private Double upcomingDraftKingsSalary;

    @JsonProperty("UpcomingYahooSalary")
    private Double upcomingYahooSalary;

    @JsonProperty("PrimaryColor")
    private String primaryColor;

    @JsonProperty("SecondaryColor")
    private String secondaryColor;

    @JsonProperty("TertiaryColor")
    private String tertiaryColor;

    @JsonProperty("QuaternaryColor")
    private String quaternaryColor;

    @JsonProperty("WikipediaLogoUrl")
    private String wikipediaLogoUrl;

    @JsonProperty("WikipediaWordMarkUrl")
    private String wikipediaWordMarkUrl;

    @JsonProperty("GlobalTeamID")
    private Long globalTeamId;

    @JsonProperty("DraftKingsName")
    private String draftKingsName;

    @JsonProperty("DraftKingsPlayerID")
    private Long draftKingsPlayerId;

    @JsonProperty("FanDuelName")
    private String fanDuelName;

    @JsonProperty("FanDuelPlayerID")
    private Long fanDuelPlayerId;

    @JsonProperty("FantasyDraftName")
    private String fantasyDraftName;

    @JsonProperty("FantasyDraftPlayerID")
    private Long fantasyDraftPlayerId;

    @JsonProperty("YahooName")
    private String yahooName;

    @JsonProperty("YahooPlayerID")
    private Long yahooPlayerId;

    @JsonProperty("AverageDraftPosition2QB")
    private Double averageDraftPosition2QB;

    @JsonProperty("AverageDraftPositionDynasty")
    private Double averageDraftPositionDynasty;

    @JsonProperty("StadiumDetails")
    private SportsDataNFLStadiumDTO stadiumDetails;

}