package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.enums.CompetitionType;
import io.limeup.flexbets.sport.model.enums.StatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompetitionRepository extends ExternalIdRepository<Competition, Long> {

    @Query("""
                SELECT c FROM Competition c
                WHERE (:areaId IS NULL OR c.area.externalId = :areaId)
                  AND (:sportId IS NULL OR c.sport.externalId = :sportId)
                  AND (:type IS NULL OR c.type = :type)
                  AND (:gender IS NULL OR c.gender = :gender)
                  AND (:statusType IS NULL OR c.statusType = :statusType)
            """)
    Page<Competition> listCompetitions(
            @Param("areaId") Integer areaId,
            @Param("sportId") Integer sportId,
            @Param("type") CompetitionType type,
            @Param("gender") String gender,
            @Param("statusType") StatusType statusType,
            Pageable pageable
    );
}
