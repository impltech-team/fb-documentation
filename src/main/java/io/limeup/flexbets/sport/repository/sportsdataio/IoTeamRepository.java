package io.limeup.flexbets.sport.repository.sportsdataio;


import io.limeup.flexbets.sport.model.IoTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IoTeamRepository extends JpaRepository<IoTeam, Long> {

    Optional<IoTeam> findByTeamId(Long teamId);
}
