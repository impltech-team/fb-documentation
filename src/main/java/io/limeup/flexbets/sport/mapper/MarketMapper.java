package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.MarketLiteDTO;
import io.limeup.flexbets.sport.model.Market;
import org.springframework.stereotype.Component;

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

}
