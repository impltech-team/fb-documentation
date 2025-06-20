package io.limeup.flexbets.sport.repository.sportsdataio;

import io.limeup.flexbets.sport.model.IoBet;
import io.limeup.flexbets.sport.model.IoEvent;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataBetRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

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
           select b
           from IoBet b
           left join fetch b.betOutcomes
           where b.event = :event
           """)
    List<IoBet> findByEventWithOutcomes(@Param("event") IoEvent event);

}
