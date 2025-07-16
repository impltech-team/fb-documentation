package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.OddsDTO;
import io.limeup.flexbets.sport.dto.sportsdata.SportsDataBettingMarketDTO;
import io.limeup.flexbets.sport.model.IoBetNFL;
import io.limeup.flexbets.sport.model.IoBetOutcomeNFL;
import io.limeup.flexbets.sport.model.IoEventNFL;
import io.limeup.flexbets.sport.model.enums.BetStatus;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataBetRow;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
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
        if (existing == null || dto == null) return;

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
                            : IoBetOutcomeNFL.builder().build();

                    outcome.setOutcomeId(dto.getBettingOutcomeId());
                    outcome.setName(dto.getParticipant());

                    // Convert payout decimal to integer odds (1.85 -> 185)
                    try {
                        if (dto.getPayoutDecimal() != null) {
                            outcome.setOdds((int)(Double.parseDouble(dto.getPayoutDecimal()) * 100));
                        }
                    } catch (NumberFormatException e) {
                        outcome.setOdds(null);
                    }

                    outcome.setAvailable(Boolean.TRUE.equals(dto.getIsAvailable()));
                    outcome.setCreatedAt(dto.getCreated());
                    outcome.setUpdatedAt(dto.getUpdated());

                    return outcome;
                })
                .collect(Collectors.toList());
    }

    public List<OddsDTO> toOddsDTOList(List<SportsDataBetRow> bets) {
        if (bets == null) return Collections.emptyList();

        return bets.stream()
                .map(this::toOddsDTO)
                .collect(Collectors.toList());
    }

    public OddsDTO toOddsDTO(SportsDataBetRow bet) {
        if (bet == null) return null;

        String roundedPrice;
        try {
            roundedPrice = new BigDecimal(bet.getPrice())
                    .setScale(3, RoundingMode.HALF_UP)
                    .toPlainString();
        } catch (Exception e) {
            roundedPrice = bet.getPrice(); // Fallback to original if conversion fails
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