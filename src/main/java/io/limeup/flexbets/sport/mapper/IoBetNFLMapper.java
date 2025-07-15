package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.sportsdata.SportsDataBettingMarketDTO;
import io.limeup.flexbets.sport.model.IoBetNFL;
import io.limeup.flexbets.sport.model.IoBetOutcomeNFL;
import io.limeup.flexbets.sport.model.IoEventNFL;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class IoBetNFLMapper {

    public IoBetNFL toEntity(SportsDataBettingMarketDTO dto, IoEventNFL event) {
        if (dto == null) return null;

        return IoBetNFL.builder()
                .marketId(dto.getBettingMarketId())
                .event(event)
                .bettingMarketTypeId(dto.getBettingMarketTypeId())
                .bettingMarketType(dto.getBettingMarketType())
                .bettingBetTypeId(dto.getBettingBetTypeId())
                .bettingBetType(dto.getBettingBetType())
                .bettingPeriodTypeId(dto.getBettingPeriodTypeId())
                .bettingPeriodType(dto.getBettingPeriodType())
                .name(dto.getName())
                .teamId(dto.getTeamId())
                .teamKey(dto.getTeamKey())
                .playerId(dto.getPlayerId())
                .playerName(dto.getPlayerName())
                .anyBetsAvailable(Boolean.TRUE.equals(dto.getAnyBetsAvailable()))
                .isArchived(Boolean.TRUE.equals(dto.getArchived()))
                .createdAt(dto.getCreated())
                .updatedAt(dto.getUpdated())
                .bettingOutcomes(mapOutcomes(dto.getBettingOutcomes(), null))
                .build();
    }

    public void updateEntity(IoBetNFL existing, SportsDataBettingMarketDTO dto) {
        existing.setBettingMarketTypeId(dto.getBettingMarketTypeId());
        existing.setBettingMarketType(dto.getBettingMarketType());
        existing.setBettingBetTypeId(dto.getBettingBetTypeId());
        existing.setBettingBetType(dto.getBettingBetType());
        existing.setBettingPeriodTypeId(dto.getBettingPeriodTypeId());
        existing.setBettingPeriodType(dto.getBettingPeriodType());
        existing.setName(dto.getName());
        existing.setTeamId(dto.getTeamId());
        existing.setTeamKey(dto.getTeamKey());
        existing.setPlayerId(dto.getPlayerId());
        existing.setPlayerName(dto.getPlayerName());
        existing.setAnyBetsAvailable(Boolean.TRUE.equals(dto.getAnyBetsAvailable()));
        existing.setArchived(Boolean.TRUE.equals(dto.getArchived()));
        existing.setUpdatedAt(dto.getUpdated());

        // Update outcomes
        Map<Long, IoBetOutcomeNFL> existingOutcomes = existing.getBettingOutcomes().stream()
                .collect(Collectors.toMap(IoBetOutcomeNFL::getOutcomeId, o -> o));

        existing.getBettingOutcomes().clear();
        existing.getBettingOutcomes().addAll(mapOutcomes(dto.getBettingOutcomes(), existingOutcomes));
    }

    private List<IoBetOutcomeNFL> mapOutcomes(List<SportsDataBettingMarketDTO.BettingOutcomeDTO> outcomeDTOs,
                                              Map<Long, IoBetOutcomeNFL> existingOutcomes) {
        if (outcomeDTOs == null) return Collections.emptyList();

        return outcomeDTOs.stream()
                .map(dto -> {
                    IoBetOutcomeNFL outcome = (existingOutcomes != null && existingOutcomes.containsKey(dto.getBettingOutcomeId()))
                            ? existingOutcomes.get(dto.getBettingOutcomeId())
                            : new IoBetOutcomeNFL();

                    outcome.setOutcomeId(dto.getBettingOutcomeId());
                    /*outcome.setName(dto.getName());
                    outcome.setOdds(dto.getOdds());
                    outcome.setHandicap(dto.getHandicap());
                    outcome.setTotal(dto.getTotal());*/
                    outcome.setAvailable(Boolean.TRUE.equals(dto.getIsAvailable()));
                    outcome.setCreatedAt(dto.getCreated());
                    outcome.setUpdatedAt(dto.getUpdated());

                    return outcome;
                })
                .collect(Collectors.toList());
    }
}
