package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.SubParticipant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubParticipantRepository extends ExternalIdRepository<SubParticipant, Long> {

    @Query(value = """
    SELECT COUNT(*) FROM (
        SELECT DISTINCT sp.id
        FROM sport.sub_participant sp
        JOIN sport.competition c ON sp.competition_id = c.id
        JOIN sport.participant p ON sp.participant_id = p.id
        JOIN sport.event_participant ep ON ep.participant_id = p.id
        JOIN sport.event e ON e.id = ep.event_id
        WHERE e.start_date > NOW()
          AND sp.position NOT IN ('Coach')
          AND (:competitionId IS NULL OR c.external_id = :competitionId)
          AND (:positions IS NULL OR sp.position IN (:positions))
          AND (:participantIds IS NULL OR p.external_id IN (:participantIds))
          AND (
               :filter IS NULL OR
               sp.player_name ILIKE CONCAT('%', :filter, '%') OR
               p.team_name ILIKE CONCAT('%', :filter, '%') OR
               sp.position ILIKE CONCAT('%', :filter, '%')
          )
    ) AS counted
    """, nativeQuery = true)
    int countSubParticipants(
            @Param("competitionId") Integer competitionId,
            @Param("positions") List<String> positions,
            @Param("participantIds") List<Integer> participantIds,
            @Param("filter") String filter
    );
}
