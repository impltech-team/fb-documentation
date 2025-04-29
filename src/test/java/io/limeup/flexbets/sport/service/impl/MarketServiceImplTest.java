package io.limeup.flexbets.sport.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static io.limeup.flexbets.sport.TestDataFactory.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import io.limeup.flexbets.sport.dto.MarketDTO;
import io.limeup.flexbets.sport.dto.MarketLiteDTO;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Market;
import io.limeup.flexbets.sport.model.MarketType;
import io.limeup.flexbets.sport.repository.MarketRepository;
import io.limeup.flexbets.sport.service.CompetitionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MarketServiceImplTest {

    @Mock
    private MarketRepository marketRepository;

    @Mock
    private CompetitionService competitionService;

    @InjectMocks
    private MarketServiceImpl marketService;

    @BeforeEach
    void setUp() {
        //nothing to set up
    }

    @Test
    void listMarketsShouldReturnLiteDTOs() {
        Market market = createTestMarket(1, MarketType.SUB_PARTICIPANT);

        when(marketRepository.findByCompetitionAndOptionalType(anyInt(), any()))
                .thenReturn(List.of(market));

        List<MarketLiteDTO> result = marketService.listMarkets(1, null);

        assertThat(result).isNotEmpty();
        verify(marketRepository).findByCompetitionAndOptionalType(anyInt(), any());
    }

    @Test
    void listMarketEntitiesShouldReturnEntities() {
        Market market = createTestMarket(1, MarketType.SUB_PARTICIPANT);

        when(marketRepository.findByCompetitionAndOptionalType(anyInt(), any()))
                .thenReturn(List.of(market));

        List<Market> result = marketService.listMarketEntities(1, null);

        assertThat(result).isNotEmpty();
    }

    @Test
    void getAllMarketsFullDTOShouldReturnFullDTOs() {
        Market market = createTestMarket(1, MarketType.SUB_PARTICIPANT);

        when(marketRepository.findAll())
                .thenReturn(List.of(market));

        List<MarketDTO> result = marketService.getAllMarketsFullDTO();

        assertThat(result).isNotEmpty();
    }

    @Test
    void createMarketShouldSaveNewMarket() {
        MarketDTO dto = new MarketDTO();
        dto.setCompetitionId(5);

        Competition competition = createTestCompetition(5, "", null, null);

        Market market = createTestMarket(1, MarketType.SUB_PARTICIPANT);

        when(competitionService.readByExternalId(eq(5))).thenReturn(Optional.of(competition));
        when(marketRepository.save(any(Market.class))).thenReturn(market);

        MarketDTO result = marketService.createMarket(dto);

        assertThat(result).isNotNull();
    }

    @Test
    void updateMarketShouldUpdateExistingMarket() {
        MarketDTO dto = new MarketDTO();
        dto.setCompetitionId(5);

        Market market = createTestMarket(1, MarketType.SUB_PARTICIPANT);

        Competition competition = createTestCompetition(5, "", null, null);

        when(marketRepository.findByExternalId(eq(1))).thenReturn(Optional.of(market));
        when(competitionService.readByExternalId(eq(5))).thenReturn(Optional.of(competition));
        when(marketRepository.save(any(Market.class))).thenReturn(market);

        MarketDTO result = marketService.updateMarket(1, dto);

        assertThat(result).isNotNull();
    }

    @Test
    void deleteMarketShouldDeleteByExternalId() {
        marketService.deleteMarket(10);

        verify(marketRepository).deleteByExternalId(eq(10));
    }

    @Test
    void setMarketEnabledShouldEnableMarket() {
        Market market = createTestMarket(1, MarketType.SUB_PARTICIPANT);
        market.setEnabled(false);

        when(marketRepository.findByExternalId(eq(1))).thenReturn(Optional.of(market));
        when(marketRepository.save(any(Market.class))).thenReturn(market);

        marketService.setMarketEnabled(1, true);

        assertThat(market.isEnabled()).isTrue();
    }

    @Test
    void getStatsByMarketWhenMarketIdNullShouldReturnStatNames() {
        Market market = createTestMarket(1, MarketType.SUB_PARTICIPANT);
        market.setLinkedStats(List.of("goals", "assists"));

        when(marketRepository.findByCompetitionAndOptionalType(anyInt(), any()))
                .thenReturn(List.of(market));

        Set<String> stats = marketService.getStatsByMarket(1, null, null);

        assertThat(stats).containsExactlyInAnyOrder("goals", "assists");
    }

    @Test
    void getStatsByMarketWhenMarketIdGivenShouldReturnMarketStats() {
        Market market = createTestMarket(1, MarketType.SUB_PARTICIPANT);
        market.setLinkedStats(List.of("shots", "passes"));

        when(marketRepository.findByExternalId(eq(1))).thenReturn(Optional.of(market));

        Set<String> stats = marketService.getStatsByMarket(1, 1, null);

        assertThat(stats).containsExactlyInAnyOrder("shots", "passes");
    }

    @Test
    void getStatsByMarketWhenMarketNotFoundShouldThrowException() {
        when(marketRepository.findByExternalId(anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(FlexBetsSportNotFoundException.class,
                () -> marketService.getStatsByMarket(1, 1, null));
    }

    @Test
    void getStatsByMarketWhenNoStatsFoundShouldThrowException() {
        Market market = createTestMarket(1, MarketType.SUB_PARTICIPANT);
        market.setLinkedStats(List.of());

        when(marketRepository.findByCompetitionAndOptionalType(anyInt(), any()))
                .thenReturn(List.of(market));

        assertThrows(FlexBetsSportNotFoundException.class,
                () -> marketService.getStatsByMarket(1, null, null));
    }
}

