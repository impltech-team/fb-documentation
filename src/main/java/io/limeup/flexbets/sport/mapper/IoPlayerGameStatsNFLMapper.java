package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.sportsdata.IoPlayerGameStatsNFLDto;
import io.limeup.flexbets.sport.model.IoPlayerGameStatsNFL;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class IoPlayerGameStatsNFLMapper {

    public void merge(IoPlayerGameStatsNFL entity, IoPlayerGameStatsNFLDto dto) {
        if (dto.getPlayerId() != null) entity.setPlayerId(dto.getPlayerId());
        if (dto.getGameKey() != null) entity.setGameKey(dto.getGameKey());
        if (dto.getGlobalGameId() != null) entity.setGlobalGameId(dto.getGlobalGameId());
        if (dto.getSeason() != null) entity.setSeason(dto.getSeason());
        if (dto.getSeasonType() != null) entity.setSeasonType(dto.getSeasonType());
        if (dto.getWeek() != null) entity.setWeek(dto.getWeek());
        if (dto.getTeam() != null) entity.setTeam(dto.getTeam());
        if (dto.getTeamId() != null) entity.setTeamId(dto.getTeamId());
        if (dto.getGlobalTeamId() != null) entity.setGlobalTeamId(dto.getGlobalTeamId());
        if (dto.getOpponent() != null) entity.setOpponent(dto.getOpponent());
        if (dto.getOpponentId() != null) entity.setOpponentId(dto.getOpponentId());
        if (dto.getGlobalOpponentId() != null) entity.setGlobalOpponentId(dto.getGlobalOpponentId());
        if (dto.getHomeOrAway() != null) entity.setHomeOrAway(dto.getHomeOrAway());
        if (dto.getName() != null) entity.setPlayerName(dto.getName());
        if (dto.getShortName() != null) entity.setPlayerShortName(dto.getShortName());
        if (dto.getNumber() != null) entity.setPlayerNumber(dto.getNumber());
        if (dto.getPosition() != null) entity.setPosition(dto.getPosition());
        if (dto.getPositionCategory() != null) entity.setPositionCategory(dto.getPositionCategory());
        if (dto.getFantasyPosition() != null) entity.setFantasyPosition(dto.getFantasyPosition());
        if (dto.getActivated() != null) entity.setActivated(dto.getActivated());
        if (dto.getPlayed() != null) entity.setPlayed(dto.getPlayed());
        if (dto.getStarted() != null) entity.setStarted(dto.getStarted());
        if (dto.getDeclaredInactive() != null) entity.setDeclaredInactive(dto.getDeclaredInactive());

        // Passing stats
        if (dto.getPassingAttempts() != null) entity.setPassingAttempts(dto.getPassingAttempts());
        if (dto.getPassingCompletions() != null) entity.setPassingCompletions(dto.getPassingCompletions());
        if (dto.getPassingYards() != null) entity.setPassingYards(dto.getPassingYards());
        if (dto.getPassingCompletionPercentage() != null) entity.setPassingCompletionPercentage(dto.getPassingCompletionPercentage());
        if (dto.getPassingYardsPerAttempt() != null) entity.setPassingYardsPerAttempt(dto.getPassingYardsPerAttempt());
        if (dto.getPassingYardsPerCompletion() != null) entity.setPassingYardsPerCompletion(dto.getPassingYardsPerCompletion());
        if (dto.getPassingTouchdowns() != null) entity.setPassingTouchdowns(dto.getPassingTouchdowns());
        if (dto.getPassingInterceptions() != null) entity.setPassingInterceptions(dto.getPassingInterceptions());
        if (dto.getPassingRating() != null) entity.setPassingRating(dto.getPassingRating());
        if (dto.getPassingLong() != null) entity.setPassingLong(dto.getPassingLong());
        if (dto.getPassingSacks() != null) entity.setPassingSacks(dto.getPassingSacks());
        if (dto.getPassingSackYards() != null) entity.setPassingSackYards(dto.getPassingSackYards());

        // Rushing stats
        if (dto.getRushingAttempts() != null) entity.setRushingAttempts(dto.getRushingAttempts());
        if (dto.getRushingYards() != null) entity.setRushingYards(dto.getRushingYards());
        if (dto.getRushingYardsPerAttempt() != null) entity.setRushingYardsPerAttempt(dto.getRushingYardsPerAttempt());
        if (dto.getRushingTouchdowns() != null) entity.setRushingTouchdowns(dto.getRushingTouchdowns());
        if (dto.getRushingLong() != null) entity.setRushingLong(dto.getRushingLong());

        // Receiving stats
        if (dto.getReceivingTargets() != null) entity.setReceivingTargets(dto.getReceivingTargets());
        if (dto.getReceptions() != null) entity.setReceptions(dto.getReceptions());
        if (dto.getReceivingYards() != null) entity.setReceivingYards(dto.getReceivingYards());
        if (dto.getReceivingYardsPerReception() != null) entity.setReceivingYardsPerReception(dto.getReceivingYardsPerReception());
        if (dto.getReceivingTouchdowns() != null) entity.setReceivingTouchdowns(dto.getReceivingTouchdowns());
        if (dto.getReceivingLong() != null) entity.setReceivingLong(dto.getReceivingLong());
        if (dto.getReceptionPercentage() != null) entity.setReceptionPercentage(dto.getReceptionPercentage());
        if (dto.getReceivingYardsPerTarget() != null) entity.setReceivingYardsPerTarget(dto.getReceivingYardsPerTarget());

        // Defensive stats
        if (dto.getSoloTackles() != null) entity.setSoloTackles(dto.getSoloTackles());
        if (dto.getAssistedTackles() != null) entity.setAssistedTackles(dto.getAssistedTackles());
        if (dto.getTackles() != null) entity.setTackles(dto.getTackles());
        if (dto.getTacklesForLoss() != null) entity.setTacklesForLoss(dto.getTacklesForLoss());
        if (dto.getSacks() != null) entity.setSacks(dto.getSacks());
        if (dto.getSackYards() != null) entity.setSackYards(dto.getSackYards());
        if (dto.getQuarterbackHits() != null) entity.setQuarterbackHits(dto.getQuarterbackHits());
        if (dto.getPassesDefended() != null) entity.setPassesDefended(dto.getPassesDefended());
        if (dto.getInterceptions() != null) entity.setInterceptions(dto.getInterceptions());
        if (dto.getInterceptionReturnYards() != null) entity.setInterceptionReturnYards(dto.getInterceptionReturnYards());
        if (dto.getInterceptionReturnTouchdowns() != null) entity.setInterceptionReturnTouchdowns(dto.getInterceptionReturnTouchdowns());

        // Fumbles
        if (dto.getFumbles() != null) entity.setFumbles(dto.getFumbles());
        if (dto.getFumblesLost() != null) entity.setFumblesLost(dto.getFumblesLost());
        if (dto.getFumblesForced() != null) entity.setFumblesForced(dto.getFumblesForced());
        if (dto.getFumblesRecovered() != null) entity.setFumblesRecovered(dto.getFumblesRecovered());
        if (dto.getFumbleReturnYards() != null) entity.setFumbleReturnYards(dto.getFumbleReturnYards());
        if (dto.getFumbleReturnTouchdowns() != null) entity.setFumbleReturnTouchdowns(dto.getFumbleReturnTouchdowns());
        if (dto.getFumblesOwnRecoveries() != null) entity.setFumblesOwnRecoveries(dto.getFumblesOwnRecoveries());
        if (dto.getFumblesOutOfBounds() != null) entity.setFumblesOutOfBounds(dto.getFumblesOutOfBounds());

        // Special teams - Punt returns
        if (dto.getPuntReturns() != null) entity.setPuntReturns(dto.getPuntReturns());
        if (dto.getPuntReturnYards() != null) entity.setPuntReturnYards(dto.getPuntReturnYards());
        if (dto.getPuntReturnYardsPerAttempt() != null) entity.setPuntReturnYardsPerAttempt(dto.getPuntReturnYardsPerAttempt());
        if (dto.getPuntReturnTouchdowns() != null) entity.setPuntReturnTouchdowns(dto.getPuntReturnTouchdowns());
        if (dto.getPuntReturnLong() != null) entity.setPuntReturnLong(dto.getPuntReturnLong());
        if (dto.getPuntReturnFairCatches() != null) entity.setPuntReturnFairCatches(dto.getPuntReturnFairCatches());

        // Special teams - Kick returns
        if (dto.getKickReturns() != null) entity.setKickReturns(dto.getKickReturns());
        if (dto.getKickReturnYards() != null) entity.setKickReturnYards(dto.getKickReturnYards());
        if (dto.getKickReturnYardsPerAttempt() != null) entity.setKickReturnYardsPerAttempt(dto.getKickReturnYardsPerAttempt());
        if (dto.getKickReturnTouchdowns() != null) entity.setKickReturnTouchdowns(dto.getKickReturnTouchdowns());
        if (dto.getKickReturnLong() != null) entity.setKickReturnLong(dto.getKickReturnLong());
        if (dto.getKickReturnFairCatches() != null) entity.setKickReturnFairCatches(dto.getKickReturnFairCatches());

        // Kicking
        if (dto.getFieldGoalsAttempted() != null) entity.setFieldGoalsAttempted(dto.getFieldGoalsAttempted());
        if (dto.getFieldGoalsMade() != null) entity.setFieldGoalsMade(dto.getFieldGoalsMade());
        if (dto.getFieldGoalsLongestMade() != null) entity.setFieldGoalsLongestMade(dto.getFieldGoalsLongestMade());
        if (dto.getFieldGoalPercentage() != null) entity.setFieldGoalPercentage(dto.getFieldGoalPercentage());
        if (dto.getFieldGoalsHadBlocked() != null) entity.setFieldGoalsHadBlocked(dto.getFieldGoalsHadBlocked());
        if (dto.getFieldGoalsMade0to19() != null) entity.setFieldGoalsMade0to19(dto.getFieldGoalsMade0to19());
        if (dto.getFieldGoalsMade20to29() != null) entity.setFieldGoalsMade20to29(dto.getFieldGoalsMade20to29());
        if (dto.getFieldGoalsMade30to39() != null) entity.setFieldGoalsMade30to39(dto.getFieldGoalsMade30to39());
        if (dto.getFieldGoalsMade40to49() != null) entity.setFieldGoalsMade40to49(dto.getFieldGoalsMade40to49());
        if (dto.getFieldGoalsMade50Plus() != null) entity.setFieldGoalsMade50plus(dto.getFieldGoalsMade50Plus());
        if (dto.getExtraPointsAttempted() != null) entity.setExtraPointsAttempted(dto.getExtraPointsAttempted());
        if (dto.getExtraPointsMade() != null) entity.setExtraPointsMade(dto.getExtraPointsMade());
        if (dto.getExtraPointsHadBlocked() != null) entity.setExtraPointsHadBlocked(dto.getExtraPointsHadBlocked());

        // Punting
        if (dto.getPunts() != null) entity.setPunts(dto.getPunts());
        if (dto.getPuntYards() != null) entity.setPuntYards(dto.getPuntYards());
        if (dto.getPuntAverage() != null) entity.setPuntAverage(dto.getPuntAverage());
        if (dto.getPuntLong() != null) entity.setPuntLong(dto.getPuntLong());
        if (dto.getPuntInside20() != null) entity.setPuntInside20(dto.getPuntInside20());
        if (dto.getPuntTouchbacks() != null) entity.setPuntTouchbacks(dto.getPuntTouchbacks());
        if (dto.getPuntsHadBlocked() != null) entity.setPuntsHadBlocked(dto.getPuntsHadBlocked());
        if (dto.getPuntNetAverage() != null) entity.setPuntNetAverage(dto.getPuntNetAverage());
        if (dto.getPuntNetYards() != null) entity.setPuntNetYards(dto.getPuntNetYards());

        // Miscellaneous
        if (dto.getBlockedKicks() != null) entity.setBlockedKicks(dto.getBlockedKicks());
        if (dto.getBlockedKickReturnYards() != null) entity.setBlockedKickReturnYards(dto.getBlockedKickReturnYards());
        if (dto.getBlockedKickReturnTouchdowns() != null) entity.setBlockedKickReturnTouchdowns(dto.getBlockedKickReturnTouchdowns());
        if (dto.getFieldGoalReturnYards() != null) entity.setFieldGoalReturnYards(dto.getFieldGoalReturnYards());
        if (dto.getFieldGoalReturnTouchdowns() != null) entity.setFieldGoalReturnTouchdowns(dto.getFieldGoalReturnTouchdowns());
        if (dto.getSafeties() != null) entity.setSafeties(dto.getSafeties());
        if (dto.getSafetiesAllowed() != null) entity.setSafetiesAllowed(dto.getSafetiesAllowed());

        // Two-point conversions
        if (dto.getTwoPointConversionPasses() != null) entity.setTwoPointConversionPasses(dto.getTwoPointConversionPasses());
        if (dto.getTwoPointConversionRuns() != null) entity.setTwoPointConversionRuns(dto.getTwoPointConversionRuns());
        if (dto.getTwoPointConversionReceptions() != null) entity.setTwoPointConversionReceptions(dto.getTwoPointConversionReceptions());
        if (dto.getTwoPointConversionReturns() != null) entity.setTwoPointConversionReturns(dto.getTwoPointConversionReturns());

        // Snap counts
        if (dto.getOffensiveSnapsPlayed() != null) entity.setOffensiveSnapsPlayed(dto.getOffensiveSnapsPlayed());
        if (dto.getOffensiveTeamSnaps() != null) entity.setOffensiveTeamSnaps(dto.getOffensiveTeamSnaps());
        if (dto.getDefensiveSnapsPlayed() != null) entity.setDefensiveSnapsPlayed(dto.getDefensiveSnapsPlayed());
        if (dto.getDefensiveTeamSnaps() != null) entity.setDefensiveTeamSnaps(dto.getDefensiveTeamSnaps());
        if (dto.getSpecialTeamsSnapsPlayed() != null) entity.setSpecialTeamsSnapsPlayed(dto.getSpecialTeamsSnapsPlayed());
        if (dto.getSpecialTeamsTeamSnaps() != null) entity.setSpecialTeamsTeamSnaps(dto.getSpecialTeamsTeamSnaps());
        if (dto.getSnapCountsConfirmed() != null) entity.setSnapCountsConfirmed(dto.getSnapCountsConfirmed());

        // Touchdowns
        if (dto.getOffensiveTouchdowns() != null) entity.setOffensiveTouchdowns(dto.getOffensiveTouchdowns());
        if (dto.getDefensiveTouchdowns() != null) entity.setDefensiveTouchdowns(dto.getDefensiveTouchdowns());
        if (dto.getSpecialTeamsTouchdowns() != null) entity.setSpecialTeamsTouchdowns(dto.getSpecialTeamsTouchdowns());
        if (dto.getTouchdowns() != null) entity.setTouchdowns(dto.getTouchdowns());

        // Fantasy points
        if (dto.getFantasyPoints() != null) entity.setFantasyPoints(dto.getFantasyPoints());
        if (dto.getFantasyPointsPPR() != null) entity.setFantasyPointsPpr(dto.getFantasyPointsPPR());
        if (dto.getFantasyPointsFanDuel() != null) entity.setFantasyPointsFanDuel(dto.getFantasyPointsFanDuel());
        if (dto.getFantasyPointsDraftKings() != null) entity.setFantasyPointsDraftKings(dto.getFantasyPointsDraftKings());
        if (dto.getFantasyPointsYahoo() != null) entity.setFantasyPointsYahoo(dto.getFantasyPointsYahoo());
        if (dto.getFantasyPointsFantasyDraft() != null) entity.setFantasyPointsFantasyDraft(dto.getFantasyPointsFantasyDraft());

        // Salaries
        if (dto.getFanDuelSalary() != null) entity.setFanDuelSalary(dto.getFanDuelSalary());
        if (dto.getDraftKingsSalary() != null) entity.setDraftKingsSalary(dto.getDraftKingsSalary());
        if (dto.getFantasyDataSalary() != null) entity.setFantasyDataSalary(dto.getFantasyDataSalary());
        if (dto.getYahooSalary() != null) entity.setYahooSalary(dto.getYahooSalary());
        if (dto.getFantasyDraftSalary() != null) entity.setFantasyDraftSalary(dto.getFantasyDraftSalary());

        // Positions
        if (dto.getFanDuelPosition() != null) entity.setFanDuelPosition(dto.getFanDuelPosition());
        if (dto.getDraftKingsPosition() != null) entity.setDraftKingsPosition(dto.getDraftKingsPosition());
        if (dto.getYahooPosition() != null) entity.setYahooPosition(dto.getYahooPosition());
        if (dto.getFantasyDraftPosition() != null) entity.setFantasyDraftPosition(dto.getFantasyDraftPosition());

        // Injuries
        if (dto.getInjuryStatus() != null) entity.setInjuryStatus(dto.getInjuryStatus());
        if (dto.getInjuryBodyPart() != null) entity.setInjuryBodyPart(dto.getInjuryBodyPart());
        if (dto.getInjuryStartDate() != null) entity.setInjuryStartDate(dto.getInjuryStartDate());
        if (dto.getInjuryNotes() != null) entity.setInjuryNotes(dto.getInjuryNotes());
        if (dto.getInjuryPractice() != null) entity.setInjuryPractice(dto.getInjuryPractice());
        if (dto.getInjuryPracticeDescription() != null) entity.setInjuryPracticeDescription(dto.getInjuryPracticeDescription());

        // Game environment
        if (dto.getStadium() != null) entity.setStadium(dto.getStadium());
        if (dto.getPlayingSurface() != null) entity.setPlayingSurface(dto.getPlayingSurface());
        if (dto.getTemperature() != null) entity.setTemperature(dto.getTemperature());
        if (dto.getHumidity() != null) entity.setHumidity(dto.getHumidity());
        if (dto.getWindSpeed() != null) entity.setWindSpeed(dto.getWindSpeed());

        // Opponent rankings
        if (dto.getOpponentRank() != null) entity.setOpponentRank(dto.getOpponentRank());
        if (dto.getOpponentPositionRank() != null) entity.setOpponentPositionRank(dto.getOpponentPositionRank());

        // Timestamps
        if (dto.getDay() != null) entity.setDay(dto.getDay());
        if (dto.getDateTime() != null) entity.setDateTime(dto.getDateTime());
        if (dto.getGameDate() != null) entity.setGameDate(dto.getGameDate());
        if (dto.getUpdated() != null) entity.setUpdated(dto.getUpdated());
    }

    public IoPlayerGameStatsNFL toEntity(IoPlayerGameStatsNFLDto dto) {
        return IoPlayerGameStatsNFL.builder()
                .playerId(dto.getPlayerId())
                .gameKey(dto.getGameKey())
                .globalGameId(dto.getGlobalGameId())
                .season(dto.getSeason())
                .seasonType(dto.getSeasonType())
                .week(dto.getWeek())
                .team(dto.getTeam())
                .teamId(dto.getTeamId())
                .globalTeamId(dto.getGlobalTeamId())
                .opponent(dto.getOpponent())
                .opponentId(dto.getOpponentId())
                .globalOpponentId(dto.getGlobalOpponentId())
                .homeOrAway(dto.getHomeOrAway())
                .playerName(dto.getName())
                .playerShortName(dto.getShortName())
                .playerNumber(dto.getNumber())
                .position(dto.getPosition())
                .positionCategory(dto.getPositionCategory())
                .fantasyPosition(dto.getFantasyPosition())
                .activated(dto.getActivated())
                .played(dto.getPlayed())
                .started(dto.getStarted())
                .declaredInactive(dto.getDeclaredInactive())

                // Passing stats
                .passingAttempts(dto.getPassingAttempts())
                .passingCompletions(dto.getPassingCompletions())
                .passingYards(dto.getPassingYards())
                .passingCompletionPercentage(dto.getPassingCompletionPercentage())
                .passingYardsPerAttempt(dto.getPassingYardsPerAttempt())
                .passingYardsPerCompletion(dto.getPassingYardsPerCompletion())
                .passingTouchdowns(dto.getPassingTouchdowns())
                .passingInterceptions(dto.getPassingInterceptions())
                .passingRating(dto.getPassingRating())
                .passingLong(dto.getPassingLong())
                .passingSacks(dto.getPassingSacks())
                .passingSackYards(dto.getPassingSackYards())

                // Rushing stats
                .rushingAttempts(dto.getRushingAttempts())
                .rushingYards(dto.getRushingYards())
                .rushingYardsPerAttempt(dto.getRushingYardsPerAttempt())
                .rushingTouchdowns(dto.getRushingTouchdowns())
                .rushingLong(dto.getRushingLong())

                // Receiving stats
                .receivingTargets(dto.getReceivingTargets())
                .receptions(dto.getReceptions())
                .receivingYards(dto.getReceivingYards())
                .receivingYardsPerReception(dto.getReceivingYardsPerReception())
                .receivingTouchdowns(dto.getReceivingTouchdowns())
                .receivingLong(dto.getReceivingLong())
                .receptionPercentage(dto.getReceptionPercentage())
                .receivingYardsPerTarget(dto.getReceivingYardsPerTarget())

                // Defensive stats
                .soloTackles(dto.getSoloTackles())
                .assistedTackles(dto.getAssistedTackles())
                .tackles(dto.getTackles())
                .tacklesForLoss(dto.getTacklesForLoss())
                .sacks(dto.getSacks())
                .sackYards(dto.getSackYards())
                .quarterbackHits(dto.getQuarterbackHits())
                .passesDefended(dto.getPassesDefended())
                .interceptions(dto.getInterceptions())
                .interceptionReturnYards(dto.getInterceptionReturnYards())
                .interceptionReturnTouchdowns(dto.getInterceptionReturnTouchdowns())

                // Fumbles
                .fumbles(dto.getFumbles())
                .fumblesLost(dto.getFumblesLost())
                .fumblesForced(dto.getFumblesForced())
                .fumblesRecovered(dto.getFumblesRecovered())
                .fumbleReturnYards(dto.getFumbleReturnYards())
                .fumbleReturnTouchdowns(dto.getFumbleReturnTouchdowns())
                .fumblesOwnRecoveries(dto.getFumblesOwnRecoveries())
                .fumblesOutOfBounds(dto.getFumblesOutOfBounds())

                // Special teams - Punt returns
                .puntReturns(dto.getPuntReturns())
                .puntReturnYards(dto.getPuntReturnYards())
                .puntReturnYardsPerAttempt(dto.getPuntReturnYardsPerAttempt())
                .puntReturnTouchdowns(dto.getPuntReturnTouchdowns())
                .puntReturnLong(dto.getPuntReturnLong())
                .puntReturnFairCatches(dto.getPuntReturnFairCatches())

                // Special teams - Kick returns
                .kickReturns(dto.getKickReturns())
                .kickReturnYards(dto.getKickReturnYards())
                .kickReturnYardsPerAttempt(dto.getKickReturnYardsPerAttempt())
                .kickReturnTouchdowns(dto.getKickReturnTouchdowns())
                .kickReturnLong(dto.getKickReturnLong())
                .kickReturnFairCatches(dto.getKickReturnFairCatches())

                // Kicking
                .fieldGoalsAttempted(dto.getFieldGoalsAttempted())
                .fieldGoalsMade(dto.getFieldGoalsMade())
                .fieldGoalsLongestMade(dto.getFieldGoalsLongestMade())
                .fieldGoalPercentage(dto.getFieldGoalPercentage())
                .fieldGoalsHadBlocked(dto.getFieldGoalsHadBlocked())
                .fieldGoalsMade0to19(dto.getFieldGoalsMade0to19())
                .fieldGoalsMade20to29(dto.getFieldGoalsMade20to29())
                .fieldGoalsMade30to39(dto.getFieldGoalsMade30to39())
                .fieldGoalsMade40to49(dto.getFieldGoalsMade40to49())
                .fieldGoalsMade50plus(dto.getFieldGoalsMade50Plus())
                .extraPointsAttempted(dto.getExtraPointsAttempted())
                .extraPointsMade(dto.getExtraPointsMade())
                .extraPointsHadBlocked(dto.getExtraPointsHadBlocked())

                // Punting
                .punts(dto.getPunts())
                .puntYards(dto.getPuntYards())
                .puntAverage(dto.getPuntAverage())
                .puntLong(dto.getPuntLong())
                .puntInside20(dto.getPuntInside20())
                .puntTouchbacks(dto.getPuntTouchbacks())
                .puntsHadBlocked(dto.getPuntsHadBlocked())
                .puntNetAverage(dto.getPuntNetAverage())
                .puntNetYards(dto.getPuntNetYards())

                // Miscellaneous
                .blockedKicks(dto.getBlockedKicks())
                .blockedKickReturnYards(dto.getBlockedKickReturnYards())
                .blockedKickReturnTouchdowns(dto.getBlockedKickReturnTouchdowns())
                .fieldGoalReturnYards(dto.getFieldGoalReturnYards())
                .fieldGoalReturnTouchdowns(dto.getFieldGoalReturnTouchdowns())
                .safeties(dto.getSafeties())
                .safetiesAllowed(dto.getSafetiesAllowed())

                // Two-point conversions
                .twoPointConversionPasses(dto.getTwoPointConversionPasses())
                .twoPointConversionRuns(dto.getTwoPointConversionRuns())
                .twoPointConversionReceptions(dto.getTwoPointConversionReceptions())
                .twoPointConversionReturns(dto.getTwoPointConversionReturns())

                // Snap counts
                .offensiveSnapsPlayed(dto.getOffensiveSnapsPlayed())
                .offensiveTeamSnaps(dto.getOffensiveTeamSnaps())
                .defensiveSnapsPlayed(dto.getDefensiveSnapsPlayed())
                .defensiveTeamSnaps(dto.getDefensiveTeamSnaps())
                .specialTeamsSnapsPlayed(dto.getSpecialTeamsSnapsPlayed())
                .specialTeamsTeamSnaps(dto.getSpecialTeamsTeamSnaps())
                .snapCountsConfirmed(dto.getSnapCountsConfirmed())

                // Touchdowns
                .offensiveTouchdowns(dto.getOffensiveTouchdowns())
                .defensiveTouchdowns(dto.getDefensiveTouchdowns())
                .specialTeamsTouchdowns(dto.getSpecialTeamsTouchdowns())
                .touchdowns(dto.getTouchdowns())

                // Fantasy points
                .fantasyPoints(dto.getFantasyPoints())
                .fantasyPointsPpr(dto.getFantasyPointsPPR())
                .fantasyPointsFanDuel(dto.getFantasyPointsFanDuel())
                .fantasyPointsDraftKings(dto.getFantasyPointsDraftKings())
                .fantasyPointsYahoo(dto.getFantasyPointsYahoo())
                .fantasyPointsFantasyDraft(dto.getFantasyPointsFantasyDraft())

                // Salaries
                .fanDuelSalary(dto.getFanDuelSalary())
                .draftKingsSalary(dto.getDraftKingsSalary())
                .fantasyDataSalary(dto.getFantasyDataSalary())
                .yahooSalary(dto.getYahooSalary())
                .fantasyDraftSalary(dto.getFantasyDraftSalary())

                // Positions
                .fanDuelPosition(dto.getFanDuelPosition())
                .draftKingsPosition(dto.getDraftKingsPosition())
                .yahooPosition(dto.getYahooPosition())
                .fantasyDraftPosition(dto.getFantasyDraftPosition())

                // Injuries
                .injuryStatus(dto.getInjuryStatus())
                .injuryBodyPart(dto.getInjuryBodyPart())
                .injuryStartDate(dto.getInjuryStartDate())
                .injuryNotes(dto.getInjuryNotes())
                .injuryPractice(dto.getInjuryPractice())
                .injuryPracticeDescription(dto.getInjuryPracticeDescription())

                // Game environment
                .stadium(dto.getStadium())
                .playingSurface(dto.getPlayingSurface())
                .temperature(dto.getTemperature())
                .humidity(dto.getHumidity())
                .windSpeed(dto.getWindSpeed())

                // Opponent rankings
                .opponentRank(dto.getOpponentRank())
                .opponentPositionRank(dto.getOpponentPositionRank())

                // Timestamps
                .day(dto.getDay())
                .dateTime(dto.getDateTime())
                .gameDate(dto.getGameDate())
                .updated(dto.getUpdated())
                .build();
    }
}
