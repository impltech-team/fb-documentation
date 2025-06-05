package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.PrefetchLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PrefetchLogRepository extends JpaRepository<PrefetchLog, Long> {

    boolean existsByPrefetchDateAndCompetitionId(LocalDate prefetchDate, Integer competitionId);

    Optional<PrefetchLog> findPrefetchLogByPrefetchDateAndCompetitionId(LocalDate prefetchDate, Integer competitionId);

    @Query(value = """
    SELECT DISTINCT ON (competition_id) *
    FROM sport.prefetch_log
    ORDER BY competition_id, last_updated DESC, prefetch_date DESC
    """, nativeQuery = true)
    List<PrefetchLog> findLatestPerCompetition();

}
