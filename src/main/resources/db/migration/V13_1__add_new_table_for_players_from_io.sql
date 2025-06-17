SET search_path TO sport;

CREATE TABLE io_player (
                           id BIGSERIAL PRIMARY KEY,

                           player_id BIGINT,
                           sports_data_id TEXT,
                           status TEXT,
                           team_id INT,
                           team VARCHAR(10),
                           jersey INT,
                           position_category VARCHAR(5),
                           position VARCHAR(5),
                           mlbam_id BIGINT,
                           first_name TEXT,
                           last_name TEXT,
                           bat_hand VARCHAR(1),
                           throw_hand VARCHAR(1),
                           height INT,
                           weight INT,
                           birth_date DATE,
                           birth_city TEXT,
                           birth_state TEXT,
                           birth_country TEXT,
                           high_school TEXT,
                           college TEXT,
                           pro_debut DATE,
                           salary TEXT,

                           photo_url TEXT,

                           sport_radar_player_id TEXT,
                           rotoworld_player_id TEXT,
                           roto_wire_player_id TEXT,
                           fantasy_alarm_player_id TEXT,
                           stats_player_id TEXT,
                           sports_direct_player_id TEXT,
                           xml_team_player_id TEXT,

                           injury_status TEXT,
                           injury_body_part TEXT,
                           injury_start_date DATE,
                           injury_notes TEXT,

                           fan_duel_player_id TEXT,
                           draft_kings_player_id TEXT,
                           yahoo_player_id TEXT,

                           upcoming_game_id TEXT,

                           fan_duel_name TEXT,
                           draft_kings_name TEXT,
                           yahoo_name TEXT,
                           global_team_id BIGINT,
                           fantasy_draft_name TEXT,
                           fantasy_draft_player_id TEXT,
                           experience TEXT,

                           usa_today_player_id TEXT,
                           usa_today_headshot_url TEXT,
                           usa_today_headshot_no_background_url TEXT,
                           usa_today_headshot_updated TIMESTAMP,
                           usa_today_headshot_no_background_updated TIMESTAMP
);


