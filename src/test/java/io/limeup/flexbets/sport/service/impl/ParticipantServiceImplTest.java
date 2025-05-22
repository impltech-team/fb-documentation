package io.limeup.flexbets.sport.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.ParticipantDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.enums.MarketType;
import io.limeup.flexbets.sport.model.Participant;
import io.limeup.flexbets.sport.repository.ParticipantRepository;
import io.limeup.flexbets.sport.repository.StatRepository;
import io.limeup.flexbets.sport.repository.projection.ParticipantStatRow;
import io.limeup.flexbets.sport.service.MarketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceImplTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private MarketService marketService;

    @Mock
    private StatRepository statRepository;

    @InjectMocks
    private ParticipantServiceImpl participantService;

    private RequestQueryDTO requestQuery;

    @BeforeEach
    void setUp() {
        requestQuery = new RequestQueryDTO();
        requestQuery.setPage(1);
        requestQuery.setPageSize(10);
        requestQuery.setSortBy("team_name");
        requestQuery.setSortOrder("asc");
    }

    @Test
    void listParticipantsWhenParticipantsExistShouldReturnPaginatedResponse() {
        when(marketService.getStatsByMarket(anyInt(), any(), eq(MarketType.PARTICIPANT)))
                .thenReturn(Set.of("goals", "assists"));

        when(participantRepository.countParticipants(anyInt(), anyList(), any()))
                .thenReturn(2L);

        ParticipantStatRow statRow = mock(ParticipantStatRow.class);

        when(statRepository.listParticipantStats(anyInt(), anyList(), any(), any(), any(), any(), any(), anyInt(), anyInt(), anySet()))
                .thenReturn(List.of(statRow));

        PaginatedResponse<ParticipantDTO> response = participantService.listParticipants(
                1, null, 1, 5, requestQuery);

        assertThat(response).isNotNull();
        assertThat(response.getItems()).isNotEmpty();
        assertThat(response.getCount()).isEqualTo(2);

        verify(marketService).getStatsByMarket(anyInt(), any(), eq(MarketType.PARTICIPANT));
        verify(participantRepository).countParticipants(anyInt(), anyList(), any());
        verify(statRepository).listParticipantStats(anyInt(), anyList(), any(), any(), any(), any(), any(), anyInt(), anyInt(), anySet());
    }

    @Test
    void listParticipantsWhenNoParticipantsFoundShouldReturnEmptyResponse() {
        when(marketService.getStatsByMarket(anyInt(), any(), eq(MarketType.PARTICIPANT)))
                .thenReturn(Set.of("goals"));

        when(participantRepository.countParticipants(anyInt(), anyList(), any()))
                .thenReturn(0L);

        PaginatedResponse<ParticipantDTO> response = participantService.listParticipants(
                1, null, 1, 5, requestQuery);

        assertThat(response.getItems()).isNull();
        assertThat(response.getCount()).isEqualTo(0);
    }

    @Test
    void getParticipantByIdShouldReturnParticipantDTO() {
        Participant participant = new Participant();
        participant.setCompetition(new Competition());
        participant.getCompetition().setExternalId(100);

        ParticipantStatRow statRow = mock(ParticipantStatRow.class);

        when(participantRepository.findByExternalId(eq(1)))
                .thenReturn(Optional.of(participant));

        when(marketService.getStatsByMarket(eq(100), any(), eq(MarketType.PARTICIPANT)))
                .thenReturn(Set.of("stat"));

        when(statRepository.getParticipantStatsDetails(eq(1), any(), anySet()))
                .thenReturn(List.of(statRow));

        ParticipantDTO dto = participantService.getParticipantById(1, 1, 5);

        assertThat(dto).isNotNull();
        verify(participantRepository).findByExternalId(eq(1));
        verify(marketService).getStatsByMarket(eq(100), any(), eq(MarketType.PARTICIPANT));
        verify(statRepository).getParticipantStatsDetails(eq(1), any(), anySet());
    }

    @Test
    void getParticipantByIdWhenParticipantNotFoundShouldThrowException() {
        when(participantRepository.findByExternalId(eq(1)))
                .thenReturn(Optional.empty());

        assertThrows(FlexBetsSportNotFoundException.class,
                () -> participantService.getParticipantById(1, 1, 5));
    }

    @Test
    void getParticipantByIdWhenStatsNotFoundShouldThrowException() {
        Participant participant = new Participant();
        participant.setCompetition(new Competition());
        participant.getCompetition().setExternalId(100);

        when(participantRepository.findByExternalId(eq(1)))
                .thenReturn(Optional.of(participant));

        when(marketService.getStatsByMarket(eq(100), any(), eq(MarketType.PARTICIPANT)))
                .thenReturn(Set.of("stat"));

        when(statRepository.getParticipantStatsDetails(eq(1), any(), anySet()))
                .thenReturn(Collections.emptyList());

        assertThrows(FlexBetsSportNotFoundException.class,
                () -> participantService.getParticipantById(1, 1, 5));
    }
}

