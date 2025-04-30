package io.limeup.flexbets.sport.service.statscore.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreGroupDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSeasonDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreStageDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.service.statscore.StatScoreDataService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class StatScoreDataServiceImplTest {

    @Mock
    private StatScoreProxyService statScoreProxyService;

    @InjectMocks
    private StatScoreDataServiceImpl statScoreDataService;

    @Test
    void getAllEventsShouldReturnListOfEvents() {
        StatScoreEventDTO event = new StatScoreEventDTO();
        StatScoreGroupDTO group = new StatScoreGroupDTO();
        group.setEvents(List.of(event));

        StatScoreStageDTO stage = new StatScoreStageDTO();
        stage.setGroups(List.of(group));

        StatScoreSeasonDTO season = new StatScoreSeasonDTO();
        season.setStages(List.of(stage));

        StatScoreCompetitionDTO competition = new StatScoreCompetitionDTO();
        competition.setSeasons(List.of(season));

        PaginatedResponse<StatScoreCompetitionDTO> paginatedResponse = PaginatedResponse.<StatScoreCompetitionDTO>builder()
                .items(List.of(competition))
                .build();

        when(statScoreProxyService.listEvents(any(EventQueryParams.class), eq(true)))
                .thenReturn(paginatedResponse);

        List<StatScoreEventDTO> events = statScoreDataService.getAllEvents(new EventQueryParams());

        assertThat(events).isNotEmpty();
        assertThat(events).hasSize(1);

        verify(statScoreProxyService).listEvents(any(EventQueryParams.class), eq(true));
    }

    @Test
    void getAllEventsWithContextShouldReturnEventContexts() {
        StatScoreEventDTO event = new StatScoreEventDTO();
        StatScoreGroupDTO group = new StatScoreGroupDTO();
        group.setEvents(List.of(event));

        StatScoreStageDTO stage = new StatScoreStageDTO();
        stage.setGroups(List.of(group));

        StatScoreSeasonDTO season = new StatScoreSeasonDTO();
        season.setStages(List.of(stage));

        StatScoreCompetitionDTO competition = new StatScoreCompetitionDTO();
        competition.setSeasons(List.of(season));

        PaginatedResponse<StatScoreCompetitionDTO> paginatedResponse = PaginatedResponse.<StatScoreCompetitionDTO>builder()
                .items(List.of(competition))
                .build();

        when(statScoreProxyService.listEvents(any(EventQueryParams.class), eq(true)))
                .thenReturn(paginatedResponse);

        List<StatScoreDataService.EventContext> eventContexts = statScoreDataService.getAllEventsWithContext(new EventQueryParams());

        assertThat(eventContexts).isNotEmpty();
        assertThat(eventContexts).hasSize(1);
        assertThat(eventContexts.get(0).event()).isEqualTo(event);

        verify(statScoreProxyService).listEvents(any(EventQueryParams.class), eq(true));
    }
}

