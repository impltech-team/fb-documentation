package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveEventDetailRepository extends JpaRepository<LiveEventDetail, Long> {
    boolean existsByEventAndDetailId(LiveEvent event, Integer detailId);
}