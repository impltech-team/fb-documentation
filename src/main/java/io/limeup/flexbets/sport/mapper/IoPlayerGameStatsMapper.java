package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.sportsdata.IoPlayerGameStatsDto;
import io.limeup.flexbets.sport.model.IoPlayerGameStats;
import org.springframework.stereotype.Component;

@Component
public class IoPlayerGameStatsMapper {

    public void merge(IoPlayerGameStats entity, IoPlayerGameStatsDto dto) {

        if (dto.getStatId()                       != null) entity.setStatId(dto.getStatId());
        if (dto.getTeamId()                       != null) entity.setTeamId(dto.getTeamId());
        if (dto.getPlayerId()                     != null) entity.setPlayerId(dto.getPlayerId());
        if (dto.getSeasonType()                   != null) entity.setSeasonType(dto.getSeasonType());
        if (dto.getSeason()                       != null) entity.setSeason(dto.getSeason());
        if (dto.getName()                         != null) entity.setName(dto.getName());
        if (dto.getTeam()                         != null) entity.setTeam(dto.getTeam());
        if (dto.getPosition()                     != null) entity.setPosition(dto.getPosition());
        if (dto.getPositionCategory()             != null) entity.setPositionCategory(dto.getPositionCategory());
        if (dto.getStarted()                      != null) entity.setStarted(dto.getStarted());
        if (dto.getBattingOrder()                 != null) entity.setBattingOrder(dto.getBattingOrder());

        if (dto.getFanDuelSalary()                != null) entity.setFanduelSalary(dto.getFanDuelSalary());
        if (dto.getDraftKingsSalary()             != null) entity.setDraftkingsSalary(dto.getDraftKingsSalary());
        if (dto.getFantasyDataSalary()            != null) entity.setFantasydataSalary(dto.getFantasyDataSalary());
        if (dto.getYahooSalary()                  != null) entity.setYahooSalary(dto.getYahooSalary());
        if (dto.getFanDuelPosition()              != null) entity.setFanduelPosition(dto.getFanDuelPosition());
        if (dto.getDraftKingsPosition()           != null) entity.setDraftkingsPosition(dto.getDraftKingsPosition());
        if (dto.getYahooPosition()                != null) entity.setYahooPosition(dto.getYahooPosition());
        if (dto.getFantasyDraftSalary()           != null) entity.setFantasydraftSalary(dto.getFantasyDraftSalary());
        if (dto.getFantasyDraftPosition()         != null) entity.setFantasydraftPosition(dto.getFantasyDraftPosition());

        if (dto.getInjuryStatus()                 != null) entity.setInjuryStatus(dto.getInjuryStatus());
        if (dto.getInjuryBodyPart()               != null) entity.setInjuryBodyPart(dto.getInjuryBodyPart());
        if (dto.getInjuryStartDate()              != null) entity.setInjuryStartDate(dto.getInjuryStartDate());
        if (dto.getInjuryNotes()                  != null) entity.setInjuryNotes(dto.getInjuryNotes());

        if (dto.getGameId()                       != null) entity.setGameId(dto.getGameId());
        if (dto.getGlobalGameId()                 != null) entity.setGlobalGameId(dto.getGlobalGameId());
        if (dto.getDay()                          != null) entity.setDay(dto.getDay());
        if (dto.getDateTime()                     != null) entity.setGameDatetime(dto.getDateTime());
        if (dto.getHomeOrAway()                   != null) entity.setHomeOrAway(dto.getHomeOrAway());
        if (dto.getIsGameOver()                   != null) entity.setIsGameOver(dto.getIsGameOver());
        if (dto.getUpdated()                      != null) entity.setUpdated(dto.getUpdated());

        if (dto.getOpponentId()                   != null) entity.setOpponentId(dto.getOpponentId());
        if (dto.getGlobalOpponentId()             != null) entity.setGlobalOpponentId(dto.getGlobalOpponentId());
        if (dto.getOpponent()                     != null) entity.setOpponent(dto.getOpponent());
        if (dto.getOpponentRank()                 != null) entity.setOpponentRank(dto.getOpponentRank());
        if (dto.getOpponentPositionRank()         != null) entity.setOpponentPositionRank(dto.getOpponentPositionRank());

        if (dto.getGames()                        != null) entity.setGames(dto.getGames());
        if (dto.getFantasyPoints()                != null) entity.setFantasyPoints(dto.getFantasyPoints());

        if (dto.getAtBats()                       != null) entity.setAtBats(dto.getAtBats());
        if (dto.getRuns()                         != null) entity.setRuns(dto.getRuns());
        if (dto.getHits()                         != null) entity.setHits(dto.getHits());
        if (dto.getSingles()                      != null) entity.setSingles(dto.getSingles());
        if (dto.getDoubles()                      != null) entity.setDoubles(dto.getDoubles());
        if (dto.getTriples()                      != null) entity.setTriples(dto.getTriples());
        if (dto.getHomeRuns()                     != null) entity.setHomeRuns(dto.getHomeRuns());
        if (dto.getRunsBattedIn()                 != null) entity.setRunsBattedIn(dto.getRunsBattedIn());
        if (dto.getBattingAverage()               != null) entity.setBattingAverage(dto.getBattingAverage());
        if (dto.getOuts()                         != null) entity.setOuts(dto.getOuts());
        if (dto.getStrikeouts()                   != null) entity.setStrikeouts(dto.getStrikeouts());
        if (dto.getWalks()                        != null) entity.setWalks(dto.getWalks());
        if (dto.getHitByPitch()                   != null) entity.setHitByPitch(dto.getHitByPitch());
        if (dto.getSacrifices()                   != null) entity.setSacrifices(dto.getSacrifices());
        if (dto.getSacrificeFlies()               != null) entity.setSacrificeFlies(dto.getSacrificeFlies());
        if (dto.getGroundIntoDoublePlay()         != null) entity.setGroundIntoDoublePlay(dto.getGroundIntoDoublePlay());
        if (dto.getStolenBases()                  != null) entity.setStolenBases(dto.getStolenBases());
        if (dto.getCaughtStealing()               != null) entity.setCaughtStealing(dto.getCaughtStealing());
        if (dto.getPitchesSeen()                  != null) entity.setPitchesSeen(dto.getPitchesSeen());
        if (dto.getOnBasePercentage()             != null) entity.setOnBasePercentage(dto.getOnBasePercentage());
        if (dto.getSluggingPercentage()           != null) entity.setSluggingPercentage(dto.getSluggingPercentage());
        if (dto.getOnBasePlusSlugging()           != null) entity.setOnBasePlusSlugging(dto.getOnBasePlusSlugging());
        if (dto.getErrors()                       != null) entity.setErrors(dto.getErrors());

        if (dto.getWins()                         != null) entity.setWins(dto.getWins());
        if (dto.getLosses()                       != null) entity.setLosses(dto.getLosses());
        if (dto.getSaves()                        != null) entity.setSaves(dto.getSaves());
        if (dto.getInningsPitchedDecimal()        != null) entity.setInningsPitchedDecimal(dto.getInningsPitchedDecimal());
        if (dto.getTotalOutsPitched()             != null) entity.setTotalOutsPitched(dto.getTotalOutsPitched());
        if (dto.getInningsPitchedFull()           != null) entity.setInningsPitchedFull(dto.getInningsPitchedFull());
        if (dto.getInningsPitchedOuts()           != null) entity.setInningsPitchedOuts(dto.getInningsPitchedOuts());
        if (dto.getEarnedRunAverage()             != null) entity.setEarnedRunAverage(dto.getEarnedRunAverage());
        if (dto.getPitchingHits()                 != null) entity.setPitchingHits(dto.getPitchingHits());
        if (dto.getPitchingRuns()                 != null) entity.setPitchingRuns(dto.getPitchingRuns());
        if (dto.getPitchingEarnedRuns()           != null) entity.setPitchingEarnedRuns(dto.getPitchingEarnedRuns());
        if (dto.getPitchingWalks()                != null) entity.setPitchingWalks(dto.getPitchingWalks());
        if (dto.getPitchingStrikeouts()           != null) entity.setPitchingStrikeouts(dto.getPitchingStrikeouts());
        if (dto.getPitchingHomeRuns()             != null) entity.setPitchingHomeRuns(dto.getPitchingHomeRuns());
        if (dto.getPitchesThrown()                != null) entity.setPitchesThrown(dto.getPitchesThrown());
        if (dto.getPitchesThrownStrikes()         != null) entity.setPitchesThrownStrikes(dto.getPitchesThrownStrikes());
        if (dto.getWalksHitsPerInningsPitched()   != null) entity.setWalksHitsPerInningsPitched(dto.getWalksHitsPerInningsPitched());
        if (dto.getPitchingBattingAverageAgainst()!= null) entity.setPitchingBattingAverageAgainst(dto.getPitchingBattingAverageAgainst());

        if (dto.getFantasyPointsFanDuel()         != null) entity.setFantasyPointsFanduel(dto.getFantasyPointsFanDuel());
        if (dto.getFantasyPointsDraftKings()      != null) entity.setFantasyPointsDraftkings(dto.getFantasyPointsDraftKings());
        if (dto.getFantasyPointsYahoo()           != null) entity.setFantasyPointsYahoo(dto.getFantasyPointsYahoo());
        if (dto.getFantasyPointsFantasyDraft()    != null) entity.setFantasyPointsFantasydraft(dto.getFantasyPointsFantasyDraft());
        if (dto.getFantasyPointsBatting()         != null) entity.setFantasyPointsBatting(dto.getFantasyPointsBatting());
        if (dto.getFantasyPointsPitching()        != null) entity.setFantasyPointsPitching(dto.getFantasyPointsPitching());

        if (dto.getGrandSlams()                   != null) entity.setPitchingGrandSlams(dto.getGrandSlams());
        if (dto.getPlateAppearances()             != null) entity.setPlateAppearances(dto.getPlateAppearances());
        if (dto.getTotalBases()                   != null) entity.setTotalBases(dto.getTotalBases());
        if (dto.getFlyOuts()                      != null) entity.setFlyOuts(dto.getFlyOuts());
        if (dto.getGroundOuts()                   != null) entity.setGroundOuts(dto.getGroundOuts());
        if (dto.getLineOuts()                     != null) entity.setLineOuts(dto.getLineOuts());
        if (dto.getPopOuts()                      != null) entity.setPopOuts(dto.getPopOuts());
        if (dto.getIntentionalWalks()             != null) entity.setIntentionalWalks(dto.getIntentionalWalks());
        if (dto.getReachedOnError()               != null) entity.setReachedOnError(dto.getReachedOnError());
        if (dto.getBallsInPlay()                  != null) entity.setBallsInPlay(dto.getBallsInPlay());
        if (dto.getBattingAverageOnBallsInPlay()  != null) entity.setBattingAverageOnBallsInPlay(dto.getBattingAverageOnBallsInPlay());
        if (dto.getWeightedOnBasePercentage()     != null) entity.setWeightedOnBasePercentage(dto.getWeightedOnBasePercentage());

        if (dto.getPitchingSingles()              != null) entity.setPitchingSingles(dto.getPitchingSingles());
        if (dto.getPitchingDoubles()              != null) entity.setPitchingDoubles(dto.getPitchingDoubles());
        if (dto.getPitchingTriples()              != null) entity.setPitchingTriples(dto.getPitchingTriples());
        if (dto.getPitchingGrandSlams()           != null) entity.setPitchingGrandSlams(dto.getPitchingGrandSlams());
        if (dto.getPitchingHitByPitch()           != null) entity.setPitchingHitByPitch(dto.getPitchingHitByPitch());
        if (dto.getPitchingSacrifices()           != null) entity.setPitchingSacrifices(dto.getPitchingSacrifices());
        if (dto.getPitchingSacrificeFlies()       != null) entity.setPitchingSacrificeFlies(dto.getPitchingSacrificeFlies());
        if (dto.getPitchingGroundIntoDoublePlay() != null) entity.setPitchingGroundIntoDoublePlay(dto.getPitchingGroundIntoDoublePlay());
        if (dto.getPitchingCompleteGames()        != null) entity.setPitchingCompleteGames(dto.getPitchingCompleteGames());
        if (dto.getPitchingShutOuts()             != null) entity.setPitchingShutOuts(dto.getPitchingShutOuts());
        if (dto.getPitchingNoHitters()            != null) entity.setPitchingNoHitters(dto.getPitchingNoHitters());
        if (dto.getPitchingPerfectGames()         != null) entity.setPitchingPerfectGames(dto.getPitchingPerfectGames());
        if (dto.getPitchingPlateAppearances()     != null) entity.setPitchingPlateAppearances(dto.getPitchingPlateAppearances());
        if (dto.getPitchingTotalBases()           != null) entity.setPitchingTotalBases(dto.getPitchingTotalBases());
        if (dto.getPitchingFlyOuts()              != null) entity.setPitchingFlyOuts(dto.getPitchingFlyOuts());
        if (dto.getPitchingGroundOuts()           != null) entity.setPitchingGroundOuts(dto.getPitchingGroundOuts());
        if (dto.getPitchingLineOuts()             != null) entity.setPitchingLineOuts(dto.getPitchingLineOuts());
        if (dto.getPitchingPopOuts()              != null) entity.setPitchingPopOuts(dto.getPitchingPopOuts());
        if (dto.getPitchingIntentionalWalks()     != null) entity.setPitchingIntentionalWalks(dto.getPitchingIntentionalWalks());
        if (dto.getPitchingReachedOnError()       != null) entity.setPitchingReachedOnError(dto.getPitchingReachedOnError());
        if (dto.getPitchingCatchersInterference() != null) entity.setPitchingCatchersInterference(dto.getPitchingCatchersInterference());
        if (dto.getPitchingBallsInPlay()          != null) entity.setPitchingBallsInPlay(dto.getPitchingBallsInPlay());
        if (dto.getPitchingOnBasePercentage()     != null) entity.setPitchingOnBasePercentage(dto.getPitchingOnBasePercentage());
        if (dto.getPitchingSluggingPercentage()   != null) entity.setPitchingSluggingPercentage(dto.getPitchingSluggingPercentage());
        if (dto.getPitchingOnBasePlusSlugging()   != null) entity.setPitchingOnBasePlusSlugging(dto.getPitchingOnBasePlusSlugging());
        if (dto.getPitchingStrikeoutsPerNineInnings() != null) entity.setPitchingStrikeoutsPerNineInnings(dto.getPitchingStrikeoutsPerNineInnings());
        if (dto.getPitchingWalksPerNineInnings()  != null) entity.setPitchingWalksPerNineInnings(dto.getPitchingWalksPerNineInnings());
        if (dto.getPitchingBattingAverageOnBallsInPlay() != null) entity.setPitchingBattingAverageOnBallsInPlay(dto.getPitchingBattingAverageOnBallsInPlay());
        if (dto.getPitchingWeightedOnBasePercentage() != null) entity.setPitchingWeightedOnBasePercentage(dto.getPitchingWeightedOnBasePercentage());

        if (dto.getDoublePlays()                 != null) entity.setDoublePlays(dto.getDoublePlays());
        if (dto.getPitchingDoublePlays()         != null) entity.setPitchingDoublePlays(dto.getPitchingDoublePlays());
        if (dto.getBattingOrderConfirmed()       != null) entity.setBattingOrderConfirmed(dto.getBattingOrderConfirmed());
        if (dto.getIsolatedPower()               != null) entity.setIsolatedPower(dto.getIsolatedPower());
        if (dto.getFieldingIndependentPitching() != null) entity.setFieldingIndependentPitching(dto.getFieldingIndependentPitching());
        if (dto.getPitchingQualityStarts()       != null) entity.setPitchingQualityStarts(dto.getPitchingQualityStarts());
        if (dto.getPitchingInningStarted()       != null) entity.setPitchingInningStarted(dto.getPitchingInningStarted());
        if (dto.getLeftOnBase()                  != null) entity.setLeftOnBase(dto.getLeftOnBase());
        if (dto.getPitchingHolds()               != null) entity.setPitchingHolds(dto.getPitchingHolds());
        if (dto.getPitchingBlownSaves()          != null) entity.setPitchingBlownSaves(dto.getPitchingBlownSaves());
        if (dto.getSubstituteBattingOrder()      != null) entity.setSubstituteBattingOrder(dto.getSubstituteBattingOrder());
        if (dto.getSubstituteBattingOrderSequence()!= null) entity.setSubstituteBattingOrderSequence(dto.getSubstituteBattingOrderSequence());

    }


