package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.EventSubParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventSubParticipantRepository extends JpaRepository<EventSubParticipant, Long> {

    @Query("""
                SELECT esp
                FROM EventSubParticipant esp
                WHERE esp.event.externalId = :eventExternalId
                  AND esp.subParticipant.externalId IN :subParticipantExternalIds
            """)
    List<EventSubParticipant> findExistingByEventAndSubParticipantIds(@Param("eventExternalId") Integer eventExternalId,
                                                                      @Param("subParticipantExternalIds") List<Integer> subParticipantExternalIds);

}
