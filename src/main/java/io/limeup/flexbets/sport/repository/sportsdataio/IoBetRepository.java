package io.limeup.flexbets.sport.repository.sportsdataio;

import io.limeup.flexbets.sport.model.IoBet;
import io.limeup.flexbets.sport.model.IoEvent;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataBetRow;
import io.limeup.flexbets.sport.repository.projection.OddsRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
               SELECT  b.market_id AS id,
                       bo.player_id AS playerId,
                       b.bet_type_id AS marketTypeId,
                       b.bet_type AS marketType,
                       bo.outcome_type AS betType,
                       bo.value AS betLine,
                       bo.payout_decimal AS price,
                       bo.updated_at AS lastUpdated
               FROM    io_bet b
                JOIN    io_bet_outcome bo ON bo.io_bet_id = b.id
                JOIN    io_event e ON b.io_event_id = e.id
               WHERE   b.any_bets_available = true
                 AND   bo.updated_at > NOW() - INTERVAL '1 day'
                 AND   bo.player_id  = ANY(:playerIds)
                 AND   b.market_type = :marketType
            """, nativeQuery = true)
    List<SportsDataBetRow> findAvailablePlayerBets(
            @Param("marketType") String marketType,
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
            FROM    io_bet b
            JOIN    io_bet_outcome bo ON bo.io_bet_id = b.id
            WHERE   b.any_bets_available= true
              AND   bo.player_id  = ANY(:playerIds)
              AND   b.bet_type_id       = :marketId
              AND   bo.updated_at > NOW() - INTERVAL '1 day'
              AND   b.market_type = :marketType
            """,
            nativeQuery = true)
    List<SportsDataBetRow> findAvailablePlayerBetsWithMarketId(
            @Param("marketType") String marketType,
            @Param("marketId") Integer marketId,
            @Param("playerIds") int[] playerIds
    );

    List<IoBet> findAllByAnyBetsAvailableTrue();

    List<IoBet> findAllByEventAndAnyBetsAvailableTrue(IoEvent event);

    @Query(value = """
            SELECT
                 o.id AS id,
                 b.bet_type AS market_name,
                 b.bet_type_id AS market_id,
                 o.value AS line,
                 o.outcome_type AS bet_type,
                 o.payout_decimal AS price,
                 'OPEN' AS status,
                 o.updated_at AS last_updated_date,
                 CASE
                    WHEN e.home_team_id IN (:teamIds) THEN e.home_team_id
                  ELSE e.away_team_id
                    END AS teamId
               FROM sport.io_bet_outcome o
                   JOIN sport.io_bet b ON o.io_bet_id = b.id
                   JOIN sport.io_event e ON b.io_event_id = e.id
                 WHERE b.market_type= :betType
                   AND b.any_bets_available = true
                   AND (
                         e.home_team_id IN (:teamIds)
                         OR e.away_team_id IN (:teamIds)
                  )
                  AND  o.updated_at > NOW() - INTERVAL '1 day'
            """, nativeQuery = true)
    List<OddsRow> getBetsForTeam(@Param("teamIds") Set<Integer> teamIds,
                                 @Param("betType") String betType);

    @Query(value = """
               SELECT
                   o.id AS id,
                   b.bet_type AS market_name,
                   b.bet_type_id AS market_id,
                   o.value AS line,
                   o.outcome_type AS bet_type,
                   o.payout_decimal AS price,
                   'OPEN' AS status,
                   o.updated_at AS last_updated_date,
                   CASE
                     WHEN e.home_team_id IN (:teamIds) THEN e.home_team_id
                   ELSE e.away_team_id
                     END AS teamId
                 FROM sport.io_bet_outcome o
                   JOIN sport.io_bet b ON o.io_bet_id = b.id
                   JOIN sport.io_event e ON b.io_event_id = e.id
                 WHERE b.market_type = :betType
                   AND b.bet_type_id = :marketId 
                   AND b.any_bets_available = true
                   AND (
                       e.home_team_id IN (:teamIds)
                       OR e.away_team_id IN (:teamIds)
                       )
                   AND o.updated_at > NOW() - INTERVAL '1 day'  
            """, nativeQuery = true)
    List<OddsRow> getBetsForTeamWithMarketId(@Param("teamIds") Set<Integer> teamIds,
                                             @Param("betType") String betType,
                                             @Param("marketId") Integer marketId
    );
}
