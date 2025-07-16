SET search_path TO sport;

CREATE TABLE IF NOT EXISTS io_stadium_nfl (
    id BIGSERIAL PRIMARY KEY,
    stadium_id INT NOT NULL UNIQUE,
    name TEXT,
    city TEXT,
    state TEXT,
    country TEXT,
    capacity INT,
    playing_surface TEXT,
    geo_lat DOUBLE PRECISION,
    geo_long DOUBLE PRECISION,
    type TEXT
);

CREATE TABLE IF NOT EXISTS io_team_nfl (
    id BIGSERIAL PRIMARY KEY,

    team_id BIGINT NOT NULL,
    player_id BIGINT,
    key TEXT NOT NULL,
    city TEXT,
    name TEXT,
    full_name TEXT,
    conference TEXT,
    division TEXT,

    stadium_id INT,

    bye_week INT,
    head_coach TEXT,
    offensive_coordinator TEXT,
    defensive_coordinator TEXT,
    special_teams_coach TEXT,
    offensive_scheme TEXT,
    defensive_scheme TEXT,

    upcoming_salary DOUBLE PRECISION,
    upcoming_opponent TEXT,
    upcoming_opponent_rank INT,
    upcoming_opponent_position_rank INT,

    primary_color TEXT,
    secondary_color TEXT,
    tertiary_color TEXT,
    quaternary_color TEXT,

    wikipedia_logo_url TEXT,
    wikipedia_word_mark_url TEXT,

    global_team_id BIGINT,

    draft_kings_name TEXT,
    draft_kings_player_id BIGINT,
    fan_duel_name TEXT,
    fan_duel_player_id BIGINT,
    fantasy_draft_name TEXT,
    fantasy_draft_player_id BIGINT,
    yahoo_name TEXT,
    yahoo_player_id BIGINT,

    average_draft_position DOUBLE PRECISION,
    average_draft_position_ppr DOUBLE PRECISION,
    average_draft_position_2qb DOUBLE PRECISION,
    average_draft_position_dynasty DOUBLE PRECISION,

    CONSTRAINT fk_team_stadium FOREIGN KEY (stadium_id) REFERENCES io_stadium_nfl(stadium_id)
);
