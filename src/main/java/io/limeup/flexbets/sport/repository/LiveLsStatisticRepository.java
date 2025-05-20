package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.LiveLsStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveLsStatisticRepository extends JpaRepository<LiveLsStatistic, Long> {}
