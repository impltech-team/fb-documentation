package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.LiveLsParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiveLsParticipantRepository extends JpaRepository<LiveLsParticipant, Long> {
    List<LiveLsParticipant> findByFixtureId(Long lsId);
}
