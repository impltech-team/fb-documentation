package io.limeup.flexbets.sport.repository.sportsdataio;

import io.limeup.flexbets.sport.model.IoBet;
import io.limeup.flexbets.sport.model.IoEvent;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataBetRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IoBetRepository extends JpaRepository<IoBet, Long> {

    List<IoBet> findByEvent(IoEvent event);

    @Query(value = """ 
            SELECT
                  bo.outcome_id AS id,
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
                  AND e.game_id IN (:gameIds)
                  AND b.any_bets_available = true
            """, nativeQuery = true)
    List<SportsDataBetRow> findAllByMarketTypeAndEventIdInAndAnyBetsAvailableTrue(String marketType, Collection<Integer> gameIds);



    @Query("""
           select distinct b
           from IoBet b
           left join fetch b.betOutcomes
           where b.event = :event
           """)
    Set<IoBet> findByEventWithOutcomes(@Param("event") IoEvent event);

    @Query(value = """ 
            SELECT
                  bo.outcome_id AS id,
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
                  AND e.game_id IN (:gameIds)
                  AND bo.player_id = :playerId
                  AND b.any_bets_available = true
            """, nativeQuery = true)
    List<SportsDataBetRow> findAllByMarketTypeAndEventIdInAndPlayerIdAndAnyBetsAvailableTrue(String marketType, Collection<Integer> gameIds, Long playerId);


    @Query(value = """
        SELECT b.player_id AS playerId,
               b.event_id  AS eventId,
               b.bet_type_id AS betTypeId,
               b.line      AS line,
               b.odds      AS odds
        FROM   sport.io_bet b
        WHERE  b.market_type        = :marketType
          AND (:betTypeId IS NULL OR b.bet_type_id = :betTypeId)
          AND  b.event_id           IN (:eventIds)
          AND  b.any_bets_available
        """, nativeQuery = true)
    List<SportsDataBetRow> fetchAvailableBets(
            @Param("marketType") String marketType,
            @Param("betTypeId")  Integer betTypeId,
            @Param("eventIds")   Collection<Long> eventIds);

    @Query(value = """
        SELECT b.io_bet_id           AS id,
               b.player_id           AS playerId,
               b.event_id            AS eventId,
               b.market_type         AS marketType,
               b.outcome_type        AS betType,
               b.payout_decimal      AS price,
               b.any_bets_available  AS anyBetsAvailable
        FROM   sport.io_bet b
        WHERE  b.market_type         = :marketType
          AND  b.any_bets_available  = TRUE
          AND  b.event_id            = ANY(:eventIds)
          AND  b.player_id           = ANY(:playerIds)
        """,
            nativeQuery = true)
    Collection<Object> findAllByMarketTypeAndEventIdInAndPlayerIdInAndAnyBetsAvailableTrue(String name, Set<Long> eventIds, Set<Integer> playerIds);

    @Query(value = """
        SELECT  bo.outcome_id       AS id,
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

        """,
            nativeQuery = true)
    List<SportsDataBetRow> findAvailablePlayerBets(
            @Param("playerIds") int[]  playerIds
    );

    @Query(value = """
        SELECT  bo.outcome_id       AS id,
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
          AND   b.bet_type_id  = :marketId

        """,
            nativeQuery = true)
    List<SportsDataBetRow> findAvailablePlayerBetsWithMarketId(
            @Param("marketId") Integer marketId,
            @Param("playerIds") int[]  playerIds
    );
}

