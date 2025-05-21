package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.LiveParticipant;
import io.limeup.flexbets.sport.model.LiveParticipantResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LiveParticipantResultRepository extends JpaRepository<LiveParticipantResult, Long> {

    List<LiveParticipantResult> findByParticipant(LiveParticipant p);
    boolean existsByParticipantAndResultId(LiveParticipant participant, Integer resultId);
}