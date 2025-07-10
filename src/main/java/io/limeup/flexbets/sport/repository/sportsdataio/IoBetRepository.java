package io.limeup.flexbets.sport.repository.sportsdataio;

import io.limeup.flexbets.sport.model.IoBet;
import io.limeup.flexbets.sport.model.IoEvent;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataBetRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface IoBetRepository extends JpaRepository<IoBet, Long> {

    @Query("""
           select distinct b
           from IoBet b
           left join fetch b.betOutcomes
           where b.event = :event
           """)
    Set<IoBet> findByEventWithOutcomes(@Param("event") IoEvent event);

    @Query(value = """
            SELECT
                  b.market_id  AS id,
                  bo.player_id AS playerId,
                  b.bet_type_id AS marketTypeId,
                  b.bet_type AS marketType,
                  bo.outcome_type AS betType,
                  bo.value AS betLine,
                  bo.payout_decimal AS price,
                  bo.updated_at as lastUpdated
            FROM sport.io_bet b
                 LEFT JOIN sport.io_bet_outcome bo on b.id = bo.io_bet_id
                 LEFT JOIN sport.io_event e on b.io_event_id = e.id
            WHERE b.market_type = :marketType
                  AND bo.player_id = :playerId
                  AND b.any_bets_available = true
                  AND    bo.updated_at > NOW() - INTERVAL '1 day'
            """, nativeQuery = true)
    List<SportsDataBetRow> findAllByMarketTypeAndEventIdInAndPlayerIdAndAnyBetsAvailableTrue(String marketType, Long playerId);

    @Query(value = """
   SELECT  b.market_id AS id,
           bo.player_id AS playerId,
           b.bet_type_id AS marketTypeId,
           b.bet_type AS marketType,
           bo.outcome_type AS betType,
           bo.value AS betLine,
           bo.payout_decimal AS price,
           bo.updated_at AS lastUpdated
   FROM    sport.io_bet b
   JOIN    sport.io_bet_outcome bo ON bo.io_bet_id = b.id
   JOIN    sport.io_event e ON b.io_event_id = e.id
   WHERE   b.any_bets_available = true
     AND   bo.updated_at > NOW() - INTERVAL '1 day'
     AND   bo.player_id IN (:playerIds)
""", nativeQuery = true)
    List<SportsDataBetRow> findAvailablePlayerBets(
            @Param("playerIds") int[] playerIds
    );
    @Query(value = """
        SELECT  b.market_id         AS id,
                bo.player_id        AS playerId,
                b.bet_type_id       AS marketTypeId,
                b.bet_type          AS marketType,
                bo.outcome_type     AS betType,
                bo.value            AS betLine,
                bo.payout_decimal   AS price,
                bo.updated_at       AS lastUpdated
        FROM    sport.io_bet b
        JOIN    sport.io_bet_outcome bo ON bo.io_bet_id = b.id
        WHERE    b.any_bets_available
          AND   bo.player_id        = ANY(:playerIds)
          AND   b.bet_type_id       = :marketId
          AND    bo.updated_at > NOW() - INTERVAL '1 day'
        """,
            nativeQuery = true)
    List<SportsDataBetRow> findAvailablePlayerBetsWithMarketId(
            @Param("marketId") Integer marketId,
            @Param("playerIds") int[]  playerIds
    );


    List<IoBet> findAllByEvent(IoEvent event);
}

