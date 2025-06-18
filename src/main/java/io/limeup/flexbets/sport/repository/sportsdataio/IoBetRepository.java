package io.limeup.flexbets.sport.repository.sportsdataio;

import io.limeup.flexbets.sport.model.IoBet;
import io.limeup.flexbets.sport.model.IoEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface IoBetRepository extends JpaRepository<IoBet, Long> {

    List<IoBet> findByEvent(IoEvent event);

    @Query(value = """ 
            SELECT b FROM IoBet b
                WHERE b.event.gameId IN (:gameIds) AND b.marketType = :marketType
            """)
    List<IoBet> findAllByMarketTypeAndEventIdInAndAnyBetsAvailableTrue(String marketType, Collection<Integer> gameIds);

}
