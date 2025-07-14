package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.model.IoPlayerScoringDetailNFL;
import io.limeup.flexbets.sport.model.IoPlayerStatsNFL;
import io.limeup.flexbets.sport.model.dto.IoSportsDataNFLPlayerStatsDTO;
import io.limeup.flexbets.sport.model.dto.IoSportsDataNFLScoringDetailDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IoPlayersStatsNFLMapper {

    public void merge(IoPlayerStatsNFL entity, IoSportsDataNFLPlayerStatsDTO dto) {
        // Player info
        entity.setPlayerId(String.valueOf(dto.getPlayerId()));
        entity.setSeasonType(String.valueOf(dto.getSeasonType()));
        entity.setSeason(dto.getSeason());
        entity.setTeam(dto.getTeam());
        entity.setNumber(String.valueOf(dto.getNumber()));
        entity.setName(dto.getName());
        entity.setPosition(dto.getPosition());
        entity.setPositionCategory(dto.getPositionCategory());
        entity.setActivated(String.valueOf(dto.getActivated()));
        entity.setPlayed(String.valueOf(dto.getPlayed()));
        entity.setStarted(String.valueOf(dto.getStarted()));

        // Passing stats
        entity.setPassingAttempts(String.valueOf(dto.getPassingAttempts()));
        entity.setPassingCompletions(String.valueOf(dto.getPassingCompletions()));
        entity.setPassingYards(String.valueOf(dto.getPassingYards()));
        entity.setPassingCompletionPercentage(String.valueOf(dto.getPassingCompletionPercentage()));
        entity.setPassingYardsPerAttempt(String.valueOf(dto.getPassingYardsPerAttempt()));
        entity.setPassingYardsPerCompletion(String.valueOf(dto.getPassingYardsPerCompletion()));
        entity.setPassingTouchdowns(String.valueOf(dto.getPassingTouchdowns()));
        entity.setPassingInterceptions(String.valueOf(dto.getPassingInterceptions()));
        entity.setPassingRating(String.valueOf(dto.getPassingRating()));
        entity.setPassingLong(String.valueOf(dto.getPassingLong()));
        entity.setPassingSacks(String.valueOf(dto.getPassingSacks()));
        entity.setPassingSackYards(String.valueOf(dto.getPassingSackYards()));

        // Rushing stats
        entity.setRushingAttempts(String.valueOf(dto.getRushingAttempts()));
        entity.setRushingYards(String.valueOf(dto.getRushingYards()));
        entity.setRushingYardsPerAttempt(String.valueOf(dto.getRushingYardsPerAttempt()));
        entity.setRushingTouchdowns(String.valueOf(dto.getRushingTouchdowns()));
        entity.setRushingLong(String.valueOf(dto.getRushingLong()));

        // Receiving stats
        entity.setReceivingTargets(String.valueOf(dto.getReceivingTargets()));
        entity.setReceptions(String.valueOf(dto.getReceptions()));
        entity.setReceivingYards(String.valueOf(dto.getReceivingYards()));
        entity.setReceivingYardsPerReception(String.valueOf(dto.getReceivingYardsPerReception()));
        entity.setReceivingTouchdowns(String.valueOf(dto.getReceivingTouchdowns()));
        entity.setReceivingLong(String.valueOf(dto.getReceivingLong()));

        // Fumbles
        entity.setFumbles(String.valueOf(dto.getFumbles()));
        entity.setFumblesLost(String.valueOf(dto.getFumblesLost()));

        // Returns
        entity.setPuntReturns(String.valueOf(dto.getPuntReturns()));
        entity.setPuntReturnYards(String.valueOf(dto.getPuntReturnYards()));
        entity.setPuntReturnYardsPerAttempt(String.valueOf(dto.getPuntReturnYardsPerAttempt()));
        entity.setPuntReturnTouchdowns(String.valueOf(dto.getPuntReturnTouchdowns()));
        entity.setPuntReturnLong(String.valueOf(dto.getPuntReturnLong()));
        entity.setKickReturns(String.valueOf(dto.getKickReturns()));
        entity.setKickReturnYards(String.valueOf(dto.getKickReturnYards()));
        entity.setKickReturnYardsPerAttempt(String.valueOf(dto.getKickReturnYardsPerAttempt()));
        entity.setKickReturnTouchdowns(String.valueOf(dto.getKickReturnTouchdowns()));
        entity.setKickReturnLong(String.valueOf(dto.getKickReturnLong()));

        // Defensive stats
        entity.setSoloTackles(String.valueOf(dto.getSoloTackles()));
        entity.setAssistedTackles(String.valueOf(dto.getAssistedTackles()));
        entity.setTacklesForLoss(String.valueOf(dto.getTacklesForLoss()));
        entity.setSacks(String.valueOf(dto.getSacks()));
        entity.setSackYards(String.valueOf(dto.getSackYards()));
        entity.setQuarterbackHits(String.valueOf(dto.getQuarterbackHits()));
        entity.setPassesDefended(String.valueOf(dto.getPassesDefended()));
        entity.setFumblesForced(String.valueOf(dto.getFumblesForced()));
        entity.setFumblesRecovered(String.valueOf(dto.getFumblesRecovered()));
        entity.setFumbleReturnYards(String.valueOf(dto.getFumbleReturnYards()));
        entity.setFumbleReturnTouchdowns(String.valueOf(dto.getFumbleReturnTouchdowns()));
        entity.setInterceptions(String.valueOf(dto.getInterceptions()));
        entity.setInterceptionReturnYards(String.valueOf(dto.getInterceptionReturnYards()));
        entity.setInterceptionReturnTouchdowns(String.valueOf(dto.getInterceptionReturnTouchdowns()));
        entity.setBlockedKicks(String.valueOf(dto.getBlockedKicks()));

        // Special teams tackles
        entity.setSpecialTeamsSoloTackles(String.valueOf(dto.getSpecialTeamsSoloTackles()));
        entity.setSpecialTeamsAssistedTackles(String.valueOf(dto.getSpecialTeamsAssistedTackles()));
        entity.setMiscSoloTackles(String.valueOf(dto.getMiscSoloTackles()));
        entity.setMiscAssistedTackles(String.valueOf(dto.getMiscAssistedTackles()));

        // Kicking stats
        entity.setPunts(String.valueOf(dto.getPunts()));
        entity.setPuntYards(String.valueOf(dto.getPuntYards()));
        entity.setPuntAverage(String.valueOf(dto.getPuntAverage()));
        entity.setFieldGoalsAttempted(String.valueOf(dto.getFieldGoalsAttempted()));
        entity.setFieldGoalsMade(String.valueOf(dto.getFieldGoalsMade()));
        entity.setFieldGoalsLongestMade(String.valueOf(dto.getFieldGoalsLongestMade()));
        entity.setExtraPointsMade(String.valueOf(dto.getExtraPointsMade()));

        // Two-point conversions
        entity.setTwoPointConversionPasses(String.valueOf(dto.getTwoPointConversionPasses()));
        entity.setTwoPointConversionRuns(String.valueOf(dto.getTwoPointConversionRuns()));
        entity.setTwoPointConversionReceptions(String.valueOf(dto.getTwoPointConversionReceptions()));

        // Fantasy stats
        entity.setFantasyPoints(String.valueOf(dto.getFantasyPoints()));
        entity.setFantasyPointsPPR(String.valueOf(dto.getFantasyPointsPPR()));
        entity.setReceptionPercentage(String.valueOf(dto.getReceptionPercentage()));
        entity.setReceivingYardsPerTarget(String.valueOf(dto.getReceivingYardsPerTarget()));

        // Combined stats
        entity.setTackles(String.valueOf(dto.getTackles()));
        entity.setOffensiveTouchdowns(String.valueOf(dto.getOffensiveTouchdowns()));
        entity.setDefensiveTouchdowns(String.valueOf(dto.getDefensiveTouchdowns()));
        entity.setSpecialTeamsTouchdowns(String.valueOf(dto.getSpecialTeamsTouchdowns()));
        entity.setTouchdowns(String.valueOf(dto.getTouchdowns()));
        entity.setFantasyPosition(dto.getFantasyPosition());
        entity.setFieldGoalPercentage(String.valueOf(dto.getFieldGoalPercentage()));

        // Additional fields
        entity.setPlayerSeasonId(String.valueOf(dto.getPlayerSeasonId()));
        entity.setFumblesOwnRecoveries(String.valueOf(dto.getFumblesOwnRecoveries()));
        entity.setFumblesOutOfBounds(String.valueOf(dto.getFumblesOutOfBounds()));
        entity.setKickReturnFairCatches(String.valueOf(dto.getKickReturnFairCatches()));
        entity.setPuntReturnFairCatches(String.valueOf(dto.getPuntReturnFairCatches()));
        entity.setPuntTouchbacks(String.valueOf(dto.getPuntTouchbacks()));
        entity.setPuntInside20(String.valueOf(dto.getPuntInside20()));
        entity.setPuntNetAverage(String.valueOf(dto.getPuntNetAverage()));
        entity.setExtraPointsAttempted(String.valueOf(dto.getExtraPointsAttempted()));
        entity.setBlockedKickReturnTouchdowns(String.valueOf(dto.getBlockedKickReturnTouchdowns()));
        entity.setFieldGoalReturnTouchdowns(String.valueOf(dto.getFieldGoalReturnTouchdowns()));
        entity.setSafeties(String.valueOf(dto.getSafeties()));
        entity.setFieldGoalsHadBlocked(String.valueOf(dto.getFieldGoalsHadBlocked()));
        entity.setPuntsHadBlocked(String.valueOf(dto.getPuntsHadBlocked()));
        entity.setExtraPointsHadBlocked(String.valueOf(dto.getExtraPointsHadBlocked()));
        entity.setPuntLong(String.valueOf(dto.getPuntLong()));
        entity.setBlockedKickReturnYards(String.valueOf(dto.getBlockedKickReturnYards()));
        entity.setFieldGoalReturnYards(String.valueOf(dto.getFieldGoalReturnYards()));
        entity.setPuntNetYards(String.valueOf(dto.getPuntNetYards()));
        entity.setSpecialTeamsFumblesForced(String.valueOf(dto.getSpecialTeamsFumblesForced()));
        entity.setSpecialTeamsFumblesRecovered(String.valueOf(dto.getSpecialTeamsFumblesRecovered()));
        entity.setMiscFumblesForced(String.valueOf(dto.getMiscFumblesForced()));
        entity.setMiscFumblesRecovered(String.valueOf(dto.getMiscFumblesRecovered()));
        entity.setShortName(dto.getShortName());
        entity.setSafetiesAllowed(String.valueOf(dto.getSafetiesAllowed()));
        entity.setTemperature(String.valueOf(dto.getTemperature()));
        entity.setHumidity(String.valueOf(dto.getHumidity()));
        entity.setWindSpeed(String.valueOf(dto.getWindSpeed()));
        entity.setOffensiveSnapsPlayed(String.valueOf(dto.getOffensiveSnapsPlayed()));
        entity.setDefensiveSnapsPlayed(String.valueOf(dto.getDefensiveSnapsPlayed()));
        entity.setSpecialTeamsSnapsPlayed(String.valueOf(dto.getSpecialTeamsSnapsPlayed()));
        entity.setOffensiveTeamSnaps(String.valueOf(dto.getOffensiveTeamSnaps()));
        entity.setDefensiveTeamSnaps(String.valueOf(dto.getDefensiveTeamSnaps()));
        entity.setSpecialTeamsTeamSnaps(String.valueOf(dto.getSpecialTeamsTeamSnaps()));
        entity.setAuctionValue(String.valueOf(dto.getAuctionValue()));
        entity.setAuctionValuePPR(String.valueOf(dto.getAuctionValuePPR()));
        entity.setTwoPointConversionReturns(String.valueOf(dto.getTwoPointConversionReturns()));
        entity.setFantasyPointsFanDuel(String.valueOf(dto.getFantasyPointsFanDuel()));
        entity.setFieldGoalsMade0to19(String.valueOf(dto.getFieldGoalsMade0to19()));
        entity.setFieldGoalsMade20to29(String.valueOf(dto.getFieldGoalsMade20to29()));
        entity.setFieldGoalsMade30to39(String.valueOf(dto.getFieldGoalsMade30to39()));
        entity.setFieldGoalsMade40to49(String.valueOf(dto.getFieldGoalsMade40to49()));
        entity.setFieldGoalsMade50Plus(String.valueOf(dto.getFieldGoalsMade50Plus()));
        entity.setFantasyPointsDraftKings(String.valueOf(dto.getFantasyPointsDraftKings()));
        entity.setFantasyPointsYahoo(String.valueOf(dto.getFantasyPointsYahoo()));
        entity.setAverageDraftPosition(String.valueOf(dto.getAverageDraftPosition()));
        entity.setAverageDraftPositionPPR(String.valueOf(dto.getAverageDraftPositionPPR()));
        entity.setTeamId(String.valueOf(dto.getTeamId()));
        entity.setGlobalTeamId(String.valueOf(dto.getGlobalTeamId()));
        entity.setFantasyPointsFantasyDraft(String.valueOf(dto.getFantasyPointsFantasyDraft()));
        entity.setAverageDraftPositionRookie(String.valueOf(dto.getAverageDraftPositionRookie()));
        entity.setAverageDraftPositionDynasty(String.valueOf(dto.getAverageDraftPositionDynasty()));
        entity.setAverageDraftPosition2QB(String.valueOf(dto.getAverageDraftPosition2QB()));
        entity.setOffensiveFumbleRecoveryTouchdowns(String.valueOf(dto.getOffensiveFumbleRecoveryTouchdowns()));
        entity.setGameId(dto.getGameId());

        // Handle scoring details - updated implementation
        if (dto.getScoringDetails() != null && !dto.getScoringDetails().isEmpty()) {
            mergeScoringDetails(entity, dto.getScoringDetails());
        }


    }

    public IoPlayerStatsNFL toEntity(IoSportsDataNFLPlayerStatsDTO dto) {
        IoPlayerStatsNFL entity = new IoPlayerStatsNFL();
        merge(entity, dto);
        return entity;
    }

    /**
     * Merges scoring details from DTO to entity
     */
    public void mergeScoringDetails(IoPlayerStatsNFL entity, List<IoSportsDataNFLScoringDetailDTO> scoringDetails) {
        // Initialize collection if null
        if (entity.getScoringDetails() == null) {
            entity.setScoringDetails(new ArrayList<>());
        } else {
            entity.getScoringDetails().clear();
        }

        for (IoSportsDataNFLScoringDetailDTO detailDto : scoringDetails) {
            IoPlayerScoringDetailNFL detail = mapScoringDetail(detailDto);
            detail.setPlayerGameStats(entity);  // Set the parent reference
            entity.getScoringDetails().add(detail);
        }
    }

    private IoPlayerScoringDetailNFL mapScoringDetail(IoSportsDataNFLScoringDetailDTO dto) {
        IoPlayerScoringDetailNFL detail = new IoPlayerScoringDetailNFL();

        // Map all fields with proper type conversion
        detail.setGameKey(dto.getGameKey());
        detail.setSeasonType(toStringSafe(dto.getSeasonType()));
        detail.setPlayerId(toStringSafe(dto.getPlayerId()));
        detail.setTeam(dto.getTeam());
        detail.setSeason(toStringSafe(dto.getSeason()));
        detail.setWeek(toStringSafe(dto.getWeek()));
        detail.setScoringType(dto.getScoringType());
        detail.setLength(toStringSafe(dto.getLength()));
        detail.setScoringDetailId(toStringSafe(dto.getScoringDetailId()));
        detail.setPlayerGameId(toStringSafe(dto.getPlayerGameId()));
        detail.setScoringPlayId(toStringSafe(dto.getScoringPlayId()));

        return detail;
    }

    // Utility methods for safe type conversion
    private String toStringSafe(Long value) {
        return value != null ? value.toString() : null;
    }

    private String toStringSafe(Integer value) {
        return value != null ? value.toString() : null;
    }

    private String toStringSafe(Object value) {
        return value != null ? String.valueOf(value) : null;
    }


}