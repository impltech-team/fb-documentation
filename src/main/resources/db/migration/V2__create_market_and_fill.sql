SET search_path TO sport;

CREATE SEQUENCE market_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE market (
    id BIGINT PRIMARY KEY DEFAULT nextval('market_id_seq'),
    external_id INT UNIQUE,
    market_name TEXT NOT NULL,
    market_type VARCHAR(32) NOT NULL,
    competition_id BIGINT,
    CONSTRAINT fk_market_competition FOREIGN KEY (competition_id) REFERENCES competition(id)
);


CREATE TABLE market_linked_stats (
    market_id BIGINT NOT NULL,
    stat_name TEXT NOT NULL,
    CONSTRAINT fk_market_linked_stats_market FOREIGN KEY (market_id) REFERENCES market(id) ON DELETE CASCADE
);

INSERT INTO market (competition_id, external_id, market_type, market_name)
VALUES
((SELECT id FROM competition WHERE external_id = 101) ,1069, 'SUB_PARTICIPANT', 'Under/Over Player Points'),
((SELECT id FROM competition WHERE external_id = 101) ,1070, 'SUB_PARTICIPANT', 'Under/Over Player Blocks'),
((SELECT id FROM competition WHERE external_id = 101) ,1071, 'SUB_PARTICIPANT', 'Under/Over Player Assists'),
((SELECT id FROM competition WHERE external_id = 101) ,1072, 'SUB_PARTICIPANT', 'Under/Over Player Rebounds'),
((SELECT id FROM competition WHERE external_id = 101) ,1073, 'SUB_PARTICIPANT', 'Under/Over Player Steals'),
((SELECT id FROM competition WHERE external_id = 101) ,1074, 'SUB_PARTICIPANT', 'Under/Over Player Turnovers'),
((SELECT id FROM competition WHERE external_id = 101) ,1075, 'SUB_PARTICIPANT', 'Under/Over Player 3 Points Made'),
((SELECT id FROM competition WHERE external_id = 101) ,1089, 'SUB_PARTICIPANT', 'Under/Over Player Steals And Blocks'),
((SELECT id FROM competition WHERE external_id = 101) ,1090, 'SUB_PARTICIPANT', 'Under/Over Player Points, Assists And Rebounds'),
((SELECT id FROM competition WHERE external_id = 101) ,1091, 'SUB_PARTICIPANT', 'Under/Over Player Assists And Rebounds'),
((SELECT id FROM competition WHERE external_id = 101) ,1092, 'SUB_PARTICIPANT', 'Under/Over Player Points And Rebounds'),
((SELECT id FROM competition WHERE external_id = 101) ,1093, 'SUB_PARTICIPANT', 'Under/Over Player Points And Assists');

INSERT INTO market_linked_stats (market_id, stat_name)
VALUES
((SELECT id FROM market WHERE external_id = 1069), 'Points'),
((SELECT id FROM market WHERE external_id = 1070), 'Blocks'),
((SELECT id FROM market WHERE external_id = 1071), 'Assists'),
((SELECT id FROM market WHERE external_id = 1072), 'Rebounds total'),
((SELECT id FROM market WHERE external_id = 1073), 'Steals'),
((SELECT id FROM market WHERE external_id = 1074), 'Turnovers'),
((SELECT id FROM market WHERE external_id = 1075), '3pt scored'),

((SELECT id FROM market WHERE external_id = 1089), 'Steals'),
((SELECT id FROM market WHERE external_id = 1089), 'Blocks'),

((SELECT id FROM market WHERE external_id = 1090), 'Points'),
((SELECT id FROM market WHERE external_id = 1090), 'Assists'),
((SELECT id FROM market WHERE external_id = 1090), 'Rebounds total'),

((SELECT id FROM market WHERE external_id = 1091), 'Assists'),
((SELECT id FROM market WHERE external_id = 1091), 'Rebounds total'),

((SELECT id FROM market WHERE external_id = 1092), 'Points'),
((SELECT id FROM market WHERE external_id = 1092), 'Rebounds total'),

((SELECT id FROM market WHERE external_id = 1093), 'Points'),
((SELECT id FROM market WHERE external_id = 1093), 'Assists');
