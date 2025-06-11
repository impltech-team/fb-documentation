package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.MarketDTO;
import io.limeup.flexbets.sport.dto.MarketLiteDTO;
import io.limeup.flexbets.sport.dto.trade360.Trade360MarketDTO;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Market;
import io.limeup.flexbets.sport.model.enums.MarketType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

public class MarketMapper {

    private MarketMapper() {
    }

    public static MarketLiteDTO toLiteDTO(Market market) {
        MarketLiteDTO dto = new MarketLiteDTO();
        dto.setMarketId(market.getExternalId());
        dto.setMarketName(market.getMarketName());
        dto.setType(market.getMarketType().name());
        return dto;
    }

    public static Market toEntity(MarketDTO dto, Competition competition) {
        Market market = new Market();
        return updateEntity(market, dto, competition);
    }

    public static Market toEntity(Trade360MarketDTO dto, MarketType marketType, Competition competition) {
        Market market = new Market();
        market.setExternalId(dto.getId());
        market.setMarketType(marketType);
        market.setMarketName(dto.getName());
        market.setEnabled(true);
        market.setCompetition(competition);

        return market;
    }

    public static MarketDTO toDTO(Market entity) {
        return MarketDTO.builder()
                .competitionId(entity.getCompetition() != null ? entity.getCompetition().getExternalId() : null)
                .id(entity.getExternalId())
                .marketName(entity.getMarketName())
                .marketType(entity.getMarketType())
                .enabled(entity.isEnabled())
                .linkedStats(entity.getLinkedStats())
                .build();
    }

    public static Market updateEntity(Market entity, MarketDTO dto, Competition competition) {
        entity.setExternalId(dto.getId());
        entity.setMarketType(dto.getMarketType());
        entity.setMarketName(dto.getMarketName());
        entity.setLinkedStats(dto.getLinkedStats());
        entity.setEnabled(dto.isEnabled());
        entity.setCompetition(competition);
        return entity;
    }

}
