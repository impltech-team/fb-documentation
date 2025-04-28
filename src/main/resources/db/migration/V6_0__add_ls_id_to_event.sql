SET search_path TO sport;

ALTER TABLE event
    ADD COLUMN ls_id BIGINT UNIQUE;
