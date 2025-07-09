package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.EventDTO;
import io.limeup.flexbets.sport.dto.FullEventDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.statscore.*;
import io.limeup.flexbets.sport.model.Venue;
import io.limeup.flexbets.sport.repository.EventRepository;
import io.limeup.flexbets.sport.repository.projection.EventRow;
import io.limeup.flexbets.sport.service.VenueService;
import io.limeup.flexbets.sport.service.statscore.StatScoreClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private StatScoreClient statScoreClient;

    @Mock
    private VenueService venueService;

    @InjectMocks
    private EventServiceImpl eventService;

    private RequestQueryDTO requestQuery;

    @BeforeEach
    void setUp() {
        requestQuery = new RequestQueryDTO();
        requestQuery.setPage(1);
        requestQuery.setPageSize(10);
        requestQuery.setSortBy("event_date");
        requestQuery.setSortOrder("asc");
    }

    @Test
    void listEventsShouldReturnPaginatedEvents() {
        when(eventRepository.countEvents(any(), any(), any(), any(), anyList(), anyList()))
                .thenReturn(2);

        EventRow eventRow = mock(EventRow.class);
        when(eventRow.getId()).thenReturn(1L);
        when(eventRow.getExternalId()).thenReturn(101);
        when(eventRow.getEventName()).thenReturn("Final Match");
        when(eventRow.getStartDate()).thenReturn(LocalDateTime.now());
        when(eventRow.getStatus()).thenReturn("scheduled");
        when(eventRow.getCompetitionId()).thenReturn(5);
        when(eventRow.getCompetitionName()).thenReturn("Champions League");
        when(eventRow.getParticipantId()).thenReturn(10);
        when(eventRow.getTeamName()).thenReturn("Team A");
        when(eventRow.getAcronym()).thenReturn("TA");
        when(eventRow.getVenueId()).thenReturn(7);
        when(eventRow.getVenueLocation()).thenReturn("Kyiv");
        when(eventRow.getVenueName()).thenReturn("Olympic Stadium");


        when(eventRepository.listEvents(any(), any(), any(), any(), anyList(), anyList(),
                any(), any(), anyInt(), anyInt()))
                .thenReturn(List.of(eventRow));

        PaginatedResponse<EventDTO> response = eventService.listEvents(
                1,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                null,
                null,
                "scheduled",
                requestQuery
        );

        assertThat(response).isNotNull();
        assertThat(response.getItems()).isNotEmpty();
        assertThat(response.getCount()).isEqualTo(2);

        verify(eventRepository).countEvents(any(), any(), any(), any(), anyList(), anyList());
        verify(eventRepository).listEvents(any(), any(), any(), any(), anyList(), anyList(),
                any(), any(), anyInt(), anyInt());
    }

    @Test
    void getEventByIdShouldReturnFullEventDTO() {
        EventRow row = mock(EventRow.class);
        when(eventRepository.getEventDetails(eq(1))).thenReturn(List.of(row));
        StatScoreCompetitionDTO competitionDTO = new StatScoreCompetitionDTO();

        StatScoreSeasonDTO season = new StatScoreSeasonDTO();
        StatScoreStageDTO stage = new StatScoreStageDTO();
        StatScoreGroupDTO group = new StatScoreGroupDTO();
        StatScoreEventDTO eventInner = new StatScoreEventDTO();

        eventInner.setVenueId(10);
        group.setEvent(eventInner);
        stage.setGroup(group);
        season.setStage(stage);
        competitionDTO.setSeason(season);

        StatScoreResponse<StatScoreCompetitionDTO> response = new StatScoreResponse<>();
        StatScoreResponse.ApiWrapper<StatScoreCompetitionDTO> api = new StatScoreResponse.ApiWrapper<>();
        api.setData(competitionDTO);
        response.setApi(api);

        when(statScoreClient.getEventById(anyInt(), eq(false)))
                .thenReturn(Mono.just(response));

        Venue venue = new Venue();
        venue.setExternalId(10);

        when(venueService.readByExternalId(eq(10)))
                .thenReturn(Optional.of(venue));

        EventDTO fullEventDTO = eventService.getEventById(1);

        assertThat(fullEventDTO).isNotNull();
        verify(statScoreClient).getEventById(anyInt(), eq(false));
        verify(venueService).readByExternalId(eq(10));
    }

}

