SET search_path TO sport;

DROP TABLE IF EXISTS io_event CASCADE;
CREATE TABLE io_event (
                          id BIGSERIAL PRIMARY KEY,
                          global_game_id BIGINT,
                          game_id BIGINT,
                          season INT,
                          season_type INT,
                          status VARCHAR(20),
                          day DATE,
                          datetime TIMESTAMP,
                          datetime_utc TIMESTAMP,

                          away_team VARCHAR(10),
                          home_team VARCHAR(10),
                          away_team_id INT,
                          home_team_id INT,
                          global_away_team_id BIGINT,
                          global_home_team_id BIGINT,

                          stadium_id INT,
                          is_closed BOOLEAN,
                          updated TIMESTAMP,

                          away_team_runs INT,
                          home_team_runs INT,
                          away_team_hits INT,
                          home_team_hits INT,
                          away_team_errors INT,
                          home_team_errors INT,

                          game_end_datetime TIMESTAMP,
                          neutral_venue BOOLEAN,

                          attendance TEXT,
                          inning TEXT,
                          inning_half TEXT,
                          rescheduled_game_id TEXT,
                          rescheduled_from_game_id TEXT,
                          suspension_resume_day TEXT,
                          suspension_resume_datetime TEXT,
                          series_info TEXT
);

