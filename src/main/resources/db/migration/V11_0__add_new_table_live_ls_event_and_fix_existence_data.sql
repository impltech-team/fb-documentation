SET search_path TO sport;

CREATE TABLE live_ls_event (
                               id BIGSERIAL PRIMARY KEY,
                               fixture_id BIGINT NOT NULL,
                               received_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_ls_event_fixture_id ON live_ls_event (fixture_id);

ALTER TABLE live_ls_scoreboard
    ADD COLUMN IF NOT EXISTS event_id BIGINT,
    ADD CONSTRAINT fk_scoreboard_event_id FOREIGN KEY (event_id) REFERENCES live_ls_event(id);

ALTER TABLE live_ls_period
    ADD COLUMN IF NOT EXISTS event_id BIGINT,
    ADD CONSTRAINT fk_period_event_id FOREIGN KEY (event_id) REFERENCES live_ls_event(id);

ALTER TABLE live_ls_statistic
    ADD COLUMN IF NOT EXISTS event_id BIGINT,
    ADD CONSTRAINT fk_stat_event_id FOREIGN KEY (event_id) REFERENCES live_ls_event(id);

ALTER TABLE live_ls_market
    ADD COLUMN IF NOT EXISTS event_id BIGINT,
    ADD CONSTRAINT fk_market_event_id FOREIGN KEY (event_id) REFERENCES live_ls_event(id);

ALTER TABLE live_ls_fixture_extra_data
    ADD COLUMN IF NOT EXISTS event_id BIGINT,
    ADD CONSTRAINT fk_extra_data_event_id FOREIGN KEY (event_id) REFERENCES live_ls_event(id);

ALTER TABLE live_ls_participant
    ADD COLUMN IF NOT EXISTS event_id BIGINT,
    ADD CONSTRAINT fk_participant_event_id FOREIGN KEY (event_id) REFERENCES live_ls_event(id);

INSERT INTO live_ls_event (fixture_id)
SELECT DISTINCT fixture_id
FROM (
         SELECT fixture_id FROM live_ls_scoreboard
         UNION
         SELECT fixture_id FROM live_ls_period
         UNION
         SELECT fixture_id FROM live_ls_statistic
         UNION
         SELECT fixture_id FROM live_ls_participant
         UNION
         SELECT fixture_id FROM live_ls_market
         UNION
         SELECT fixture_id FROM live_ls_fixture_extra_data
     ) AS all_fixtures;

CREATE TEMP TABLE tmp_fixture_event (
    fixture_id BIGINT PRIMARY KEY,
    event_id BIGINT
) ON COMMIT DROP;

INSERT INTO tmp_fixture_event (fixture_id, event_id)
SELECT DISTINCT ON (fixture_id) fixture_id, id
FROM live_ls_event
ORDER BY fixture_id, id DESC;

UPDATE live_ls_scoreboard sb
SET event_id = t.event_id
    FROM tmp_fixture_event t
WHERE sb.fixture_id = t.fixture_id;

UPDATE live_ls_period p
SET event_id = t.event_id
    FROM tmp_fixture_event t
WHERE p.fixture_id = t.fixture_id;

UPDATE live_ls_statistic s
SET event_id = t.event_id
    FROM tmp_fixture_event t
WHERE s.fixture_id = t.fixture_id;

UPDATE live_ls_participant pa
SET event_id = t.event_id
    FROM tmp_fixture_event t
WHERE pa.fixture_id = t.fixture_id;

UPDATE live_ls_market m
SET event_id = t.event_id
    FROM tmp_fixture_event t
WHERE m.fixture_id = t.fixture_id;

UPDATE live_ls_fixture_extra_data f
SET event_id = t.event_id
    FROM tmp_fixture_event t
WHERE f.fixture_id = t.fixture_id;
