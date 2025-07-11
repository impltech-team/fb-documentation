package io.limeup.flexbets.sport.service.impl.sportsdataio;

import io.limeup.flexbets.sport.dto.MarketDTO;
import io.limeup.flexbets.sport.dto.MarketLiteDTO;
import io.limeup.flexbets.sport.model.Market;
import io.limeup.flexbets.sport.model.enums.MarketType;
import io.limeup.flexbets.sport.repository.ExternalIdRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoBetRepository;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.MarketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class MarketServiceIoMlbImpl extends ExternalIdReadServiceImpl<Market, MarketLiteDTO, Long> implements MarketService {

    private final IoBetRepository betRepository;


    protected MarketServiceIoMlbImpl(ExternalIdRepository<Market, Long> repository, IoBetRepository betRepository) {
        super(repository);
        this.betRepository = betRepository;
    }

    @Override
    public List<MarketLiteDTO> listMarkets(Integer competitionId, MarketType marketType) {

        Set<Integer> seenIds = new HashSet<>();

        return betRepository.findAllByAnyBetsAvailableTrue().stream()
                .filter(ioBet -> seenIds.add(ioBet.getBetTypeId()))
                .map(ioBet -> {
                    MarketLiteDTO dto = new MarketLiteDTO();
                    dto.setMarketId(ioBet.getBetTypeId());
                    dto.setMarketName(ioBet.getBetType());
                    dto.setType(ioBet.getMarketType());
                    return dto;
                })
                .toList();
    }

    @Override
    public Set<String> getStatsByMarket(Integer competitionId, Integer marketId, MarketType marketType) {
        return Set.of();
    }

    @Override
    public List<MarketDTO> getAllMarketsFullDTO() {
        return List.of();
    }

    @Override
    public MarketDTO createMarket(MarketDTO dto) {
        return null;
    }

    @Override
    public MarketDTO updateMarket(Integer id, MarketDTO dto) {
        return null;
    }

    @Override
    public void deleteMarket(Integer id) {

    }

    @Override
    public void setMarketEnabled(Integer id, boolean enabled) {

    }
}
