package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.LiveLsEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveLsEventRepository extends JpaRepository<LiveLsEvent, Long> {
}