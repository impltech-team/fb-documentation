package io.limeup.flexbets.sport.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static io.limeup.flexbets.sport.TestDataFactory.*;

import java.util.List;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SportDTO;
import io.limeup.flexbets.sport.dto.SportLiteDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportLiteDTO;
import io.limeup.flexbets.sport.mapper.SportMapper;
import io.limeup.flexbets.sport.model.Sport;
import io.limeup.flexbets.sport.repository.SportRepository;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class SportServiceImplTest {

    @Mock
    private SportRepository sportRepository;

    @Mock
    private StatScoreProxyService statScoreProxyService;

    @Mock
    private SportMapper sportMapper;

    @InjectMocks
    private SportServiceImpl sportService;

    private RequestQueryDTO requestQuery;

    @BeforeEach
    void setUp() {
        requestQuery = new RequestQueryDTO();
        requestQuery.setPage(1);
        requestQuery.setPageSize(10);
        requestQuery.setSortBy("name");
        requestQuery.setSortOrder("asc");
    }

    @Test
    void listSportsShouldReturnPaginatedResponse() {
        Sport sport = createTestSport(1, "Football");
        Page<Sport> page = new PageImpl<>(List.of(sport));

        when(sportRepository.listSports(anyList(), anyBoolean(), anyString(), any(PageRequest.class)))
                .thenReturn(page);

        PaginatedResponse<SportLiteDTO> response = sportService.listSports(null, "Football", requestQuery);

        assertThat(response).isNotNull();
        assertThat(response.getItems()).isNotEmpty();
        assertThat(response.getCount()).isEqualTo(1);

        verify(sportRepository).listSports(anyList(), anyBoolean(), anyString(), any(PageRequest.class));
    }

    @Test
    void listSportsWhenSportIdsNullShouldUseSafeList() {
        Sport sport = createTestSport(2, "Basketball");
        Page<Sport> page = new PageImpl<>(List.of(sport));

        when(sportRepository.listSports(eq(List.of(-1)), eq(true), any(), any(PageRequest.class)))
                .thenReturn(page);

        PaginatedResponse<SportLiteDTO> response = sportService.listSports(null, null, requestQuery);

        assertThat(response).isNotNull();
        assertThat(response.getItems()).isNotEmpty();
    }

    @Test
    void getSportByIdShouldReturnSportDTO() {
        StatScoreSportDTO proxyResponse = new StatScoreSportDTO();
        proxyResponse.setId(1);
        proxyResponse.setName("Tennis");

        when(statScoreProxyService.getSportById(eq(1), eq(false)))
                .thenReturn(proxyResponse);

        SportDTO result = sportService.getSportById(1);

        assertThat(result).isNotNull();
        verify(statScoreProxyService).getSportById(eq(1), eq(false));
    }

    @Test
    void fetchSportDataShouldFetchAndSave() {
        StatScoreSportLiteDTO statDTO = new StatScoreSportLiteDTO();
        statDTO.setId(10);

        when(statScoreProxyService.listSports(any(), eq(true)))
                .thenReturn(PaginatedResponse.<StatScoreSportLiteDTO>builder()
                        .items(List.of(statDTO))
                        .count(1L)
                        .page(1)
                        .pageSize(500)
                        .totalPages(1)
                        .build());

        when(sportRepository.findByExternalIdIn(anyList()))
                .thenReturn(List.of());

        when(sportMapper.toEntity(any(StatScoreSportLiteDTO.class)))
                .thenReturn(new Sport());

        sportService.fetchSportData();

        verify(sportRepository).saveAllAndFlush(anyList());
    }
}

