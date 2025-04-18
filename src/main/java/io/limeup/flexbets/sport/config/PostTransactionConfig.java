package io.limeup.flexbets.sport.config;

import io.limeup.flexbets.sport.utils.ConstantUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PostTransactionConfig {

    private final JobLauncher jobLauncher;

    private final ListableJobLocator jobRegistry;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createSpringBatchJob(String jobName)
            throws NoSuchJobException, JobParametersNotFoundException, JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters params = new JobParametersBuilder()
                .addLong("runTime", System.currentTimeMillis())
                .toJobParameters();
        Job prefetchJob = jobRegistry.getJob(jobName);
        jobLauncher.run(prefetchJob, params);

        log.info(String.format("Attempting to launch archive product job with name=%s", jobName));
    }

}
