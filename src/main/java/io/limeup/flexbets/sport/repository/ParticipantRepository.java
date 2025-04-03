package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long>, BatchRepositoryCustom<Participant> {
}
