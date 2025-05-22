package io.limeup.flexbets.sport.repository;


import io.limeup.flexbets.sport.model.LiveLsPeriodIncident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiveLsPeriodIncidentRepository extends JpaRepository<LiveLsPeriodIncident, Long> {
    List<LiveLsPeriodIncident> findByPeriodIdIn(List<Long> periodIds);
}

