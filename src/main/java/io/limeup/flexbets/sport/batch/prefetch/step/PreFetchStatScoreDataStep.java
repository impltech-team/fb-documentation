package io.limeup.flexbets.sport.batch.prefetch.step;

import io.limeup.flexbets.sport.batch.prefetch.listener.MarkAsFailedSkipListener;
import io.limeup.flexbets.sport.model.PrefetchLog;
import io.limeup.flexbets.sport.service.StatsService;
import io.limeup.flexbets.sport.service.trade360.Trade360DataService;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PreFetchStatScoreDataStep {

    private final EntityManagerFactory entityManagerFactory;
    private final StatsService statsService;
    private final Trade360DataService dataService;

    @Bean
    public Step preFetchStatScoreDataStepDef(JobRepository jobRepository,
                                             PlatformTransactionManager transactionManager) {
        return new StepBuilder("preFetchStatScoreDataStepDef", jobRepository)
                .<PrefetchLog, PrefetchLog>chunk(10, transactionManager)
                .reader(prefetchLogReader())
                .processor(prefetchProcessor())
                .writer(prefetchWriter())
                .faultTolerant()
                .skip(Exception.class)
                .listener(markAsFailedSkipListener())
                .build();
    }

    @Bean
    public SkipListener<PrefetchLog, PrefetchLog> markAsFailedSkipListener() {
        return new MarkAsFailedSkipListener();
    }

    @Bean
    public ItemReader<PrefetchLog> prefetchLogReader() {
        return new JpaPagingItemReaderBuilder<PrefetchLog>()
                .name("prefetchLogReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT p FROM PrefetchLog p WHERE p.status = 'PENDING' OR p.status = 'FAILED'")
                .pageSize(10)
                .build();
    }

    @Bean
    public ItemProcessor<PrefetchLog, PrefetchLog> prefetchProcessor() {
        return log -> {
            statsService.fetchStatDataForCompetitionAndDate(log.getCompetitionId(), log.getPrefetchDate());
            dataService.fetchDataFromTrade360ApiForCompetitionAndDate(log.getCompetitionId(), log.getPrefetchDate());
            log.setStatus(PrefetchLog.Status.SUCCESS);
            log.setErrorMessage(null);
            return log;
        };
    }

    @Bean
    public ItemWriter<PrefetchLog> prefetchWriter() {
        return new JpaItemWriterBuilder<PrefetchLog>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

}
