package io.limeup.flexbets.sport.repository.sportsdataio;

import io.limeup.flexbets.sport.model.IoPlayerNFL;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataPlayerRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IoPlayerNFLRepository extends JpaRepository<IoPlayerNFL, Long> {

    Optional<IoPlayerNFL> findByPlayerId(Long playerId);

    @Query(value = """
    SELECT
        p.player_id AS id,
        p.photo_url AS avatarUrl,
        p.first_name || ' ' || p.last_name AS playerName,
        p.number AS shirtNumber,
        p.position AS position,
        p.weight AS weight,
        COALESCE(p.height_feet, 0) * 12 + COALESCE(p.height_inches, 0) AS height,
        p.birth_date AS birthDate,
        p.global_team_id AS playerTeamId,
        p.current_team AS playerTeamName,
        s.country AS country,
        e.id AS eventId,
        e.datetime AS eventDatetime,
        TRIM(COALESCE(e.home_team, '') || ' - ' || COALESCE(e.away_team, '')) AS eventName,
        CASE
            WHEN p.team_id = e.home_team_id THEN e.away_team
            WHEN p.team_id = e.away_team_id THEN e.home_team
            ELSE NULL
        END AS opponentTeamKey
    FROM sport.io_player_nfl p
    LEFT JOIN sport.io_team_nfl t ON t.team_id = p.team_id
    LEFT JOIN sport.io_event_nfl e
        ON e.week = p.upcoming_game_week
        AND (p.team_id = e.home_team_id OR p.team_id = e.away_team_id)
    LEFT JOIN sport.io_stadium_nfl s ON s.stadium_id = e.stadium_id
    WHERE
        (:positions IS NULL OR p.position IN (:positions))
        AND (:teamIds IS NULL OR p.team_id IN (:teamIds))
        AND (
            :filter IS NULL OR
            p.first_name ILIKE CONCAT('%', :filter, '%') OR
            p.last_name ILIKE CONCAT('%', :filter, '%') OR
            t.name ILIKE CONCAT('%', :filter, '%') OR
            p.position ILIKE CONCAT('%', :filter, '%')
        )
    ORDER BY
        CASE WHEN :sortBy = 'player_name' THEN p.last_name END ASC,
        CASE WHEN :sortBy = 'team_name' THEN t.name END ASC,
        CASE WHEN :sortBy = 'position' THEN p.position END ASC,
        p.last_name ASC
    OFFSET :offset
    LIMIT :limit
""", nativeQuery = true)
    List<SportsDataPlayerRow> listPlayersWithFilters(
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("onlyWithOdds") boolean onlyWithOdds,
            @Param("sortBy") String sortBy,
            @Param("sortOrder") String sortOrder,
            @Param("filter") String filter,
            @Param("competitionId") Integer competitionId,
            @Param("positions") Collection<String> positions,
            @Param("teamIds") Collection<Integer> teamIds
    );



    @Query(value = """
    SELECT
        p.player_id AS id,
        p.first_name || ' ' || p.last_name AS playerName,
        p.number AS shirtNumber,
        p.position AS position,
        p.weight AS weight,
        COALESCE(p.height_feet, 0) * 12 + COALESCE(p.height_inches, 0) AS height,
        p.birth_date AS birthDate,
        TRIM(COALESCE(t.city || ' ', '') || t.name) AS playerTeamName,
        p.photo_url AS avatarUrl,
        p.team_id AS playerTeamId,
        s.country AS country,
        e.id AS event_id,
        e.datetime AS event_datetime,
        TRIM(COALESCE(e.home_team, '') || ' - ' || COALESCE(e.away_team, '')) AS eventName,
        CASE
            WHEN p.team_id = e.home_team_id THEN e.away_team
            WHEN p.team_id = e.away_team_id THEN e.home_team
        END AS opponentTeamKey
    FROM sport.io_player_nfl p
    LEFT JOIN sport.io_team_nfl t ON t.team_id = p.team_id
    LEFT JOIN sport.io_event_nfl e ON 
        e.week = p.upcoming_game_week AND
        (e.home_team_id = p.team_id OR e.away_team_id = p.team_id)
    LEFT JOIN sport.io_stadium_nfl s ON s.stadium_id = e.stadium_id
    WHERE p.player_id = :playerId
""", nativeQuery = true)
    Optional<SportsDataPlayerRow> getNFLPlayerWithBetsById(
            @Param("playerId") Long playerId
    );


}
