package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AreaRepository extends ExternalIdRepository<Area, Long> {

    @Query(value = """
            SELECT * FROM sport.area a
            WHERE (:areaIdsEmpty = TRUE OR a.external_id IN (:areaIds))
              AND (:name IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%')))
            """,
            countQuery = """
                    SELECT COUNT(*) FROM sport.area a
                    WHERE (:areaIdsEmpty = TRUE OR a.external_id IN (:areaIds))
                      AND (:name IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%')))
                    """, nativeQuery = true)
    Page<Area> listAreas(
            @Param("areaIds") List<Integer> areaIds,
            @Param("areaIdsEmpty") boolean areaIdsEmpty,
            @Param("name") String name,
            Pageable pageable);

}
