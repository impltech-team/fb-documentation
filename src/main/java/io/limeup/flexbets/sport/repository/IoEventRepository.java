package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.IoEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IoEventRepository extends JpaRepository<IoEvent, Long> {

    Optional <IoEvent> findByGameId(Long aLong);
}
