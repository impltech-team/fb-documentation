package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "io_player_game_stats_nfl", schema = "sport")
public class IoPlayerGameStatsNFL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "player_id")
    private Long playerId;

    @Column(name = "game_key")
    private String gameKey;

    @Column(name = "global_game_id")
    private Long globalGameId;

    @Column(name = "season")
    private Integer season;

    @Column(name = "season_type")
    private Integer seasonType;

    @Column(name = "week")
    private Integer week;

    @Column(name = "team")
    private String team;

    @Column(name = "team_id")
    private Integer teamId;

    @Column(name = "global_team_id")
    private Long globalTeamId;

    @Column(name = "opponent")
    private String opponent;

    @Column(name = "opponent_id")
    private Integer opponentId;

    @Column(name = "global_opponent_id")
    private Long globalOpponentId;

    @Column(name = "home_or_away")
    private String homeOrAway;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "player_short_name")
    private String playerShortName;

    @Column(name = "player_number")
    private Integer playerNumber;

    @Column(name = "position")
    private String position;

    @Column(name = "position_category")
    private String positionCategory;

    @Column(name = "fantasy_position")
    private String fantasyPosition;

    @Column(name = "activated")
    private Integer activated;

    @Column(name = "played")
    private Integer played;

    @Column(name = "started")
    private Integer started;

    @Column(name = "declared_inactive")
    private Boolean declaredInactive;

    // Passing stats
    @Column(name = "passing_attempts")
    private Integer passingAttempts;

    @Column(name = "passing_completions")
    private Integer passingCompletions;

    @Column(name = "passing_yards")
    private Integer passingYards;

    @Column(name = "passing_completion_percentage")
    private BigDecimal passingCompletionPercentage;

    @Column(name = "passing_yards_per_attempt")
    private BigDecimal passingYardsPerAttempt;

    @Column(name = "passing_yards_per_completion")
    private BigDecimal passingYardsPerCompletion;

    @Column(name = "passing_touchdowns")
    private Integer passingTouchdowns;

    @Column(name = "passing_interceptions")
    private Integer passingInterceptions;

    @Column(name = "passing_rating")
    private BigDecimal passingRating;

    @Column(name = "passing_long")
    private Integer passingLong;

    @Column(name = "passing_sacks")
    private Integer passingSacks;

    @Column(name = "passing_sack_yards")
    private Integer passingSackYards;

    // Rushing stats
    @Column(name = "rushing_attempts")
    private Integer rushingAttempts;

    @Column(name = "rushing_yards")
    private Integer rushingYards;

    @Column(name = "rushing_yards_per_attempt")
    private BigDecimal rushingYardsPerAttempt;

    @Column(name = "rushing_touchdowns")
    private Integer rushingTouchdowns;

    @Column(name = "rushing_long")
    private Integer rushingLong;

    // Receiving stats
    @Column(name = "receiving_targets")
    private Integer receivingTargets;

    @Column(name = "receptions")
    private Integer receptions;

    @Column(name = "receiving_yards")
    private Integer receivingYards;

    @Column(name = "receiving_yards_per_reception")
    private BigDecimal receivingYardsPerReception;

    @Column(name = "receiving_touchdowns")
    private Integer receivingTouchdowns;

    @Column(name = "receiving_long")
    private Integer receivingLong;

    @Column(name = "reception_percentage")
    private BigDecimal receptionPercentage;

    @Column(name = "receiving_yards_per_target")
    private BigDecimal receivingYardsPerTarget;

    // Defensive stats
    @Column(name = "solo_tackles")
    private Integer soloTackles;

    @Column(name = "assisted_tackles")
    private Integer assistedTackles;

    @Column(name = "tackles")
    private Integer tackles;

    @Column(name = "tackles_for_loss")
    private Integer tacklesForLoss;

    @Column(name = "sacks")
    private Integer sacks;

    @Column(name = "sack_yards")
    private Integer sackYards;

    @Column(name = "quarterback_hits")
    private Integer quarterbackHits;

    @Column(name = "passes_defended")
    private Integer passesDefended;

    @Column(name = "interceptions")
    private Integer interceptions;

    @Column(name = "interception_return_yards")
    private Integer interceptionReturnYards;

    @Column(name = "interception_return_touchdowns")
    private Integer interceptionReturnTouchdowns;

    // Fumbles
    @Column(name = "fumbles")
    private Integer fumbles;

    @Column(name = "fumbles_lost")
    private Integer fumblesLost;

    @Column(name = "fumbles_forced")
    private Integer fumblesForced;

    @Column(name = "fumbles_recovered")
    private Integer fumblesRecovered;

    @Column(name = "fumble_return_yards")
    private Integer fumbleReturnYards;

    @Column(name = "fumble_return_touchdowns")
    private Integer fumbleReturnTouchdowns;

    @Column(name = "fumbles_own_recoveries")
    private Integer fumblesOwnRecoveries;

    @Column(name = "fumbles_out_of_bounds")
    private Integer fumblesOutOfBounds;

    // Special teams - Punt returns
    @Column(name = "punt_returns")
    private Integer puntReturns;

    @Column(name = "punt_return_yards")
    private Integer puntReturnYards;

    @Column(name = "punt_return_yards_per_attempt")
    private BigDecimal puntReturnYardsPerAttempt;

    @Column(name = "punt_return_touchdowns")
    private Integer puntReturnTouchdowns;

    @Column(name = "punt_return_long")
    private Integer puntReturnLong;

    @Column(name = "punt_return_fair_catches")
    private Integer puntReturnFairCatches;

    // Special teams - Kick returns
    @Column(name = "kick_returns")
    private Integer kickReturns;

    @Column(name = "kick_return_yards")
    private Integer kickReturnYards;

    @Column(name = "kick_return_yards_per_attempt")
    private BigDecimal kickReturnYardsPerAttempt;

    @Column(name = "kick_return_touchdowns")
    private Integer kickReturnTouchdowns;

    @Column(name = "kick_return_long")
    private Integer kickReturnLong;

    @Column(name = "kick_return_fair_catches")
    private Integer kickReturnFairCatches;

    // Kicking
    @Column(name = "field_goals_attempted")
    private Integer fieldGoalsAttempted;

    @Column(name = "field_goals_made")
    private Integer fieldGoalsMade;

    @Column(name = "field_goals_longest_made")
    private Integer fieldGoalsLongestMade;

    @Column(name = "field_goal_percentage")
    private BigDecimal fieldGoalPercentage;

    @Column(name = "field_goals_had_blocked")
    private Integer fieldGoalsHadBlocked;

    @Column(name = "field_goals_made_0to19")
    private Integer fieldGoalsMade0to19;

    @Column(name = "field_goals_made_20to29")
    private Integer fieldGoalsMade20to29;

    @Column(name = "field_goals_made_30to39")
    private Integer fieldGoalsMade30to39;

    @Column(name = "field_goals_made_40to49")
    private Integer fieldGoalsMade40to49;

    @Column(name = "field_goals_made_50plus")
    private Integer fieldGoalsMade50plus;

    @Column(name = "extra_points_attempted")
    private Integer extraPointsAttempted;

    @Column(name = "extra_points_made")
    private Integer extraPointsMade;

    @Column(name = "extra_points_had_blocked")
    private Integer extraPointsHadBlocked;

    // Punting
    @Column(name = "punts")
    private Integer punts;

    @Column(name = "punt_yards")
    private Integer puntYards;

    @Column(name = "punt_average")
    private BigDecimal puntAverage;

    @Column(name = "punt_long")
    private Integer puntLong;

    @Column(name = "punt_inside20")
    private Integer puntInside20;

    @Column(name = "punt_touchbacks")
    private Integer puntTouchbacks;

    @Column(name = "punts_had_blocked")
    private Integer puntsHadBlocked;

    @Column(name = "punt_net_average")
    private BigDecimal puntNetAverage;

    @Column(name = "punt_net_yards")
    private Integer puntNetYards;

    // Miscellaneous
    @Column(name = "blocked_kicks")
    private Integer blockedKicks;

    @Column(name = "blocked_kick_return_yards")
    private Integer blockedKickReturnYards;

    @Column(name = "blocked_kick_return_touchdowns")
    private Integer blockedKickReturnTouchdowns;

    @Column(name = "field_goal_return_yards")
    private Integer fieldGoalReturnYards;

    @Column(name = "field_goal_return_touchdowns")
    private Integer fieldGoalReturnTouchdowns;

    @Column(name = "safeties")
    private Integer safeties;

    @Column(name = "safeties_allowed")
    private Integer safetiesAllowed;

    // Two-point conversions
    @Column(name = "two_point_conversion_passes")
    private Integer twoPointConversionPasses;

    @Column(name = "two_point_conversion_runs")
    private Integer twoPointConversionRuns;

    @Column(name = "two_point_conversion_receptions")
    private Integer twoPointConversionReceptions;

    @Column(name = "two_point_conversion_returns")
    private Integer twoPointConversionReturns;

    // Snap counts
    @Column(name = "offensive_snaps_played")
    private Integer offensiveSnapsPlayed;

    @Column(name = "offensive_team_snaps")
    private Integer offensiveTeamSnaps;

    @Column(name = "defensive_snaps_played")
    private Integer defensiveSnapsPlayed;

    @Column(name = "defensive_team_snaps")
    private Integer defensiveTeamSnaps;

    @Column(name = "special_teams_snaps_played")
    private Integer specialTeamsSnapsPlayed;

    @Column(name = "special_teams_team_snaps")
    private Integer specialTeamsTeamSnaps;

    @Column(name = "snap_counts_confirmed")
    private Boolean snapCountsConfirmed;

    // Touchdowns
    @Column(name = "offensive_touchdowns")
    private Integer offensiveTouchdowns;

    @Column(name = "defensive_touchdowns")
    private Integer defensiveTouchdowns;

    @Column(name = "special_teams_touchdowns")
    private Integer specialTeamsTouchdowns;

    @Column(name = "touchdowns")
    private Integer touchdowns;

    // Fantasy points
    @Column(name = "fantasy_points")
    private BigDecimal fantasyPoints;

    @Column(name = "fantasy_points_ppr")
    private BigDecimal fantasyPointsPpr;

    @Column(name = "fantasy_points_fan_duel")
    private BigDecimal fantasyPointsFanDuel;

    @Column(name = "fantasy_points_draft_kings")
    private BigDecimal fantasyPointsDraftKings;

    @Column(name = "fantasy_points_yahoo")
    private BigDecimal fantasyPointsYahoo;

    @Column(name = "fantasy_points_fantasy_draft")
    private BigDecimal fantasyPointsFantasyDraft;

    // Salaries
    @Column(name = "fan_duel_salary")
    private Integer fanDuelSalary;

    @Column(name = "draft_kings_salary")
    private Integer draftKingsSalary;

    @Column(name = "fantasy_data_salary")
    private Integer fantasyDataSalary;

    @Column(name = "yahoo_salary")
    private Integer yahooSalary;

    @Column(name = "fantasy_draft_salary")
    private Integer fantasyDraftSalary;

    // Positions
    @Column(name = "fan_duel_position")
    private String fanDuelPosition;

    @Column(name = "draft_kings_position")
    private String draftKingsPosition;

    @Column(name = "yahoo_position")
    private String yahooPosition;

    @Column(name = "fantasy_draft_position")
    private String fantasyDraftPosition;

    // Injuries
    @Column(name = "injury_status")
    private String injuryStatus;

    @Column(name = "injury_body_part")
    private String injuryBodyPart;

    @Column(name = "injury_start_date")
    private LocalDate injuryStartDate;

    @Column(name = "injury_notes")
    private String injuryNotes;

    @Column(name = "injury_practice")
    private String injuryPractice;

    @Column(name = "injury_practice_description")
    private String injuryPracticeDescription;

    // Game environment
    @Column(name = "stadium")
    private String stadium;

    @Column(name = "playing_surface")
    private String playingSurface;

    @Column(name = "temperature")
    private Integer temperature;

    @Column(name = "humidity")
    private Integer humidity;

    @Column(name = "wind_speed")
    private Integer windSpeed;

    // Opponent rankings
    @Column(name = "opponent_rank")
    private Integer opponentRank;

    @Column(name = "opponent_position_rank")
    private Integer opponentPositionRank;

    // Timestamps
    @Column(name = "day")
    private LocalDate day;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "game_date")
    private LocalDateTime gameDate;

    @Column(name = "updated")
    private LocalDateTime updated;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}