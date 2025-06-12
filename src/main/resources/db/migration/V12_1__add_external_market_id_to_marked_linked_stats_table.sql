SET search_path TO sport;

ALTER TABLE market_linked_stats
    DROP CONSTRAINT IF EXISTS fk_market_linked_stats_market;

UPDATE market_linked_stats
SET market_id = m.external_id
    FROM market m
WHERE market_linked_stats.market_id = m.id;