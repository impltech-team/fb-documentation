package io.limeup.flexbets.sport.dto.sportsdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class IoPlayerGameStatsNFLDto {

    // Player Info
    @JsonProperty("PlayerID") private Long playerId;
    @JsonProperty("Name") private String name;
    @JsonProperty("ShortName") private String shortName;
    @JsonProperty("Number") private Integer number;
    @JsonProperty("Position") private String position;
    @JsonProperty("PositionCategory") private String positionCategory;
    @JsonProperty("FantasyPosition") private String fantasyPosition;

    // Game Info
    @JsonProperty("GameKey") private String gameKey;
    @JsonProperty("GlobalGameID") private Long globalGameId;
    @JsonProperty("SeasonType") private Integer seasonType;
    @JsonProperty("Season") private Integer season;
    @JsonProperty("Week") private Integer week;
    @JsonProperty("Day") private LocalDate day;
    @JsonProperty("DateTime") private LocalDateTime dateTime;
    @JsonProperty("GameDate") private LocalDateTime gameDate;
    @JsonProperty("HomeOrAway") private String homeOrAway;
    @JsonProperty("IsGameOver") private Boolean isGameOver;
    @JsonProperty("Updated") private LocalDateTime updated;

    // Team Info
    @JsonProperty("Team") private String team;
    @JsonProperty("TeamID") private Integer teamId;
    @JsonProperty("GlobalTeamID") private Long globalTeamId;
    @JsonProperty("Opponent") private String opponent;
    @JsonProperty("OpponentID") private Integer opponentId;
    @JsonProperty("GlobalOpponentID") private Long globalOpponentId;
    @JsonProperty("OpponentRank") private Integer opponentRank;
    @JsonProperty("OpponentPositionRank") private Integer opponentPositionRank;

    // Game Status
    @JsonProperty("Activated") private Integer activated;
    @JsonProperty("Played") private Integer played;
    @JsonProperty("Started") private Integer started;
    @JsonProperty("DeclaredInactive") private Boolean declaredInactive;

    // Passing Stats
    @JsonProperty("PassingAttempts") private Integer passingAttempts;
    @JsonProperty("PassingCompletions") private Integer passingCompletions;
    @JsonProperty("PassingYards") private Integer passingYards;
    @JsonProperty("PassingCompletionPercentage") private BigDecimal passingCompletionPercentage;
    @JsonProperty("PassingYardsPerAttempt") private BigDecimal passingYardsPerAttempt;
    @JsonProperty("PassingYardsPerCompletion") private BigDecimal passingYardsPerCompletion;
    @JsonProperty("PassingTouchdowns") private Integer passingTouchdowns;
    @JsonProperty("PassingInterceptions") private Integer passingInterceptions;
    @JsonProperty("PassingRating") private BigDecimal passingRating;
    @JsonProperty("PassingLong") private Integer passingLong;
    @JsonProperty("PassingSacks") private Integer passingSacks;
    @JsonProperty("PassingSackYards") private Integer passingSackYards;

    // Rushing Stats
    @JsonProperty("RushingAttempts") private Integer rushingAttempts;
    @JsonProperty("RushingYards") private Integer rushingYards;
    @JsonProperty("RushingYardsPerAttempt") private BigDecimal rushingYardsPerAttempt;
    @JsonProperty("RushingTouchdowns") private Integer rushingTouchdowns;
    @JsonProperty("RushingLong") private Integer rushingLong;

    // Receiving Stats
    @JsonProperty("ReceivingTargets") private Integer receivingTargets;
    @JsonProperty("Receptions") private Integer receptions;
    @JsonProperty("ReceivingYards") private Integer receivingYards;
    @JsonProperty("ReceivingYardsPerReception") private BigDecimal receivingYardsPerReception;
    @JsonProperty("ReceivingTouchdowns") private Integer receivingTouchdowns;
    @JsonProperty("ReceivingLong") private Integer receivingLong;
    @JsonProperty("ReceptionPercentage") private BigDecimal receptionPercentage;
    @JsonProperty("ReceivingYardsPerTarget") private BigDecimal receivingYardsPerTarget;

    // Defensive Stats
    @JsonProperty("SoloTackles") private Integer soloTackles;
    @JsonProperty("AssistedTackles") private Integer assistedTackles;
    @JsonProperty("Tackles") private Integer tackles;
    @JsonProperty("TacklesForLoss") private Integer tacklesForLoss;
    @JsonProperty("Sacks") private Integer sacks;
    @JsonProperty("SackYards") private Integer sackYards;
    @JsonProperty("QuarterbackHits") private Integer quarterbackHits;
    @JsonProperty("PassesDefended") private Integer passesDefended;
    @JsonProperty("Interceptions") private Integer interceptions;
    @JsonProperty("InterceptionReturnYards") private Integer interceptionReturnYards;
    @JsonProperty("InterceptionReturnTouchdowns") private Integer interceptionReturnTouchdowns;

    // Fumbles
    @JsonProperty("Fumbles") private Integer fumbles;
    @JsonProperty("FumblesLost") private Integer fumblesLost;
    @JsonProperty("FumblesForced") private Integer fumblesForced;
    @JsonProperty("FumblesRecovered") private Integer fumblesRecovered;
    @JsonProperty("FumbleReturnYards") private Integer fumbleReturnYards;
    @JsonProperty("FumbleReturnTouchdowns") private Integer fumbleReturnTouchdowns;
    @JsonProperty("FumblesOwnRecoveries") private Integer fumblesOwnRecoveries;
    @JsonProperty("FumblesOutOfBounds") private Integer fumblesOutOfBounds;

    // Special Teams
    @JsonProperty("PuntReturns") private Integer puntReturns;
    @JsonProperty("PuntReturnYards") private Integer puntReturnYards;
    @JsonProperty("PuntReturnYardsPerAttempt") private BigDecimal puntReturnYardsPerAttempt;
    @JsonProperty("PuntReturnTouchdowns") private Integer puntReturnTouchdowns;
    @JsonProperty("PuntReturnLong") private Integer puntReturnLong;
    @JsonProperty("PuntReturnFairCatches") private Integer puntReturnFairCatches;

    @JsonProperty("KickReturns") private Integer kickReturns;
    @JsonProperty("KickReturnYards") private Integer kickReturnYards;
    @JsonProperty("KickReturnYardsPerAttempt") private BigDecimal kickReturnYardsPerAttempt;
    @JsonProperty("KickReturnTouchdowns") private Integer kickReturnTouchdowns;
    @JsonProperty("KickReturnLong") private Integer kickReturnLong;
    @JsonProperty("KickReturnFairCatches") private Integer kickReturnFairCatches;

    // Kicking
    @JsonProperty("FieldGoalsAttempted") private Integer fieldGoalsAttempted;
    @JsonProperty("FieldGoalsMade") private Integer fieldGoalsMade;
    @JsonProperty("FieldGoalsLongestMade") private Integer fieldGoalsLongestMade;
    @JsonProperty("FieldGoalPercentage") private BigDecimal fieldGoalPercentage;
    @JsonProperty("FieldGoalsHadBlocked") private Integer fieldGoalsHadBlocked;
    @JsonProperty("FieldGoalsMade0to19") private Integer fieldGoalsMade0to19;
    @JsonProperty("FieldGoalsMade20to29") private Integer fieldGoalsMade20to29;
    @JsonProperty("FieldGoalsMade30to39") private Integer fieldGoalsMade30to39;
    @JsonProperty("FieldGoalsMade40to49") private Integer fieldGoalsMade40to49;
    @JsonProperty("FieldGoalsMade50Plus") private Integer fieldGoalsMade50Plus;

    @JsonProperty("ExtraPointsAttempted") private Integer extraPointsAttempted;
    @JsonProperty("ExtraPointsMade") private Integer extraPointsMade;
    @JsonProperty("ExtraPointsHadBlocked") private Integer extraPointsHadBlocked;

    // Punting
    @JsonProperty("Punts") private Integer punts;
    @JsonProperty("PuntYards") private Integer puntYards;
    @JsonProperty("PuntAverage") private BigDecimal puntAverage;
    @JsonProperty("PuntLong") private Integer puntLong;
    @JsonProperty("PuntInside20") private Integer puntInside20;
    @JsonProperty("PuntTouchbacks") private Integer puntTouchbacks;
    @JsonProperty("PuntsHadBlocked") private Integer puntsHadBlocked;
    @JsonProperty("PuntNetAverage") private BigDecimal puntNetAverage;
    @JsonProperty("PuntNetYards") private Integer puntNetYards;

    // Miscellaneous
    @JsonProperty("BlockedKicks") private Integer blockedKicks;
    @JsonProperty("BlockedKickReturnYards") private Integer blockedKickReturnYards;
    @JsonProperty("BlockedKickReturnTouchdowns") private Integer blockedKickReturnTouchdowns;
    @JsonProperty("FieldGoalReturnYards") private Integer fieldGoalReturnYards;
    @JsonProperty("FieldGoalReturnTouchdowns") private Integer fieldGoalReturnTouchdowns;
    @JsonProperty("Safeties") private Integer safeties;
    @JsonProperty("SafetiesAllowed") private Integer safetiesAllowed;

    // Two-Point Conversions
    @JsonProperty("TwoPointConversionPasses") private Integer twoPointConversionPasses;
    @JsonProperty("TwoPointConversionRuns") private Integer twoPointConversionRuns;
    @JsonProperty("TwoPointConversionReceptions") private Integer twoPointConversionReceptions;
    @JsonProperty("TwoPointConversionReturns") private Integer twoPointConversionReturns;

    // Offensive Stats
    @JsonProperty("OffensiveSnapsPlayed") private Integer offensiveSnapsPlayed;
    @JsonProperty("OffensiveTeamSnaps") private Integer offensiveTeamSnaps;
    @JsonProperty("OffensiveTouchdowns") private Integer offensiveTouchdowns;

    // Defensive Stats
    @JsonProperty("DefensiveSnapsPlayed") private Integer defensiveSnapsPlayed;
    @JsonProperty("DefensiveTeamSnaps") private Integer defensiveTeamSnaps;
    @JsonProperty("DefensiveTouchdowns") private Integer defensiveTouchdowns;

    // Special Teams Stats
    @JsonProperty("SpecialTeamsSnapsPlayed") private Integer specialTeamsSnapsPlayed;
    @JsonProperty("SpecialTeamsTeamSnaps") private Integer specialTeamsTeamSnaps;
    @JsonProperty("SpecialTeamsTouchdowns") private Integer specialTeamsTouchdowns;
    @JsonProperty("SpecialTeamsFumblesForced") private Integer specialTeamsFumblesForced;
    @JsonProperty("SpecialTeamsFumblesRecovered") private Integer specialTeamsFumblesRecovered;

    // Touchdowns
    @JsonProperty("Touchdowns") private Integer touchdowns;

    // Fantasy
    @JsonProperty("FantasyPoints") private BigDecimal fantasyPoints;
    @JsonProperty("FantasyPointsPPR") private BigDecimal fantasyPointsPPR;
    @JsonProperty("FantasyPointsFanDuel") private BigDecimal fantasyPointsFanDuel;
    @JsonProperty("FantasyPointsDraftKings") private BigDecimal fantasyPointsDraftKings;
    @JsonProperty("FantasyPointsYahoo") private BigDecimal fantasyPointsYahoo;
    @JsonProperty("FantasyPointsFantasyDraft") private BigDecimal fantasyPointsFantasyDraft;

    // Salaries
    @JsonProperty("FanDuelSalary") private Integer fanDuelSalary;
    @JsonProperty("DraftKingsSalary") private Integer draftKingsSalary;
    @JsonProperty("FantasyDataSalary") private Integer fantasyDataSalary;
    @JsonProperty("YahooSalary") private Integer yahooSalary;
    @JsonProperty("FantasyDraftSalary") private Integer fantasyDraftSalary;

    // Positions
    @JsonProperty("FanDuelPosition") private String fanDuelPosition;
    @JsonProperty("DraftKingsPosition") private String draftKingsPosition;
    @JsonProperty("YahooPosition") private String yahooPosition;
    @JsonProperty("FantasyDraftPosition") private String fantasyDraftPosition;

    // Injuries
    @JsonProperty("InjuryStatus") private String injuryStatus;
    @JsonProperty("InjuryBodyPart") private String injuryBodyPart;
    @JsonProperty("InjuryStartDate") private LocalDate injuryStartDate;
    @JsonProperty("InjuryNotes") private String injuryNotes;
    @JsonProperty("InjuryPractice") private String injuryPractice;
    @JsonProperty("InjuryPracticeDescription") private String injuryPracticeDescription;

    // Game Environment
    @JsonProperty("Stadium") private String stadium;
    @JsonProperty("PlayingSurface") private String playingSurface;
    @JsonProperty("Temperature") private Integer temperature;
    @JsonProperty("Humidity") private Integer humidity;
    @JsonProperty("WindSpeed") private Integer windSpeed;

    // Snap Counts
    @JsonProperty("SnapCountsConfirmed") private Boolean snapCountsConfirmed;

    // Scoring Details (if available)
    // @JsonProperty("ScoringDetails") private List<ScoringDetailDto> scoringDetails;
}