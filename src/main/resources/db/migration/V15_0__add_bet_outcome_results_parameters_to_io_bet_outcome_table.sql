SET search_path TO sport;

ALTER TABLE IF EXISTS io_bet_outcome
    ADD COLUMN IF NOT EXISTS result_type_id INTEGER,
    ADD COLUMN IF NOT EXISTS result_type VARCHAR(125),
    ADD COLUMN IF NOT EXISTS result_value VARCHAR(50);