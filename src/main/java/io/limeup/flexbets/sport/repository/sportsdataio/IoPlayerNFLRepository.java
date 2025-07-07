package io.limeup.flexbets.sport.repository.sportsdataio;


import io.limeup.flexbets.sport.model.IoPlayerNFL;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataPlayerRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IoPlayerNFLRepository extends JpaRepository<IoPlayerNFL, Long> {

    Optional<IoPlayerNFL> findByPlayerId(Long playerID);

    @Query(value = """
                  SELECT
                        p.player_id AS id,
                        p.first_name || ' ' || p.last_name AS playerName,
                        NULL AS shirtNumber,
                        p.team_id AS playerTeamId,
                        TRIM(COALESCE(t.city || ' ', '') || t.name) AS playerTeamName,
                        p.position AS position,
                        p.height AS height,
                        p.weight AS weight,
                        NULL AS country,
                        p.birth_date AS birthDate,
                        e.game_id AS event_id,
                        e.datetime AS event_datetime,
                        TRIM(COALESCE(home_team.city || ' ', '') || home_team.name) || ' - ' || TRIM(COALESCE(away_team.city || ' ', '') || away_team.name) AS eventName,                                              
                        CASE
                            WHEN p.team_id = e.home_team_id THEN away_team.key
                            WHEN p.team_id = e.away_team_id THEN home_team.key
                        END AS opponentTeamKey
                FROM io_player_nfl p
                        JOIN io_event e ON e.game_id = CAST(p.upcoming_game_id AS BIGINT)
                        LEFT JOIN sport.io_team t ON t.team_id = p.team_id
                        LEFT JOIN sport.io_team home_team ON home_team.team_id = e.home_team_id
                        LEFT JOIN sport.io_team away_team ON away_team.team_id = e.away_team_id
                WHERE p.upcoming_game_id IS NOT NULL
                ORDER BY e.datetime ASC
                OFFSET :offset
                LIMIT :limit
            """, nativeQuery = true)
    List<SportsDataPlayerRow> listPlayersWithUpcomingEventNFL(
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    @Query(value = """
                  SELECT
                        p.player_id AS id,
                        p.first_name || ' ' || p.last_name AS playerName,
                        NULL AS shirtNumber,
                        p.team_id AS playerTeamId,
                        TRIM(COALESCE(t.city || ' ', '') || t.name) AS playerTeamName,
                        p.position AS position,
                        p.height AS height,
                        p.weight AS weight,
                        NULL AS country,
                        p.birth_date AS birthDate,
                        e.game_id AS event_id,
                        e.datetime AS event_datetime,
                        TRIM(COALESCE(home_team.city || ' ', '') || home_team.name) || ' - ' || TRIM(COALESCE(away_team.city || ' ', '') || away_team.name) AS eventName,                                              
                        CASE
                            WHEN p.team_id = e.home_team_id THEN away_team.key
                            WHEN p.team_id = e.away_team_id THEN home_team.key
                        END AS opponentTeamKey
                FROM io_player_nfl p
                        JOIN io_event e ON e.game_id = CAST(p.upcoming_game_id AS BIGINT)
                        LEFT JOIN sport.io_team t ON t.team_id = p.team_id
                        LEFT JOIN sport.io_team home_team ON home_team.team_id = e.home_team_id
                        LEFT JOIN sport.io_team away_team ON away_team.team_id = e.away_team_id
                WHERE p.player_id = :playerId
            """, nativeQuery = true)
    Optional<SportsDataPlayerRow> getNFLPlayerWithBetsById(@Param("playerId") Integer playerId);

    @Query(value = """
        SELECT COUNT(*)
        FROM io_player_nfl p
        JOIN io_event e ON e.game_id = CAST(p.upcoming_game_id AS BIGINT)
        LEFT JOIN sport.io_team t ON t.team_id = p.team_id
        LEFT JOIN sport.io_team home_team ON home_team.team_id = e.home_team_id
        LEFT JOIN sport.io_team away_team ON away_team.team_id = e.away_team_id
        WHERE p.upcoming_game_id IS NOT NULL
        """, nativeQuery = true)
    long countNFLPlayersWithUpcomingEvent();

    List<IoPlayerNFL> findByPlayerIdIn(Set<Long> playerIds);
}