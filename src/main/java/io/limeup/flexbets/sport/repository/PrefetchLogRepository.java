package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.PrefetchLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PrefetchLogRepository extends JpaRepository<PrefetchLog, Long> {

    boolean existsByPrefetchDateAndCompetitionId(LocalDate prefetchDate, Integer competitionId);

    Optional<PrefetchLog> findPrefetchLogByPrefetchDateAndCompetitionId(LocalDate prefetchDate, Integer competitionId);

}
