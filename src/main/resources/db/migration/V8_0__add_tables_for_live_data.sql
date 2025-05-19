SET search_path TO sport;

CREATE TABLE live_event (
                            id BIGINT PRIMARY KEY,
                            uuid UUID NOT NULL,
                            type TEXT,
                            source INT,
                            ut BIGINT,
                            event_data_id BIGINT,
                            ls_id BIGINT,
                            action TEXT,
                            start_date TIMESTAMP,
                            ft_only BOOLEAN,
                            coverage_type TEXT,
                            status_id INT,
                            status_name TEXT,
                            sport_id INT,
                            sport_name TEXT,
                            day INT,
                            neutral_venue BOOLEAN,
                            item_status TEXT,
                            clock_time TEXT,
                            clock_status TEXT,
                            area_id INT,
                            area_name TEXT,
                            competition_id INT,
                            competition_name TEXT,
                            season_id INT,
                            stage_id INT,
                            stage_name TEXT,
                            group_id INT,
                            tour_id INT,
                            tour_name TEXT,
                            gender TEXT,
                            bet_status TEXT,
                            bet_cards TEXT,
                            bet_corners TEXT,
                            relation_status TEXT,
                            status_type TEXT,
                            name TEXT,
                            round_id INT,
                            round_name TEXT,
                            scoutsfeed BOOLEAN,
                            latency TEXT,
                            event_stats_lvl TEXT,
                            event_stats_live TEXT,
                            event_stats_after TEXT,
                            verified_result BOOLEAN,
                            is_stats_verified BOOLEAN,
                            is_coverage_limited BOOLEAN,
                            played_time TEXT,
                            referee_id INT,
                            participants_id_stats_modified TEXT
);

CREATE INDEX idx_live_event_sport ON live_event(sport_id);
CREATE INDEX idx_live_event_start_date ON live_event(start_date);

CREATE TABLE live_event_detail (
                                   id SERIAL PRIMARY KEY,
                                   event_id BIGINT NOT NULL REFERENCES live_event(id) ON DELETE CASCADE,
                                   detail_id INT NOT NULL,
                                   value TEXT
);

CREATE INDEX idx_live_event_detail_event ON live_event_detail(event_id);

CREATE TABLE live_event_bet_status (
                                       id SERIAL PRIMARY KEY,
                                       event_id BIGINT NOT NULL REFERENCES live_event(id) ON DELETE CASCADE,
                                       name TEXT NOT NULL,
                                       value TEXT
);

CREATE INDEX idx_live_event_bet_status_event ON live_event_bet_status(event_id);

CREATE TABLE live_participant (
                                  id BIGINT PRIMARY KEY,
                                  event_id BIGINT NOT NULL REFERENCES live_event(id) ON DELETE CASCADE,
                                  counter INT,
                                  name TEXT,
                                  short_name TEXT,
                                  acronym TEXT,
                                  area_id INT,
                                  area_name TEXT,
                                  area_code TEXT,
                                  ut BIGINT,
                                  type TEXT,
                                  lineups_copied BOOLEAN,
                                  stats TEXT,
                                  event_status_stats TEXT,
                                  subparticipants TEXT
);

CREATE INDEX idx_live_participant_event ON live_participant(event_id);
CREATE INDEX idx_live_participant_area ON live_participant(area_id);

CREATE TABLE live_participant_result (
                                         id SERIAL PRIMARY KEY,
                                         participant_id BIGINT NOT NULL REFERENCES live_participant(id) ON DELETE CASCADE,
                                         result_id INT NOT NULL,
                                         value TEXT
);

CREATE INDEX idx_live_result_participant ON live_participant_result(participant_id);
