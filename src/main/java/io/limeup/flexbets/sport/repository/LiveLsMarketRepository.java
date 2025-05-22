package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.LiveLsMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiveLsMarketRepository extends JpaRepository<LiveLsMarket, Long> {
    List<LiveLsMarket> findByFixtureId(Long lsId);
}
