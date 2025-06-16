package io.limeup.flexbets.sport.repository;


import io.limeup.flexbets.sport.model.IoPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IoPlayerRepository extends JpaRepository<IoPlayer, Long> {

    Optional<IoPlayer> findByPlayerId(Long playerID);
}
