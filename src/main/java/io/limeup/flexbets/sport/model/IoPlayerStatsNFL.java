package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "io_player_stats_nfl", schema = "sport")
public class IoPlayerStatsNFL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_id", columnDefinition = "text")
    private String playerId;

    @Column(name = "season_type", columnDefinition = "text")
    private String seasonType;

    @Column(name = "season", columnDefinition = "text")
    private String season;

    @Column(name = "team", columnDefinition = "text")
    private String team;

    @Column(name = "number", columnDefinition = "text")
    private String number;

    @Column(name = "name", columnDefinition = "text")
    private String name;

    @Column(name = "position", columnDefinition = "text")
    private String position;

    @Column(name = "position_category", columnDefinition = "text")
    private String positionCategory;

    @Column(name = "activated", columnDefinition = "text")
    private String activated;

    @Column(name = "played", columnDefinition = "text")
    private String played;

    @Column(name = "started", columnDefinition = "text")
    private String started;

    @Column(name = "passing_attempts", columnDefinition = "text")
    private String passingAttempts;

    @Column(name = "passing_completions", columnDefinition = "text")
    private String passingCompletions;

    @Column(name = "passing_yards", columnDefinition = "text")
    private String passingYards;

    @Column(name = "passing_completion_percentage", columnDefinition = "text")
    private String passingCompletionPercentage;

    @Column(name = "passing_yards_per_attempt", columnDefinition = "text")
    private String passingYardsPerAttempt;

    @Column(name = "passing_yards_per_completion", columnDefinition = "text")
    private String passingYardsPerCompletion;

    @Column(name = "passing_touchdowns", columnDefinition = "text")
    private String passingTouchdowns;

    @Column(name = "passing_interceptions", columnDefinition = "text")
    private String passingInterceptions;

    @Column(name = "passing_rating", columnDefinition = "text")
    private String passingRating;

    @Column(name = "passing_long", columnDefinition = "text")
    private String passingLong;

    @Column(name = "passing_sacks", columnDefinition = "text")
    private String passingSacks;

    @Column(name = "passing_sack_yards", columnDefinition = "text")
    private String passingSackYards;

    @Column(name = "rushing_attempts", columnDefinition = "text")
    private String rushingAttempts;

    @Column(name = "rushing_yards", columnDefinition = "text")
    private String rushingYards;

    @Column(name = "rushing_yards_per_attempt", columnDefinition = "text")
    private String rushingYardsPerAttempt;

    @Column(name = "rushing_touchdowns", columnDefinition = "text")
    private String rushingTouchdowns;

    @Column(name = "rushing_long", columnDefinition = "text")
    private String rushingLong;

    @Column(name = "receiving_targets", columnDefinition = "text")
    private String receivingTargets;

    @Column(name = "receptions", columnDefinition = "text")
    private String receptions;

    @Column(name = "receiving_yards", columnDefinition = "text")
    private String receivingYards;

    @Column(name = "receiving_yards_per_reception", columnDefinition = "text")
    private String receivingYardsPerReception;

    @Column(name = "receiving_touchdowns", columnDefinition = "text")
    private String receivingTouchdowns;

    @Column(name = "receiving_long", columnDefinition = "text")
    private String receivingLong;

    @Column(name = "fumbles", columnDefinition = "text")
    private String fumbles;

    @Column(name = "fumbles_lost", columnDefinition = "text")
    private String fumblesLost;

    @Column(name = "punt_returns", columnDefinition = "text")
    private String puntReturns;

    @Column(name = "punt_return_yards", columnDefinition = "text")
    private String puntReturnYards;

    @Column(name = "punt_return_yards_per_attempt", columnDefinition = "text")
    private String puntReturnYardsPerAttempt;

    @Column(name = "punt_return_touchdowns", columnDefinition = "text")
    private String puntReturnTouchdowns;

    @Column(name = "punt_return_long", columnDefinition = "text")
    private String puntReturnLong;

    @Column(name = "kick_returns", columnDefinition = "text")
    private String kickReturns;

    @Column(name = "kick_return_yards", columnDefinition = "text")
    private String kickReturnYards;

    @Column(name = "kick_return_yards_per_attempt", columnDefinition = "text")
    private String kickReturnYardsPerAttempt;

    @Column(name = "kick_return_touchdowns", columnDefinition = "text")
    private String kickReturnTouchdowns;

    @Column(name = "kick_return_long", columnDefinition = "text")
    private String kickReturnLong;

    @Column(name = "solo_tackles", columnDefinition = "text")
    private String soloTackles;

    @Column(name = "assisted_tackles", columnDefinition = "text")
    private String assistedTackles;

    @Column(name = "tackles_for_loss", columnDefinition = "text")
    private String tacklesForLoss;

    @Column(name = "sacks", columnDefinition = "text")
    private String sacks;

    @Column(name = "sack_yards", columnDefinition = "text")
    private String sackYards;

    @Column(name = "quarterback_hits", columnDefinition = "text")
    private String quarterbackHits;

    @Column(name = "passes_defended", columnDefinition = "text")
    private String passesDefended;

    @Column(name = "fumbles_forced", columnDefinition = "text")
    private String fumblesForced;

    @Column(name = "fumbles_recovered", columnDefinition = "text")
    private String fumblesRecovered;

    @Column(name = "fumble_return_yards", columnDefinition = "text")
    private String fumbleReturnYards;

    @Column(name = "fumble_return_touchdowns", columnDefinition = "text")
    private String fumbleReturnTouchdowns;

    @Column(name = "interceptions", columnDefinition = "text")
    private String interceptions;

    @Column(name = "interception_return_yards", columnDefinition = "text")
    private String interceptionReturnYards;

    @Column(name = "interception_return_touchdowns", columnDefinition = "text")
    private String interceptionReturnTouchdowns;

    @Column(name = "blocked_kicks", columnDefinition = "text")
    private String blockedKicks;

    @Column(name = "special_teams_solo_tackles", columnDefinition = "text")
    private String specialTeamsSoloTackles;

    @Column(name = "special_teams_assisted_tackles", columnDefinition = "text")
    private String specialTeamsAssistedTackles;

    @Column(name = "misc_solo_tackles", columnDefinition = "text")
    private String miscSoloTackles;

    @Column(name = "misc_assisted_tackles", columnDefinition = "text")
    private String miscAssistedTackles;

    @Column(name = "punts", columnDefinition = "text")
    private String punts;

    @Column(name = "punt_yards", columnDefinition = "text")
    private String puntYards;

    @Column(name = "punt_average", columnDefinition = "text")
    private String puntAverage;

    @Column(name = "field_goals_attempted", columnDefinition = "text")
    private String fieldGoalsAttempted;

    @Column(name = "field_goals_made", columnDefinition = "text")
    private String fieldGoalsMade;

    @Column(name = "field_goals_longest_made", columnDefinition = "text")
    private String fieldGoalsLongestMade;

    @Column(name = "extra_points_made", columnDefinition = "text")
    private String extraPointsMade;

    @Column(name = "two_point_conversion_passes", columnDefinition = "text")
    private String twoPointConversionPasses;

    @Column(name = "two_point_conversion_runs", columnDefinition = "text")
    private String twoPointConversionRuns;

    @Column(name = "two_point_conversion_receptions", columnDefinition = "text")
    private String twoPointConversionReceptions;

    @Column(name = "fantasy_points", columnDefinition = "text")
    private String fantasyPoints;

    @Column(name = "fantasy_points_ppr", columnDefinition = "text")
    private String fantasyPointsPPR;

    @Column(name = "reception_percentage", columnDefinition = "text")
    private String receptionPercentage;

    @Column(name = "receiving_yards_per_target", columnDefinition = "text")
    private String receivingYardsPerTarget;

    @Column(name = "tackles", columnDefinition = "text")
    private String tackles;

    @Column(name = "offensive_touchdowns", columnDefinition = "text")
    private String offensiveTouchdowns;

    @Column(name = "defensive_touchdowns", columnDefinition = "text")
    private String defensiveTouchdowns;

    @Column(name = "special_teams_touchdowns", columnDefinition = "text")
    private String specialTeamsTouchdowns;

    @Column(name = "touchdowns", columnDefinition = "text")
    private String touchdowns;

    @Column(name = "fantasy_position", columnDefinition = "text")
    private String fantasyPosition;

    @Column(name = "field_goal_percentage", columnDefinition = "text")
    private String fieldGoalPercentage;

    @Column(name = "player_season_id", columnDefinition = "text")
    private String playerSeasonId;

    @Column(name = "fumbles_own_recoveries", columnDefinition = "text")
    private String fumblesOwnRecoveries;

    @Column(name = "fumbles_out_of_bounds", columnDefinition = "text")
    private String fumblesOutOfBounds;

    @Column(name = "kick_return_fair_catches", columnDefinition = "text")
    private String kickReturnFairCatches;

    @Column(name = "punt_return_fair_catches", columnDefinition = "text")
    private String puntReturnFairCatches;

    @Column(name = "punt_touchbacks", columnDefinition = "text")
    private String puntTouchbacks;

    @Column(name = "punt_inside20", columnDefinition = "text")
    private String puntInside20;

    @Column(name = "punt_net_average", columnDefinition = "text")
    private String puntNetAverage;

    @Column(name = "extra_points_attempted", columnDefinition = "text")
    private String extraPointsAttempted;

    @Column(name = "blocked_kick_return_touchdowns", columnDefinition = "text")
    private String blockedKickReturnTouchdowns;

    @Column(name = "field_goal_return_touchdowns", columnDefinition = "text")
    private String fieldGoalReturnTouchdowns;

    @Column(name = "safeties", columnDefinition = "text")
    private String safeties;

    @Column(name = "field_goals_had_blocked", columnDefinition = "text")
    private String fieldGoalsHadBlocked;

    @Column(name = "punts_had_blocked", columnDefinition = "text")
    private String puntsHadBlocked;

    @Column(name = "extra_points_had_blocked", columnDefinition = "text")
    private String extraPointsHadBlocked;

    @Column(name = "punt_long", columnDefinition = "text")
    private String puntLong;

    @Column(name = "blocked_kick_return_yards", columnDefinition = "text")
    private String blockedKickReturnYards;

    @Column(name = "field_goal_return_yards", columnDefinition = "text")
    private String fieldGoalReturnYards;

    @Column(name = "punt_net_yards", columnDefinition = "text")
    private String puntNetYards;

    @Column(name = "special_teams_fumbles_forced", columnDefinition = "text")
    private String specialTeamsFumblesForced;

    @Column(name = "special_teams_fumbles_recovered", columnDefinition = "text")
    private String specialTeamsFumblesRecovered;

    @Column(name = "misc_fumbles_forced", columnDefinition = "text")
    private String miscFumblesForced;

    @Column(name = "misc_fumbles_recovered", columnDefinition = "text")
    private String miscFumblesRecovered;

    @Column(name = "short_name", columnDefinition = "text")
    private String shortName;

    @Column(name = "safeties_allowed", columnDefinition = "text")
    private String safetiesAllowed;

    @Column(name = "temperature", columnDefinition = "text")
    private String temperature;

    @Column(name = "humidity", columnDefinition = "text")
    private String humidity;

    @Column(name = "wind_speed", columnDefinition = "text")
    private String windSpeed;

    @Column(name = "offensive_snaps_played", columnDefinition = "text")
    private String offensiveSnapsPlayed;

    @Column(name = "defensive_snaps_played", columnDefinition = "text")
    private String defensiveSnapsPlayed;

    @Column(name = "special_teams_snaps_played", columnDefinition = "text")
    private String specialTeamsSnapsPlayed;

    @Column(name = "offensive_team_snaps", columnDefinition = "text")
    private String offensiveTeamSnaps;

    @Column(name = "defensive_team_snaps", columnDefinition = "text")
    private String defensiveTeamSnaps;

    @Column(name = "special_teams_team_snaps", columnDefinition = "text")
    private String specialTeamsTeamSnaps;

    @Column(name = "auction_value", columnDefinition = "text")
    private String auctionValue;

    @Column(name = "auction_value_ppr", columnDefinition = "text")
    private String auctionValuePPR;

    @Column(name = "two_point_conversion_returns", columnDefinition = "text")
    private String twoPointConversionReturns;

    @Column(name = "fantasy_points_fanduel", columnDefinition = "text")
    private String fantasyPointsFanDuel;

    @Column(name = "field_goals_made0to19", columnDefinition = "text")
    private String fieldGoalsMade0to19;

    @Column(name = "field_goals_made20to29", columnDefinition = "text")
    private String fieldGoalsMade20to29;

    @Column(name = "field_goals_made30to39", columnDefinition = "text")
    private String fieldGoalsMade30to39;

    @Column(name = "field_goals_made40to49", columnDefinition = "text")
    private String fieldGoalsMade40to49;

    @Column(name = "field_goals_made50plus", columnDefinition = "text")
    private String fieldGoalsMade50Plus;

    @Column(name = "fantasy_points_draftkings", columnDefinition = "text")
    private String fantasyPointsDraftKings;

    @Column(name = "fantasy_points_yahoo", columnDefinition = "text")
    private String fantasyPointsYahoo;

    @Column(name = "average_draft_position", columnDefinition = "text")
    private String averageDraftPosition;

    @Column(name = "average_draft_position_ppr", columnDefinition = "text")
    private String averageDraftPositionPPR;

    @Column(name = "team_id", columnDefinition = "text")
    private String teamId;

    @Column(name = "global_team_id", columnDefinition = "text")
    private String globalTeamId;

    @Column(name = "fantasy_points_fantasydraft", columnDefinition = "text")
    private String fantasyPointsFantasyDraft;

    @Column(name = "average_draft_position_rookie", columnDefinition = "text")
    private String averageDraftPositionRookie;

    @Column(name = "average_draft_position_dynasty", columnDefinition = "text")
    private String averageDraftPositionDynasty;

    @Column(name = "average_draft_position2qb", columnDefinition = "text")
    private String averageDraftPosition2QB;

    @Column(name = "offensive_fumble_recovery_touchdowns", columnDefinition = "text")
    private String offensiveFumbleRecoveryTouchdowns;

    @Column(name = "game_id", columnDefinition = "text")
    private String gameId;

    // === Scoring Details Mapping ===
    @OneToMany(mappedBy = "playerGameStats", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<IoPlayerScoringDetailNFL> scoringDetails;
}