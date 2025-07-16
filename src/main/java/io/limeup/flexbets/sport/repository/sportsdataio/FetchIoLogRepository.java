package io.limeup.flexbets.sport.repository.sportsdataio;

import io.limeup.flexbets.sport.dto.statscore.params.FetchIoType;
import io.limeup.flexbets.sport.dto.statscore.params.SportIoType;
import io.limeup.flexbets.sport.model.IoFetchLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FetchIoLogRepository extends JpaRepository<IoFetchLog, Long> {

        @Query("""
       SELECT COUNT(l) > 0
       FROM IoFetchLog l
       WHERE l.fetchType = :type
         AND l.sportType     = :sport
         AND l.startedAt   >= :since
    """)
        boolean existsRecently(@Param("type")  FetchIoType type,
                               @Param("sport") SportIoType sport,
                               @Param("since") LocalDateTime since);

}

