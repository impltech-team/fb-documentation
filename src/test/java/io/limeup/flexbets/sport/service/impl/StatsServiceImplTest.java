package io.limeup.flexbets.sport.service.impl;

import static io.limeup.flexbets.sport.model.EventStatus.SCHEDULED;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSeasonDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.mapper.EventMapper;
import io.limeup.flexbets.sport.mapper.ParticipantMapper;
import io.limeup.flexbets.sport.mapper.SubParticipantMapper;
import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.Participant;
import io.limeup.flexbets.sport.model.SubParticipant;
import io.limeup.flexbets.sport.model.Venue;
import io.limeup.flexbets.sport.repository.EventRepository;
import io.limeup.flexbets.sport.repository.EventSubParticipantRepository;
import io.limeup.flexbets.sport.repository.ParticipantRepository;
import io.limeup.flexbets.sport.repository.StatRepository;
import io.limeup.flexbets.sport.repository.SubParticipantRepository;
import io.limeup.flexbets.sport.service.AreaService;
import io.limeup.flexbets.sport.service.CompetitionService;
import io.limeup.flexbets.sport.service.VenueService;
import io.limeup.flexbets.sport.service.statscore.StatScoreDataService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StatsServiceImplTest {

    @Mock
    private StatScoreDataService statScoreDataService;

    @Mock
    private StatScoreProxyService statScoreProxyService;

    @Mock
    private ParticipantMapper participantMapper;

    @Mock
    private SubParticipantMapper subParticipantMapper;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private CompetitionService competitionService;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private VenueService venueService;

    @Mock
    private SubParticipantRepository subParticipantRepository;

    @Mock
    private EventSubParticipantRepository eventSubParticipantRepository;

    @Mock
    private StatRepository statRepository;

    @Mock
    private AreaService areaService;

    @InjectMocks
    private StatsServiceImpl statsService;

    private StatScoreDataService.EventContext eventContext;

    @BeforeEach
    void setup() {
        eventContext = mock(StatScoreDataService.EventContext.class);
        StatScoreEventDTO eventDTO = new StatScoreEventDTO();
        eventDTO.setId(100);
        StatScoreEventParticipantDTO dto = new StatScoreEventParticipantDTO();
        dto.setId(1);
        dto.setName("Team A");
        eventDTO.setParticipants(List.of(dto));
        when(eventContext.event()).thenReturn(eventDTO);

        StatScoreSeasonDTO seasonDTO = new StatScoreSeasonDTO();
        seasonDTO.setId(10);
        when(eventContext.season()).thenReturn(seasonDTO);

        StatScoreCompetitionDTO competitionDTO = new StatScoreCompetitionDTO();
        competitionDTO.setId(20);
        when(eventContext.competition()).thenReturn(competitionDTO);
        when(areaService.readByExternalId(any())).thenReturn(Optional.of(new Area()));
    }

    @Test
    void fetchStatDataForCompetitionAndDateShouldProcessEventsAndParticipants() {
        when(statScoreDataService.getAllEventsWithContext(any()))
                .thenReturn(List.of(eventContext));

        when(competitionService.readByExternalId(anyInt()))
                .thenReturn(Optional.of(new Competition()));

        when(participantRepository.findByExternalIdIn(anyList()))
                .thenReturn(Collections.emptyList());

        when(eventRepository.findByExternalIdIn(anyList()))
                .thenReturn(Collections.emptyList());

        when(eventMapper.toEntity(any(), any(), any(), any()))
                .thenReturn(new Event(1L, 1, "Event", LocalDateTime.now().plusDays(1), null, null, List.of(), 1, "Season 1", SCHEDULED, null));

        when(participantMapper.toEntity(any(), any()))
                .thenReturn(new Participant(1L, 1, "Team A", "TA", new Competition(), List.of(), List.of(), ""));

        when(subParticipantMapper.toEntity(any(), any(), any()))
                .thenReturn(new SubParticipant(1L, 1, "Player A", "Forward", 1, null, "male",
                        "95", "195", LocalDate.of(1995, 10,10), null, null, null, null));

        when(statScoreProxyService.listSquadSubParticipants(any(), anyInt(), eq(true)))
                .thenReturn(PaginatedResponse.<StatScoreSubParticipantDTO>builder()
                        .items(Collections.emptyList())
                        .build());

        when(venueService.readByExternalId(anyInt())).thenReturn(Optional.of(new Venue()));

        when(subParticipantRepository.findByExternalIdIn(anyList()))
                .thenReturn(Collections.emptyList());

        when(eventSubParticipantRepository.findExistingByEventAndSubParticipantIds(anyInt(), anyList()))
                .thenReturn(Collections.emptyList());

        doNothing().when(statRepository).deleteByEventIdIn(anyCollection());

        Participant participant = new Participant();
        participant.setExternalId(1);

        StatScoreEventParticipantDTO participantDTO = new StatScoreEventParticipantDTO();
        participantDTO.setId(1);
        participantDTO.setStats(Collections.emptyList());

        StatScoreEventDTO eventDTO = new StatScoreEventDTO();
        eventDTO.setId(100);
        eventDTO.setParticipants(List.of(participantDTO));

        when(eventContext.event()).thenReturn(eventDTO);

        StatScoreSubParticipantDTO subParticipantDTO = new StatScoreSubParticipantDTO();
        subParticipantDTO.setId(200);
        subParticipantDTO.setTeamId(1);
        subParticipantDTO.setStats(Collections.emptyList());

        when(statScoreProxyService.listEventSubParticipants(anyInt(), eq(true)))
                .thenReturn(PaginatedResponse.<StatScoreSubParticipantDTO>builder()
                        .items(List.of(subParticipantDTO))
                        .build());

        statsService.fetchStatDataForCompetitionAndDate(20, LocalDate.now());

        verify(statScoreDataService, times(2)).getAllEventsWithContext(any());
        verify(participantRepository, times(2)).saveAllAndFlush(any());
        verify(eventRepository, times(2)).saveAllAndFlush(any());
        verify(statScoreProxyService, atLeastOnce()).listSquadSubParticipants(any(), anyInt(), eq(true));
    }
}

