package io.limeup.flexbets.sport.repository.sportsdataio;

import io.limeup.flexbets.sport.model.IoBetNFL;
import io.limeup.flexbets.sport.model.IoBetOutcomeNFL;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataBetRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IoBetNFLRepository extends JpaRepository<IoBetNFL, Long> {

    @Query("SELECT b FROM IoBetNFL b JOIN FETCH b.bettingOutcomes WHERE b.event.id = :eventId " +
            "AND b.bettingMarketType IN :marketTypes")
    Set<IoBetNFL> findByEventIdAndMarketTypes(
            @Param("eventId") Long eventId,
            @Param("marketTypes") Collection<String> marketTypes);

    @Query(value = """
    SELECT
        b.market_id         AS id,
        b.player_id         AS playerId,
        b.betting_market_type_id AS marketTypeId,
        b.betting_market_type   AS marketType,
        bo.name             AS betType,
        bo.total            AS betLine,
        CAST(bo.odds AS VARCHAR) AS price,
        bo.updated_at       AS lastUpdated
    FROM sport.io_bet_nfl b
    JOIN sport.io_bet_outcome_nfl bo ON bo.io_bet_nfl_id = b.id
    WHERE b.any_bets_available = true
      AND b.player_id = ANY(:playerIds)
      AND bo.updated_at > NOW() - INTERVAL '1 day'
      AND b.betting_market_type = :marketType
""", nativeQuery = true)
    List<SportsDataBetRow> findAvailablePlayerBets(
            @Param("marketType") String marketType,
            @Param("playerIds") List<Long> playerIds
    );

    @Query(value = """
    SELECT
        b.market_id         AS id,
        b.player_id         AS playerId,
        b.betting_market_type_id AS marketTypeId,
        b.betting_market_type   AS marketType,
        bo.name             AS betType,
        bo.total            AS betLine,
        CAST(bo.odds AS VARCHAR) AS price,
        bo.updated_at       AS lastUpdated
    FROM sport.io_bet_nfl b
    JOIN sport.io_bet_outcome_nfl bo ON bo.io_bet_nfl_id = b.id
    WHERE b.any_bets_available = true
      AND b.player_id = ANY(:playerIds)
      AND b.market_id = :marketId
      AND bo.updated_at > NOW() - INTERVAL '1 day'
      AND b.betting_market_type = :marketType
""", nativeQuery = true)
    List<SportsDataBetRow> findAvailablePlayerBetsWithMarketId(
            @Param("marketType") String marketType,
            @Param("marketId") Long marketId,
            @Param("playerIds") List<Long> playerIds
    );
}
