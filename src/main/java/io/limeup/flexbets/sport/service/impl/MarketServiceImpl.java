package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.MarketLiteDTO;
import io.limeup.flexbets.sport.service.MarketService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketServiceImpl implements MarketService {
    @Override
    public List<MarketLiteDTO> listMarkets(Integer competitionId) {
        return null;
    }
}
