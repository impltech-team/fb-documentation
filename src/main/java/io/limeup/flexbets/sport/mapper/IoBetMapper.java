package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.sportsdata.SportsDataBettingMarketDTO;
import io.limeup.flexbets.sport.model.IoBet;
import io.limeup.flexbets.sport.model.IoBetOutcome;
import io.limeup.flexbets.sport.model.IoEvent;
import org.springframework.stereotype.Component;

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

    public static List<IoBetOutcome> toBetOutcomeEntityList(IoBet bet, List<SportsDataBettingMarketDTO.BettingOutcomeDTO> betOutcomeDTOList) {
        return betOutcomeDTOList.stream()
                .map(betOutcomeDTO -> toBetOutcomeEntity(betOutcomeDTO, bet))
                .toList();
    }
}
