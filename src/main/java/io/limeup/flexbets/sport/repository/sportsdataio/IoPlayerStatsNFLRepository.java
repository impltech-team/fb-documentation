package io.limeup.flexbets.sport.repository.sportsdataio;


import io.limeup.flexbets.sport.model.IoPlayerStatsNFL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IoPlayerStatsNFLRepository extends JpaRepository<IoPlayerStatsNFL, Long> {

    // Find all stats for a specific player
    List<IoPlayerStatsNFL> findByPlayerId(String playerId);

    // Find stats for a player in a specific season
    Optional<IoPlayerStatsNFL> findByPlayerIdAndSeason(String playerId, String season);

    // Find stats for a player with a specific team
    List<IoPlayerStatsNFL> findByPlayerIdAndTeam(String playerId, String team);
}