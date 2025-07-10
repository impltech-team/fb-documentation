SET search_path TO sport;

DROP TABLE IF EXISTS io_bet CASCADE;
CREATE TABLE IF NOT EXISTS sport.io_bet (
    id BIGSERIAL PRIMARY KEY,
    io_event_id BIGINT,
    market_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    market_type_id INT,
    market_type VARCHAR(250),
    bet_type_id INT,
    bet_type VARCHAR(250),
    period_type_id INT,
    period_type VARCHAR(250),
    name VARCHAR(250),
    team_id BIGINT,
    team_key VARCHAR(3),
    player_id BIGINT,
    player_name VARCHAR(250),
    any_bets_available BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_event_id FOREIGN KEY (io_event_id) REFERENCES io_event(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX idx_io_bet_io_event_id ON io_bet (io_event_id);
CREATE INDEX idx_io_bet_io_player_id ON io_bet (player_id);
