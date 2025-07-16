SET search_path TO sport;

CREATE TABLE IF NOT EXISTS sport.io_player_game_stats_nfl (
    id BIGSERIAL PRIMARY KEY,
    player_id BIGINT NOT NULL,
    game_key VARCHAR(20) NOT NULL,
    global_game_id BIGINT,
    season INTEGER NOT NULL,
    season_type INTEGER NOT NULL,
    week INTEGER,
    team VARCHAR(10),
    team_id INTEGER,
    global_team_id BIGINT,
    opponent VARCHAR(10),
    opponent_id INTEGER,
    global_opponent_id BIGINT,
    home_or_away VARCHAR(10),
    player_name VARCHAR(100),
    player_short_name VARCHAR(50),
    player_number INTEGER,
    position VARCHAR(10),
    position_category VARCHAR(10),
    fantasy_position VARCHAR(10),
    activated INTEGER,
    played INTEGER,
    started INTEGER,
    declared_inactive BOOLEAN,

    -- Passing stats
    passing_attempts INTEGER,
    passing_completions INTEGER,
    passing_yards INTEGER,
    passing_completion_percentage DECIMAL(5,2),
    passing_yards_per_attempt DECIMAL(5,2),
    passing_yards_per_completion DECIMAL(5,2),
    passing_touchdowns INTEGER,
    passing_interceptions INTEGER,
    passing_rating DECIMAL(5,2),
    passing_long INTEGER,
    passing_sacks INTEGER,
    passing_sack_yards INTEGER,

    -- Rushing stats
    rushing_attempts INTEGER,
    rushing_yards INTEGER,
    rushing_yards_per_attempt DECIMAL(5,2),
    rushing_touchdowns INTEGER,
    rushing_long INTEGER,

    -- Receiving stats
    receiving_targets INTEGER,
    receptions INTEGER,
    receiving_yards INTEGER,
    receiving_yards_per_reception DECIMAL(5,2),
    receiving_touchdowns INTEGER,
    receiving_long INTEGER,
    reception_percentage DECIMAL(5,2),
    receiving_yards_per_target DECIMAL(5,2),

    -- Defensive stats
    solo_tackles INTEGER,
    assisted_tackles INTEGER,
    tackles INTEGER,
    tackles_for_loss INTEGER,
    sacks INTEGER,
    sack_yards INTEGER,
    quarterback_hits INTEGER,
    passes_defended INTEGER,
    interceptions INTEGER,
    interception_return_yards INTEGER,
    interception_return_touchdowns INTEGER,

    -- Fumbles
    fumbles INTEGER,
    fumbles_lost INTEGER,
    fumbles_forced INTEGER,
    fumbles_recovered INTEGER,
    fumble_return_yards INTEGER,
    fumble_return_touchdowns INTEGER,
    fumbles_own_recoveries INTEGER,
    fumbles_out_of_bounds INTEGER,

    -- Special teams
    punt_returns INTEGER,
    punt_return_yards INTEGER,
    punt_return_yards_per_attempt DECIMAL(5,2),
    punt_return_touchdowns INTEGER,
    punt_return_long INTEGER,
    punt_return_fair_catches INTEGER,

    kick_returns INTEGER,
    kick_return_yards INTEGER,
    kick_return_yards_per_attempt DECIMAL(5,2),
    kick_return_touchdowns INTEGER,
    kick_return_long INTEGER,
    kick_return_fair_catches INTEGER,

    -- Kicking
    field_goals_attempted INTEGER,
    field_goals_made INTEGER,
    field_goals_longest_made INTEGER,
    field_goal_percentage DECIMAL(5,2),
    field_goals_had_blocked INTEGER,
    field_goals_made_0to19 INTEGER,
    field_goals_made_20to29 INTEGER,
    field_goals_made_30to39 INTEGER,
    field_goals_made_40to49 INTEGER,
    field_goals_made_50plus INTEGER,

    extra_points_attempted INTEGER,
    extra_points_made INTEGER,
    extra_points_had_blocked INTEGER,

    -- Punting
    punts INTEGER,
    punt_yards INTEGER,
    punt_average DECIMAL(5,2),
    punt_long INTEGER,
    punt_inside20 INTEGER,
    punt_touchbacks INTEGER,
    punts_had_blocked INTEGER,
    punt_net_average DECIMAL(5,2),
    punt_net_yards INTEGER,

    -- Miscellaneous
    blocked_kicks INTEGER,
    blocked_kick_return_yards INTEGER,
    blocked_kick_return_touchdowns INTEGER,
    field_goal_return_yards INTEGER,
    field_goal_return_touchdowns INTEGER,
    safeties INTEGER,
    safeties_allowed INTEGER,

    -- Two-point conversions
    two_point_conversion_passes INTEGER,
    two_point_conversion_runs INTEGER,
    two_point_conversion_receptions INTEGER,
    two_point_conversion_returns INTEGER,

    -- Snap counts
    offensive_snaps_played INTEGER,
    offensive_team_snaps INTEGER,
    defensive_snaps_played INTEGER,
    defensive_team_snaps INTEGER,
    special_teams_snaps_played INTEGER,
    special_teams_team_snaps INTEGER,
    snap_counts_confirmed BOOLEAN,

    -- Touchdowns
    offensive_touchdowns INTEGER,
    defensive_touchdowns INTEGER,
    special_teams_touchdowns INTEGER,
    touchdowns INTEGER,

    -- Fantasy points
    fantasy_points DECIMAL(5,2),
    fantasy_points_ppr DECIMAL(5,2),
    fantasy_points_fan_duel DECIMAL(5,2),
    fantasy_points_draft_kings DECIMAL(5,2),
    fantasy_points_yahoo DECIMAL(5,2),
    fantasy_points_fantasy_draft DECIMAL(5,2),

    -- Salaries
    fan_duel_salary INTEGER,
    draft_kings_salary INTEGER,
    fantasy_data_salary INTEGER,
    yahoo_salary INTEGER,
    fantasy_draft_salary INTEGER,

    -- Positions
    fan_duel_position VARCHAR(10),
    draft_kings_position VARCHAR(10),
    yahoo_position VARCHAR(10),
    fantasy_draft_position VARCHAR(10),

    -- Injuries
    injury_status VARCHAR(50),
    injury_body_part VARCHAR(50),
    injury_start_date DATE,
    injury_notes TEXT,
    injury_practice VARCHAR(50),
    injury_practice_description TEXT,

    -- Game environment
    stadium VARCHAR(100),
    playing_surface VARCHAR(50),
    temperature INTEGER,
    humidity INTEGER,
    wind_speed INTEGER,

    -- Opponent rankings
    opponent_rank INTEGER,
    opponent_position_rank INTEGER,

    -- Timestamps
    day DATE,
    date_time TIMESTAMP,
    game_date TIMESTAMP,
    updated TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Constraints
    CONSTRAINT uq_player_game UNIQUE (player_id, game_key)
);

-- Indexes
CREATE INDEX idx_player_game_stats_nfl_player_id ON sport.io_player_game_stats_nfl(player_id);
CREATE INDEX idx_player_game_stats_nfl_game_key ON sport.io_player_game_stats_nfl(game_key);
CREATE INDEX idx_player_game_stats_nfl_season ON sport.io_player_game_stats_nfl(season);
CREATE INDEX idx_player_game_stats_nfl_position ON sport.io_player_game_stats_nfl(position);
CREATE INDEX idx_player_game_stats_nfl_team ON sport.io_player_game_stats_nfl(team);



