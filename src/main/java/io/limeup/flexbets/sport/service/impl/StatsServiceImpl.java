package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.service.StatsService;
import io.limeup.flexbets.sport.dto.StatsBatchRequestDTO;
import io.limeup.flexbets.sport.dto.StatsResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsServiceImpl implements StatsService {
    @Override
    public List<StatsResponseDTO> listBatchStats(StatsBatchRequestDTO request) {
        return null;
    }
}
