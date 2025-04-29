package io.limeup.flexbets.sport.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static io.limeup.flexbets.sport.TestDataFactory.*;

import io.limeup.flexbets.sport.dto.AreaDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreAreaDTO;
import io.limeup.flexbets.sport.mapper.AreaMapper;
import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.repository.AreaRepository;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import io.limeup.flexbets.sport.utils.ConstantUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AreaServiceImplTest {

    @Mock
    private AreaRepository areaRepository;

    @Mock
    private StatScoreProxyService statScoreProxyService;

    @Mock
    private AreaMapper areaMapper;

    @InjectMocks
    private AreaServiceImpl areaService;

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
    void listAreasWhenAreasExistShouldReturnPaginatedResponse() {
        Area area = createTestArea(1, ConstantUtils.TestConstants.USA);

        Page<Area> page = new PageImpl<>(List.of(area));

        when(areaRepository.listAreas(anyList(), anyBoolean(), any(), any(PageRequest.class)))
                .thenReturn(page);

        PaginatedResponse<AreaDTO> response = areaService.listAreas(List.of(1), ConstantUtils.TestConstants.USA, requestQuery);

        assertThat(response.getItems()).isNotNull();
        assertThat(response.getCount()).isEqualTo(1);
        verify(areaRepository).listAreas(anyList(), anyBoolean(), any(), any(PageRequest.class));
    }


    @Test
    void listAreasWhenAreaIdsNullShouldUseSafeList() {
        Area area = createTestArea(1, ConstantUtils.TestConstants.USA);

        Page<Area> page = new PageImpl<>(List.of(area));

        when(areaRepository.listAreas(eq(List.of(-1)), eq(true), any(), any(PageRequest.class)))
                .thenReturn(page);

        PaginatedResponse<AreaDTO> response = areaService.listAreas(null, null, requestQuery);

        assertThat(response.getItems()).isNotNull();
        assertThat(response.getCount()).isEqualTo(1);
    }


    @Test
    void fetchAreaDataShouldFetchAndSave() {
        StatScoreAreaDTO statScoreAreaDTO = new StatScoreAreaDTO();
        statScoreAreaDTO.setId(1);

        when(statScoreProxyService.listAreas(any(), eq(true)))
                .thenReturn(PaginatedResponse.<StatScoreAreaDTO>builder()
                        .items(List.of(statScoreAreaDTO))
                        .count(1L)
                        .page(1)
                        .pageSize(500)
                        .totalPages(1)
                        .build());

        when(areaRepository.findByExternalIdIn(anyList()))
                .thenReturn(List.of());

        when(areaMapper.toEntity(any(StatScoreAreaDTO.class)))
                .thenReturn(new Area());

        areaService.fetchAreaData();

        verify(areaRepository).saveAllAndFlush(anyList());
    }

    @Test
    void readByExternalIdShouldReturnArea() {
        Area area = new Area();
        when(areaRepository.findByExternalId(anyInt()))
                .thenReturn(Optional.of(area));

        Optional<Area> result = areaService.readByExternalId(1);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(area);
    }

    @Test
    void readByExternalIdInShouldReturnAreas() {
        Area area = new Area();
        when(areaRepository.findByExternalIdIn(anyList()))
                .thenReturn(List.of(area));

        List<Area> result = areaService.readByExternalIdIn(List.of(1, 2, 3));

        assertThat(result).isNotEmpty();
    }
}

