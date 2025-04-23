SET search_path TO sport;

ALTER TABLE event
    ADD COLUMN status VARCHAR(20);

UPDATE event
SET status = CASE
                 WHEN start_date > NOW() THEN 'scheduled'
                 ELSE 'finished'
    END;

ALTER TABLE event
    ALTER COLUMN status SET NOT NULL;
