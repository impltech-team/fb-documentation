SET
search_path TO sport;

DELETE
FROM io_team a USING io_team b
WHERE a.ctid
    < b.ctid
  AND a.team_id = b.team_id;

ALTER TABLE io_team
    ADD CONSTRAINT uq_io_team_team_id UNIQUE (team_id);

DELETE
FROM io_event a USING io_event b
WHERE a.ctid
    < b.ctid
  AND a.global_game_id IS NOT NULL
  AND a.global_game_id = b.global_game_id;

ALTER TABLE io_event
    ADD CONSTRAINT uq_io_event_global_game_id UNIQUE (global_game_id);

DELETE
FROM io_player a USING io_player b
WHERE a.ctid
    < b.ctid
  AND a.player_id IS NOT NULL
  AND a.player_id = b.player_id;

ALTER TABLE io_player
    ADD CONSTRAINT uq_io_player_player_id UNIQUE (player_id);

CREATE UNIQUE INDEX uq_idx_io_player_player_id ON io_player (player_id);
DELETE
FROM io_player_game_stats a USING  io_player_game_stats b
WHERE a.ctid
    < b.ctid
  AND a.game_id = b.game_id
  AND a.player_id = b.player_id
  AND a.game_id IS NOT NULL
  AND a.player_id IS NOT NULL;
ALTER TABLE io_player_game_stats
    ADD CONSTRAINT uq_io_pgs_game_player UNIQUE (game_id, player_id);
