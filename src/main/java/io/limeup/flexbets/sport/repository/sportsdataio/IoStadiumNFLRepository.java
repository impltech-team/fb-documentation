package io.limeup.flexbets.sport.repository.sportsdataio;

import io.limeup.flexbets.sport.model.IoStadiumNFL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IoStadiumNFLRepository extends JpaRepository<IoStadiumNFL, Long> {
    Optional<IoStadiumNFL> findByStadiumId(Integer stadiumId);
}
