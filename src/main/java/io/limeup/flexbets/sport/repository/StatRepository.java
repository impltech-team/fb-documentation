package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.EventStat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatRepository extends JpaRepository<EventStat, Long> {
}
