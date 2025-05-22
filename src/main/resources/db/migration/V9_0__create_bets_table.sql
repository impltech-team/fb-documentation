SET search_path TO sport;

CREATE SEQUENCE bet_id_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS sport.bet (
    id BIGINT PRIMARY KEY DEFAULT nextval('bet_id_seq'),
    external_id BIGINT UNIQUE,
    market_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    name VARCHAR NOT NULL,
    line VARCHAR(100) NOT NULL,
    baseline VARCHAR(100) NOT NULL,
    price VARCHAR(15) NOT NULL,
    participant_name VARCHAR(250),
    settlement VARCHAR(20),
    suspension_reason VARCHAR(20),
    last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_market_id FOREIGN KEY (market_id) REFERENCES market(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX idx_bet_market_id ON bet (market_id);
CREATE INDEX idx_bet_event_id ON bet (event_id);
