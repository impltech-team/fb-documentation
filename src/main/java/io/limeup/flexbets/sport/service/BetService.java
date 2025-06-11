package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.trade360.Trade360BetDTO;
import io.limeup.flexbets.sport.dto.trade360.Trade360MarketDTO;
import io.limeup.flexbets.sport.mapper.BetMapper;
import io.limeup.flexbets.sport.mapper.MarketMapper;
import io.limeup.flexbets.sport.model.Bet;
import io.limeup.flexbets.sport.model.Competition;
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

    public void updateBetsInfoFromTrade360(Long eventLsId, List<Trade360MarketDTO> marketBetsList) {
        Optional<Event> eventOptional = eventRepository.findByLsId(eventLsId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            Competition competition = event.getCompetition();
            Long competitionId = competition.getId();

            System.out.println("Updating bets info process has started for event with id " + eventLsId +
                    " and competition with id - " + competitionId);
            List<Bet> betsToSave = new ArrayList<>();

            marketBetsList.forEach(marketBetDTO -> {
                Optional<Market> marketOptional = marketRepository.findByExternalIdAndCompetitionId(marketBetDTO.getId(), competitionId);
                Market market;
                if (marketOptional.isEmpty()) {
                    String playerName = marketBetDTO.getBets().stream()
                            .findFirst().get().getParticipantName();
                    MarketType marketType = playerName != null ? MarketType.SUB_PARTICIPANT : MarketType.PARTICIPANT;
                    market = marketRepository.saveAndFlush(MarketMapper.toEntity(marketBetDTO, marketType, competition));
                } else {
                    market = marketOptional.get();
                }

                Map<Long, Bet> existingBets = betRepository.findAllByEventLsIdAndMarketExternalId(eventLsId, market.getExternalId())
                        .stream()
                        .collect(Collectors.toMap(Bet::getExternalId, Function.identity()));
                List<Trade360BetDTO> newBets = marketBetDTO.getBets();

                for (Trade360BetDTO betDto : newBets) {
                    Bet bet = existingBets.get(betDto.getId());

                    if (bet == null) {
                        bet = BetMapper.toEntity(betDto, event, market);
                    } else {
                        BetMapper.updateEntity(bet, betDto, event, market);
                    }

                    betsToSave.add(bet);
                }
            });

            betRepository.saveAll(betsToSave);
            System.out.println("Updating bets info process has finished for event with id " + eventLsId +
                    " and competition with id - " + competitionId);
        }
    }
}
