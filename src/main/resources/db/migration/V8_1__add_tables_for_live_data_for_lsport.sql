SET search_path TO sport;

-- 🔄 Очистка таблиць (якщо існують)
DROP TABLE IF EXISTS live_ls_bet CASCADE;
DROP TABLE IF EXISTS live_ls_market CASCADE;
DROP TABLE IF EXISTS live_ls_participant CASCADE;
DROP TABLE IF EXISTS live_ls_statistic CASCADE;
DROP TABLE IF EXISTS live_ls_period_incident CASCADE;
DROP TABLE IF EXISTS live_ls_period CASCADE;
DROP TABLE IF EXISTS live_ls_scoreboard CASCADE;
DROP TABLE IF EXISTS live_ls_fixture_extra_data CASCADE;

-- 📊 Scoreboard (Type 1)
CREATE TABLE live_ls_scoreboard (
                                    id BIGSERIAL PRIMARY KEY,
                                    fixture_id BIGINT,
                                    ls_scoreboard_status INTEGER,
                                    ls_scoreboard_current_period INTEGER,
                                    ls_scoreboard_time TEXT,
                                    ls_scoreboard_result_position_1 INTEGER,
                                    ls_scoreboard_result_position_2 INTEGER
);
CREATE INDEX idx_ls_scoreboard_fixture_id ON live_ls_scoreboard (fixture_id);

-- ⏱ Period (Type 1)
CREATE TABLE live_ls_period (
                                id BIGSERIAL PRIMARY KEY,
                                fixture_id BIGINT,
                                ls_period_type INTEGER,
                                ls_period_is_finished BOOLEAN,
                                ls_period_is_confirmed BOOLEAN,
                                ls_period_sequence_number INTEGER,
                                ls_period_result_position_1 INTEGER,
                                ls_period_result_position_2 INTEGER
);
CREATE INDEX idx_ls_period_fixture_id ON live_ls_period (fixture_id);

-- 🚨 Period incidents (Type 1)
CREATE TABLE live_ls_period_incident (
                                         id BIGSERIAL PRIMARY KEY,
                                         period_id BIGINT,
                                         ls_incident_period INTEGER,
                                         ls_incident_type INTEGER,
                                         ls_incident_seconds INTEGER,
                                         ls_incident_participant_position TEXT,
                                         ls_incident_result_position_1 INTEGER,
                                         ls_incident_result_position_2 INTEGER,
                                         CONSTRAINT fk_incident_period_id FOREIGN KEY (period_id) REFERENCES live_ls_period(id)
);
CREATE INDEX idx_ls_incident_period_id ON live_ls_period_incident (period_id);

-- 📈 Statistics (Type 1)
CREATE TABLE live_ls_statistic (
                                   id BIGSERIAL PRIMARY KEY,
                                   fixture_id BIGINT,
                                   ls_stat_type INTEGER,
                                   ls_stat_result_position_1 INTEGER,
                                   ls_stat_result_position_2 INTEGER
);
CREATE INDEX idx_ls_stat_fixture_id ON live_ls_statistic (fixture_id);

-- 👥 Participants (Type 1)
CREATE TABLE live_ls_participant (
                                     id BIGINT PRIMARY KEY,
                                     fixture_id BIGINT,
                                     ls_participant_name TEXT,
                                     ls_participant_position INTEGER
);
CREATE INDEX idx_ls_participant_fixture_id ON live_ls_participant (fixture_id);

-- 📦 Market (Type 2)
CREATE TABLE live_ls_market (
                                id BIGSERIAL PRIMARY KEY,
                                fixture_id BIGINT,
                                market_id BIGINT,
                                market_name TEXT,
                                market_main_line TEXT
);
CREATE INDEX idx_ls_market_fixture_id ON live_ls_market (fixture_id);

-- 🎯 Bet (Type 3)
CREATE TABLE live_ls_bet (
                             id BIGSERIAL PRIMARY KEY,
                             market_id BIGINT,
                             bet_id BIGINT,
                             bet_name TEXT,
                             bet_probability DOUBLE PRECISION,
                             bet_suspension_reason TEXT,
                             bet_calculated_margin TEXT,
                             bet_serialized_calculated_margin TEXT,
                             bet_line TEXT,
                             bet_base_line TEXT,
                             bet_status INTEGER,
                             bet_start_price TEXT,
                             bet_price TEXT,
                             bet_provider_id TEXT,
                             bet_last_update TIMESTAMP,
                             bet_serialized_last_update TEXT,
                             CONSTRAINT fk_bet_market_id FOREIGN KEY (market_id) REFERENCES live_ls_market(id)
);
CREATE INDEX idx_ls_bet_market_id ON live_ls_bet (market_id);

-- 📝 Fixture extra data (Type 1)
CREATE TABLE live_ls_fixture_extra_data (
                                            id BIGSERIAL PRIMARY KEY,
                                            fixture_id BIGINT,
                                            name TEXT,
                                            value TEXT
);
CREATE INDEX idx_ls_fixture_extra_fixture_id ON live_ls_fixture_extra_data (fixture_id);
