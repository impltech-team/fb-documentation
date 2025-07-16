package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.MarketDTO;
import io.limeup.flexbets.sport.dto.MarketLiteDTO;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Market;
import io.limeup.flexbets.sport.model.enums.MarketType;
import io.limeup.flexbets.sport.utils.ConstantUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MarketMapperTest {

    @Test
    void toLiteDTOShouldMapMarketToLiteDTO() {
        Market market = new Market();
        market.setExternalId(10);
        market.setMarketName(ConstantUtils.TestConstants.PLAYER_ASSISTS_MARKET);
        market.setMarketType(MarketType.SUB_PARTICIPANT);

        MarketLiteDTO dto = MarketMapper.toLiteDTO(market);

        assertThat(dto).isNotNull();
        assertThat(dto.getMarketId()).isEqualTo(10);
        assertThat(dto.getMarketName()).isEqualTo(ConstantUtils.TestConstants.PLAYER_ASSISTS_MARKET);
        assertThat(dto.getType()).isEqualTo(MarketType.SUB_PARTICIPANT.name());
    }

    @Test
    void toEntityShouldCreateEntityFromDTO() {
        MarketDTO dto = MarketDTO.builder()
                .id(20)
                .marketName(ConstantUtils.TestConstants.PLAYER_ASSISTS_MARKET)
                .marketType(MarketType.SUB_PARTICIPANT)
                .enabled(true)
                .linkedStats(List.of(ConstantUtils.TestConstants.ASSISTS))
                .competitionId(5)
                .build();
        Competition competition = new Competition();
        competition.setExternalId(5);

        Market market = MarketMapper.toEntity(dto, competition);

        assertThat(market).isNotNull();
        assertThat(market.getExternalId()).isEqualTo(20);
        assertThat(market.getMarketName()).isEqualTo(ConstantUtils.TestConstants.PLAYER_ASSISTS_MARKET);
        assertThat(market.getMarketType()).isEqualTo(MarketType.SUB_PARTICIPANT);
        assertThat(market.isEnabled()).isTrue();
        assertThat(market.getCompetition()).isSameAs(competition);
    }

    @Test
    void toDTOShouldMapEntityToDTO() {
        Competition competition = new Competition();
        competition.setExternalId(5);

        Market market = new Market();
        market.setExternalId(30);
        market.setMarketName("Under/Over Player Assists And Rebounds");
        market.setMarketType(MarketType.SUB_PARTICIPANT);
        market.setEnabled(false);
        market.setCompetition(competition);

        MarketDTO dto = MarketMapper.toDTO(market);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(30);
        assertThat(dto.getMarketName()).isEqualTo("Under/Over Player Assists And Rebounds");
        assertThat(dto.getMarketType()).isEqualTo(MarketType.SUB_PARTICIPANT);
        assertThat(dto.isEnabled()).isFalse();
        assertThat(dto.getCompetitionId()).isEqualTo(5);
        assertThat(dto.getLinkedStats()).containsExactly(ConstantUtils.TestConstants.ASSISTS, ConstantUtils.TestConstants.REBOUNDS);
    }

    @Test
    void updateEntityShouldUpdateEntityFields() {
        Market market = new Market();
        Competition competition = new Competition();
        competition.setExternalId(7);

        MarketDTO dto = MarketDTO.builder()
                .id(40)
                .marketName(ConstantUtils.TestConstants.PLAYER_ASSISTS_MARKET)
                .marketType(MarketType.SUB_PARTICIPANT)
                .enabled(true)
                .linkedStats(List.of(ConstantUtils.TestConstants.ASSISTS))
                .build();

        MarketMapper.updateEntity(market, dto, competition);

        assertThat(market.getExternalId()).isEqualTo(40);
        assertThat(market.getMarketName()).isEqualTo(ConstantUtils.TestConstants.PLAYER_ASSISTS_MARKET);
        assertThat(market.getMarketType()).isEqualTo(MarketType.SUB_PARTICIPANT);
        assertThat(market.isEnabled()).isTrue();
        assertThat(market.getCompetition()).isSameAs(competition);
    }
}

