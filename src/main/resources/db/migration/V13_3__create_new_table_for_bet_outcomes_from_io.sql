SET search_path TO sport;

DROP TABLE IF EXISTS io_bet_outcome;
CREATE TABLE IF NOT EXISTS sport.io_bet_outcome(
    id BIGSERIAL PRIMARY KEY,
    io_bet_id BIGINT NOT NULL,
    outcome_id BIGINT,
    outcome_type_id INT,
    outcome_type VARCHAR(100),
    payout_american VARCHAR(15),
    payout_decimal VARCHAR(50),
    participant VARCHAR(250),
    value VARCHAR(50),
    team_id BIGINT,
    player_id BIGINT,
    is_available BOOLEAN,
    is_alternate BOOLEAN,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_bet_outcome_id FOREIGN KEY (io_bet_id) REFERENCES io_bet(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX idx_io_bet_outcome_io_bet_id ON io_bet_outcome (io_bet_id);
