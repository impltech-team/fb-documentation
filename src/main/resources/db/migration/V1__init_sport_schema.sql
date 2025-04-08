-- Create the schema
CREATE SCHEMA IF NOT EXISTS sport;

-- Set search path
SET search_path TO sport;

-- TABLES
-- AREA
CREATE SEQUENCE area_id_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE area (
    id BIGINT PRIMARY KEY DEFAULT nextval('area_id_seq'),
    external_id INT UNIQUE,
    name VARCHAR(255),
    area_code VARCHAR(255),
    parent_area_id INT
);

-- SPORT
CREATE SEQUENCE sport_id_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE sport (
    id BIGINT PRIMARY KEY DEFAULT nextval('sport_id_seq'),
    external_id INT UNIQUE,
    name VARCHAR(255)
);

-- COMPETITION
CREATE SEQUENCE competition_id_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE competition (
    id BIGINT PRIMARY KEY DEFAULT nextval('competition_id_seq'),
    external_id INT UNIQUE,
    name VARCHAR(255),
    type VARCHAR(255),
    sport_id BIGINT,
    area_id BIGINT,
    status_type VARCHAR(255),
    gender VARCHAR(255),
    CONSTRAINT fk_competition_sport FOREIGN KEY (sport_id) REFERENCES sport(id),
    CONSTRAINT fk_competition_area FOREIGN KEY (area_id) REFERENCES area(id)
);

-- VENUE
CREATE SEQUENCE venue_id_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE venue (
    id BIGINT PRIMARY KEY DEFAULT nextval('venue_id_seq'),
    external_id INT UNIQUE,
    name VARCHAR(255),
    short_name VARCHAR(255),
    country VARCHAR(255),
    status VARCHAR(255),
    url VARCHAR(255),
    city VARCHAR(255),
    lat DOUBLE PRECISION,
    lng DOUBLE PRECISION,
    opened DATE
);

-- EVENT
CREATE SEQUENCE event_id_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE event (
    id BIGINT PRIMARY KEY DEFAULT nextval('event_id_seq'),
    external_id INT UNIQUE,
    name VARCHAR(255),
    start_date TIMESTAMP,
    competition_id BIGINT,
    venue_id BIGINT,
    CONSTRAINT fk_event_competition FOREIGN KEY (competition_id) REFERENCES competition(id),
    CONSTRAINT fk_event_venue FOREIGN KEY (venue_id) REFERENCES venue(id)
);

-- PARTICIPANT
CREATE SEQUENCE participant_id_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE participant (
    id BIGINT PRIMARY KEY DEFAULT nextval('participant_id_seq'),
    external_id INT UNIQUE,
    team_name VARCHAR(255),
    acronym VARCHAR(255),
    competition_id BIGINT,
    type VARCHAR(255),
    CONSTRAINT fk_participant_competition FOREIGN KEY (competition_id) REFERENCES competition(id)
);

-- SUB_PARTICIPANT
CREATE SEQUENCE sub_participant_id_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE sub_participant (
    id BIGINT PRIMARY KEY DEFAULT nextval('sub_participant_id_seq'),
    external_id INT UNIQUE,
    player_name VARCHAR(255),
    position VARCHAR(255),
    shirt_number INT,
    avatar_url VARCHAR(255),
    gender VARCHAR(255),
    weight VARCHAR(255),
    height VARCHAR(255),
    birth_date DATE,
    competition_id BIGINT,
    area_id BIGINT,
    participant_id BIGINT,
    CONSTRAINT fk_sub_participant_competition FOREIGN KEY (competition_id) REFERENCES competition(id),
    CONSTRAINT fk_sub_participant_area FOREIGN KEY (area_id) REFERENCES area(id),
    CONSTRAINT fk_sub_participant_participant FOREIGN KEY (participant_id) REFERENCES participant(id)
);

-- EVENT_PARTICIPANT (Many-to-Many)
CREATE TABLE event_participant (
    event_id BIGINT,
    participant_id BIGINT,
    PRIMARY KEY (event_id, participant_id),
    CONSTRAINT fk_event_participant_event FOREIGN KEY (event_id) REFERENCES event(id),
    CONSTRAINT fk_event_participant_participant FOREIGN KEY (participant_id) REFERENCES participant(id)
);

-- EVENT_STAT
CREATE SEQUENCE event_stat_id_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE event_stat (
    id BIGINT PRIMARY KEY DEFAULT nextval('event_stat_id_seq'),
    external_id INT,
    target_type VARCHAR(255),
    target_id BIGINT,
    target_external_id INT,
    stat_name VARCHAR(255),
    value_raw VARCHAR(255),
    value_numeric DOUBLE PRECISION,
    data_type VARCHAR(255),
    created_at TIMESTAMP,
    event_id BIGINT,
    CONSTRAINT fk_event_stat_event FOREIGN KEY (event_id) REFERENCES event(id)
);

-- INDEXES
-- EVENT_STAT
CREATE INDEX idx_event_stat_target_event ON event_stat (target_id, event_id);
CREATE INDEX idx_event_stat_target_external_id ON event_stat (target_external_id);
CREATE INDEX idx_event_stat_sub_appearance ON event_stat (stat_name, value_raw, target_type);
CREATE INDEX idx_event_stat_target_event_name ON event_stat (target_id, event_id, stat_name);

-- EVENT
CREATE INDEX idx_event_start_date_desc ON event (start_date DESC);
CREATE INDEX idx_event_external_id ON event (external_id);

-- PARTICIPANT
CREATE INDEX idx_participant_external_id ON participant (external_id);
CREATE INDEX idx_participant_competition_id ON participant (competition_id);

-- SUB_PARTICIPANT
CREATE INDEX idx_sub_participant_external_id ON sub_participant (external_id);
CREATE INDEX idx_sub_participant_participant_id ON sub_participant (participant_id);
CREATE INDEX idx_sub_participant_competition_id ON sub_participant (competition_id);
CREATE INDEX idx_sub_participant_area_id ON sub_participant (area_id);

-- COMPETITION
CREATE INDEX idx_competition_external_id ON competition (external_id);
CREATE INDEX idx_competition_sport_id ON competition (sport_id);
CREATE INDEX idx_competition_area_id ON competition (area_id);

-- VENUE
CREATE INDEX idx_venue_external_id ON venue (external_id);

-- AREA
CREATE INDEX idx_area_external_id ON area (external_id);
CREATE INDEX idx_area_parent_id ON area (parent_area_id);

-- SPORT
CREATE INDEX idx_sport_external_id ON sport (external_id);

-- EVENT_PARTICIPANT
CREATE INDEX idx_event_participant_participant_id ON event_participant(participant_id);
