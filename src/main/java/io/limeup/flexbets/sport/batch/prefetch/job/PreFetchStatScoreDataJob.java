package io.limeup.flexbets.sport.batch.prefetch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PreFetchStatScoreDataJob {
    @Bean
    public Job prefetchJob(JobRepository jobRepository,
                           @Qualifier("preFetchStatScoreDataStepDef") Step step) {
        return new JobBuilder("prefetchJob", jobRepository)
                .start(step)
                .build();
    }
}
