package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.batch.event.BatchJobEvent;
import io.limeup.flexbets.sport.model.PrefetchLog;
import io.limeup.flexbets.sport.repository.PrefetchLogRepository;
import io.limeup.flexbets.sport.service.BatchJobRunner;
import io.limeup.flexbets.sport.service.CompetitionService;
import io.limeup.flexbets.sport.utils.ConstantUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class BatchJobRunnerImpl implements BatchJobRunner {

    private final PrefetchLogRepository prefetchLogRepository;

    private final CompetitionService competitionService;

    private final ApplicationEventPublisher eventPublisher;

    public BatchJobRunnerImpl(PrefetchLogRepository prefetchLogRepository,
                              CompetitionService competitionService, ApplicationEventPublisher eventPublisher) {
        this.prefetchLogRepository = prefetchLogRepository;
        this.competitionService = competitionService;
        this.eventPublisher = eventPublisher;
    }


    @Override
    public ResponseEntity<String> runStatsPreFetchingJob(Integer days) {
        LocalDate now = LocalDate.now();
        List<Integer> competitionIds = competitionService.findAllExternalIds();

        for (int i = 1; i <= days; i++) {
            LocalDate targetDate = now.plusDays(i);

            for (Integer compId : competitionIds) {
                PrefetchLog log = PrefetchLog.builder()
                        .prefetchDate(targetDate)
                        .competitionId(compId)
                        .status(PrefetchLog.Status.PENDING)
                        .build();
                prefetchLogRepository.save(log);
            }
        }

        eventPublisher.publishEvent(new BatchJobEvent(ConstantUtils.Batch.PRE_FETCH_STAT_SCORE_DATA_JOB));
        return ResponseEntity.ok("Prefetch job triggered for next " + days + " days.");
    }
}
