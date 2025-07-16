package io.limeup.flexbets.sport.repository.sportsdataio;

import io.limeup.flexbets.sport.model.IoPlayerGameStatsNFL;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IoPlayerGameStatsNFLRepository extends JpaRepository<IoPlayerGameStatsNFL, Long> {

    Optional<IoPlayerGameStatsNFL> findByPlayerIdAndGameKey(Long playerId, String gameKey);

    List<IoPlayerGameStatsNFL> findByPlayerId(Long playerId);

    List<IoPlayerGameStatsNFL> findByGameKey(String gameKey);

    List<IoPlayerGameStatsNFL> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT p FROM IoPlayerGameStatsNFL p WHERE p.season = :season AND p.week = :week")
    List<IoPlayerGameStatsNFL> findBySeasonAndWeek(@Param("season") Integer season, @Param("week") Integer week);

    @Query("SELECT p FROM IoPlayerGameStatsNFL p WHERE p.team = :team AND p.season = :season")
    List<IoPlayerGameStatsNFL> findByTeamAndSeason(@Param("team") String team, @Param("season") Integer season);

    @Query(value = """
        SELECT p.* FROM sport.io_player_game_stats_nfl p
        WHERE (:playerId IS NULL OR p.player_id = :playerId)
          AND (:team IS NULL OR p.team = :team)
          AND (:position IS NULL OR p.position = :position)
          AND (:season IS NULL OR p.season = :season)
          AND (:week IS NULL OR p.week = :week)
          AND (:minFantasyPoints IS NULL OR p.fantasy_points >= :minFantasyPoints)
        ORDER BY p.fantasy_points DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<IoPlayerGameStatsNFL> findTopPerformers(
            @Param("playerId") Long playerId,
            @Param("team") String team,
            @Param("position") String position,
            @Param("season") Integer season,
            @Param("week") Integer week,
            @Param("minFantasyPoints") BigDecimal minFantasyPoints,
            @Param("limit") int limit
    );

    @Query("SELECT g FROM IoPlayerGameStatsNFL g WHERE g.playerId = :playerId ORDER BY g.gameDate DESC")
    List<IoPlayerGameStatsNFL> findTopByPlayerIdOrderByGameDateDesc(
            @Param("playerId") Long playerId,
            @Param("limit") int limit
    );

    @Query(value = """
    SELECT g
    FROM IoPlayerGameStatsNFL g
    WHERE g.playerId = :playerId
    ORDER BY g.gameDate DESC
""")
    List<IoPlayerGameStatsNFL> findTopByPlayerIdLimit(
            @Param("playerId") Long playerId,
            Pageable pageable
    );

    List<IoPlayerGameStatsNFL> findByPlayerIdOrderByGameDateDesc(Long playerId, Pageable pageable);

}