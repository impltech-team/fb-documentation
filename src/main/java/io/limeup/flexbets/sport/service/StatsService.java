package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.StatsBatchRequestDTO;
import io.limeup.flexbets.sport.dto.StatsResponseDTO;
import io.limeup.flexbets.sport.model.EventStat;

import java.util.List;

public interface StatsService extends ReadService<EventStat, StatsResponseDTO, Long> {

    List<StatsResponseDTO> listBatchStats(StatsBatchRequestDTO request);

    void fetchStatData(int durationDays);

}
