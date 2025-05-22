package io.limeup.flexbets.sport.repository;


import io.limeup.flexbets.sport.model.LiveLsFixtureExtraData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiveLsFixtureExtraDataRepository extends JpaRepository<LiveLsFixtureExtraData, Long> {
    List<LiveLsFixtureExtraData> findByFixtureId(Long lsId);
}