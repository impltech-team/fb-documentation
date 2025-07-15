package io.limeup.flexbets.sport.repository.sportsdataio;


import io.limeup.flexbets.sport.model.IoPlayerGameStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IoPlayerGamesStatsRepository extends JpaRepository<IoPlayerGameStats, Long> {

    Optional<IoPlayerGameStats> findByStatId(Long statId);


    @Query(value = """
            SELECT * FROM io_player_game_stats
            WHERE player_id = :playerId
            ORDER BY game_datetime DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<IoPlayerGameStats> findTopByPlayerIdLimit(
            @Param("playerId") Long playerId,
            @Param("limit") int limit
    );

    @Query(value = """
            SELECT * FROM io_player_game_stats
            WHERE team_id = :teamId
            ORDER BY game_datetime DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<IoPlayerGameStats> findTopByTeamIdLimit(
            @Param("teamId") Long teamId,
            @Param("limit") int limit
    );
}
