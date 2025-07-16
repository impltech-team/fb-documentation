package io.limeup.flexbets.sport.dto.sportsdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IoPlayerGameStatsDto {

    @JsonProperty("StatID")                    private Long statId;
    @JsonProperty("TeamID")                    private Integer teamId;
    @JsonProperty("PlayerID")                  private Long playerId;
    @JsonProperty("SeasonType")                private Integer seasonType;
    @JsonProperty("Season")                    private Integer season;
    @JsonProperty("Name")                      private String name;
    @JsonProperty("Team")                      private String team;
    @JsonProperty("Position")                  private String position;
    @JsonProperty("PositionCategory")          private String positionCategory;
    @JsonProperty("Started")                   private Integer started;
    @JsonProperty("BattingOrder")              private Integer battingOrder;

    @JsonProperty("FanDuelSalary")             private Integer fanDuelSalary;
    @JsonProperty("DraftKingsSalary")          private Integer draftKingsSalary;
    @JsonProperty("FantasyDataSalary")         private Integer fantasyDataSalary;
    @JsonProperty("YahooSalary")               private Integer yahooSalary;
    @JsonProperty("FanDuelPosition")           private String fanDuelPosition;
    @JsonProperty("DraftKingsPosition")        private String draftKingsPosition;
    @JsonProperty("YahooPosition")             private String yahooPosition;
    @JsonProperty("FantasyDraftSalary")        private Integer fantasyDraftSalary;
    @JsonProperty("FantasyDraftPosition")      private String fantasyDraftPosition;

    @JsonProperty("InjuryStatus")              private String injuryStatus;
    @JsonProperty("InjuryBodyPart")            private String injuryBodyPart;
    @JsonProperty("InjuryStartDate")           private LocalDate injuryStartDate;
    @JsonProperty("InjuryNotes")               private String injuryNotes;

    @JsonProperty("GameID")                    private Long gameId;
    @JsonProperty("GlobalGameID")              private Long globalGameId;
    @JsonProperty("Day")                       private LocalDate day;
    @JsonProperty("DateTime")                  private LocalDateTime dateTime;
    @JsonProperty("HomeOrAway")                private String homeOrAway;
    @JsonProperty("IsGameOver")                private Boolean isGameOver;
    @JsonProperty("Updated")                   private LocalDateTime updated;

    @JsonProperty("OpponentID")                private Integer opponentId;
    @JsonProperty("GlobalOpponentID")          private Long globalOpponentId;
    @JsonProperty("Opponent")                  private String opponent;
    @JsonProperty("OpponentRank")              private Integer opponentRank;
    @JsonProperty("OpponentPositionRank")      private Integer opponentPositionRank;

    @JsonProperty("GlobalTeamID")              private Long globalTeamId;

    @JsonProperty("Games")                     private Integer games;
    @JsonProperty("FantasyPoints")             private BigDecimal fantasyPoints;

    @JsonProperty("AtBats")                    private Integer atBats;
    @JsonProperty("Runs")                      private Integer runs;
    @JsonProperty("Hits")                      private Integer hits;
    @JsonProperty("Singles")                   private Integer singles;
    @JsonProperty("Doubles")                   private Integer doubles;
    @JsonProperty("Triples")                   private Integer triples;
    @JsonProperty("HomeRuns")                  private Integer homeRuns;
    @JsonProperty("RunsBattedIn")              private Integer runsBattedIn;
    @JsonProperty("BattingAverage")            private BigDecimal battingAverage;
    @JsonProperty("Outs")                      private Integer outs;
    @JsonProperty("Strikeouts")                private Integer strikeouts;
    @JsonProperty("Walks")                     private Integer walks;
    @JsonProperty("HitByPitch")                private Integer hitByPitch;
    @JsonProperty("Sacrifices")                private Integer sacrifices;
    @JsonProperty("SacrificeFlies")            private Integer sacrificeFlies;
    @JsonProperty("GroundIntoDoublePlay")      private Integer groundIntoDoublePlay;
    @JsonProperty("StolenBases")               private Integer stolenBases;
    @JsonProperty("CaughtStealing")            private Integer caughtStealing;
    @JsonProperty("PitchesSeen")               private Integer pitchesSeen;
    @JsonProperty("OnBasePercentage")          private BigDecimal onBasePercentage;
    @JsonProperty("SluggingPercentage")        private BigDecimal sluggingPercentage;
    @JsonProperty("OnBasePlusSlugging")        private BigDecimal onBasePlusSlugging;
    @JsonProperty("Errors")                    private Integer errors;

    @JsonProperty("Wins")                      private Integer wins;
    @JsonProperty("Losses")                    private Integer losses;
    @JsonProperty("Saves")                     private Integer saves;
    @JsonProperty("InningsPitchedDecimal")     private BigDecimal inningsPitchedDecimal;
    @JsonProperty("TotalOutsPitched")          private Integer totalOutsPitched;
    @JsonProperty("InningsPitchedFull")        private Integer inningsPitchedFull;
    @JsonProperty("InningsPitchedOuts")        private Integer inningsPitchedOuts;
    @JsonProperty("EarnedRunAverage")          private BigDecimal earnedRunAverage;
    @JsonProperty("PitchingHits")              private Integer pitchingHits;
    @JsonProperty("PitchingRuns")              private Integer pitchingRuns;
    @JsonProperty("PitchingEarnedRuns")        private Integer pitchingEarnedRuns;
    @JsonProperty("PitchingWalks")             private Integer pitchingWalks;
    @JsonProperty("PitchingStrikeouts")        private Integer pitchingStrikeouts;
    @JsonProperty("PitchingHomeRuns")          private Integer pitchingHomeRuns;
    @JsonProperty("PitchesThrown")             private Integer pitchesThrown;
    @JsonProperty("PitchesThrownStrikes")      private Integer pitchesThrownStrikes;
    @JsonProperty("WalksHitsPerInningsPitched")private BigDecimal walksHitsPerInningsPitched;
    @JsonProperty("PitchingBattingAverageAgainst") private BigDecimal pitchingBattingAverageAgainst;

    @JsonProperty("FantasyPointsFanDuel")      private BigDecimal fantasyPointsFanDuel;
    @JsonProperty("FantasyPointsDraftKings")   private BigDecimal fantasyPointsDraftKings;
    @JsonProperty("FantasyPointsYahoo")        private BigDecimal fantasyPointsYahoo;
    @JsonProperty("FantasyPointsFantasyDraft") private BigDecimal fantasyPointsFantasyDraft;
    @JsonProperty("FantasyPointsBatting")      private BigDecimal fantasyPointsBatting;
    @JsonProperty("FantasyPointsPitching")     private BigDecimal fantasyPointsPitching;

    @JsonProperty("GrandSlams")                private Integer grandSlams;
    @JsonProperty("PlateAppearances")          private Integer plateAppearances;
    @JsonProperty("TotalBases")                private Integer totalBases;
    @JsonProperty("FlyOuts")                   private Integer flyOuts;
    @JsonProperty("GroundOuts")                private Integer groundOuts;
    @JsonProperty("LineOuts")                  private Integer lineOuts;
    @JsonProperty("PopOuts")                   private Integer popOuts;
    @JsonProperty("IntentionalWalks")          private Integer intentionalWalks;
    @JsonProperty("ReachedOnError")            private Integer reachedOnError;
    @JsonProperty("BallsInPlay")               private Integer ballsInPlay;
    @JsonProperty("BattingAverageOnBallsInPlay") private BigDecimal battingAverageOnBallsInPlay;
    @JsonProperty("WeightedOnBasePercentage")  private BigDecimal weightedOnBasePercentage;

    @JsonProperty("PitchingSingles")           private Integer pitchingSingles;
    @JsonProperty("PitchingDoubles")           private Integer pitchingDoubles;
    @JsonProperty("PitchingTriples")           private Integer pitchingTriples;
    @JsonProperty("PitchingGrandSlams")        private Integer pitchingGrandSlams;
    @JsonProperty("PitchingHitByPitch")        private Integer pitchingHitByPitch;
    @JsonProperty("PitchingSacrifices")        private Integer pitchingSacrifices;
    @JsonProperty("PitchingSacrificeFlies")    private Integer pitchingSacrificeFlies;
    @JsonProperty("PitchingGroundIntoDoublePlay") private Integer pitchingGroundIntoDoublePlay;
    @JsonProperty("PitchingCompleteGames")     private Integer pitchingCompleteGames;
    @JsonProperty("PitchingShutOuts")          private Integer pitchingShutOuts;
    @JsonProperty("PitchingNoHitters")         private Integer pitchingNoHitters;
    @JsonProperty("PitchingPerfectGames")      private Integer pitchingPerfectGames;
    @JsonProperty("PitchingPlateAppearances")  private Integer pitchingPlateAppearances;
    @JsonProperty("PitchingTotalBases")        private Integer pitchingTotalBases;
    @JsonProperty("PitchingFlyOuts")           private Integer pitchingFlyOuts;
    @JsonProperty("PitchingGroundOuts")        private Integer pitchingGroundOuts;
    @JsonProperty("PitchingLineOuts")          private Integer pitchingLineOuts;
    @JsonProperty("PitchingPopOuts")           private Integer pitchingPopOuts;
    @JsonProperty("PitchingIntentionalWalks")  private Integer pitchingIntentionalWalks;
    @JsonProperty("PitchingReachedOnError")    private Integer pitchingReachedOnError;
    @JsonProperty("PitchingCatchersInterference") private Integer pitchingCatchersInterference;
    @JsonProperty("PitchingBallsInPlay")       private Integer pitchingBallsInPlay;
    @JsonProperty("PitchingOnBasePercentage")  private BigDecimal pitchingOnBasePercentage;
    @JsonProperty("PitchingSluggingPercentage") private BigDecimal pitchingSluggingPercentage;
    @JsonProperty("PitchingOnBasePlusSlugging") private BigDecimal pitchingOnBasePlusSlugging;
    @JsonProperty("PitchingStrikeoutsPerNineInnings") private BigDecimal pitchingStrikeoutsPerNineInnings;
    @JsonProperty("PitchingWalksPerNineInnings") private BigDecimal pitchingWalksPerNineInnings;
    @JsonProperty("PitchingBattingAverageOnBallsInPlay") private BigDecimal pitchingBattingAverageOnBallsInPlay;
    @JsonProperty("PitchingWeightedOnBasePercentage") private BigDecimal pitchingWeightedOnBasePercentage;

    @JsonProperty("DoublePlays")              private Integer doublePlays;
    @JsonProperty("PitchingDoublePlays")      private Integer pitchingDoublePlays;
    @JsonProperty("BattingOrderConfirmed")    private Boolean battingOrderConfirmed;
    @JsonProperty("IsolatedPower")            private BigDecimal isolatedPower;
    @JsonProperty("FieldingIndependentPitching") private BigDecimal fieldingIndependentPitching;
    @JsonProperty("PitchingQualityStarts")    private Integer pitchingQualityStarts;
    @JsonProperty("PitchingInningStarted")    private Integer pitchingInningStarted;
    @JsonProperty("LeftOnBase")               private Integer leftOnBase;
    @JsonProperty("PitchingHolds")            private Integer pitchingHolds;
    @JsonProperty("PitchingBlownSaves")       private Integer pitchingBlownSaves;
    @JsonProperty("SubstituteBattingOrder")   private Integer substituteBattingOrder;
    @JsonProperty("SubstituteBattingOrderSequence") private Integer substituteBattingOrderSequence;

    @JsonProperty("BettingMarketID")          private Long bettingMarketId;
}
