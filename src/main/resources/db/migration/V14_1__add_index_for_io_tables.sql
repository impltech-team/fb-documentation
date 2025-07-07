SET
search_path TO sport;

CREATE INDEX idx_io_bet_bet_type_id ON sport.io_bet (bet_type_id);

CREATE INDEX idx_io_bet_outcome_player_id ON sport.io_bet_outcome (player_id);

CREATE INDEX idx_io_player_team_id ON sport.io_player (team_id);
CREATE INDEX idx_io_player_upcoming_game_id ON sport.io_player (upcoming_game_id);

CREATE INDEX idx_io_event_game_id ON sport.io_event (game_id);
CREATE INDEX idx_io_event_home_team_id ON sport.io_event (home_team_id);
CREATE INDEX idx_io_event_away_team_id ON sport.io_event (away_team_id);
CREATE INDEX idx_io_event_datetime ON sport.io_event (datetime);