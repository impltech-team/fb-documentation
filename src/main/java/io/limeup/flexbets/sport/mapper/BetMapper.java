package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.OddsDTO;
import io.limeup.flexbets.sport.dto.trade360.Trade360BetDTO;
import io.limeup.flexbets.sport.model.Bet;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.Market;
import io.limeup.flexbets.sport.model.enums.BetStatus;
import io.limeup.flexbets.sport.model.enums.SettlementType;
import io.limeup.flexbets.sport.repository.projection.BetRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BetMapper {

    public static Bet toEntity(Trade360BetDTO dto, Event event, Market market) {
        if (dto == null) {
            return null;
        }

        Bet entity = new Bet();
        return updateEntity(entity, dto, event, market);
    }

    public static Bet updateEntity(Bet entity, Trade360BetDTO dto, Event event, Market market) {
        if (entity == null || dto == null) {
            return entity;
        }
        entity.setExternalId(dto.getId());
        entity.setName(dto.getName());

        if (dto.getLastUpdated() != null) {
            entity.setLastUpdated(dto.getLastUpdated());
        }

        entity.setEvent(event);
        entity.setMarket(market);
        entity.setStatus(BetStatus.getById(dto.getStatus()));
        entity.setName(dto.getName());
        entity.setLine(dto.getLine());
        entity.setBaseline(dto.getBaseLine());
        entity.setPrice(dto.getPrice());
        entity.setParticipantName(dto.getParticipantName());
        entity.setSettlement(SettlementType.getById(dto.getSettlement()));
        return entity;
    }

    public static List<OddsDTO> betRowListToOddsDtoList(List<BetRow> betRowList) {
        if (Objects.isNull(betRowList) || betRowList.isEmpty()) {
            return new ArrayList<>();
        } else{
            return betRowList.stream()
                    .filter(Objects::nonNull)
                    .map(BetMapper::betRowToOddsDto)
                    .collect(Collectors.toList());
        }
    }

    public static OddsDTO betRowToOddsDto(BetRow betRow) {
        OddsDTO oddsDTO = new OddsDTO();
        oddsDTO.setId(betRow.getId());
        oddsDTO.setMarketName(betRow.getMarketName());
        oddsDTO.setMarketId(betRow.getMarketExternalId());
        oddsDTO.setLine(betRow.getLine());
        oddsDTO.setBetType(betRow.getName());
        oddsDTO.setPrice(betRow.getPrice());
        oddsDTO.setStatus(betRow.getStatus());
        oddsDTO.setLastUpdatedDate(betRow.getLastUpdated());
        return oddsDTO;
    }
}
