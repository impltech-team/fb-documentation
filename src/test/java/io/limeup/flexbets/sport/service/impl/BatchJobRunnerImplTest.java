package io.limeup.flexbets.sport.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import io.limeup.flexbets.sport.batch.event.BatchJobEvent;
import io.limeup.flexbets.sport.model.PrefetchLog;
import io.limeup.flexbets.sport.repository.PrefetchLogRepository;
import io.limeup.flexbets.sport.service.CompetitionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class BatchJobRunnerImplTest {

    @Mock
    private PrefetchLogRepository prefetchLogRepository;

    @Mock
    private CompetitionService competitionService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private BatchJobRunnerImpl batchJobRunner;

    @BeforeEach
    void setUp() {
        //nothing to set up
    }

    @Test
    void runStatsPreFetchingJobShouldCreateLogsAndPublishEvent() {
        when(competitionService.findAllExternalIds()).thenReturn(List.of(1, 2));

        ResponseEntity<String> response = batchJobRunner.runStatsPreFetchingJob(2);

        verify(prefetchLogRepository, times(4)).save(any(PrefetchLog.class));
        verify(eventPublisher, times(1)).publishEvent(any(BatchJobEvent.class));

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).contains("Prefetch job triggered for next 2 days.");
    }

    @Test
    void runStatsPreFetchingJobWhenLogsAlreadyExistShouldNotSave() {
        when(competitionService.findAllExternalIds()).thenReturn(List.of(1));

        ResponseEntity<String> response = batchJobRunner.runStatsPreFetchingJob(1);

        verify(prefetchLogRepository, never()).save(any(PrefetchLog.class));

        verify(eventPublisher, times(1)).publishEvent(any(BatchJobEvent.class));

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).contains("Prefetch job triggered for next 1 days.");
    }

    @Test
    void runStatsPreFetchingJobShouldSaveCorrectPrefetchLogFields() {
        when(competitionService.findAllExternalIds()).thenReturn(List.of(42));

        batchJobRunner.runStatsPreFetchingJob(1);

        ArgumentCaptor<PrefetchLog> captor = ArgumentCaptor.forClass(PrefetchLog.class);
        verify(prefetchLogRepository).save(captor.capture());

        PrefetchLog savedLog = captor.getValue();
        assertThat(savedLog.getCompetitionId()).isEqualTo(42);
        assertThat(savedLog.getStatus()).isEqualTo(PrefetchLog.Status.PENDING);
        assertThat(savedLog.getPrefetchDate()).isEqualTo(LocalDate.now().plusDays(1));
    }
}

