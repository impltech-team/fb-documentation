SET search_path TO sport;

ALTER TABLE IF EXISTS competition
    ADD COLUMN IF NOT EXISTS ls_id BIGINT UNIQUE;
