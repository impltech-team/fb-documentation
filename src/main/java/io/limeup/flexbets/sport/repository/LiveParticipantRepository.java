package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.LiveParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LiveParticipantRepository extends JpaRepository<LiveParticipant, Long> {

    List<LiveParticipant> findByEventId(Long id);
}