    public IoPlayerGameStats toEntity(IoPlayerGameStatsDto d) {

        return IoPlayerGameStats.builder()
                .statId(d.getStatId())
                .teamId(d.getTeamId())
                .playerId(d.getPlayerId())
                .seasonType(d.getSeasonType())
                .season(d.getSeason())
                .name(d.getName())
                .team(d.getTeam())
                .position(d.getPosition())
                .positionCategory(d.getPositionCategory())
                .started(d.getStarted())
                .battingOrder(d.getBattingOrder())

                .fanduelSalary(d.getFanDuelSalary())
                .draftkingsSalary(d.getDraftKingsSalary())
                .fantasydataSalary(d.getFantasyDataSalary())
                .yahooSalary(d.getYahooSalary())
                .fanduelPosition(d.getFanDuelPosition())
                .draftkingsPosition(d.getDraftKingsPosition())
                .yahooPosition(d.getYahooPosition())
                .fantasydraftSalary(d.getFantasyDraftSalary())
                .fantasydraftPosition(d.getFantasyDraftPosition())

                .injuryStatus(d.getInjuryStatus())
                .injuryBodyPart(d.getInjuryBodyPart())
                .injuryStartDate(d.getInjuryStartDate())
                .injuryNotes(d.getInjuryNotes())

                .gameId(d.getGameId())
                .globalGameId(d.getGlobalGameId())
                .day(d.getDay())
                .gameDatetime(d.getDateTime())
                .homeOrAway(d.getHomeOrAway())
                .isGameOver(d.getIsGameOver())
                .updated(d.getUpdated())

                .opponentId(d.getOpponentId())
                .globalOpponentId(d.getGlobalOpponentId())
                .opponent(d.getOpponent())
                .opponentRank(d.getOpponentRank())
                .opponentPositionRank(d.getOpponentPositionRank())
                .globalTeamId(d.getGlobalTeamId())

                .games(d.getGames())
                .fantasyPoints(d.getFantasyPoints())

                .atBats(d.getAtBats())
                .runs(d.getRuns())
                .hits(d.getHits())
                .singles(d.getSingles())
                .doubles(d.getDoubles())
                .triples(d.getTriples())
                .homeRuns(d.getHomeRuns())
                .runsBattedIn(d.getRunsBattedIn())
                .battingAverage(d.getBattingAverage())
                .outs(d.getOuts())
                .strikeouts(d.getStrikeouts())
                .walks(d.getWalks())
                .hitByPitch(d.getHitByPitch())
                .sacrifices(d.getSacrifices())
                .sacrificeFlies(d.getSacrificeFlies())
                .groundIntoDoublePlay(d.getGroundIntoDoublePlay())
                .stolenBases(d.getStolenBases())
                .caughtStealing(d.getCaughtStealing())
                .pitchesSeen(d.getPitchesSeen())
                .onBasePercentage(d.getOnBasePercentage())
                .sluggingPercentage(d.getSluggingPercentage())
                .onBasePlusSlugging(d.getOnBasePlusSlugging())
                .errors(d.getErrors())

                .wins(d.getWins())
                .losses(d.getLosses())
                .saves(d.getSaves())
                .inningsPitchedDecimal(d.getInningsPitchedDecimal())
                .totalOutsPitched(d.getTotalOutsPitched())
                .inningsPitchedFull(d.getInningsPitchedFull())
                .inningsPitchedOuts(d.getInningsPitchedOuts())
                .earnedRunAverage(d.getEarnedRunAverage())
                .pitchingHits(d.getPitchingHits())
                .pitchingRuns(d.getPitchingRuns())
                .pitchingEarnedRuns(d.getPitchingEarnedRuns())
                .pitchingWalks(d.getPitchingWalks())
                .pitchingStrikeouts(d.getPitchingStrikeouts())
                .pitchingHomeRuns(d.getPitchingHomeRuns())
                .pitchesThrown(d.getPitchesThrown())
                .pitchesThrownStrikes(d.getPitchesThrownStrikes())
                .walksHitsPerInningsPitched(d.getWalksHitsPerInningsPitched())
                .pitchingBattingAverageAgainst(d.getPitchingBattingAverageAgainst())

                .fantasyPointsFanduel(d.getFantasyPointsFanDuel())
                .fantasyPointsDraftkings(d.getFantasyPointsDraftKings())
                .fantasyPointsYahoo(d.getFantasyPointsYahoo())
                .fantasyPointsFantasydraft(d.getFantasyPointsFantasyDraft())
                .fantasyPointsBatting(d.getFantasyPointsBatting())
                .fantasyPointsPitching(d.getFantasyPointsPitching())

                .grandSlams(d.getGrandSlams())
                .plateAppearances(d.getPlateAppearances())
                .totalBases(d.getTotalBases())
                .flyOuts(d.getFlyOuts())
                .groundOuts(d.getGroundOuts())
                .lineOuts(d.getLineOuts())
                .popOuts(d.getPopOuts())
                .intentionalWalks(d.getIntentionalWalks())
                .reachedOnError(d.getReachedOnError())
                .ballsInPlay(d.getBallsInPlay())
                .battingAverageOnBallsInPlay(d.getBattingAverageOnBallsInPlay())
                .weightedOnBasePercentage(d.getWeightedOnBasePercentage())

                .pitchingSingles(d.getPitchingSingles())
                .pitchingDoubles(d.getPitchingDoubles())
                .pitchingTriples(d.getPitchingTriples())
                .pitchingGrandSlams(d.getPitchingGrandSlams())
                .pitchingHitByPitch(d.getPitchingHitByPitch())
                .pitchingSacrifices(d.getPitchingSacrifices())
                .pitchingSacrificeFlies(d.getPitchingSacrificeFlies())
                .pitchingGroundIntoDoublePlay(d.getPitchingGroundIntoDoublePlay())
                .pitchingCompleteGames(d.getPitchingCompleteGames())
                .pitchingShutOuts(d.getPitchingShutOuts())
                .pitchingNoHitters(d.getPitchingNoHitters())
                .pitchingPerfectGames(d.getPitchingPerfectGames())
                .pitchingPlateAppearances(d.getPitchingPlateAppearances())
                .pitchingTotalBases(d.getPitchingTotalBases())
                .pitchingFlyOuts(d.getPitchingFlyOuts())
                .pitchingGroundOuts(d.getPitchingGroundOuts())
                .pitchingLineOuts(d.getPitchingLineOuts())
                .pitchingPopOuts(d.getPitchingPopOuts())
                .pitchingIntentionalWalks(d.getPitchingIntentionalWalks())
                .pitchingReachedOnError(d.getPitchingReachedOnError())
                .pitchingCatchersInterference(d.getPitchingCatchersInterference())
                .pitchingBallsInPlay(d.getPitchingBallsInPlay())
                .pitchingOnBasePercentage(d.getPitchingOnBasePercentage())
                .pitchingSluggingPercentage(d.getPitchingSluggingPercentage())
                .pitchingOnBasePlusSlugging(d.getPitchingOnBasePlusSlugging())
                .pitchingStrikeoutsPerNineInnings(d.getPitchingStrikeoutsPerNineInnings())
                .pitchingWalksPerNineInnings(d.getPitchingWalksPerNineInnings())
                .pitchingBattingAverageOnBallsInPlay(d.getPitchingBattingAverageOnBallsInPlay())
                .pitchingWeightedOnBasePercentage(d.getPitchingWeightedOnBasePercentage())

                .doublePlays(d.getDoublePlays())
                .pitchingDoublePlays(d.getPitchingDoublePlays())
                .battingOrderConfirmed(d.getBattingOrderConfirmed())
                .isolatedPower(d.getIsolatedPower())
                .fieldingIndependentPitching(d.getFieldingIndependentPitching())
                .pitchingQualityStarts(d.getPitchingQualityStarts())
                .pitchingInningStarted(d.getPitchingInningStarted())
                .leftOnBase(d.getLeftOnBase())
                .pitchingHolds(d.getPitchingHolds())
                .pitchingBlownSaves(d.getPitchingBlownSaves())
                .substituteBattingOrder(d.getSubstituteBattingOrder())
                .substituteBattingOrderSequence(d.getSubstituteBattingOrderSequence())

//               .bettingMarketId(d.getBettingMarketId())
                .build();
    }
}
