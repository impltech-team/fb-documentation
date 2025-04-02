package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<Sport, Long> {
}
