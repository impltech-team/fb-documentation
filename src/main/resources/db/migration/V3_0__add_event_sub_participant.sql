SET search_path TO sport;

ALTER TABLE sub_participant DROP CONSTRAINT IF EXISTS fk_sub_participant_participant;
ALTER TABLE sub_participant DROP COLUMN IF EXISTS participant_id;

DROP TABLE IF EXISTS event_participant;

CREATE SEQUENCE event_sub_participant_id_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE event_sub_participant (
id BIGSERIAL PRIMARY KEY,
event_id BIGINT NOT NULL,
participant_id BIGINT NOT NULL,
sub_participant_id BIGINT NOT NULL,
position_reason VARCHAR(255),
position VARCHAR(255),
is_home_team BOOLEAN,

CONSTRAINT fk_event_sub_participant_event
   FOREIGN KEY (event_id)
       REFERENCES event(id)
       ON DELETE CASCADE,

CONSTRAINT fk_event_sub_participant_participant
   FOREIGN KEY (participant_id)
       REFERENCES participant(id)
       ON DELETE CASCADE,

CONSTRAINT fk_event_sub_participant_sub_participant
   FOREIGN KEY (sub_participant_id)
       REFERENCES sub_participant(id)
       ON DELETE CASCADE
);

CREATE INDEX idx_event_sub_participant_event_id ON event_sub_participant(event_id);
CREATE INDEX idx_event_sub_participant_participant_id ON event_sub_participant(participant_id);
CREATE INDEX idx_event_sub_participant_sub_participant_id ON event_sub_participant(sub_participant_id);
