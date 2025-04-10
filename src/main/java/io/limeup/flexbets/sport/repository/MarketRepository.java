package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.Market;
import io.limeup.flexbets.sport.model.MarketType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarketRepository extends ExternalIdRepository<Market, Long> {

    @Query("""
        SELECT m FROM Market m
        WHERE m.competition.externalId = :competitionId
          AND (:marketType IS NULL OR m.marketType = :marketType)
    """)
    List<Market> findByCompetitionAndOptionalType(
            @Param("competitionId") Integer competitionId,
            @Param("marketType") MarketType marketType
    );

}
