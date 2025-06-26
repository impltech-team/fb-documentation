package io.limeup.flexbets.sport.repository.sportsdataio;


import io.limeup.flexbets.sport.model.IoTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IoTeamRepository extends JpaRepository<IoTeam, Long> {

    Optional<IoTeam> findByTeamId(Long teamId);

    List<IoTeam> findAllByTeamIdIn(Set<Long> collect);
}
