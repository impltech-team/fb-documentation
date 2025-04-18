package io.limeup.flexbets.sport.config;

import io.limeup.flexbets.sport.batch.JobExecutorService;
import io.limeup.flexbets.sport.batch.event.BatchJobEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PostTransactionConfig {

    private final JobLauncher jobLauncher;

    private final ListableJobLocator jobRegistry;

    private final JobExecutorService jobExecutorService;

    /**
     * message received here is published by
     * {@link io.limeup.flexbets.sport.service.impl.BatchJobRunnerImpl#runStatsPreFetchingJob(Integer)}
     **/
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createSpringBatchJob(BatchJobEvent event) throws NoSuchJobException {
        JobParameters params = new JobParametersBuilder()
                .addLong("runTime", System.currentTimeMillis())
                .toJobParameters();
        Job prefetchJob = jobRegistry.getJob(event.getJobName());
        jobExecutorService.runJob(jobLauncher, prefetchJob, params);

        log.info(String.format("Attempting to launch job with name=%s", event.getJobName()));
    }

}
