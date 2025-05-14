SET search_path TO sport;

CREATE TABLE IF NOT EXISTS sport.prefetch_log (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    prefetch_date DATE NOT NULL,
    competition_id BIGINT NOT NULL,
    status VARCHAR(10) NOT NULL DEFAULT 'PENDING',
    last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    error_message TEXT,
    CONSTRAINT uq_prefetch_log_date_competition UNIQUE (prefetch_date, competition_id)
);

