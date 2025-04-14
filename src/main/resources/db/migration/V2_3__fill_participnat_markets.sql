SET search_path TO sport;

INSERT INTO market (competition_id, external_id, market_type, market_name)
VALUES
((SELECT id FROM competition WHERE external_id = 101) ,1988, 'PARTICIPANT', 'Under/Over Steals - Home Team'),
((SELECT id FROM competition WHERE external_id = 101) ,1989, 'PARTICIPANT', 'Under/Over Steals - Away Team'),
((SELECT id FROM competition WHERE external_id = 101) ,1833, 'PARTICIPANT', 'Under/Over Home Team 3 Points Made'),
((SELECT id FROM competition WHERE external_id = 101) ,1834, 'PARTICIPANT', 'Under/Over Away Team 3 Points Made'),
((SELECT id FROM competition WHERE external_id = 101) ,101, 'PARTICIPANT', 'Under/Over - Home Team'),
((SELECT id FROM competition WHERE external_id = 101) ,102, 'PARTICIPANT', 'Under/Over - Away Team'),
((SELECT id FROM competition WHERE external_id = 101) ,2006, 'PARTICIPANT', 'Under/Over Away Team 2 Points Made'),
((SELECT id FROM competition WHERE external_id = 101) ,2007, 'PARTICIPANT', 'Under/Over Home Team 2 Points Made'),
((SELECT id FROM competition WHERE external_id = 101) ,198, 'PARTICIPANT', 'Odd/Even - Home Team'),
((SELECT id FROM competition WHERE external_id = 101) ,199, 'PARTICIPANT', 'Odd/Even - Away Team'),
((SELECT id FROM competition WHERE external_id = 101) ,1827, 'PARTICIPANT', 'Under/Over Home team Assists'),
((SELECT id FROM competition WHERE external_id = 101) ,1828, 'PARTICIPANT', 'Under/Over Away Team Assists');

INSERT INTO market_linked_stats (market_id, stat_name)
VALUES
((SELECT id FROM market WHERE external_id = 1988), 'Steals'),
((SELECT id FROM market WHERE external_id = 1989), 'Steals'),
((SELECT id FROM market WHERE external_id = 1833), '3pt scored'),
((SELECT id FROM market WHERE external_id = 1834), '3pt scored'),
((SELECT id FROM market WHERE external_id = 101), 'Points'),
((SELECT id FROM market WHERE external_id = 102), 'Points'),
((SELECT id FROM market WHERE external_id = 2006), '2pt scored'),
((SELECT id FROM market WHERE external_id = 2007), '2pt scored'),
((SELECT id FROM market WHERE external_id = 198), 'Points'),
((SELECT id FROM market WHERE external_id = 199), 'Points'),
((SELECT id FROM market WHERE external_id = 1827), 'Assists'),
((SELECT id FROM market WHERE external_id = 1828), 'Assists')
