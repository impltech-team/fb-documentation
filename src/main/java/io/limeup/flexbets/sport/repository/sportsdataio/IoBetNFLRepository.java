package io.limeup.flexbets.sport.repository.sportsdataio;

import io.limeup.flexbets.sport.model.IoBetNFL;
import io.limeup.flexbets.sport.model.IoBetOutcomeNFL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Set;

public interface IoBetNFLRepository extends JpaRepository<IoBetNFL, Long> {
    @Query("SELECT b FROM IoBetNFL b JOIN FETCH b.bettingOutcomes WHERE b.event.id = :eventId " +
            "AND b.bettingMarketType IN :marketTypes")
    Set<IoBetNFL> findByEventIdAndMarketTypes(@Param("eventId") Long eventId,
                                              @Param("marketTypes") Collection<String> marketTypes);
}
