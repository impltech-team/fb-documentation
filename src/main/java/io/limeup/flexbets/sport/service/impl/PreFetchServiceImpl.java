package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.PrefetchStatusDTO;
import io.limeup.flexbets.sport.model.PrefetchLog;
import io.limeup.flexbets.sport.repository.PrefetchLogRepository;
import io.limeup.flexbets.sport.service.PreFetchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class PreFetchServiceImpl implements PreFetchService {

    private final PrefetchLogRepository prefetchLogRepository;

    public PreFetchServiceImpl(PrefetchLogRepository prefetchLogRepository) {
        this.prefetchLogRepository = prefetchLogRepository;
    }

    @Override
    public List<PrefetchStatusDTO> preFetchStatus() {
        List<PrefetchLog> all = prefetchLogRepository.findLatestPerCompetition();
        return all.stream()
                .map(entity -> {
                    PrefetchStatusDTO dto = new PrefetchStatusDTO();
                    dto.setId(entity.getId());
                    dto.setPrefetchDate(entity.getPrefetchDate());
                    dto.setCompetitionId(entity.getCompetitionId());
                    dto.setStatus(entity.getStatus());
                    dto.setLastUpdated(entity.getLastUpdated());
                    dto.setErrorMessage(entity.getErrorMessage());
                    return dto;
                })
                .toList();
    }
}
