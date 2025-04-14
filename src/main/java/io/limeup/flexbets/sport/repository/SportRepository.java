package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.Sport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SportRepository extends ExternalIdRepository<Sport, Long> {

    @Query(value = """
        SELECT * FROM sport.sport s
        WHERE (:sportIdsEmpty = TRUE OR s.external_id IN (:sportIds))
          AND (:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')))
        """,
            countQuery = """
        SELECT COUNT(*) FROM sport.sport s
        WHERE (:sportIdsEmpty = TRUE OR s.external_id IN (:sportIds))
          AND (:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')))
        """, nativeQuery = true)
    Page<Sport> listSports(
            @Param("sportIds") List<Integer> sportIds,
            @Param("sportIdsEmpty") boolean sportIdsEmpty,
            @Param("name") String name,
            Pageable pageable
    );

}
