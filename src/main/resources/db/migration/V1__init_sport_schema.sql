-- Create the schema
CREATE SCHEMA IF NOT EXISTS sport;

-- Set search path
SET search_path TO sport;

-- Event table
CREATE TABLE IF NOT EXISTS sport.event (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    start_date TIMESTAMP,
    competition_id BIGINT,
    competition_name VARCHAR(255),
    opponent_short_code VARCHAR(50)
);

-- SubParticipant table
CREATE TABLE IF NOT EXISTS sport.sub_participant (
    id UUID PRIMARY KEY,
    player_name VARCHAR(255),
    team_name VARCHAR(255),
    competition_id BIGINT,
    participant_id BIGINT,
    position VARCHAR(100),
    shirt_number INT,
    avatar_url TEXT,
    area_id INT,
    area_name VARCHAR(255),
    gender VARCHAR(50),
    weight VARCHAR(50),
    height VARCHAR(50),
    birth_date DATE,
    next_event_id UUID,
    CONSTRAINT fk_next_event FOREIGN KEY (next_event_id) REFERENCES sport.event (id)
);

-- EventStat table
CREATE TABLE IF NOT EXISTS sport.event_stat (
    id UUID PRIMARY KEY,
    target_type VARCHAR(50),
    target_id BIGINT,
    stat_name VARCHAR(255),
    value INT,
    created_at TIMESTAMP,
    event_id UUID,
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES sport.event (id)
);
