SET search_path TO sport;

-- Table for NFL bets
CREATE TABLE IF NOT EXISTS sport.io_bet_nfl (
    id BIGSERIAL PRIMARY KEY,
    io_event_nfl_id BIGINT,
    market_id BIGINT,
    betting_market_type_id INTEGER,
    betting_market_type VARCHAR(255),
    betting_bet_type_id INTEGER,
    betting_bet_type VARCHAR(255),
    betting_period_type_id INTEGER,
    betting_period_type VARCHAR(255),
    name VARCHAR(255),
    team_id BIGINT,
    team_key VARCHAR(255),
    player_id BIGINT,
    player_name VARCHAR(255),
    any_bets_available BOOLEAN DEFAULT FALSE,
    is_archived BOOLEAN DEFAULT FALSE,
    archive_location VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_bet_event FOREIGN KEY (io_event_nfl_id) REFERENCES sport.io_event_nfl(id)
);

-- Table for NFL bet outcomes
CREATE TABLE IF NOT EXISTS sport.io_bet_outcome_nfl (
    id BIGSERIAL PRIMARY KEY,
    outcome_id BIGINT,
    io_bet_nfl_id BIGINT,
    name VARCHAR(255),
    odds INTEGER,
    handicap INTEGER,
    total DECIMAL(10,2),
    is_available BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_outcome_bet FOREIGN KEY (io_bet_nfl_id) REFERENCES sport.io_bet_nfl(id)
);

-- Create index for better performance on foreign key
CREATE INDEX IF NOT EXISTS idx_bet_outcome_bet_id ON sport.io_bet_outcome_nfl(io_bet_nfl_id);



