package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.repository.projection.EventRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends ExternalIdRepository<Event, Long> {

    @Query(value = """
                WITH sorted AS (
                SELECT
                    e.id AS event_id
                    FROM sport.event e
                        JOIN sport.competition c ON e.competition_id = c.id
                        LEFT JOIN sport.event_sub_participant esp ON esp.event_id = e.id
                        LEFT JOIN sport.participant p ON p.id = esp.participant_id
                        LEFT JOIN sport.venue v ON v.id = e.venue_id
                        WHERE c.external_id = :competitionId
                        AND e.start_date >= COALESCE(:dateFrom, e.start_date)
                        AND e.start_date <= COALESCE(:dateTo, e.start_date)
                        AND e.status = COALESCE(:status, e.status)
                        AND (:venueIds IS NULL OR v.external_id IN (:venueIds))
                        AND (:participantIds IS NULL OR p.external_id IN (:participantIds))
                        GROUP BY e.id
                        ORDER BY
                        CASE WHEN :sortBy = 'event_name' AND :sortOrder = 'asc' THEN e.name END ASC,
                        CASE WHEN :sortBy = 'event_name' AND :sortOrder = 'desc' THEN e.name END DESC,
                        CASE WHEN :sortBy = 'event_date' AND :sortOrder = 'asc' THEN e.start_date END ASC,
                        CASE WHEN :sortBy = 'event_date' AND :sortOrder = 'desc' THEN e.start_date END DESC
                    ),
                    paged AS (
                    SELECT event_id
                    FROM sorted
                    LIMIT :limit OFFSET :offset
                )
                SELECT
                    e.id,
                    e.external_id,
                    e.name AS event_name,
                    e.start_date,
                    e.status,
                    c.external_id AS competition_id,
                    c.name AS competition_name,
                    p.external_id AS participant_id,
                    p.team_name,
                    p.acronym,
                    COALESCE(v.country || ', ' || v.city, '') AS venue_location,
                    v.name AS venue_name,
                    v.external_id AS venue_id
                    FROM paged page
                        JOIN sport.event e ON e.id = page.event_id
                        JOIN sport.competition c ON e.competition_id = c.id
                        LEFT JOIN sport.venue v ON e.venue_id = v.id
                        LEFT JOIN sport.event_sub_participant esp ON esp.event_id = e.id
                        LEFT JOIN sport.participant p ON p.id = esp.participant_id
                GROUP BY e.id, p.id, c.id, v.id
                ORDER BY
                CASE WHEN :sortBy = 'event_name' AND :sortOrder = 'asc' THEN e.name END ASC,
                CASE WHEN :sortBy = 'event_name' AND :sortOrder = 'desc' THEN e.name END DESC,
                CASE WHEN :sortBy = 'event_date' AND :sortOrder = 'asc' THEN e.start_date END ASC,
                CASE WHEN :sortBy = 'event_date' AND :sortOrder = 'desc' THEN e.start_date END DESC
            """, nativeQuery = true)
    List<EventRow> listEvents(
            @Param("competitionId") Integer competitionId,
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
                SELECT COUNT(DISTINCT e.id)
                FROM sport.event e
                JOIN sport.competition c ON e.competition_id = c.id
                LEFT JOIN sport.venue v ON e.venue_id = v.id
                LEFT JOIN sport.event_sub_participant esp ON esp.event_id = e.id
                LEFT JOIN sport.participant p ON p.id = esp.participant_id
                WHERE c.external_id = :competitionId
                    AND e.start_date >= COALESCE(:dateFrom, e.start_date)
                    AND e.start_date <= COALESCE(:dateTo, e.start_date)
                    AND e.status = COALESCE(:status, e.status)
                    AND (:venueIds IS NULL OR v.external_id IN (:venueIds))
                    AND (:participantIds IS NULL OR p.external_id IN (:participantIds))
            """, nativeQuery = true)
    int countEvents(
            @Param("competitionId") Integer competitionId,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo,
            @Param("status") String status,
            @Param("venueIds") List<Integer> venueIds,
            @Param("participantIds") List<Integer> participantIds
    );

    Optional<Event> findByLsId(Long lsId);

    List<Event> findAllByStartDateBetweenAndLsIdIsNull(LocalDateTime startDate, LocalDateTime endDate);
}
