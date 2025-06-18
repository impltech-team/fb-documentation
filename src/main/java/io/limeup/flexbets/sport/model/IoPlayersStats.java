package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "io_players_stats")
public class IoPlayersStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "stat_id")
    private Long statId;

    @Column(name = "team_id")               private Long teamId;
    @Column(name = "player_id")             private Long playerId;
    @Column(name = "season_type")           private Integer seasonType;
    @Column(name = "season")                private Integer season;

    @Column(name = "name")                  private String name;
    @Column(name = "team")                  private String team;
    @Column(name = "position")              private String position;
    @Column(name = "position_category")     private String positionCategory;
    @Column(name = "started")               private Integer started;
    @Column(name = "batting_order")         private String battingOrder;
    @Column(name = "batting_order_confirmed") private Boolean battingOrderConfirmed;
    @Column(name = "global_team_id")        private Long globalTeamId;
    @Column(name = "average_draft_position") private String averageDraftPosition;
    @Column(name = "auction_value")         private String auctionValue;
    @Column(name = "updated")               private LocalDateTime updated;

    @Column(name = "games")                         private Integer games;
    @Column(name = "fantasy_points")               private Double fantasyPoints;
    @Column(name = "fantasy_points_fan_duel")      private Double fantasyPointsFanDuel;
    @Column(name = "fantasy_points_draft_kings")   private Double fantasyPointsDraftKings;
    @Column(name = "fantasy_points_yahoo")         private Double fantasyPointsYahoo;
    @Column(name = "fantasy_points_fantasy_draft") private Double fantasyPointsFantasyDraft;
    @Column(name = "fantasy_points_batting")       private Double fantasyPointsBatting;
    @Column(name = "fantasy_points_pitching")      private Double fantasyPointsPitching;

    @Column(name = "at_bats")                      private Integer atBats;
    @Column(name = "runs")                         private Integer runs;
    @Column(name = "hits")                         private Integer hits;
    @Column(name = "singles")                      private Integer singles;
    @Column(name = "doubles")                      private Integer doublesCount;
    @Column(name = "triples")                      private Integer triples;
    @Column(name = "home_runs")                    private Integer homeRuns;
    @Column(name = "runs_batted_in")               private Integer runsBattedIn;
    @Column(name = "batting_average")              private Double battingAverage;
    @Column(name = "outs")                         private Integer outs;
    @Column(name = "strikeouts")                   private Integer strikeouts;
    @Column(name = "walks")                        private Integer walks;
    @Column(name = "hit_by_pitch")                 private Integer hitByPitch;
    @Column(name = "sacrifices")                   private Integer sacrifices;
    @Column(name = "sacrifice_flies")              private Integer sacrificeFlies;
    @Column(name = "ground_into_double_play")      private Integer groundIntoDoublePlay;
    @Column(name = "stolen_bases")                 private Integer stolenBases;
    @Column(name = "caught_stealing")              private Integer caughtStealing;
    @Column(name = "pitches_seen")                 private Integer pitchesSeen;
    @Column(name = "on_base_percentage")           private Double onBasePercentage;
    @Column(name = "slugging_percentage")          private Double sluggingPercentage;
    @Column(name = "on_base_plus_slugging")        private Double onBasePlusSlugging;
    @Column(name = "errors")                       private Integer errors;

    @Column(name = "isolated_power")               private Double isolatedPower;
    @Column(name = "balls_in_play")                private Integer ballsInPlay;
    @Column(name = "batting_average_on_balls_in_play")
    private Double battingAverageOnBallsInPlay;
    @Column(name = "weighted_on_base_percentage")  private Double weightedOnBasePercentage;
    @Column(name = "grand_slams")                  private Integer grandSlams;
    @Column(name = "double_plays")                 private Integer doublePlays;
    @Column(name = "plate_appearances")            private Integer plateAppearances;
    @Column(name = "total_bases")                  private Integer totalBases;
    @Column(name = "fly_outs")                     private Integer flyOuts;
    @Column(name = "ground_outs")                  private Integer groundOuts;
    @Column(name = "line_outs")                    private Integer lineOuts;
    @Column(name = "pop_outs")                     private Integer popOuts;
    @Column(name = "intentional_walks")            private Integer intentionalWalks;
    @Column(name = "reached_on_error")             private Integer reachedOnError;
    @Column(name = "left_on_base")                 private Integer leftOnBase;

    @Column(name = "wins")                         private Integer wins;
    @Column(name = "losses")                       private Integer losses;
    @Column(name = "saves")                        private Integer saves;
    @Column(name = "innings_pitched_decimal")      private Double inningsPitchedDecimal;
    @Column(name = "total_outs_pitched")           private Integer totalOutsPitched;
    @Column(name = "innings_pitched_full")         private Integer inningsPitchedFull;
    @Column(name = "innings_pitched_outs")         private Integer inningsPitchedOuts;
    @Column(name = "earned_run_average")           private Double earnedRunAverage;
    @Column(name = "fielding_independent_pitching") private Double fieldingIndependentPitching;
    @Column(name = "pitching_quality_starts")      private Double pitchingQualityStarts;
    @Column(name = "pitching_inning_started")      private String pitchingInningStarted;
    @Column(name = "pitching_holds")               private Integer pitchingHolds;
    @Column(name = "pitching_blown_saves")         private Integer pitchingBlownSaves;

    @Column(name = "pitching_hits")                         private Integer pitchingHits;
    @Column(name = "pitching_runs")                         private Integer pitchingRuns;
    @Column(name = "pitching_earned_runs")                  private Integer pitchingEarnedRuns;
    @Column(name = "pitching_walks")                        private Integer pitchingWalks;
    @Column(name = "pitching_strikeouts")                   private Integer pitchingStrikeouts;
    @Column(name = "pitching_home_runs")                    private Integer pitchingHomeRuns;
    @Column(name = "pitches_thrown")                        private Integer pitchesThrown;
    @Column(name = "pitches_thrown_strikes")                private Integer pitchesThrownStrikes;
    @Column(name = "walks_hits_per_innings_pitched")        private Double walksHitsPerInningsPitched;
    @Column(name = "pitching_batting_average_against")      private Double pitchingBattingAverageAgainst;
    @Column(name = "pitching_singles")                      private Integer pitchingSingles;
    @Column(name = "pitching_doubles")                      private Integer pitchingDoubles;
    @Column(name = "pitching_triples")                      private Integer pitchingTriples;
    @Column(name = "pitching_grand_slams")                  private Integer pitchingGrandSlams;
    @Column(name = "pitching_hit_by_pitch")                 private Integer pitchingHitByPitch;
    @Column(name = "pitching_sacrifices")                   private Integer pitchingSacrifices;
    @Column(name = "pitching_sacrifice_flies")              private Integer pitchingSacrificeFlies;
    @Column(name = "pitching_ground_into_double_play")      private Integer pitchingGroundIntoDoublePlay;
    @Column(name = "pitching_complete_games")               private Integer pitchingCompleteGames;
    @Column(name = "pitching_shut_outs")                    private Integer pitchingShutOuts;
    @Column(name = "pitching_no_hitters")                   private Integer pitchingNoHitters;
    @Column(name = "pitching_perfect_games")                private Integer pitchingPerfectGames;
    @Column(name = "pitching_plate_appearances")            private Integer pitchingPlateAppearances;
    @Column(name = "pitching_total_bases")                  private Integer pitchingTotalBases;
    @Column(name = "pitching_fly_outs")                     private Integer pitchingFlyOuts;
    @Column(name = "pitching_ground_outs")                  private Integer pitchingGroundOuts;
    @Column(name = "pitching_line_outs")                    private Integer pitchingLineOuts;
    @Column(name = "pitching_pop_outs")                     private Integer pitchingPopOuts;
    @Column(name = "pitching_intentional_walks")            private Integer pitchingIntentionalWalks;
    @Column(name = "pitching_reached_on_error")             private Integer pitchingReachedOnError;
    @Column(name = "pitching_catchers_interference")        private Integer pitchingCatchersInterference;
    @Column(name = "pitching_balls_in_play")                private Integer pitchingBallsInPlay;
    @Column(name = "pitching_on_base_percentage")           private Double pitchingOnBasePercentage;
    @Column(name = "pitching_slugging_percentage")          private Double pitchingSluggingPercentage;
    @Column(name = "pitching_on_base_plus_slugging")        private Double pitchingOnBasePlusSlugging;
    @Column(name = "pitching_strikeouts_per_nine_innings")  private Double pitchingStrikeoutsPerNineInnings;
    @Column(name = "pitching_walks_per_nine_innings")       private Double pitchingWalksPerNineInnings;
    @Column(name = "pitching_batting_average_on_balls_in_play")
    private Double pitchingBattingAverageOnBallsInPlay;
    @Column(name = "pitching_weighted_on_base_percentage")  private Double pitchingWeightedOnBasePercentage;
    @Column(name = "pitching_double_plays")                 private Integer pitchingDoublePlays;

    @Column(name = "substitute_batting_order")              private String substituteBattingOrder;
    @Column(name = "substitute_batting_order_sequence")     private String substituteBattingOrderSequence;
}
