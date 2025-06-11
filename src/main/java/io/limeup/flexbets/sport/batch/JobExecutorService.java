package io.limeup.flexbets.sport.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JobExecutorService {

    @Async
    public void runJob(JobLauncher jobLauncher, Job job, JobParameters parameters) {
        try {
            jobLauncher.run(job, parameters);
        } catch (Exception e) {
            log.error("Failed to run job {}", job.getName(), e);
        }
    }
}
