package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.LiveEvent;
import io.limeup.flexbets.sport.model.LiveEventBetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveEventBetStatusRepository extends JpaRepository<LiveEventBetStatus, Long> {
    boolean existsByEventAndName(LiveEvent event, String name);
}