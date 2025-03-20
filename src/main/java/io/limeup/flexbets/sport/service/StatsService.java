package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.StatsBatchRequestDTO;
import io.limeup.flexbets.sport.dto.StatsResponseDTO;

import java.util.List;

public interface StatsService {
    List<StatsResponseDTO> listBatchStats(StatsBatchRequestDTO request);
}
