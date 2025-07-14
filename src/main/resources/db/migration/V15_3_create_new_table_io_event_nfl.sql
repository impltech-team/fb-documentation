SET search_path TO sport;

CREATE TABLE sport.io_event_nfl (
    id BIGSERIAL PRIMARY KEY,
    global_game_id BIGINT,
    game_key VARCHAR(20),
    season INTEGER,
    season_type INTEGER,
    week INTEGER,
    status VARCHAR(20),
    day DATE,
    datetime TIMESTAMP,
    datetime_utc TIMESTAMP,
    away_team VARCHAR(10),
    home_team VARCHAR(10),
    away_score INTEGER,
    home_score INTEGER,
    away_team_id INTEGER,
    home_team_id INTEGER,
    global_away_team_id BIGINT,
    global_home_team_id BIGINT,
    stadium_id INTEGER,
    is_closed BOOLEAN,
    last_updated TIMESTAMP,
    game_end_datetime TIMESTAMP,
    neutral_venue BOOLEAN,
    quarter_description VARCHAR(50),
    attendance INTEGER,
    channel VARCHAR(10),
    point_spread NUMERIC(5,1),
    over_under NUMERIC(5,1),
    referee_id INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_game_key UNIQUE (game_key)
);

CREATE INDEX idx_event_nfl_datetime_utc ON sport.io_event_nfl(datetime_utc);
CREATE INDEX idx_event_nfl_teams ON sport.io_event_nfl(home_team_id, away_team_id);
CREATE INDEX idx_event_nfl_status ON sport.io_event_nfl(status);



