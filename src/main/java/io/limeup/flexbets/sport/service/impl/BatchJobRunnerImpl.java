package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.model.PrefetchLog;
import io.limeup.flexbets.sport.repository.PrefetchLogRepository;
import io.limeup.flexbets.sport.service.BatchJobRunner;
import io.limeup.flexbets.sport.service.CompetitionService;
import io.limeup.flexbets.sport.utils.ConstantUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BatchJobRunnerImpl implements BatchJobRunner {

    private final PrefetchLogRepository prefetchLogRepository;

    private final JobRegistry jobRegistry;

    private final JobLauncher jobLauncher;

    private final CompetitionService competitionService;

    public BatchJobRunnerImpl(PrefetchLogRepository prefetchLogRepository, JobRegistry jobRegistry, JobLauncher jobLauncher, CompetitionService competitionService) {
        this.prefetchLogRepository = prefetchLogRepository;
        this.jobRegistry = jobRegistry;
        this.jobLauncher = jobLauncher;
        this.competitionService = competitionService;
    }


    @Override
    public ResponseEntity<String> runStatsPreFetchingJob(Integer days) {
        LocalDate now = LocalDate.now();
        List<Integer> competitionIds = competitionService.findAllExternalIds();

        for (int i = 1; i <= days; i++) {
            LocalDate targetDate = now.plusDays(i);

            for (Integer compId : competitionIds) {
                boolean exists = prefetchLogRepository.existsByPrefetchDateAndCompetitionId(targetDate, compId);
                if (!exists) {
                    PrefetchLog log = PrefetchLog.builder()
                            .prefetchDate(targetDate)
                            .competitionId(compId)
                            .status(PrefetchLog.Status.PENDING)
                            .build();
                    prefetchLogRepository.save(log);
                }
            }
        }

        JobParameters params = new JobParametersBuilder()
                .addLong("runTime", System.currentTimeMillis())
                .toJobParameters();
        Job prefetchJob = null;
        try {
            prefetchJob = jobRegistry.getJob(ConstantUtils.Batch.PRE_FETCH_STAT_SCORE_DATA_JOB);
            jobLauncher.run(prefetchJob, params);
        } catch (NoSuchJobException | JobInstanceAlreadyCompleteException | JobRestartException | JobParametersInvalidException | JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("Prefetch job triggered for next " + days + " days.");
    }
}
