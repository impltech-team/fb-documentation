package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.MarketLiteDTO;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.mapper.MarketMapper;
import io.limeup.flexbets.sport.model.Market;
import io.limeup.flexbets.sport.model.MarketType;
import io.limeup.flexbets.sport.repository.MarketRepository;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.MarketService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MarketServiceImpl extends ExternalIdReadServiceImpl<Market, MarketLiteDTO, Long> implements MarketService {

    private final MarketRepository repository;

    protected MarketServiceImpl(MarketRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<MarketLiteDTO> listMarkets(Integer competitionId, MarketType marketType) {
        return repository.findByCompetitionAndOptionalType(competitionId, marketType)
                .stream()
                .map(MarketMapper::toLiteDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Market> listMarketEntities(Integer competitionId, MarketType marketType) {
        return repository.findByCompetitionAndOptionalType(competitionId, marketType);
    }

    @Override
    public Set<String> getStatsByMarket(Integer competitionId, Integer marketId, MarketType marketType) {
        if (marketId == null) {
            Set<String> statNames = listMarketEntities(competitionId, marketType).stream()
                    .flatMap(market -> market.getLinkedStats().stream())
                    .collect(Collectors.toSet());

            if (statNames.isEmpty()) {
                throw new FlexBetsSportNotFoundException("No stats found for sub-participant markets in competition " + competitionId);
            }
            return statNames;
        } else {
            Market market = readByExternalId(marketId)
                    .orElseThrow(() -> new FlexBetsSportNotFoundException("Market " + marketId + " not found"));

            Set<String> stats = new HashSet<>(market.getLinkedStats());
            if (stats.isEmpty()) {
                throw new FlexBetsSportNotFoundException("Market " + marketId + " has no linked stats");
            }
            return stats;
        }
    }
}
