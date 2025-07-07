package io.limeup.flexbets.sport.repository.sportsdataio;


import io.limeup.flexbets.sport.model.IoPlayer;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataPlayerRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IoPlayerRepository extends JpaRepository<IoPlayer, Long> {

    Optional<IoPlayer> findByPlayerId(Long playerID);

    @Query(value = """
            WITH selected_players AS (
                SELECT
                    p.player_id                                          AS id,
                    p.photo_url                                          AS avatar_url,
                    p.first_name || ' ' || p.last_name                   AS player_name,
                    p.position,
                    p.jersey                                             AS shirt_number,
                    p.height,
                    p.weight,
                    p.birth_country                                      AS country,
                    p.birth_date,
                    p.team_id                                            AS player_team_id,
                    TRIM(COALESCE(t.city || ' ', '') || t.name)          AS player_team_name,
                    e.id                                                 AS event_id,
                    e.datetime                                           AS event_datetime,
                    TRIM(COALESCE(ht.city || ' ', '') || ht.name)
                    || ' - ' ||
                    TRIM(COALESCE(at.city || ' ', '') || at.name)        AS event_name,
                    CASE
                        WHEN p.team_id = e.home_team_id THEN at.key
                        WHEN p.team_id = e.away_team_id THEN ht.key
                    END                                                  AS opponent_team_key
                FROM   sport.io_player  p
                JOIN   sport.io_team    t   ON t.team_id  = p.team_id
                JOIN   sport.io_event   e   ON e.game_id  = CAST(p.upcoming_game_id AS BIGINT)
                JOIN   sport.io_team    ht  ON ht.team_id = e.home_team_id
                JOIN   sport.io_team    at  ON at.team_id = e.away_team_id
                WHERE  e.datetime > NOW()

                 AND (:positions IS NULL OR p.position IN (:positions))                                                                              \s
                 AND (:participantIds IS NULL OR p.player_id IN (:participantIds))
                 AND (
                     p.first_name ILIKE CONCAT('%', :filter, '%') OR
                     p.last_name ILIKE CONCAT('%', :filter, '%') OR
                     t.name ILIKE CONCAT('%', :filter, '%') OR
                     p.position ILIKE CONCAT('%', :filter, '%')
                   )),
             player_has_bet AS (
                   SELECT DISTINCT bo.player_id
                FROM sport.io_bet          b
                JOIN sport.io_bet_outcome  bo  ON bo.io_bet_id = b.id
                JOIN selected_players      sp  ON sp.id        = bo.player_id
                                                AND sp.event_id  = b.io_event_id
                WHERE   b.any_bets_available
            ),  
          paged_players AS ( 
                SELECT id
                FROM   selected_players sp
                WHERE (
                         :betTypeId IS NOT NULL
                         AND id IN (
                             SELECT bo.player_id
                             FROM sport.io_bet b
                             JOIN sport.io_bet_outcome bo ON bo.io_bet_id = b.id
                             WHERE b.bet_type_id = :betTypeId
                         )
                      )
                   OR (
                        :betTypeId IS NULL AND (
                            (:onlyWithOdds = TRUE AND id IN (SELECT player_id FROM player_has_bet))
                            OR :onlyWithOdds = FALSE
                        )
                   )
                ORDER BY
                    CASE WHEN :sortBy = 'player_name' AND :sortOrder = 'asc' THEN sp.player_name END ASC,
                    CASE WHEN :sortBy = 'player_name' AND :sortOrder = 'desc' THEN sp.player_name END DESC,
                    CASE WHEN :sortBy = 'team_name' AND :sortOrder = 'asc' THEN sp.player_team_name END ASC,
                    CASE WHEN :sortBy = 'team_name' AND :sortOrder = 'desc' THEN sp.player_team_name END DESC,
                    CASE WHEN :sortBy = 'position' AND :sortOrder = 'asc' THEN sp.position END ASC,
                    CASE WHEN :sortBy = 'position' AND :sortOrder = 'desc' THEN sp.position END DESC,
                    CASE WHEN :sortBy = 'event_time' AND :sortOrder = 'asc' THEN sp.event_datetime END ASC,
                    CASE WHEN :sortBy = 'event_time' AND :sortOrder = 'desc' THEN sp.event_datetime END DESC,
                    sp.player_name DESC
                OFFSET :offset
                LIMIT  :limit
            )
            SELECT
                   sp.id,
                   sp.avatar_url,
                sp.player_name,
                sp.shirt_number,
                sp.player_team_id,
                sp.player_team_name,
                sp.position,
                sp.height,
                sp.weight,
                sp.country,
                sp.birth_date,
                sp.event_id,
                sp.event_datetime,
                sp.event_name,
                sp.opponent_team_key
            FROM   selected_players sp
            JOIN   paged_players    pp ON pp.id = sp.id
                
                ORDER BY
                          CASE WHEN :sortBy = 'player_name' AND :sortOrder = 'asc' THEN sp.player_name END ASC,
                          CASE WHEN :sortBy = 'player_name' AND :sortOrder = 'desc' THEN sp.player_name END DESC,
                          CASE WHEN :sortBy = 'team_name' AND :sortOrder = 'asc' THEN sp.player_team_name END ASC,
                          CASE WHEN :sortBy = 'team_name' AND :sortOrder = 'desc' THEN sp.player_team_name END DESC,
                          CASE WHEN :sortBy = 'position' AND :sortOrder = 'asc' THEN sp.position END ASC,
                          CASE WHEN :sortBy = 'position' AND :sortOrder = 'desc' THEN sp.position END DESC,
                          CASE WHEN :sortBy = 'event_time' AND :sortOrder = 'asc' THEN sp.event_datetime END ASC,
                          CASE WHEN :sortBy = 'event_time' AND :sortOrder = 'desc' THEN sp.event_datetime END DESC,
                sp.player_name desc
                                                                                                               
    """, nativeQuery = true)
    List<SportsDataPlayerRow> listPlayersWithOdds2(
            @Param("offset") Integer offset,
            @Param("limit") Integer limit,
            @Param("onlyWithOdds") Boolean onlyWithOdds,
            @Param("sortBy") String sortBy,
            @Param("sortOrder") String sortOrder,
            @Param("filter") String filter,
            @Param("betTypeId") Integer betTypeId,
            @Param("positions") Collection<String> positions,
            @Param("participantIds") Collection<Integer> participantIds
    );

    @Query(value = """
                  SELECT
                        p.player_id AS id,
                        p.first_name || ' ' || p.last_name AS playerName,
                        p.jersey AS shirtNumber,
                        p.team_id AS playerTeamId,
                        TRIM(COALESCE(t.city || ' ', '') || t.name) AS playerTeamName,
                        p.position AS position,
                        p.height AS height,
                        p.weight AS weight,
                        p.birth_country AS country,
                        p.birth_date AS birthDate,
                        p.photo_url  AS avatar_url,                    
                                                                         
                        e.game_id AS event_id,
                        e.datetime AS event_datetime,
                        TRIM(COALESCE(home_team.city || ' ', '') || home_team.name) || ' - ' || TRIM(COALESCE(away_team.city || ' ', '') || away_team.name) AS eventName,                                              
                        CASE
                            WHEN p.team_id = e.home_team_id THEN away_team.key
                            WHEN p.team_id = e.away_team_id THEN home_team.key
                        END AS opponentTeamKey
                FROM io_player p
                        JOIN io_event e ON e.game_id = CAST(p.upcoming_game_id AS BIGINT)
                        LEFT JOIN sport.io_team t ON t.team_id = p.team_id
                        LEFT JOIN sport.io_team home_team ON home_team.team_id = e.home_team_id
                        LEFT JOIN sport.io_team away_team ON away_team.team_id = e.away_team_id
                WHERE p.player_id = :playerId
            """, nativeQuery = true)
    Optional<SportsDataPlayerRow> getPlayerWithBetsById(@Param("playerId") Integer playerId);

}
