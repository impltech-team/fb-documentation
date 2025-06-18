SET search_path TO sport;

CREATE TABLE IF NOT EXISTS sport.io_team (
    id                    BIGSERIAL PRIMARY KEY,
    team_id               INT NOT NULL,
    key                   VARCHAR(10) NOT NULL,
    active                BOOLEAN NOT NULL,
    city                  VARCHAR(50),
    name                  VARCHAR(50),
    stadium_id            INT,
    league                VARCHAR(10),
    division              VARCHAR(20),
    primary_color         VARCHAR(10),
    secondary_color       VARCHAR(10),
    tertiary_color        VARCHAR(10),
    quaternary_color      VARCHAR(10),
    wikipedia_logo_url    VARCHAR(250),
    wikipedia_word_mark_url VARCHAR(250),
    global_team_id        BIGINT,
    head_coach            VARCHAR(50),
    hitting_coach         VARCHAR(100),
    pitching_coach        VARCHAR(50)
);

CREATE INDEX idx_io_team_team_id ON io_team (team_id);
