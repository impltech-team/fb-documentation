package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.Bet;
import io.limeup.flexbets.sport.repository.projection.BetRow;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface BetRepository extends ExternalIdRepository<Bet, Long> {

    @Query(value = """ 
        SELECT b.external_id AS id,
               e.external_id AS eventExternalId,
               m.external_id AS marketExternalId,
               m.market_name AS marketName,
               b.status AS status,
               b.name AS name,
               b.line AS line,
               b.price AS price,
               b.participant_name AS participantName,
               b.last_updated AS lastUpdated
        FROM sport.bet b
            JOIN sport.event e ON b.event_id = e.id
            JOIN sport.market m ON b.market_id = m.id
        WHERE e.external_id IN (:eventExternalIds) AND m.market_type = :marketType
    """, nativeQuery = true)
    List<BetRow> findAllByEventExternalIdInAndMarketType(Collection<Integer> eventExternalIds, String marketType);

    @Query(value = """ 
            SELECT b FROM Bet b
                WHERE b.market.externalId = :marketExternalId AND b.event.lsId = :eventLsId
            """)
    List<Bet> findAllByEventLsIdAndMarketExternalId(Long eventLsId, Integer marketExternalId);
}