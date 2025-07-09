SET
search_path TO sport;

CREATE TABLE sport.io_venue
(
    stadium_id             INTEGER PRIMARY KEY,
    active                 BOOLEAN,
    name                   VARCHAR(255),
    city                   VARCHAR(255),
    state                  VARCHAR(255),
    country                VARCHAR(255),
    capacity               INTEGER,
    surface                VARCHAR(50),
    left_field             INTEGER,
    mid_left_field         INTEGER,
    left_center_field      INTEGER,
    mid_left_center_field  INTEGER,
    center_field           INTEGER,
    mid_right_center_field INTEGER,
    right_center_field     INTEGER,
    mid_right_field        INTEGER,
    right_field            INTEGER,
    geo_lat                DOUBLE PRECISION,
    geo_long               DOUBLE PRECISION,
    altitude               INTEGER,
    home_plate_direction   INTEGER,
    type                   VARCHAR(50)
);