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
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.mapper.SubParticipantMapper;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.enums.MarketType;
import io.limeup.flexbets.sport.model.SubParticipant;
import io.limeup.flexbets.sport.repository.StatRepository;
import io.limeup.flexbets.sport.repository.SubParticipantRepository;
import io.limeup.flexbets.sport.repository.projection.SubParticipantStatRow;
import io.limeup.flexbets.sport.service.MarketService;
import io.limeup.flexbets.sport.service.impl.statscore.StatscoreSubParticipantServiceImpl;
import io.limeup.flexbets.sport.utils.ConstantUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StatscoreSubParticipantServiceImplTest {

    @Mock
    private SubParticipantRepository subParticipantRepository;

    @Mock
    private StatRepository statRepository;

    @Mock
    private MarketService marketService;

    @Mock
    private SubParticipantMapper mapper;

    @InjectMocks
    private StatscoreSubParticipantServiceImpl subParticipantService;

    private RequestQueryDTO requestQuery;

    @BeforeEach
    void setUp() {
        requestQuery = new RequestQueryDTO();
        requestQuery.setPage(1);
        requestQuery.setPageSize(10);
        requestQuery.setSortBy("player_name");
        requestQuery.setSortOrder("asc");
        requestQuery.setFilter(null);
    }

    @Test
    void listSubParticipantsWhenParticipantsFoundShouldReturnPaginatedResponse() {
        when(marketService.getStatsByMarket(anyInt(), any(), eq(MarketType.SUB_PARTICIPANT)))
                .thenReturn(Set.of(ConstantUtils.TestConstants.REBOUNDS, ConstantUtils.TestConstants.ASSISTS));
        when(subParticipantRepository.countSubParticipants(anyInt(), anyList(), anyList(), any()))
                .thenReturn(2L);
        when(statRepository.listSubParticipantStats(
                anyInt(),
                anyList(),
                anyList(),
                any(),
                any(),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                anyInt(),
                anyInt(),
                anySet()
        )).thenReturn(List.of(mock(SubParticipantStatRow.class), mock(SubParticipantStatRow.class)));
        when(mapper.toDTO(anyList(), anyMap()))
                .thenReturn(List.of(mock(SubParticipantDTO.class), mock(SubParticipantDTO.class)));
        PaginatedResponse<SubParticipantDTO> response = subParticipantService.listSubParticipants(1, null, null, 1, null, 5, requestQuery);
        assertThat(response.getItems()).hasSize(2);
        assertThat(response.getCount()).isEqualTo(2);
    }

    @Test
    void getSubParticipantByIdWhenFoundShouldReturnSubParticipant() {
        SubParticipant subParticipant = new SubParticipant();
        subParticipant.setCompetition(new Competition());
        subParticipant.getCompetition().setExternalId(1);

        when(subParticipantRepository.findByExternalId(anyInt())).thenReturn(Optional.of(subParticipant));
        when(subParticipantRepository.findByExternalId(anyInt())).thenReturn(Optional.of(subParticipant));
        when(marketService.getStatsByMarket(anyInt(), any(), eq(MarketType.SUB_PARTICIPANT))).thenReturn(Set.of(ConstantUtils.TestConstants.REBOUNDS, ConstantUtils.TestConstants.ASSISTS));
        when(statRepository.getSubParticipantStatsDetails(anyInt(), any(), anySet())).thenReturn(List.of(mock(SubParticipantStatRow.class)));
        when(mapper.toDTO(anyList(), anyMap())).thenReturn(List.of(mock(SubParticipantDTO.class)));

        SubParticipantDTO dto = subParticipantService.getSubParticipantById(1, 1, 5);

        assertThat(dto).isNotNull();
    }

    @Test
    void getSubParticipantByIdWhenNotFoundShouldThrowException() {
        when(subParticipantRepository.findByExternalId(anyInt())).thenReturn(Optional.empty());

        FlexBetsSportNotFoundException exception = assertThrows(FlexBetsSportNotFoundException.class, () ->
                subParticipantService.getSubParticipantById(1, 1, 5)
        );

        assertThat(exception.getMessage()).contains("SubParticipant 1 Not Found");
    }

    @Test
    void getSubParticipantByIdWhenStatsEmptyShouldThrowException() {
        SubParticipant subParticipant = new SubParticipant();
        subParticipant.setCompetition(new Competition());
        subParticipant.getCompetition().setExternalId(1);

        when(subParticipantRepository.findByExternalId(anyInt())).thenReturn(Optional.of(subParticipant));
        when(marketService.getStatsByMarket(anyInt(), any(), eq(MarketType.SUB_PARTICIPANT))).thenReturn(Set.of(ConstantUtils.TestConstants.REBOUNDS));
        when(statRepository.getSubParticipantStatsDetails(anyInt(), any(), anySet())).thenReturn(Collections.emptyList());

        FlexBetsSportNotFoundException exception = assertThrows(FlexBetsSportNotFoundException.class, () ->
                subParticipantService.getSubParticipantById(1, 1, 5)
        );

        assertThat(exception.getMessage()).contains("SubParticipant 1 Not Found");
    }

    @Test
    void listSubParticipantsShouldReturnEmptyPaginatedResponseWhenCountIsZero() {
        Integer competitionId = 1;
        Integer marketId = 10;
        List<String> positions = null;
        List<Integer> participantIds = null;
        RequestQueryDTO requestQuery = new RequestQueryDTO();
        requestQuery.setPage(1);
        requestQuery.setPageSize(20);
        requestQuery.setSortBy("player_name");
        requestQuery.setSortOrder("asc");

        when(marketService.getStatsByMarket(competitionId, marketId, MarketType.SUB_PARTICIPANT))
                .thenReturn(Set.of("Goals", "Assists"));
        when(subParticipantRepository.countSubParticipants(
                eq(competitionId),
                anyList(),
                anyList(),
                any()
        )).thenReturn(0L);

        PaginatedResponse<SubParticipantDTO> result = subParticipantService.listSubParticipants(
                competitionId, positions, participantIds, marketId, false, 5, requestQuery
        );

        assertThat(result).isNotNull();
        assertThat(result.getItems()).isNull();
        assertThat(result.getCount()).isEqualTo(0L);
        assertThat(result.getPage()).isEqualTo(1);
        assertThat(result.getPageSize()).isEqualTo(20);
    }
}
