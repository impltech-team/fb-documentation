package io.limeup.flexbets.sport.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static io.limeup.flexbets.sport.TestDataFactory.*;

import java.util.List;

import io.limeup.flexbets.sport.dto.CompetitionDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.mapper.CompetitionMapper;
import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Sport;
import io.limeup.flexbets.sport.repository.CompetitionRepository;
import io.limeup.flexbets.sport.service.AreaService;
import io.limeup.flexbets.sport.service.SportService;
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
class CompetitionServiceImplTest {

    @Mock
    private CompetitionRepository competitionRepository;

    @Mock
    private StatScoreProxyService statScoreProxyService;

    @Mock
    private CompetitionMapper competitionMapper;

    @Mock
    private SportService sportService;

    @Mock
    private AreaService areaService;

    @InjectMocks
    private CompetitionServiceImpl competitionService;

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
    void listCompetitionsShouldReturnPaginatedResponse() {
        Competition competition = new Competition();
        competition.setExternalId(1);
        competition.setName("Premier League");

        Page<Competition> page = new PageImpl<>(List.of(competition));

        when(competitionRepository.listCompetitions(any(), any(), any(), any(), any(), any(PageRequest.class)))
                .thenReturn(page);

        PaginatedResponse<CompetitionDTO> response = competitionService.listCompetitions(null, null, null, null, null, requestQuery);

        assertThat(response).isNotNull();
        assertThat(response.getItems()).isNotEmpty();
        assertThat(response.getCount()).isEqualTo(1);

        verify(competitionRepository).listCompetitions(any(), any(), any(), any(), any(), any(PageRequest.class));
    }

    @Test
    void createShouldSaveCompetition() {
        StatScoreCompetitionDTO dto = new StatScoreCompetitionDTO();
        dto.setSportId(8);
        dto.setAreaId(1);

        Sport sport = createTestSport(8, "American Football");
        Area area = createTestArea(1, "Ukraine");
        Competition competition = createTestCompetition(100, "NFL", sport, area);

        when(sportService.readByExternalId(eq(8))).thenReturn(java.util.Optional.of(sport));
        when(areaService.readByExternalId(eq(1))).thenReturn(java.util.Optional.of(area));
        when(competitionMapper.toEntity(dto, sport, area)).thenReturn(competition);
        when(competitionRepository.save(any(Competition.class))).thenReturn(competition);

        Competition result = competitionService.create(dto);

        assertThat(result).isNotNull();
        assertThat(result.getExternalId()).isEqualTo(100);

        verify(competitionRepository).save(any(Competition.class));
    }

    @Test
    void fetchCompetitionDataShouldFetchAndSaveCompetitions() {
        StatScoreCompetitionDTO dto1 = new StatScoreCompetitionDTO();
        dto1.setId(1);
        dto1.setSportId(8);
        dto1.setAreaId(1);

        Sport sport = createTestSport(8, "American Football");
        Area area = createTestArea(1, "Ukraine");

        when(statScoreProxyService.listCompetitions(any(), eq(true)))
                .thenReturn(PaginatedResponse.<StatScoreCompetitionDTO>builder()
                        .items(List.of(dto1))
                        .count(1L)
                        .page(1)
                        .pageSize(100)
                        .totalPages(1)
                        .build());

        when(sportService.readByExternalIdIn(anyList())).thenReturn(List.of(sport));
        when(areaService.readByExternalIdIn(anyList())).thenReturn(List.of(area));

        when(competitionMapper.toEntity(any(StatScoreCompetitionDTO.class), any(Sport.class), any(Area.class)))
                .thenReturn(new Competition());

        when(competitionRepository.findByExternalIdIn(anyList()))
                .thenReturn(List.of());

        competitionService.fetchCompetitionData();

        verify(competitionRepository, times(1)).saveAllAndFlush(anyList());
    }
}

