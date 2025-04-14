package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.Participant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipantRepository extends ExternalIdRepository<Participant, Long> {

    @Query(value = """
    SELECT COUNT(*) FROM (
        SELECT DISTINCT p.id
        FROM sport.participant p
        JOIN sport.competition c ON p.competition_id = c.id
        JOIN sport.event_participant ep ON ep.participant_id = p.id
        JOIN sport.event e ON e.id = ep.event_id
        WHERE e.start_date > NOW()
          AND (:competitionId IS NULL OR c.external_id = :competitionId)
          AND (:participantIds IS NULL OR p.external_id IN (:participantIds))
          AND (
               :filter IS NULL OR
               p.team_name ILIKE CONCAT('%', :filter, '%') OR
               p.acronym ILIKE CONCAT('%', :filter, '%')
          )
    ) AS counted
    """, nativeQuery = true)
    long countParticipants(
            @Param("competitionId") Integer competitionId,
            @Param("participantIds") List<Integer> participantIds,
            @Param("filter") String filter
    );

}
