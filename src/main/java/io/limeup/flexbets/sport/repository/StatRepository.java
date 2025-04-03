package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.EventStat;
import io.limeup.flexbets.sport.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatRepository extends JpaRepository<EventStat, Long>, BatchRepositoryCustom<EventStat> {
}
