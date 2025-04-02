package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
