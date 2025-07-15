package io.limeup.flexbets.sport.repository.sportsdataio;

import io.limeup.flexbets.sport.model.IoEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IoEventRepository extends JpaRepository<IoEvent, Long> {

    Optional <IoEvent> findByGameId(Long aLong);

    List<IoEvent> findAllByDatetimeUtcBetween(LocalDateTime from, LocalDateTime to);
    @Query(value = """
            WITH sorted AS (
                SELECT e.id
                FROM sport.io_event e
                WHERE    e.datetime_utc >= COALESCE(:dateFrom, e.datetime_utc)
                         AND e.datetime_utc <= COALESCE(:dateTo, e.datetime_utc) 
            
                  AND (:status IS NULL OR LOWER(e.status) = LOWER(:status))
                  AND (:venueIds IS NULL OR e.stadium_id IN (:venueIds))
                  AND (
                        :participantIds IS NULL OR
                        e.home_team_id IN (:participantIds) OR
                        e.away_team_id IN (:participantIds)
                  )
                ORDER BY
                    CASE WHEN :sortBy = 'event_name' AND :sortOrder = 'asc' THEN e.home_team || ' vs ' || e.away_team END ASC,
                    CASE WHEN :sortBy = 'event_name' AND :sortOrder = 'desc' THEN e.home_team || ' vs ' || e.away_team END DESC,
                    CASE WHEN :sortBy = 'event_date' AND :sortOrder = 'asc' THEN e.datetime_utc END ASC,
                    CASE WHEN :sortBy = 'event_date' AND :sortOrder = 'desc' THEN e.datetime_utc END DESC
            ), paged AS (
                SELECT id
                FROM sorted
                LIMIT :limit OFFSET :offset
            )
            SELECT e.*
            FROM paged p
                     JOIN sport.io_event e ON e.id = p.id
            ORDER BY
                CASE WHEN :sortBy = 'event_name' AND :sortOrder = 'asc' THEN e.home_team || ' vs ' || e.away_team END ASC,
                CASE WHEN :sortBy = 'event_name' AND :sortOrder = 'desc' THEN e.home_team || ' vs ' || e.away_team END DESC,
                CASE WHEN :sortBy = 'event_date' AND :sortOrder = 'asc' THEN e.datetime_utc END ASC,
                CASE WHEN :sortBy = 'event_date' AND :sortOrder = 'desc' THEN e.datetime_utc END DESC
            """, nativeQuery = true)
    List<IoEvent> listEvents(
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo,
            @Param("status") String status,
            @Param("venueIds") List<Integer> venueIds,
            @Param("participantIds") List<Integer> participantIds,
            @Param("sortBy") String sortBy,
            @Param("sortOrder") String sortOrder,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Query(value = """
    SELECT COUNT(*)
    FROM sport.io_event e
    WHERE e.datetime_utc >= COALESCE(:dateFrom, e.datetime_utc)
      AND e.datetime_utc <= COALESCE(:dateTo, e.datetime_utc)
      AND (:status IS NULL OR LOWER(e.status) = LOWER(:status))
      AND (:venueIds IS NULL OR e.stadium_id IN (:venueIds))
      AND (
            :participantIds IS NULL OR
            e.home_team_id IN (:participantIds) OR
            e.away_team_id IN (:participantIds)
      )
    """, nativeQuery = true)
    long countEvents(
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo,
            @Param("status") String status,
            @Param("venueIds") List<Integer> venueIds,
            @Param("participantIds") List<Integer> participantIds
    );

}

