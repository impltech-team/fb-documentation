package io.limeup.flexbets.sport.repository.sportsdataio;


import io.limeup.flexbets.sport.model.IoTeamNFL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IoTeamNFLRepository extends JpaRepository<IoTeamNFL, Long> {

    Optional<IoTeamNFL> findByTeamId(Long teamId);

    List<IoTeamNFL> findAllByTeamIdIn(Set<Long> collect);
}

