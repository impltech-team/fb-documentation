SET search_path TO sport;

ALTER TABLE sport.event
    ADD COLUMN season_external_id INTEGER;

ALTER TABLE sport.event
    ADD COLUMN season_name VARCHAR(255);

UPDATE sport.event
SET season_external_id = 62587,
    season_name = 'NBA 2024/25';
