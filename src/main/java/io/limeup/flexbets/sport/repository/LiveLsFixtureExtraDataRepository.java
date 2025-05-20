package io.limeup.flexbets.sport.repository;


import io.limeup.flexbets.sport.model.LiveLsFixtureExtraData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveLsFixtureExtraDataRepository extends JpaRepository<LiveLsFixtureExtraData, Long> {}