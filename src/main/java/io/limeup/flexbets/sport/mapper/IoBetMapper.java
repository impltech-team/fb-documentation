package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.OddsDTO;
import io.limeup.flexbets.sport.dto.sportsdata.IoBettingOutcomeResultDto;
import io.limeup.flexbets.sport.dto.sportsdata.SportsDataBettingMarketDTO;
import io.limeup.flexbets.sport.model.IoBet;
import io.limeup.flexbets.sport.model.IoBetOutcome;
import io.limeup.flexbets.sport.model.IoEvent;
import io.limeup.flexbets.sport.model.enums.BetStatus;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataBetRow;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class IoBetMapper {
    public static IoBet toEntity(SportsDataBettingMarketDTO dto, IoEvent event) {
        if (dto == null) {
            return null;
        }

        IoBet entity = new IoBet();
        entity = updateEntity(entity, dto, event, toBetOutcomeEntityList(entity, dto.getBettingOutcomes()));
        return entity;
    }

    public static IoBet updateEntity(IoBet entity, SportsDataBettingMarketDTO dto, IoEvent event, List<IoBetOutcome> betOutcomeList) {
        if (entity == null || dto == null) {
            return entity;
        }
        entity.setEvent(event);
        entity.setMarketId(dto.getBettingMarketId());
        entity.setEventId(dto.getBettingEventId());
        entity.setMarketTypeId(dto.getBettingMarketTypeId());
        entity.setMarketType(dto.getBettingMarketType());
        entity.setBetTypeId(dto.getBettingBetTypeId());
        entity.setBetType(dto.getBettingBetType());
        entity.setPeriodTypeId(dto.getBettingPeriodTypeId());
        entity.setPeriodType(dto.getBettingPeriodType());
        entity.setName(dto.getName());
        entity.setTeamId(dto.getTeamId());
        entity.setTeamKey(dto.getTeamKey());
        entity.setPlayerId(dto.getPlayerId());
        entity.setPlayerName(dto.getPlayerName());
        entity.setAnyBetsAvailable(dto.getAnyBetsAvailable());
        entity.setCreatedAt(dto.getCreated());
        entity.setUpdatedAt(dto.getUpdated());
        entity.setBetOutcomes(betOutcomeList);

        return entity;
    }

    public static IoBetOutcome toBetOutcomeEntity(SportsDataBettingMarketDTO.BettingOutcomeDTO dto, IoBet bet) {
        if (dto == null) {
            return null;
        }

        IoBetOutcome entity = new IoBetOutcome();
        return updateEntity(entity, dto, bet);
    }

    public static IoBetOutcome updateEntity(IoBetOutcome entity, SportsDataBettingMarketDTO.BettingOutcomeDTO dto, IoBet bet) {
        if (entity == null || dto == null) {
            return entity;
        }
        entity.setOutcomeId(dto.getBettingOutcomeId());
        entity.setOutcomeTypeId(dto.getBettingOutcomeTypeId());
        entity.setOutcomeType(dto.getBettingOutcomeType());
        entity.setBet(bet);
        entity.setPayoutAmerican(dto.getPayoutAmerican());
        entity.setPayoutDecimal(dto.getPayoutDecimal());
        entity.setParticipant(dto.getParticipant());
        entity.setValue(dto.getValue());
        entity.setTeamId(dto.getTeamId());
        entity.setPlayerId(dto.getPlayerId());
        entity.setAvailable(dto.getIsAvailable());
        entity.setAlternate(dto.getIsAlternate());
        entity.setCreatedAt(dto.getCreated());
        entity.setUpdatedAt(dto.getUpdated());
        return entity;
    }

    public static IoBetOutcome addResultData(IoBetOutcome entity, IoBettingOutcomeResultDto resultDto) {
        if (entity == null || resultDto == null) {
            return entity;
        }
        entity.setResultTypeId(resultDto.typeId());
        entity.setResultType(resultDto.type());
        entity.setResultValue(resultDto.actualValue());
        return entity;
    }

    public static List<IoBetOutcome> toBetOutcomeEntityList(IoBet bet, List<SportsDataBettingMarketDTO.BettingOutcomeDTO> betOutcomeDTOList) {
        return betOutcomeDTOList.stream()
                .map(betOutcomeDTO -> toBetOutcomeEntity(betOutcomeDTO, bet))
                .toList();
    }

    public List<OddsDTO> toOddsDTOList(List<SportsDataBetRow> bets) {
        return bets.stream()
                .map(this::toOddsDTO)
                .toList();
    }

    public OddsDTO toOddsDTO(SportsDataBetRow bet) {
        String price = bet.getPrice();
        String roundedPrice;

        try {
            roundedPrice = new BigDecimal(price)
                    .setScale(3, RoundingMode.HALF_UP)
                    .toPlainString();
        } catch (Exception e) {
            // fallback to original string if conversion fails
            roundedPrice = price;
        }


        return OddsDTO.builder()
                .id(bet.getId())
                .marketName(bet.getMarketType())
                .marketId(bet.getMarketTypeId())
                .betType(bet.getBetType())
                .line(bet.getBetLine())
                .price(roundedPrice)
                .status(BetStatus.OPEN.name())
                .lastUpdatedDate(bet.getLastUpdated())
                .build();
    }
}
