package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.SubParticipant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubParticipantRepository extends ExternalIdRepository<SubParticipant, Long> {

    @Query(value = """
                SELECT COUNT(DISTINCT sp.id)
                    FROM sport.sub_participant sp
                    JOIN sport.event_sub_participant esp ON esp.sub_participant_id = sp.id
                    JOIN sport.participant p ON p.id = esp.participant_id
                    JOIN sport.event e ON e.id = esp.event_id
                    JOIN sport.competition c ON c.id = sp.competition_id
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
            """, nativeQuery = true)
    Long countSubParticipants(
            @Param("competitionId") Integer competitionId,
            @Param("positions") List<String> positions,
            @Param("participantIds") List<Integer> participantIds,
            @Param("filter") String filter
    );
}
