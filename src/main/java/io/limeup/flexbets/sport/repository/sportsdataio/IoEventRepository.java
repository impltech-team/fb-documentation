package io.limeup.flexbets.sport.repository.sportsdataio;

import io.limeup.flexbets.sport.model.IoEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IoEventRepository extends JpaRepository<IoEvent, Long> {

    Optional <IoEvent> findByGameId(Long aLong);

    List<IoEvent> findAllByDatetimeUtcBetween(LocalDateTime from, LocalDateTime to);
}
