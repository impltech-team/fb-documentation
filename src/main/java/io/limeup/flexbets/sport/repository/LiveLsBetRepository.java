package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.LiveLsBet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiveLsBetRepository extends JpaRepository<LiveLsBet, Long> {
    List<LiveLsBet> findByMarketIdIn(List<Long> list);
}
