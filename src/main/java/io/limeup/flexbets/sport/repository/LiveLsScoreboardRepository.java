package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.LiveLsScoreboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveLsScoreboardRepository extends JpaRepository<LiveLsScoreboard, Long> {}
