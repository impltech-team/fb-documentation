package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.LiveLsBet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveLsBetRepository extends JpaRepository<LiveLsBet, Long> {}
