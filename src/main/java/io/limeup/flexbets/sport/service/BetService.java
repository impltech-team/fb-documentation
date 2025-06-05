package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.trade360.Trade360BetDTO;
import io.limeup.flexbets.sport.mapper.BetMapper;
import io.limeup.flexbets.sport.model.Bet;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.Market;
import io.limeup.flexbets.sport.model.enums.BetStatus;
import io.limeup.flexbets.sport.model.enums.MarketType;
import io.limeup.flexbets.sport.repository.BetRepository;
import io.limeup.flexbets.sport.repository.EventRepository;
import io.limeup.flexbets.sport.repository.MarketRepository;
import io.limeup.flexbets.sport.repository.projection.BetRow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class BetService {

    private final BetRepository betRepository;
    private final EventRepository eventRepository;
    private final MarketRepository marketRepository;

    public BetService(BetRepository betRepository, EventRepository eventRepository, MarketRepository marketRepository) {
        this.betRepository = betRepository;
        this.eventRepository = eventRepository;
        this.marketRepository = marketRepository;
    }

    public Map<Integer, List<BetRow>> getBetsEventMapByEventExternalIdsAndMarketTypeAndBetStatus(Collection<Integer> eventIds, MarketType marketType, BetStatus betStatus) {
        return betRepository.findAllByEventExternalIdInAndMarketTypeAndBetStatus(eventIds, marketType.name(), betStatus.name()).stream()
                .collect(Collectors.groupingBy(BetRow::getEventExternalId));
    }

    public List<BetRow> getBetsByExternalIdInAndBetStatus(Collection<Integer> externalIds, BetStatus betStatus) {
        return betRepository.findAllByEventExternalIdInAndBetStatus(externalIds, betStatus.name());
    }

    public void updateBetsInfoFromTrade360(Long eventLsId, Map<Integer, List<Trade360BetDTO>> marketBetsMap) {
        Optional<Event> eventOptional = eventRepository.findByLsId(eventLsId);
        if(eventOptional.isPresent()) {
            System.out.println("Updating bets info process has started.");
            Event event = eventOptional.get();
            Long competitionId = event.getCompetition().getId();

            List<Bet> betsToSave = new ArrayList<>();

            for(Integer marketId : marketBetsMap.keySet()) {
                Market market = marketRepository.findByExternalIdAndCompetitionId(marketId, competitionId).orElse(null);
                Map<Long, Bet> existingBets = betRepository.findAllByEventLsIdAndMarketExternalId(eventLsId, marketId)
                        .stream()
                        .collect(Collectors.toMap(Bet::getExternalId, Function.identity()));
                if(market != null) {
                    List<Trade360BetDTO> newBets = marketBetsMap.get(marketId);

                    for(Trade360BetDTO betDto : newBets) {
                        Bet bet = existingBets.get(betDto.getId());

                        if (bet == null) {
                            bet = BetMapper.toEntity(betDto, event, market);
                        } else {
                            BetMapper.updateEntity(bet, betDto, event, market);
                        }

                        betsToSave.add(bet);
                    }
                }
            }

            betRepository.saveAll(betsToSave);
        }
    }
}
