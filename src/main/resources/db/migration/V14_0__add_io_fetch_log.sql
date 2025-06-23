CREATE TABLE sport.io_fetch_log (
                                    id           BIGSERIAL PRIMARY KEY,
                                    fetch_type   VARCHAR(32)  NOT NULL,
                                    sport_type   VARCHAR(16)  NOT NULL,
                                    status       VARCHAR(16)  NOT NULL,
                                    started_at   TIMESTAMP    NOT NULL,
                                    finished_at  TIMESTAMP,
                                    duration_ms  BIGINT,
                                    details      TEXT
);

CREATE INDEX idx_fetch_log_type_time
    ON sport.io_fetch_log (sport_type, fetch_type, started_at DESC);
