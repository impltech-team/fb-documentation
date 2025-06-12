package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.Market;
import io.limeup.flexbets.sport.model.enums.MarketType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    Optional<Market> findByExternalIdAndCompetitionId(Integer externalId, Long competitionId);

    @Query(value = """
               SELECT stat_name 
               FROM sport.market_linked_stats mls
                    JOIN sport.market m ON mls.market_id = m.external_id
                    JOIN sport.competition c ON c.id = m.competition_id
            WHERE m.market_type = :marketType AND c.external_id = :competitionId
            """, nativeQuery = true)
    Set<String> findMarketStatsByCompetitionIdAndMarketType(String marketType, Integer competitionId);

    @Query(value = """
               SELECT stat_name 
               FROM sport.market_linked_stats
            WHERE market_id = :marketId
            """, nativeQuery = true)
    Set<String> findMarketStatsByMarketId(Integer marketId);
}
