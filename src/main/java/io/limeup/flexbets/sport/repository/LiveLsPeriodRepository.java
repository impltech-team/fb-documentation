package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.LiveLsPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveLsPeriodRepository extends JpaRepository<LiveLsPeriod, Long> {}
