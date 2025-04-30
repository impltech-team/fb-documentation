SET search_path TO sport;



ALTER TABLE market DROP CONSTRAINT IF EXISTS market_external_id_key;

ALTER TABLE market
    ADD CONSTRAINT unique_market_external_competition UNIQUE (external_id, competition_id);



INSERT INTO market (competition_id, external_id, market_type, market_name)
VALUES
((SELECT id FROM competition WHERE external_id = 5611) ,101, 'PARTICIPANT', 'Under/Over - Home Team'),
((SELECT id FROM competition WHERE external_id = 5611) ,102, 'PARTICIPANT', 'Under/Over - Away Team'),
((SELECT id FROM competition WHERE external_id = 5611) ,198, 'PARTICIPANT', 'Odd/Even - Home Team'),
((SELECT id FROM competition WHERE external_id = 5611) ,199, 'PARTICIPANT', 'Odd/Even - Away Team'),
((SELECT id FROM competition WHERE external_id = 5611) ,387, 'PARTICIPANT', 'Odd/Even - Home Team Including Overtime'),
((SELECT id FROM competition WHERE external_id = 5611) ,388, 'PARTICIPANT', 'Odd/Even - Away Team Including Overtime'),
((SELECT id FROM competition WHERE external_id = 5611) ,927, 'PARTICIPANT', 'Home Team Under/Over Touchdowns'),
((SELECT id FROM competition WHERE external_id = 5611) ,928, 'PARTICIPANT', 'Away Team Under/Over Touchdowns'),
((SELECT id FROM competition WHERE external_id = 5611) ,929, 'PARTICIPANT', 'Home Team Under/Over Field Goals'),
((SELECT id FROM competition WHERE external_id = 5611) ,930, 'PARTICIPANT', 'Away Team Under/Over Field Goals'),
((SELECT id FROM competition WHERE external_id = 5611) ,3062, 'PARTICIPANT', 'Under/Exactly/Over - Home Team Field Goals'),
((SELECT id FROM competition WHERE external_id = 5611) ,3063, 'PARTICIPANT', 'Under/Exactly/Over - Away Team Field Goals');

