package io.limeup.flexbets.sport.service.impl;

import static io.limeup.flexbets.sport.TestDataFactory.createTestArea;
import static io.limeup.flexbets.sport.TestDataFactory.createTestCompetition;
import static io.limeup.flexbets.sport.TestDataFactory.createTestSport;
import static io.limeup.flexbets.sport.TestDataFactory.createTestVenue;
import static io.limeup.flexbets.sport.TestDataFactory.createTestEvent;
import static io.limeup.flexbets.sport.TestDataFactory.createTestParticipant;
import static io.limeup.flexbets.sport.model.enums.EventStatus.SCHEDULED;
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
import io.limeup.flexbets.sport.dto.statscore.StatScoreGroupDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSeasonDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreStageDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreStatDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.mapper.EventMapper;
import io.limeup.flexbets.sport.mapper.EventStatMapper;
import io.limeup.flexbets.sport.mapper.ParticipantMapper;
import io.limeup.flexbets.sport.mapper.SubParticipantMapper;
import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.EventStat;
import io.limeup.flexbets.sport.model.enums.EventStatus;
import io.limeup.flexbets.sport.model.EventSubParticipant;
import io.limeup.flexbets.sport.model.Participant;
import io.limeup.flexbets.sport.model.Sport;
import io.limeup.flexbets.sport.model.enums.StatDataType;
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

    @Mock
    private EventStatMapper eventStatMapper;

    private StatScoreDataService.EventContext eventContext;

    @InjectMocks
    private StatsServiceImpl statsService;

    @BeforeEach
    void setup() {
        StatScoreEventParticipantDTO participantDTO = new StatScoreEventParticipantDTO();
        participantDTO.setId(1);
        participantDTO.setName("Team A");
        participantDTO.setStats(List.of());

        StatScoreGroupDTO group = new StatScoreGroupDTO();
        StatScoreStageDTO stage = new StatScoreStageDTO();
        StatScoreSeasonDTO season = new StatScoreSeasonDTO();
        StatScoreEventDTO event = new StatScoreEventDTO();
        event.setId(100);
        event.setParticipants(List.of(participantDTO));
        season.setId(10);
        StatScoreCompetitionDTO competition = new StatScoreCompetitionDTO();
        competition.setId(20);

        eventContext = new StatScoreDataService.EventContext(event, group, stage, season, competition);
    }

    @Test
    void fetchStatDataForCompetitionAndDateShouldProcessEventsAndParticipants() {
        lenient().when(statScoreDataService.getAllEventsWithContext(any()))
                .thenAnswer(invocation -> List.of(eventContext));

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
                        "95", "195", LocalDate.of(1995, 10, 10), null, null, null, null));

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

        StatScoreSubParticipantDTO subParticipantDTO = new StatScoreSubParticipantDTO();
        subParticipantDTO.setId(200);
        subParticipantDTO.setTeamId(1);
        subParticipantDTO.setStats(Collections.emptyList());

        when(statScoreProxyService.listEventSubParticipants(anyInt(), eq(true)))
                .thenReturn(PaginatedResponse.<StatScoreSubParticipantDTO>builder()
                        .items(List.of(subParticipantDTO))
                        .build());

        statsService.fetchStatDataForCompetitionAndDate(20, LocalDate.now());

        verify(participantRepository, times(2)).saveAllAndFlush(any());
        verify(eventRepository, times(2)).saveAllAndFlush(any());
        verify(statScoreProxyService, atLeastOnce()).listSquadSubParticipants(any(), anyInt(), eq(true));
    }

    @Test
    void fetchStatDataForCompetitionAndDateShouldCoverHistoricalStats() {
        StatScoreEventParticipantDTO participantDTO = new StatScoreEventParticipantDTO();
        participantDTO.setId(1);
        participantDTO.setName("Team A");
        participantDTO.setCounter(1);
        participantDTO.setStats(List.of(new StatScoreStatDTO(1, "Possession", "Possession", "60", StatDataType.DECIMAL.toString().toUpperCase())));

        StatScoreGroupDTO group = new StatScoreGroupDTO();
        StatScoreStageDTO stage = new StatScoreStageDTO();
        StatScoreSeasonDTO season = new StatScoreSeasonDTO();
        season.setId(10);

        StatScoreEventDTO event = new StatScoreEventDTO();
        event.setId(100);
        event.setVenueId(5);
        event.setParticipants(List.of(participantDTO));
        event.setStartDate(LocalDateTime.now().minusDays(1).toString());

        StatScoreCompetitionDTO competitionDTO = new StatScoreCompetitionDTO();
        competitionDTO.setId(20);

        StatScoreDataService.EventContext context = new StatScoreDataService.EventContext(event, group, stage, season, competitionDTO);

        when(statScoreDataService.getAllEventsWithContext(any()))
                .thenReturn(List.of(context));

        Sport sport = createTestSport(8, "American Football");
        Area area = createTestArea(1, "Ukraine");
        Competition competition = createTestCompetition(100, "NFL", sport, area);
        Venue venue = createTestVenue( 1, "Olympic Stadium");
        Event mappedEvent = createTestEvent(1, "event", EventStatus.FINISHED, competition, LocalDateTime.now().minusDays(1), venue);
        Participant participant = createTestParticipant(2, "2", "team2", "typeA", competition);

        when(competitionService.readByExternalId(20)).thenReturn(Optional.of(competition));
        when(venueService.readByExternalId(anyInt())).thenReturn(Optional.of(venue));
        when(participantRepository.findByExternalIdIn(anyList())).thenReturn(List.of());
        when(eventRepository.findByExternalIdIn(anyList())).thenReturn(List.of());
        when(eventMapper.toEntity(any(), any(), any(), any())).thenReturn(mappedEvent);
        when(participantMapper.toEntity(any(), any())).thenReturn(participant);
        when(eventStatMapper.toEntity(any(StatScoreStatDTO.class), anyLong(), any(), any(), any()))
                .thenReturn(new EventStat());

        StatScoreSubParticipantDTO sub = new StatScoreSubParticipantDTO();
        sub.setId(300);
        sub.setTeamId(1);
        sub.setPosition("Midfield");
        sub.setPositionReason(null);
        sub.setTeamConnection("current");
        sub.setStats(List.of(new StatScoreStatDTO(1, "Possession", "Possession", "60", StatDataType.DECIMAL.toString().toUpperCase())));

        SubParticipant existingSub = new SubParticipant(10L, 300, "Player A", "Midfield", 1, null, "male", "75", "175", LocalDate.of(1995, 5, 5), null, null, null, null);

        when(statScoreProxyService.listSquadSubParticipants(anyInt(), anyInt(), eq(true)))
                .thenReturn(PaginatedResponse.<StatScoreSubParticipantDTO>builder().items(List.of(sub)).build());
        when(statScoreProxyService.listEventSubParticipants(anyInt(), eq(true)))
                .thenReturn(PaginatedResponse.<StatScoreSubParticipantDTO>builder().items(List.of(sub)).build());

        when(subParticipantRepository.findByExternalIdIn(anyList())).thenReturn(List.of(existingSub));
        when(eventSubParticipantRepository.findExistingByEventAndSubParticipantIds(anyInt(), anyList())).thenReturn(List.of(
                new EventSubParticipant(1L, mappedEvent, participant, existingSub, "in play", null, true)
        ));

        doNothing().when(statRepository).deleteByEventIdIn(anyList());

        statsService.fetchStatDataForCompetitionAndDate(20, LocalDate.now());

        verify(statRepository).saveAllAndFlush(anySet());
        verify(eventSubParticipantRepository, times(2)).saveAllAndFlush(anyList());
    }

}
