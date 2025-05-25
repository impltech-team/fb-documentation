SET search_path TO sport;

ALTER TABLE IF EXISTS bet
    DROP CONSTRAINT IF EXISTS bet_external_id_key;

ALTER TABLE IF EXISTS bet
    ADD CONSTRAINT bet_external_id_market_id_event_id_unique_key UNIQUE(external_id, market_id, event_id);

