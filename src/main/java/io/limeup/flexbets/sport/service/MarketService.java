package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.MarketDTO;
import io.limeup.flexbets.sport.dto.MarketLiteDTO;
import io.limeup.flexbets.sport.model.Market;
import io.limeup.flexbets.sport.model.MarketType;

import java.util.List;
import java.util.Set;

public interface MarketService extends ExternalIdReadService<Market, MarketLiteDTO, Long> {

    List<MarketLiteDTO> listMarkets(Integer competitionId, MarketType marketType);

    List<Market> listMarketEntities(Integer competitionId, MarketType marketType);

    Set<String> getStatsByMarket(Integer competitionId, Integer marketId, MarketType marketType);

    List<MarketDTO> getAllMarketsFullDTO();

    MarketDTO createMarket(MarketDTO dto);

    MarketDTO updateMarket(Integer id, MarketDTO dto);

    void deleteMarket(Integer id);

    void setMarketEnabled(Integer id, boolean enabled);
}
