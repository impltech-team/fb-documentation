package io.limeup.flexbets.sport.repository.sportsdataio;


import io.limeup.flexbets.sport.model.IoPlayerGameStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IoPlayerGamesStatsRepository extends JpaRepository<IoPlayerGameStats, Long> {

    Optional<IoPlayerGameStats> findByStatId(Long statId);

    List<IoPlayerGameStats> findAllByPlayerIdOrderByGameDatetimeDesc(Long playerId);
}
