package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.SportDTO;
import io.limeup.flexbets.sport.model.EventStat;
import io.limeup.flexbets.sport.model.Sport;
import io.limeup.flexbets.sport.service.AbstractReadService;
import io.limeup.flexbets.sport.service.StatsService;
import io.limeup.flexbets.sport.dto.StatsBatchRequestDTO;
import io.limeup.flexbets.sport.dto.StatsResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsServiceImpl extends AbstractReadService<EventStat, StatsResponseDTO, Long> implements StatsService {

    protected StatsServiceImpl(JpaRepository<EventStat, Long> repository) {
        super(repository);
    }

    @Override
    public List<StatsResponseDTO> listBatchStats(StatsBatchRequestDTO request) {
        return null;
    }
}
