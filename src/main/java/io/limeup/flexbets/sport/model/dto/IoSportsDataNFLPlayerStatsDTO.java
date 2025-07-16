package io.limeup.flexbets.sport.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class IoSportsDataNFLPlayerStatsDTO {

    @JsonProperty("PlayerID") private String playerId;
    @JsonProperty("SeasonType") private Integer seasonType;
    @JsonProperty("Season") private String season;
    @JsonProperty("Team") private String team;
    @JsonProperty("Number") private Integer number;
    @JsonProperty("Name") private String name;
    @JsonProperty("Position") private String position;
    @JsonProperty("PositionCategory") private String positionCategory;
    @JsonProperty("Activated") private Integer activated;
    @JsonProperty("Played") private Integer played;
    @JsonProperty("Started") private Integer started;

    // Passing stats
    @JsonProperty("PassingAttempts") private Double passingAttempts;
    @JsonProperty("PassingCompletions") private Double passingCompletions;
    @JsonProperty("PassingYards") private Double passingYards;
    @JsonProperty("PassingCompletionPercentage") private Integer passingCompletionPercentage;
    @JsonProperty("PassingYardsPerAttempt") private Integer passingYardsPerAttempt;
    @JsonProperty("PassingYardsPerCompletion") private Integer passingYardsPerCompletion;
    @JsonProperty("PassingTouchdowns") private Double passingTouchdowns;
    @JsonProperty("PassingInterceptions") private Double passingInterceptions;
    @JsonProperty("PassingRating") private Integer passingRating;
    @JsonProperty("PassingLong") private Double passingLong;
    @JsonProperty("PassingSacks") private Double passingSacks;
    @JsonProperty("PassingSackYards") private Double passingSackYards;

    // Rushing stats
    @JsonProperty("RushingAttempts") private Double rushingAttempts;
    @JsonProperty("RushingYards") private Double rushingYards;
    @JsonProperty("RushingYardsPerAttempt") private Integer rushingYardsPerAttempt;
    @JsonProperty("RushingTouchdowns") private Double rushingTouchdowns;
    @JsonProperty("RushingLong") private Double rushingLong;

    // Receiving stats
    @JsonProperty("ReceivingTargets") private Double receivingTargets;
    @JsonProperty("Receptions") private Double receptions;
    @JsonProperty("ReceivingYards") private Double receivingYards;
    @JsonProperty("ReceivingYardsPerReception") private Integer receivingYardsPerReception;
    @JsonProperty("ReceivingTouchdowns") private Double receivingTouchdowns;
    @JsonProperty("ReceivingLong") private Double receivingLong;

    // Fumbles
    @JsonProperty("Fumbles") private Double fumbles;
    @JsonProperty("FumblesLost") private Double fumblesLost;

    // Returns
    @JsonProperty("PuntReturns") private Double puntReturns;
    @JsonProperty("PuntReturnYards") private Double puntReturnYards;
    @JsonProperty("PuntReturnYardsPerAttempt") private Integer puntReturnYardsPerAttempt;
    @JsonProperty("PuntReturnTouchdowns") private Double puntReturnTouchdowns;
    @JsonProperty("PuntReturnLong") private Double puntReturnLong;
    @JsonProperty("KickReturns") private Double kickReturns;
    @JsonProperty("KickReturnYards") private Double kickReturnYards;
    @JsonProperty("KickReturnYardsPerAttempt") private Integer kickReturnYardsPerAttempt;
    @JsonProperty("KickReturnTouchdowns") private Double kickReturnTouchdowns;
    @JsonProperty("KickReturnLong") private Double kickReturnLong;

    // Defensive stats
    @JsonProperty("SoloTackles") private Double soloTackles;
    @JsonProperty("AssistedTackles") private Double assistedTackles;
    @JsonProperty("TacklesForLoss") private Double tacklesForLoss;
    @JsonProperty("Sacks") private Double sacks;
    @JsonProperty("SackYards") private Double sackYards;
    @JsonProperty("QuarterbackHits") private Double quarterbackHits;
    @JsonProperty("PassesDefended") private Double passesDefended;
    @JsonProperty("FumblesForced") private Double fumblesForced;
    @JsonProperty("FumblesRecovered") private Double fumblesRecovered;
    @JsonProperty("FumbleReturnYards") private Double fumbleReturnYards;
    @JsonProperty("FumbleReturnTouchdowns") private Double fumbleReturnTouchdowns;
    @JsonProperty("Interceptions") private Double interceptions;
    @JsonProperty("InterceptionReturnYards") private Double interceptionReturnYards;
    @JsonProperty("InterceptionReturnTouchdowns") private Double interceptionReturnTouchdowns;
    @JsonProperty("BlockedKicks") private Double blockedKicks;

    // Special teams tackles
    @JsonProperty("SpecialTeamsSoloTackles") private Double specialTeamsSoloTackles;
    @JsonProperty("SpecialTeamsAssistedTackles") private Double specialTeamsAssistedTackles;
    @JsonProperty("MiscSoloTackles") private Double miscSoloTackles;
    @JsonProperty("MiscAssistedTackles") private Double miscAssistedTackles;

    // Kicking stats
    @JsonProperty("Punts") private Double punts;
    @JsonProperty("PuntYards") private Double puntYards;
    @JsonProperty("PuntAverage") private Integer puntAverage;
    @JsonProperty("FieldGoalsAttempted") private Double fieldGoalsAttempted;
    @JsonProperty("FieldGoalsMade") private Double fieldGoalsMade;
    @JsonProperty("FieldGoalsLongestMade") private Double fieldGoalsLongestMade;
    @JsonProperty("ExtraPointsMade") private Double extraPointsMade;

    // Two-point conversions
    @JsonProperty("TwoPointConversionPasses") private Double twoPointConversionPasses;
    @JsonProperty("TwoPointConversionRuns") private Double twoPointConversionRuns;
    @JsonProperty("TwoPointConversionReceptions") private Double twoPointConversionReceptions;

    // Fantasy stats
    @JsonProperty("FantasyPoints") private Double fantasyPoints;
    @JsonProperty("FantasyPointsPPR") private Double fantasyPointsPPR;
    @JsonProperty("ReceptionPercentage") private Integer receptionPercentage;
    @JsonProperty("ReceivingYardsPerTarget") private Integer receivingYardsPerTarget;

    // Combined stats
    @JsonProperty("Tackles") private Integer tackles;
    @JsonProperty("OffensiveTouchdowns") private Integer offensiveTouchdowns;
    @JsonProperty("DefensiveTouchdowns") private Integer defensiveTouchdowns;
    @JsonProperty("SpecialTeamsTouchdowns") private Integer specialTeamsTouchdowns;
    @JsonProperty("Touchdowns") private Integer touchdowns;
    @JsonProperty("FantasyPosition") private String fantasyPosition;
    @JsonProperty("FieldGoalPercentage") private Integer fieldGoalPercentage;

    // Additional fields
    @JsonProperty("PlayerSeasonID") private Long playerSeasonId;
    @JsonProperty("FumblesOwnRecoveries") private Double fumblesOwnRecoveries;
    @JsonProperty("FumblesOutOfBounds") private Double fumblesOutOfBounds;
    @JsonProperty("KickReturnFairCatches") private Double kickReturnFairCatches;
    @JsonProperty("PuntReturnFairCatches") private Double puntReturnFairCatches;
    @JsonProperty("PuntTouchbacks") private Double puntTouchbacks;
    @JsonProperty("PuntInside20") private Double puntInside20;
    @JsonProperty("PuntNetAverage") private Integer puntNetAverage;
    @JsonProperty("ExtraPointsAttempted") private Double extraPointsAttempted;
    @JsonProperty("BlockedKickReturnTouchdowns") private Double blockedKickReturnTouchdowns;
    @JsonProperty("FieldGoalReturnTouchdowns") private Double fieldGoalReturnTouchdowns;
    @JsonProperty("Safeties") private Double safeties;
    @JsonProperty("FieldGoalsHadBlocked") private Double fieldGoalsHadBlocked;
    @JsonProperty("PuntsHadBlocked") private Double puntsHadBlocked;
    @JsonProperty("ExtraPointsHadBlocked") private Double extraPointsHadBlocked;
    @JsonProperty("PuntLong") private Double puntLong;
    @JsonProperty("BlockedKickReturnYards") private Double blockedKickReturnYards;
    @JsonProperty("FieldGoalReturnYards") private Double fieldGoalReturnYards;
    @JsonProperty("PuntNetYards") private Double puntNetYards;
    @JsonProperty("SpecialTeamsFumblesForced") private Double specialTeamsFumblesForced;
    @JsonProperty("SpecialTeamsFumblesRecovered") private Double specialTeamsFumblesRecovered;
    @JsonProperty("MiscFumblesForced") private Double miscFumblesForced;
    @JsonProperty("MiscFumblesRecovered") private Double miscFumblesRecovered;
    @JsonProperty("ShortName") private String shortName;
    @JsonProperty("SafetiesAllowed") private Double safetiesAllowed;
    @JsonProperty("Temperature") private Integer temperature;
    @JsonProperty("Humidity") private Integer humidity;
    @JsonProperty("WindSpeed") private Integer windSpeed;
    @JsonProperty("OffensiveSnapsPlayed") private Integer offensiveSnapsPlayed;
    @JsonProperty("DefensiveSnapsPlayed") private Integer defensiveSnapsPlayed;
    @JsonProperty("SpecialTeamsSnapsPlayed") private Integer specialTeamsSnapsPlayed;
    @JsonProperty("OffensiveTeamSnaps") private Integer offensiveTeamSnaps;
    @JsonProperty("DefensiveTeamSnaps") private Integer defensiveTeamSnaps;
    @JsonProperty("SpecialTeamsTeamSnaps") private Integer specialTeamsTeamSnaps;
    @JsonProperty("AuctionValue") private Object auctionValue;
    @JsonProperty("AuctionValuePPR") private Object auctionValuePPR;
    @JsonProperty("TwoPointConversionReturns") private Double twoPointConversionReturns;
    @JsonProperty("FantasyPointsFanDuel") private Double fantasyPointsFanDuel;
    @JsonProperty("FieldGoalsMade0to19") private Double fieldGoalsMade0to19;
    @JsonProperty("FieldGoalsMade20to29") private Double fieldGoalsMade20to29;
    @JsonProperty("FieldGoalsMade30to39") private Double fieldGoalsMade30to39;
    @JsonProperty("FieldGoalsMade40to49") private Double fieldGoalsMade40to49;
    @JsonProperty("FieldGoalsMade50Plus") private Double fieldGoalsMade50Plus;
    @JsonProperty("FantasyPointsDraftKings") private Double fantasyPointsDraftKings;
    @JsonProperty("FantasyPointsYahoo") private Double fantasyPointsYahoo;
    @JsonProperty("AverageDraftPosition") private Object averageDraftPosition;
    @JsonProperty("AverageDraftPositionPPR") private Object averageDraftPositionPPR;
    @JsonProperty("TeamID") private Integer teamId;
    @JsonProperty("GlobalTeamID") private Integer globalTeamId;
    @JsonProperty("FantasyPointsFantasyDraft") private Double fantasyPointsFantasyDraft;
    @JsonProperty("AverageDraftPositionRookie") private Object averageDraftPositionRookie;
    @JsonProperty("AverageDraftPositionDynasty") private Object averageDraftPositionDynasty;
    @JsonProperty("AverageDraftPosition2QB") private Object averageDraftPosition2QB;
    @JsonProperty("OffensiveFumbleRecoveryTouchdowns") private Object offensiveFumbleRecoveryTouchdowns;
    @JsonProperty("GameID") private String gameId;

    // Scoring details array
    @JsonProperty("ScoringDetails") private List<IoSportsDataNFLScoringDetailDTO> scoringDetails;
}
