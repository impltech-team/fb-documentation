SET search_path TO sport;

ALTER TABLE IF EXISTS sub_participant
    ADD COLUMN IF NOT EXISTS player_short_name VARCHAR(255);
