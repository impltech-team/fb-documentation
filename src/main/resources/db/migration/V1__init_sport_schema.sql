-- Create the schema
CREATE SCHEMA IF NOT EXISTS sport;

-- Set search path
SET search_path TO sport;

-- Venue table
CREATE TABLE IF NOT EXISTS sport.venue (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    short_name VARCHAR(100),
    country VARCHAR(100),
    status VARCHAR(50),
    url TEXT,
    city VARCHAR(100),
    lat DOUBLE PRECISION,
    lng DOUBLE PRECISION,
    opened DATE
);

-- Area table
CREATE TABLE IF NOT EXISTS sport.area (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    area_code VARCHAR(10),
    parent_area_id BIGINT
);

-- Sport table
CREATE TABLE IF NOT EXISTS sport.sport (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255)
);

-- Competition table
CREATE TABLE IF NOT EXISTS sport.competition (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    type VARCHAR(50),
    sport_id BIGINT,
    area_id BIGINT,
    status_type VARCHAR(50),
    gender VARCHAR(50),
    active BOOLEAN DEFAULT true,
    CONSTRAINT fk_sport FOREIGN KEY (sport_id) REFERENCES sport.sport (id),
    CONSTRAINT fk_area FOREIGN KEY (area_id) REFERENCES sport.area (id)
);

-- Event table
CREATE TABLE IF NOT EXISTS sport.event (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    start_date TIMESTAMP,
    competition_id BIGINT,
    venue_id BIGINT,
    CONSTRAINT fk_event_competition FOREIGN KEY (competition_id) REFERENCES sport.competition (id),
    CONSTRAINT fk_event_venue FOREIGN KEY (venue_id) REFERENCES sport.venue (id)
);

-- Participant table
CREATE TABLE IF NOT EXISTS sport.participant (
    id BIGINT PRIMARY KEY,
    team_name VARCHAR(255),
    acronym VARCHAR(10),
    competition_id BIGINT,
    type VARCHAR(255),
    CONSTRAINT fk_participant_competition FOREIGN KEY (competition_id) REFERENCES sport.competition (id)
);

-- Join table: event_participant
CREATE TABLE IF NOT EXISTS sport.event_participant (
    event_id BIGINT NOT NULL,
    participant_id BIGINT NOT NULL,
    PRIMARY KEY (event_id, participant_id),
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES sport.event (id),
    CONSTRAINT fk_participant FOREIGN KEY (participant_id) REFERENCES sport.participant (id)
);

-- SubParticipant table
CREATE TABLE IF NOT EXISTS sport.sub_participant (
    id BIGINT PRIMARY KEY,
    player_name VARCHAR(255),
    position VARCHAR(100),
    shirt_number INT,
    avatar_url TEXT,
    gender VARCHAR(50),
    weight VARCHAR(50),
    height VARCHAR(50),
    birth_date DATE,
    competition_id BIGINT,
    area_id BIGINT,
    participant_id BIGINT,
    CONSTRAINT fk_sub_participant_competition FOREIGN KEY (competition_id) REFERENCES sport.competition (id),
    CONSTRAINT fk_sub_participant_area FOREIGN KEY (area_id) REFERENCES sport.area (id),
    CONSTRAINT fk_sub_participant_participant FOREIGN KEY (participant_id) REFERENCES sport.participant (id)
);

-- EventStat table
CREATE TABLE IF NOT EXISTS sport.event_stat (
    id BIGINT PRIMARY KEY,
    target_type VARCHAR(50),
    target_id BIGINT,
    stat_name VARCHAR(255),
    value INT,
    created_at TIMESTAMP,
    event_id BIGINT,
    CONSTRAINT fk_event_stat_event FOREIGN KEY (event_id) REFERENCES sport.event (id)
);
