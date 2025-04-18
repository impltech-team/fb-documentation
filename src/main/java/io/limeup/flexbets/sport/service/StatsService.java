package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.StatsBatchRequestDTO;
import io.limeup.flexbets.sport.dto.StatsResponseDTO;
import io.limeup.flexbets.sport.model.EventStat;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface StatsService extends ExternalIdReadService<EventStat, StatsResponseDTO, Long> {

    List<StatsResponseDTO> listBatchStats(StatsBatchRequestDTO request);

    void fetchStatDataForCompetitionAndDate(Integer competitionId, LocalDate prefetchDate);

}