INSERT INTO market_linked_stats (market_id, stat_name)
VALUES
((SELECT id FROM market WHERE external_id = 101 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Result'),
((SELECT id FROM market WHERE external_id = 102 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Result'),
((SELECT id FROM market WHERE external_id = 198 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Result'),
((SELECT id FROM market WHERE external_id = 199 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Result'),
((SELECT id FROM market WHERE external_id = 387 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Result'),
((SELECT id FROM market WHERE external_id = 388 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Result'),

((SELECT id FROM market WHERE external_id = 927 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Touchdowns'),
((SELECT id FROM market WHERE external_id = 928 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Touchdowns'),

((SELECT id FROM market WHERE external_id = 929 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Field goals'),
((SELECT id FROM market WHERE external_id = 930 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Field goals'),
((SELECT id FROM market WHERE external_id = 929 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Field goals scored'),
((SELECT id FROM market WHERE external_id = 930 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Field goals scored'),
((SELECT id FROM market WHERE external_id = 929 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Field goals missed'),
((SELECT id FROM market WHERE external_id = 930 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Field goals missed'),

((SELECT id FROM market WHERE external_id = 3062 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Field goals'),
((SELECT id FROM market WHERE external_id = 3063 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Field goals'),
((SELECT id FROM market WHERE external_id = 3062 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Field goals scored'),
((SELECT id FROM market WHERE external_id = 3063 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Field goals scored'),
((SELECT id FROM market WHERE external_id = 3062 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Field goals missed'),
((SELECT id FROM market WHERE external_id = 3063 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Field goals missed');



INSERT INTO market (competition_id, external_id, market_type, market_name)
VALUES
((SELECT id FROM competition WHERE external_id = 5611) ,995, 'SUB_PARTICIPANT', 'Player To Score Touchdown In Anytime'),
((SELECT id FROM competition WHERE external_id = 5611) ,996, 'SUB_PARTICIPANT', 'Player To Score 2 Or More Touchdowns'),
((SELECT id FROM competition WHERE external_id = 5611) ,997, 'SUB_PARTICIPANT', 'Player To Score 3 Or More Touchdowns'),
((SELECT id FROM competition WHERE external_id = 5611) ,1194, 'SUB_PARTICIPANT', 'Under/Over Player Passing Touchdowns'),
((SELECT id FROM competition WHERE external_id = 5611) ,1195, 'SUB_PARTICIPANT', 'Under/Over Longest Player Pass Completion'),
((SELECT id FROM competition WHERE external_id = 5611) ,1196, 'SUB_PARTICIPANT', 'Under/Over Player Receiving Yards'),
((SELECT id FROM competition WHERE external_id = 5611) ,1197, 'SUB_PARTICIPANT', 'Under/Over Player Interceptions'),
((SELECT id FROM competition WHERE external_id = 5611) ,1198, 'SUB_PARTICIPANT', 'Under/Over Player Kicking Points'),
((SELECT id FROM competition WHERE external_id = 5611) ,1199, 'SUB_PARTICIPANT', 'Under/Over Player Rushing & Receiving Yards'),
((SELECT id FROM competition WHERE external_id = 5611) ,1200, 'SUB_PARTICIPANT', 'Under/Over Player Rushing Yards'),
((SELECT id FROM competition WHERE external_id = 5611) ,1201, 'SUB_PARTICIPANT', 'Under/Over Player Longest Reception'),
((SELECT id FROM competition WHERE external_id = 5611) ,1203, 'SUB_PARTICIPANT', 'Under/Over Player Receptions'),
((SELECT id FROM competition WHERE external_id = 5611) ,1218, 'SUB_PARTICIPANT', 'Under/Over Player Rush Attempts'),
((SELECT id FROM competition WHERE external_id = 5611) ,1703, 'SUB_PARTICIPANT', 'Under/Over Player Passing Completions'),
((SELECT id FROM competition WHERE external_id = 5611) ,2352, 'SUB_PARTICIPANT', 'Under/Over Player Tackles'),
((SELECT id FROM competition WHERE external_id = 5611) ,2400, 'SUB_PARTICIPANT', 'Under/Over Player Passing Yards'),
((SELECT id FROM competition WHERE external_id = 5611) ,3070, 'SUB_PARTICIPANT', 'Under/Over Player Passing and Rushing Yards');

INSERT INTO market_linked_stats (market_id, stat_name)
VALUES
((SELECT id FROM market WHERE external_id = 995 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Touchdowns'),
((SELECT id FROM market WHERE external_id = 996 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Touchdowns'),
((SELECT id FROM market WHERE external_id = 997 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Touchdowns'),
((SELECT id FROM market WHERE external_id = 1194 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Passing touchdowns'),
((SELECT id FROM market WHERE external_id = 1196 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Receiving yards'),

((SELECT id FROM market WHERE external_id = 1197 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Interceptions thrown'),
((SELECT id FROM market WHERE external_id = 1197 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Interceptions'),

((SELECT id FROM market WHERE external_id = 1198 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Field goals'),
((SELECT id FROM market WHERE external_id = 1198 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Extra points'),

((SELECT id FROM market WHERE external_id = 1199 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Receiving yards'),
((SELECT id FROM market WHERE external_id = 1199 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Rushing yards'),

((SELECT id FROM market WHERE external_id = 1200 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Rushing yards'),

((SELECT id FROM market WHERE external_id = 1201 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Yards per reception'),
((SELECT id FROM market WHERE external_id = 1201 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Receiving yards per reception'),

((SELECT id FROM market WHERE external_id = 1203 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Pass receptions'),
((SELECT id FROM market WHERE external_id = 1203 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Yards per reception'),
((SELECT id FROM market WHERE external_id = 1203 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Receiving yards per reception'),

((SELECT id FROM market WHERE external_id = 1218 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Rush Attempts'),

((SELECT id FROM market WHERE external_id = 1703 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Passes completed'),

((SELECT id FROM market WHERE external_id = 2352 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Tackles'),

((SELECT id FROM market WHERE external_id = 2400 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Passing yards'),

((SELECT id FROM market WHERE external_id = 3062 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Rushing yards'),
((SELECT id FROM market WHERE external_id = 3063 AND competition_id = (SELECT id FROM competition WHERE external_id = 5611)), 'Passing yards');



INSERT INTO market (competition_id, external_id, market_type, market_name)
VALUES
((SELECT id FROM competition WHERE external_id = 101) ,1077, 'SUB_PARTICIPANT', 'Under/Over Player 2 Points Made');

INSERT INTO market_linked_stats (market_id, stat_name)
VALUES
((SELECT id FROM market WHERE external_id = 1077 AND competition_id = (SELECT id FROM competition WHERE external_id = 101)), '2pt scored');
