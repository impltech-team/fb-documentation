package io.limeup.flexbets.sport.service.impl;

import com.github.tomakehurst.wiremock.admin.NotFoundException;
import io.limeup.flexbets.sport.dto.MarketDTO;
import io.limeup.flexbets.sport.dto.MarketLiteDTO;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.mapper.MarketMapper;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Market;
import io.limeup.flexbets.sport.model.enums.MarketType;
import io.limeup.flexbets.sport.repository.MarketRepository;
import io.limeup.flexbets.sport.service.CompetitionService;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.MarketService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class MarketServiceImpl extends ExternalIdReadServiceImpl<Market, MarketLiteDTO, Long> implements MarketService {

    private final MarketRepository marketRepository;

    private final CompetitionService competitionService;

    protected MarketServiceImpl(MarketRepository repository, CompetitionService competitionService) {
        super(repository);
        this.marketRepository = repository;
        this.competitionService = competitionService;
    }

    @Override
    public List<MarketLiteDTO> listMarkets(Integer competitionId, MarketType marketType) {
        return marketRepository.findByCompetitionAndOptionalType(competitionId, marketType)
                .stream()
                .map(MarketMapper::toLiteDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Market> listMarketEntities(Integer competitionId, MarketType marketType) {
        return marketRepository.findByCompetitionAndOptionalType(competitionId, marketType);
    }

    @Override
    public List<MarketDTO> getAllMarketsFullDTO() {
        return marketRepository.findAll().stream()
                .map(MarketMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MarketDTO createMarket(MarketDTO dto) {

        Market market = MarketMapper.toEntity(dto,
                competitionService.readByExternalId(dto.getCompetitionId())
                        .orElseThrow(() -> new NotFoundException("Competition not found")));
        return MarketMapper.toDTO(marketRepository.save(market));
    }

    @Override
    public MarketDTO updateMarket(Integer id, MarketDTO dto) {
        Market market = marketRepository.findByExternalId(id)
                .orElseThrow(() -> new EntityNotFoundException("Market not found"));
        Competition competition = competitionService.readByExternalId(dto.getCompetitionId())
                .orElseThrow(() -> new NotFoundException("Competition not found"));
        return MarketMapper.toDTO(marketRepository.save(MarketMapper.updateEntity(market, dto, competition)));
    }

    @Override
    public void deleteMarket(Integer id) {
        marketRepository.deleteByExternalId(id);
    }

    @Override
    public void setMarketEnabled(Integer id, boolean enabled) {
        Market market = marketRepository.findByExternalId(id)
                .orElseThrow(() -> new EntityNotFoundException("Market not found"));
        market.setEnabled(enabled);
        marketRepository.save(market);
    }

    @Override
    public Set<String> getStatsByMarket(Integer competitionId, Integer marketId, MarketType marketType) {
        if (marketId == null) {
            Set<String> statNames = listMarketEntities(competitionId, marketType).stream()
                    .flatMap(market -> market.getLinkedStats().stream())
                    .collect(Collectors.toSet());

            if (statNames.isEmpty()) {
//                throw new FlexBetsSportNotFoundException("No stats found for sub-participant markets in competition " + competitionId);
            }
            return statNames;
        } else {
            Market market = marketRepository.findByExternalIdAndExternalCompetitionId(marketId, competitionId)
                    .orElseThrow(() -> new FlexBetsSportNotFoundException("Market " + marketId + " not found"));

            Set<String> stats = new HashSet<>(market.getLinkedStats());
            if (stats.isEmpty()) {
                throw new FlexBetsSportNotFoundException("Market " + marketId + " has no linked stats");
            }
            return stats;
        }
    }
}
