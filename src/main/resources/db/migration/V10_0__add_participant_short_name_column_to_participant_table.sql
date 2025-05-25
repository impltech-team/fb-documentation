SET search_path TO sport;

ALTER TABLE IF EXISTS participant
    ADD COLUMN IF NOT EXISTS team_short_name VARCHAR(25);
