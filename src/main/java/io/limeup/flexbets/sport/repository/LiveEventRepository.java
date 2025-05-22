package io.limeup.flexbets.sport.repository;


import io.limeup.flexbets.sport.model.LiveEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LiveEventRepository extends JpaRepository<LiveEvent, Long> {

    Optional<LiveEvent> findByLsId(long lsId);
}