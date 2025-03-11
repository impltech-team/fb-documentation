package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.MarketLiteDTO;

import java.util.List;

public interface MarketService {
    List<MarketLiteDTO> listMarkets(Integer competitionId);
}
