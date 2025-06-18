package io.limeup.flexbets.sport.repository.sportsdataio;


import io.limeup.flexbets.sport.model.IoPlayersStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IoPlayerStatsRepository extends JpaRepository<IoPlayersStats, Long> {
    List<IoPlayersStats> findByStatId(Long statID);

    Optional<IoPlayersStats> findTopByPlayerIdOrderByUpdatedDesc(Long playerId);
}
