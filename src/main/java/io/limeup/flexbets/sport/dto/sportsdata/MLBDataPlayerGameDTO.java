package io.limeup.flexbets.sport.dto.sportsdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MLBDataPlayerGameDTO {

    private Integer statId;

    @JsonProperty("StatID")
    public void setStatId(Integer statId) {
        this.statId = statId;
    }

    @JsonProperty("statId")
    public Integer getStatId() {
        return statId;
    }

    private Integer teamId;

    @JsonProperty("teamId")
    public Integer getTeamId() {
        return teamId;
    }

    @JsonProperty("TeamID")
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    private Long playerId;

    @JsonProperty("playerId")
    public Long getPlayerId() {
        return playerId;
    }

    @JsonProperty("PlayerID")
    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    private Integer seasonType;

    @JsonProperty("seasonType")
    public Integer getSeasonType() {
        return seasonType;
    }

    @JsonProperty("SeasonType")
    public void setSeasonType(Integer seasonType) {
        this.seasonType = seasonType;
    }

    private Integer season;

    @JsonProperty("season")
    public Integer getSeason() {
        return season;
    }

    @JsonProperty("Season")
    public void setSeason(Integer season) {
        this.season = season;
    }

    private String name;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    private String team;

    @JsonProperty("team")
    public String getTeam() {
        return team;
    }

    @JsonProperty("Team")
    public void setTeam(String team) {
        this.team = team;
    }

    private String position;

    @JsonProperty("position")
    public String getPosition() {
        return position;
    }

    @JsonProperty("Position")
    public void setPosition(String position) {
        this.position = position;
    }

    private String positionCategory;

    @JsonProperty("positionCategory")
    public String getPositionCategory() {
        return positionCategory;
    }

    @JsonProperty("PositionCategory")
    public void setPositionCategory(String positionCategory) {
        this.positionCategory = positionCategory;
    }

    private Integer started;

    @JsonProperty("started")
    public Integer getStarted() {
        return started;
    }

    @JsonProperty("Started")
    public void setStarted(Integer started) {
        this.started = started;
    }

    private Integer battingOrder;

    @JsonProperty("battingOrder")
    public Integer getBattingOrder() {
        return battingOrder;
    }

    @JsonProperty("BattingOrder")
    public void setBattingOrder(Integer battingOrder) {
        this.battingOrder = battingOrder;
    }

    private Integer fanDuelSalary;

    @JsonProperty("fanDuelSalary")
    public Integer getFanDuelSalary() {
        return fanDuelSalary;
    }

    @JsonProperty("FanDuelSalary")
    public void setFanDuelSalary(Integer fanDuelSalary) {
        this.fanDuelSalary = fanDuelSalary;
    }

    private Integer draftKingsSalary;

    @JsonProperty("draftKingsSalary")
    public Integer getDraftKingsSalary() {
        return draftKingsSalary;
    }

    @JsonProperty("DraftKingsSalary")
    public void setDraftKingsSalary(Integer draftKingsSalary) {
        this.draftKingsSalary = draftKingsSalary;
    }

    private Integer fantasyDataSalary;

    @JsonProperty("fantasyDataSalary")
    public Integer getFantasyDataSalary() {
        return fantasyDataSalary;
    }

    @JsonProperty("FantasyDataSalary")
    public void setFantasyDataSalary(Integer fantasyDataSalary) {
        this.fantasyDataSalary = fantasyDataSalary;
    }

    private Integer yahooSalary;

    @JsonProperty("yahooSalary")
    public Integer getYahooSalary() {
        return yahooSalary;
    }

    @JsonProperty("YahooSalary")
    public void setYahooSalary(Integer yahooSalary) {
        this.yahooSalary = yahooSalary;
    }

    private String injuryStatus;

    @JsonProperty("injuryStatus")
    public String getInjuryStatus() {
        return injuryStatus;
    }

    @JsonProperty("InjuryStatus")
    public void setInjuryStatus(String injuryStatus) {
        this.injuryStatus = injuryStatus;
    }

    private String injuryBodyPart;

    @JsonProperty("injuryBodyPart")
    public String getInjuryBodyPart() {
        return injuryBodyPart;
    }

    @JsonProperty("InjuryBodyPart")
    public void setInjuryBodyPart(String injuryBodyPart) {
        this.injuryBodyPart = injuryBodyPart;
    }

    private LocalDate injuryStartDate;

    @JsonProperty("injuryStartDate")
    public LocalDate getInjuryStartDate() {
        return injuryStartDate;
    }

    @JsonProperty("InjuryStartDate")
    public void setInjuryStartDate(LocalDate injuryStartDate) {
        this.injuryStartDate = injuryStartDate;
    }

    private String injuryNotes;

    @JsonProperty("injuryNotes")
    public String getInjuryNotes() {
        return injuryNotes;
    }

    @JsonProperty("InjuryNotes")
    public void setInjuryNotes(String injuryNotes) {
        this.injuryNotes = injuryNotes;
    }

    private String fanDuelPosition;

    @JsonProperty("fanDuelPosition")
    public String getFanDuelPosition() {
        return fanDuelPosition;
    }

    @JsonProperty("FanDuelPosition")
    public void setFanDuelPosition(String fanDuelPosition) {
        this.fanDuelPosition = fanDuelPosition;
    }

    private String draftKingsPosition;

    @JsonProperty("draftKingsPosition")
    public String getDraftKingsPosition() {
        return draftKingsPosition;
    }

    @JsonProperty("DraftKingsPosition")
    public void setDraftKingsPosition(String draftKingsPosition) {
        this.draftKingsPosition = draftKingsPosition;
    }

    private String yahooPosition;

    @JsonProperty("yahooPosition")
    public String getYahooPosition() {
        return yahooPosition;
    }

    @JsonProperty("YahooPosition")
    public void setYahooPosition(String yahooPosition) {
        this.yahooPosition = yahooPosition;
    }

    private Integer opponentRank;

    @JsonProperty("opponentRank")
    public Integer getOpponentRank() {
        return opponentRank;
    }

    @JsonProperty("OpponentRank")
    public void setOpponentRank(Integer opponentRank) {
        this.opponentRank = opponentRank;
    }

    private Integer opponentPositionRank;

    @JsonProperty("opponentPositionRank")
    public Integer getOpponentPositionRank() {
        return opponentPositionRank;
    }

    @JsonProperty("OpponentPositionRank")
    public void setOpponentPositionRank(Integer opponentPositionRank) {
        this.opponentPositionRank = opponentPositionRank;
    }

    private Integer globalTeamId;

    @JsonProperty("globalTeamId")
    public Integer getGlobalTeamId() {
        return globalTeamId;
    }

    @JsonProperty("GlobalTeamID")
    public void setGlobalTeamId(Integer globalTeamId) {
        this.globalTeamId = globalTeamId;
    }

    private Integer fantasyDraftSalary;

    @JsonProperty("fantasyDraftSalary")
    public Integer getFantasyDraftSalary() {
        return fantasyDraftSalary;
    }

    @JsonProperty("FantasyDraftSalary")
    public void setFantasyDraftSalary(Integer fantasyDraftSalary) {
        this.fantasyDraftSalary = fantasyDraftSalary;
    }

    private String fantasyDraftPosition;

    @JsonProperty("fantasyDraftPosition")
    public String getFantasyDraftPosition() {
        return fantasyDraftPosition;
    }

    @JsonProperty("FantasyDraftPosition")
    public void setFantasyDraftPosition(String fantasyDraftPosition) {
        this.fantasyDraftPosition = fantasyDraftPosition;
    }

    private Integer gameId;

    @JsonProperty("gameId")
    public Integer getGameId() {
        return gameId;
    }

    @JsonProperty("GameID")
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    private Integer opponentId;

    @JsonProperty("opponentId")
    public Integer getOpponentId() {
        return opponentId;
    }

    @JsonProperty("OpponentID")
    public void setOpponentId(Integer opponentId) {
        this.opponentId = opponentId;
    }

    private String opponent;

    @JsonProperty("opponent")
    public String getOpponent() {
        return opponent;
    }

    @JsonProperty("Opponent")
    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    private LocalDate day;

    @JsonProperty("day")
    public LocalDate getDay() {
        return day;
    }

    @JsonProperty("Day")
    public void setDay(LocalDate day) {
        this.day = day;
    }

    private LocalDateTime dateTime;

    @JsonProperty("dateTime")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @JsonProperty("DateTime")
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    private String homeOrAway;

    @JsonProperty("homeOrAway")
    public String getHomeOrAway() {
        return homeOrAway;
    }

    @JsonProperty("HomeOrAway")
    public void setHomeOrAway(String homeOrAway) {
        this.homeOrAway = homeOrAway;
    }

    private Boolean isGameOver;

    @JsonProperty("isGameOver")
    public Boolean getGameOver() {
        return isGameOver;
    }

    @JsonProperty("IsGameOver")
    public void setGameOver(Boolean gameOver) {
        isGameOver = gameOver;
    }

    private Integer globalGameId;

    @JsonProperty("globalGameId")
    public Integer getGlobalGameId() {
        return globalGameId;
    }

    @JsonProperty("GlobalGameID")
    public void setGlobalGameId(Integer globalGameId) {
        this.globalGameId = globalGameId;
    }

    private Integer globalOpponentId;

    @JsonProperty("globalOpponentId")
    public Integer getGlobalOpponentId() {
        return globalOpponentId;
    }

    @JsonProperty("GlobalOpponentID")
    public void setGlobalOpponentId(Integer globalOpponentId) {
        this.globalOpponentId = globalOpponentId;
    }

    private LocalDateTime updated;

    @JsonProperty("updated")
    public LocalDateTime getUpdated() {
        return updated;
    }

    @JsonProperty("Updated")
    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    private Integer games;

    @JsonProperty("games")
    public Integer getGames() {
        return games;
    }

    @JsonProperty("Games")
    public void setGames(Integer games) {
        this.games = games;
    }

    private BigDecimal fantasyPoints;

    @JsonProperty("fantasyPoints")
    public BigDecimal getFantasyPoints() {
        return fantasyPoints;
    }

    @JsonProperty("FantasyPoints")
    public void setFantasyPoints(BigDecimal fantasyPoints) {
        this.fantasyPoints = fantasyPoints;
    }

    private BigDecimal atBats;

    @JsonProperty("atBats")
    public BigDecimal getAtBats() {
        return atBats;
    }

    @JsonProperty("AtBats")
    public void setAtBats(BigDecimal atBats) {
        this.atBats = atBats;
    }

    private BigDecimal runs;

    @JsonProperty("runs")
    public BigDecimal getRuns() {
        return runs;
    }

    @JsonProperty("Runs")
    public void setRuns(BigDecimal runs) {
        this.runs = runs;
    }

    private BigDecimal hits;

    @JsonProperty("hits")
    public BigDecimal getHits() {
        return hits;
    }

    @JsonProperty("Hits")
    public void setHits(BigDecimal hits) {
        this.hits = hits;
    }

    private BigDecimal singles;

    @JsonProperty("singles")
    public BigDecimal getSingles() {
        return singles;
    }

    @JsonProperty("Singles")
    public void setSingles(BigDecimal singles) {
        this.singles = singles;
    }

    private BigDecimal doubles;

    @JsonProperty("doubles")
    public BigDecimal getDoubles() {
        return doubles;
    }

    @JsonProperty("Doubles")
    public void setDoubles(BigDecimal doubles) {
        this.doubles = doubles;
    }

    private BigDecimal triples;

    @JsonProperty("triples")
    public BigDecimal getTriples() {
        return triples;
    }

    @JsonProperty("Triples")
    public void setTriples(BigDecimal triples) {
        this.triples = triples;
    }

    private BigDecimal homeRuns;

    @JsonProperty("homeRuns")
    public BigDecimal getHomeRuns() {
        return homeRuns;
    }

    @JsonProperty("HomeRuns")
    public void setHomeRuns(BigDecimal homeRuns) {
        this.homeRuns = homeRuns;
    }

    private BigDecimal runsBattedIn;

    @JsonProperty("runsBattedIn")
    public BigDecimal getRunsBattedIn() {
        return runsBattedIn;
    }

    @JsonProperty("RunsBattedIn")
    public void setRunsBattedIn(BigDecimal runsBattedIn) {
        this.runsBattedIn = runsBattedIn;
    }

    private BigDecimal battingAverage;

    @JsonProperty("battingAverage")
    public BigDecimal getBattingAverage() {
        return battingAverage;
    }

    @JsonProperty("BattingAverage")
    public void setBattingAverage(BigDecimal battingAverage) {
        this.battingAverage = battingAverage;
    }

    private BigDecimal outs;

    @JsonProperty("outs")
    public BigDecimal getOuts() {
        return outs;
    }

    @JsonProperty("Outs")
    public void setOuts(BigDecimal outs) {
        this.outs = outs;
    }

    private BigDecimal strikeouts;

    @JsonProperty("strikeouts")
    public BigDecimal getStrikeouts() {
        return strikeouts;
    }

    @JsonProperty("Strikeouts")
    public void setStrikeouts(BigDecimal strikeouts) {
        this.strikeouts = strikeouts;
    }

    private BigDecimal walks;

    @JsonProperty("walks")
    public BigDecimal getWalks() {
        return walks;
    }

    @JsonProperty("Walks")
    public void setWalks(BigDecimal walks) {
        this.walks = walks;
    }

    private BigDecimal hitByPitch;

    @JsonProperty("hitByPitch")
    public BigDecimal getHitByPitch() {
        return hitByPitch;
    }

    @JsonProperty("HitByPitch")
    public void setHitByPitch(BigDecimal hitByPitch) {
        this.hitByPitch = hitByPitch;
    }

    private BigDecimal sacrifices;

    @JsonProperty("sacrifices")
    public BigDecimal getSacrifices() {
        return sacrifices;
    }

    @JsonProperty("Sacrifices")
    public void setSacrifices(BigDecimal sacrifices) {
        this.sacrifices = sacrifices;
    }

    private BigDecimal sacrificeFlies;

    @JsonProperty("sacrificeFlies")
    public BigDecimal getSacrificeFlies() {
        return sacrificeFlies;
    }

    @JsonProperty("SacrificeFlies")
    public void setSacrificeFlies(BigDecimal sacrificeFlies) {
        this.sacrificeFlies = sacrificeFlies;
    }

    private BigDecimal groundIntoDoublePlay;

    @JsonProperty("groundIntoDoublePlay")
    public BigDecimal getGroundIntoDoublePlay() {
        return groundIntoDoublePlay;
    }

    @JsonProperty("GroundIntoDoublePlay")
    public void setGroundIntoDoublePlay(BigDecimal groundIntoDoublePlay) {
        this.groundIntoDoublePlay = groundIntoDoublePlay;
    }

    private BigDecimal stolenBases;

    @JsonProperty("stolenBases")
    public BigDecimal getStolenBases() {
        return stolenBases;
    }

    @JsonProperty("StolenBases")
    public void setStolenBases(BigDecimal stolenBases) {
        this.stolenBases = stolenBases;
    }

    private BigDecimal caughtStealing;

    @JsonProperty("caughtStealing")
    public BigDecimal getCaughtStealing() {
        return caughtStealing;
    }

    @JsonProperty("CaughtStealing")
    public void setCaughtStealing(BigDecimal caughtStealing) {
        this.caughtStealing = caughtStealing;
    }

    private BigDecimal pitchesSeen;

    @JsonProperty("pitchesSeen")
    public BigDecimal getPitchesSeen() {
        return pitchesSeen;
    }

    @JsonProperty("PitchesSeen")
    public void setPitchesSeen(BigDecimal pitchesSeen) {
        this.pitchesSeen = pitchesSeen;
    }

    private BigDecimal onBasePercentage;

    @JsonProperty("onBasePercentage")
    public BigDecimal getOnBasePercentage() {
        return onBasePercentage;
    }

    @JsonProperty("OnBasePercentage")
    public void setOnBasePercentage(BigDecimal onBasePercentage) {
        this.onBasePercentage = onBasePercentage;
    }

    private BigDecimal sluggingPercentage;

    @JsonProperty("sluggingPercentage")
    public BigDecimal getSluggingPercentage() {
        return sluggingPercentage;
    }

    @JsonProperty("SluggingPercentage")
    public void setSluggingPercentage(BigDecimal sluggingPercentage) {
        this.sluggingPercentage = sluggingPercentage;
    }

    private BigDecimal onBasePlusSlugging;

    @JsonProperty("onBasePlusSlugging")
    public BigDecimal getOnBasePlusSlugging() {
        return onBasePlusSlugging;
    }

    @JsonProperty("OnBasePlusSlugging")
    public void setOnBasePlusSlugging(BigDecimal onBasePlusSlugging) {
        this.onBasePlusSlugging = onBasePlusSlugging;
    }

    private BigDecimal errors;

    @JsonProperty("errors")
    public BigDecimal getErrors() {
        return errors;
    }

    @JsonProperty("Errors")
    public void setErrors(BigDecimal errors) {
        this.errors = errors;
    }

    private BigDecimal wins;

    @JsonProperty("wins")
    public BigDecimal getWins() {
        return wins;
    }

    @JsonProperty("Wins")
    public void setWins(BigDecimal wins) {
        this.wins = wins;
    }

    private BigDecimal losses;

    @JsonProperty("losses")
    public BigDecimal getLosses() {
        return losses;
    }

    @JsonProperty("Losses")
    public void setLosses(BigDecimal losses) {
        this.losses = losses;
    }

    private BigDecimal saves;

    @JsonProperty("saves")
    public BigDecimal getSaves() {
        return saves;
    }

    @JsonProperty("Saves")
    public void setSaves(BigDecimal saves) {
        this.saves = saves;
    }

    private BigDecimal inningsPitchedDecimal;

    @JsonProperty("inningsPitchedDecimal")
    public BigDecimal getInningsPitchedDecimal() {
        return inningsPitchedDecimal;
    }

    @JsonProperty("InningsPitchedDecimal")
    public void setInningsPitchedDecimal(BigDecimal inningsPitchedDecimal) {
        this.inningsPitchedDecimal = inningsPitchedDecimal;
    }

    private BigDecimal totalOutsPitched;

    @JsonProperty("totalOutsPitched")
    public BigDecimal getTotalOutsPitched() {
        return totalOutsPitched;
    }

    @JsonProperty("TotalOutsPitched")
    public void setTotalOutsPitched(BigDecimal totalOutsPitched) {
        this.totalOutsPitched = totalOutsPitched;
    }

    private BigDecimal inningsPitchedFull;

    @JsonProperty("inningsPitchedFull")
    public BigDecimal getInningsPitchedFull() {
        return inningsPitchedFull;
    }

    @JsonProperty("InningsPitchedFull")
    public void setInningsPitchedFull(BigDecimal inningsPitchedFull) {
        this.inningsPitchedFull = inningsPitchedFull;
    }

    private BigDecimal inningsPitchedOuts;

    @JsonProperty("inningsPitchedOuts")
    public BigDecimal getInningsPitchedOuts() {
        return inningsPitchedOuts;
    }

    @JsonProperty("InningsPitchedOuts")
    public void setInningsPitchedOuts(BigDecimal inningsPitchedOuts) {
        this.inningsPitchedOuts = inningsPitchedOuts;
    }

    private BigDecimal earnedRunAverage;

    @JsonProperty("earnedRunAverage")
    public BigDecimal getEarnedRunAverage() {
        return earnedRunAverage;
    }

    @JsonProperty("EarnedRunAverage")
    public void setEarnedRunAverage(BigDecimal earnedRunAverage) {
        this.earnedRunAverage = earnedRunAverage;
    }

    private BigDecimal pitchingHits;

    @JsonProperty("pitchingHits")
    public BigDecimal getPitchingHits() {
        return pitchingHits;
    }

    @JsonProperty("PitchingHits")
    public void setPitchingHits(BigDecimal pitchingHits) {
        this.pitchingHits = pitchingHits;
    }

    private BigDecimal pitchingRuns;

    @JsonProperty("pitchingRuns")
    public BigDecimal getPitchingRuns() {
        return pitchingRuns;
    }

    @JsonProperty("PitchingRuns")
    public void setPitchingRuns(BigDecimal pitchingRuns) {
        this.pitchingRuns = pitchingRuns;
    }

    private BigDecimal pitchingEarnedRuns;

    @JsonProperty("pitchingEarnedRuns")
    public BigDecimal getPitchingEarnedRuns() {
        return pitchingEarnedRuns;
    }

    @JsonProperty("PitchingEarnedRuns")
    public void setPitchingEarnedRuns(BigDecimal pitchingEarnedRuns) {
        this.pitchingEarnedRuns = pitchingEarnedRuns;
    }

    private BigDecimal pitchingWalks;

    @JsonProperty("pitchingWalks")
    public BigDecimal getPitchingWalks() {
        return pitchingWalks;
    }

    @JsonProperty("PitchingWalks")
    public void setPitchingWalks(BigDecimal pitchingWalks) {
        this.pitchingWalks = pitchingWalks;
    }

    private BigDecimal pitchingStrikeouts;

    @JsonProperty("pitchingStrikeouts")
    public BigDecimal getPitchingStrikeouts() {
        return pitchingStrikeouts;
    }

    @JsonProperty("PitchingStrikeouts")
    public void setPitchingStrikeouts(BigDecimal pitchingStrikeouts) {
        this.pitchingStrikeouts = pitchingStrikeouts;
    }

    private BigDecimal pitchingHomeRuns;

    @JsonProperty("pitchingHomeRuns")
    public BigDecimal getPitchingHomeRuns() {
        return pitchingHomeRuns;
    }

    @JsonProperty("PitchingHomeRuns")
    public void setPitchingHomeRuns(BigDecimal pitchingHomeRuns) {
        this.pitchingHomeRuns = pitchingHomeRuns;
    }

    private BigDecimal pitchesThrown;

    @JsonProperty("pitchesThrown")
    public BigDecimal getPitchesThrown() {
        return pitchesThrown;
    }

    @JsonProperty("PitchesThrown")
    public void setPitchesThrown(BigDecimal pitchesThrown) {
        this.pitchesThrown = pitchesThrown;
    }

    private BigDecimal pitchesThrownStrikes;

    @JsonProperty("pitchesThrownStrikes")
    public BigDecimal getPitchesThrownStrikes() {
        return pitchesThrownStrikes;
    }

    @JsonProperty("PitchesThrownStrikes")
    public void setPitchesThrownStrikes(BigDecimal pitchesThrownStrikes) {
        this.pitchesThrownStrikes = pitchesThrownStrikes;
    }

    private BigDecimal walksHitsPerInningsPitched;

    @JsonProperty("walksHitsPerInningsPitched")
    public BigDecimal getWalksHitsPerInningsPitched() {
        return walksHitsPerInningsPitched;
    }

    @JsonProperty("WalksHitsPerInningsPitched")
    public void setWalksHitsPerInningsPitched(BigDecimal walksHitsPerInningsPitched) {
        this.walksHitsPerInningsPitched = walksHitsPerInningsPitched;
    }

    private BigDecimal pitchingBattingAverageAgainst;

    @JsonProperty("pitchingBattingAverageAgainst")
    public BigDecimal getPitchingBattingAverageAgainst() {
        return pitchingBattingAverageAgainst;
    }

    @JsonProperty("PitchingBattingAverageAgainst")
    public void setPitchingBattingAverageAgainst(BigDecimal pitchingBattingAverageAgainst) {
        this.pitchingBattingAverageAgainst = pitchingBattingAverageAgainst;
    }

    private BigDecimal grandSlams;

    @JsonProperty("grandSlams")
    public BigDecimal getGrandSlams() {
        return grandSlams;
    }

    @JsonProperty("GrandSlams")
    public void setGrandSlams(BigDecimal grandSlams) {
        this.grandSlams = grandSlams;
    }

    private BigDecimal fantasyPointsFanDuel;

    @JsonProperty("fantasyPointsFanDuel")
    public BigDecimal getFantasyPointsFanDuel() {
        return fantasyPointsFanDuel;
    }

    @JsonProperty("FantasyPointsFanDuel")
    public void setFantasyPointsFanDuel(BigDecimal fantasyPointsFanDuel) {
        this.fantasyPointsFanDuel = fantasyPointsFanDuel;
    }

    private BigDecimal fantasyPointsDraftKings;

    @JsonProperty("fantasyPointsDraftKings")
    public BigDecimal getFantasyPointsDraftKings() {
        return fantasyPointsDraftKings;
    }

    @JsonProperty("FantasyPointsDraftKings")
    public void setFantasyPointsDraftKings(BigDecimal fantasyPointsDraftKings) {
        this.fantasyPointsDraftKings = fantasyPointsDraftKings;
    }

    private BigDecimal fantasyPointsYahoo;

    @JsonProperty("fantasyPointsYahoo")
    public BigDecimal getFantasyPointsYahoo() {
        return fantasyPointsYahoo;
    }

    @JsonProperty("FantasyPointsYahoo")
    public void setFantasyPointsYahoo(BigDecimal fantasyPointsYahoo) {
        this.fantasyPointsYahoo = fantasyPointsYahoo;
    }

    private BigDecimal plateAppearances;

    @JsonProperty("plateAppearances")
    public BigDecimal getPlateAppearances() {
        return plateAppearances;
    }

    @JsonProperty("PlateAppearances")
    public void setPlateAppearances(BigDecimal plateAppearances) {
        this.plateAppearances = plateAppearances;
    }

    private BigDecimal totalBases;

    @JsonProperty("totalBases")
    public BigDecimal getTotalBases() {
        return totalBases;
    }

    @JsonProperty("TotalBases")
    public void setTotalBases(BigDecimal totalBases) {
        this.totalBases = totalBases;
    }

    private BigDecimal flyOuts;

    @JsonProperty("flyOuts")
    public BigDecimal getFlyOuts() {
        return flyOuts;
    }

    @JsonProperty("FlyOuts")
    public void setFlyOuts(BigDecimal flyOuts) {
        this.flyOuts = flyOuts;
    }

    private BigDecimal groundOuts;

    @JsonProperty("groundOuts")
    public BigDecimal getGroundOuts() {
        return groundOuts;
    }

    @JsonProperty("GroundOuts")
    public void setGroundOuts(BigDecimal groundOuts) {
        this.groundOuts = groundOuts;
    }

    private BigDecimal lineOuts;

    @JsonProperty("lineOuts")
    public BigDecimal getLineOuts() {
        return lineOuts;
    }

    @JsonProperty("LineOuts")
    public void setLineOuts(BigDecimal lineOuts) {
        this.lineOuts = lineOuts;
    }

    private BigDecimal popOuts;

    @JsonProperty("popOuts")
    public BigDecimal getPopOuts() {
        return popOuts;
    }

    @JsonProperty("PopOuts")
    public void setPopOuts(BigDecimal popOuts) {
        this.popOuts = popOuts;
    }

    private BigDecimal intentionalWalks;

    @JsonProperty("intentionalWalks")
    public BigDecimal getIntentionalWalks() {
        return intentionalWalks;
    }

    @JsonProperty("IntentionalWalks")
    public void setIntentionalWalks(BigDecimal intentionalWalks) {
        this.intentionalWalks = intentionalWalks;
    }

    private BigDecimal reachedOnError;

    @JsonProperty("reachedOnError")
    public BigDecimal getReachedOnError() {
        return reachedOnError;
    }

    @JsonProperty("ReachedOnError")
    public void setReachedOnError(BigDecimal reachedOnError) {
        this.reachedOnError = reachedOnError;
    }

    private BigDecimal ballsInPlay;

    @JsonProperty("ballsInPlay")
    public BigDecimal getBallsInPlay() {
        return ballsInPlay;
    }

    @JsonProperty("BallsInPlay")
    public void setBallsInPlay(BigDecimal ballsInPlay) {
        this.ballsInPlay = ballsInPlay;
    }

    private BigDecimal battingAverageOnBallsInPlay;

    @JsonProperty("battingAverageOnBallsInPlay")
    public BigDecimal getBattingAverageOnBallsInPlay() {
        return battingAverageOnBallsInPlay;
    }

    @JsonProperty("BattingAverageOnBallsInPlay")
    public void setBattingAverageOnBallsInPlay(BigDecimal battingAverageOnBallsInPlay) {
        this.battingAverageOnBallsInPlay = battingAverageOnBallsInPlay;
    }

    private BigDecimal weightedOnBasePercentage;

    @JsonProperty("weightedOnBasePercentage")
    public BigDecimal getWeightedOnBasePercentage() {
        return weightedOnBasePercentage;
    }

    @JsonProperty("WeightedOnBasePercentage")
    public void setWeightedOnBasePercentage(BigDecimal weightedOnBasePercentage) {
        this.weightedOnBasePercentage = weightedOnBasePercentage;
    }

    private BigDecimal pitchingSingles;

    @JsonProperty("pitchingSingles")
    public BigDecimal getPitchingSingles() {
        return pitchingSingles;
    }

    @JsonProperty("PitchingSingles")
    public void setPitchingSingles(BigDecimal pitchingSingles) {
        this.pitchingSingles = pitchingSingles;
    }

    private BigDecimal pitchingDoubles;

    @JsonProperty("pitchingDoubles")
    public BigDecimal getPitchingDoubles() {
        return pitchingDoubles;
    }

    @JsonProperty("PitchingDoubles")
    public void setPitchingDoubles(BigDecimal pitchingDoubles) {
        this.pitchingDoubles = pitchingDoubles;
    }

    private BigDecimal pitchingTriples;

    @JsonProperty("pitchingTriples")
    public BigDecimal getPitchingTriples() {
        return pitchingTriples;
    }

    @JsonProperty("PitchingTriples")
    public void setPitchingTriples(BigDecimal pitchingTriples) {
        this.pitchingTriples = pitchingTriples;
    }

    private BigDecimal pitchingGrandSlams;

    @JsonProperty("pitchingGrandSlams")
    public BigDecimal getPitchingGrandSlams() {
        return pitchingGrandSlams;
    }

    @JsonProperty("PitchingGrandSlams")
    public void setPitchingGrandSlams(BigDecimal pitchingGrandSlams) {
        this.pitchingGrandSlams = pitchingGrandSlams;
    }

    private BigDecimal pitchingHitByPitch;

    @JsonProperty("pitchingHitByPitch")
    public BigDecimal getPitchingHitByPitch() {
        return pitchingHitByPitch;
    }

    @JsonProperty("PitchingHitByPitch")
    public void setPitchingHitByPitch(BigDecimal pitchingHitByPitch) {
        this.pitchingHitByPitch = pitchingHitByPitch;
    }

    private BigDecimal pitchingSacrifices;

    @JsonProperty("pitchingSacrifices")
    public BigDecimal getPitchingSacrifices() {
        return pitchingSacrifices;
    }

    @JsonProperty("PitchingSacrifices")
    public void setPitchingSacrifices(BigDecimal pitchingSacrifices) {
        this.pitchingSacrifices = pitchingSacrifices;
    }

    private BigDecimal pitchingSacrificeFlies;

    @JsonProperty("pitchingSacrificeFlies")
    public BigDecimal getPitchingSacrificeFlies() {
        return pitchingSacrificeFlies;
    }

    @JsonProperty("PitchingSacrificeFlies")
    public void setPitchingSacrificeFlies(BigDecimal pitchingSacrificeFlies) {
        this.pitchingSacrificeFlies = pitchingSacrificeFlies;
    }

    private BigDecimal pitchingGroundIntoDoublePlay;

    @JsonProperty("pitchingGroundIntoDoublePlay")
    public BigDecimal getPitchingGroundIntoDoublePlay() {
        return pitchingGroundIntoDoublePlay;
    }

    @JsonProperty("PitchingGroundIntoDoublePlay")
    public void setPitchingGroundIntoDoublePlay(BigDecimal pitchingGroundIntoDoublePlay) {
        this.pitchingGroundIntoDoublePlay = pitchingGroundIntoDoublePlay;
    }

    private BigDecimal pitchingCompleteGames;

    @JsonProperty("pitchingCompleteGames")
    public BigDecimal getPitchingCompleteGames() {
        return pitchingCompleteGames;
    }

    @JsonProperty("PitchingCompleteGames")
    public void setPitchingCompleteGames(BigDecimal pitchingCompleteGames) {
        this.pitchingCompleteGames = pitchingCompleteGames;
    }

    private BigDecimal pitchingShutOuts;

    @JsonProperty("pitchingShutOuts")
    public BigDecimal getPitchingShutOuts() {
        return pitchingShutOuts;
    }

    @JsonProperty("PitchingShutOuts")
    public void setPitchingShutOuts(BigDecimal pitchingShutOuts) {
        this.pitchingShutOuts = pitchingShutOuts;
    }

    private BigDecimal pitchingNoHitters;

    @JsonProperty("pitchingNoHitters")
    public BigDecimal getPitchingNoHitters() {
        return pitchingNoHitters;
    }

    @JsonProperty("PitchingNoHitters")
    public void setPitchingNoHitters(BigDecimal pitchingNoHitters) {
        this.pitchingNoHitters = pitchingNoHitters;
    }

    private BigDecimal pitchingPerfectGames;

    @JsonProperty("pitchingPerfectGames")
    public BigDecimal getPitchingPerfectGames() {
        return pitchingPerfectGames;
    }

    @JsonProperty("PitchingPerfectGames")
    public void setPitchingPerfectGames(BigDecimal pitchingPerfectGames) {
        this.pitchingPerfectGames = pitchingPerfectGames;
    }

    private BigDecimal pitchingPlateAppearances;

    @JsonProperty("pitchingPlateAppearances")
    public BigDecimal getPitchingPlateAppearances() {
        return pitchingPlateAppearances;
    }

    @JsonProperty("PitchingPlateAppearances")
    public void setPitchingPlateAppearances(BigDecimal pitchingPlateAppearances) {
        this.pitchingPlateAppearances = pitchingPlateAppearances;
    }

    private BigDecimal pitchingTotalBases;

    @JsonProperty("pitchingTotalBases")
    public BigDecimal getPitchingTotalBases() {
        return pitchingTotalBases;
    }

    @JsonProperty("PitchingTotalBases")
    public void setPitchingTotalBases(BigDecimal pitchingTotalBases) {
        this.pitchingTotalBases = pitchingTotalBases;
    }

    private BigDecimal pitchingFlyOuts;

    @JsonProperty("pitchingFlyOuts")
    public BigDecimal getPitchingFlyOuts() {
        return pitchingFlyOuts;
    }

    @JsonProperty("PitchingFlyOuts")
    public void setPitchingFlyOuts(BigDecimal pitchingFlyOuts) {
        this.pitchingFlyOuts = pitchingFlyOuts;
    }

    private BigDecimal pitchingGroundOuts;

    @JsonProperty("pitchingGroundOuts")
    public BigDecimal getPitchingGroundOuts() {
        return pitchingGroundOuts;
    }

    @JsonProperty("PitchingGroundOuts")
    public void setPitchingGroundOuts(BigDecimal pitchingGroundOuts) {
        this.pitchingGroundOuts = pitchingGroundOuts;
    }

    private BigDecimal pitchingLineOuts;

    @JsonProperty("pitchingLineOuts")
    public BigDecimal getPitchingLineOuts() {
        return pitchingLineOuts;
    }

    @JsonProperty("PitchingLineOuts")
    public void setPitchingLineOuts(BigDecimal pitchingLineOuts) {
        this.pitchingLineOuts = pitchingLineOuts;
    }

    private BigDecimal pitchingPopOuts;

    @JsonProperty("pitchingPopOuts")
    public BigDecimal getPitchingPopOuts() {
        return pitchingPopOuts;
    }

    @JsonProperty("PitchingPopOuts")
    public void setPitchingPopOuts(BigDecimal pitchingPopOuts) {
        this.pitchingPopOuts = pitchingPopOuts;
    }

    private BigDecimal pitchingIntentionalWalks;

    @JsonProperty("pitchingIntentionalWalks")
    public BigDecimal getPitchingIntentionalWalks() {
        return pitchingIntentionalWalks;
    }

    @JsonProperty("PitchingIntentionalWalks")
    public void setPitchingIntentionalWalks(BigDecimal pitchingIntentionalWalks) {
        this.pitchingIntentionalWalks = pitchingIntentionalWalks;
    }

    private BigDecimal pitchingReachedOnError;

    @JsonProperty("pitchingReachedOnError")
    public BigDecimal getPitchingReachedOnError() {
        return pitchingReachedOnError;
    }

    @JsonProperty("PitchingReachedOnError")
    public void setPitchingReachedOnError(BigDecimal pitchingReachedOnError) {
        this.pitchingReachedOnError = pitchingReachedOnError;
    }

    private BigDecimal pitchingCatchersInterference;

    @JsonProperty("pitchingCatchersInterference")
    public BigDecimal getPitchingCatchersInterference() {
        return pitchingCatchersInterference;
    }

    @JsonProperty("PitchingCatchersInterference")
    public void setPitchingCatchersInterference(BigDecimal pitchingCatchersInterference) {
        this.pitchingCatchersInterference = pitchingCatchersInterference;
    }

    private BigDecimal pitchingBallsInPlay;

    @JsonProperty("pitchingBallsInPlay")
    public BigDecimal getPitchingBallsInPlay() {
        return pitchingBallsInPlay;
    }

    @JsonProperty("PitchingBallsInPlay")
    public void setPitchingBallsInPlay(BigDecimal pitchingBallsInPlay) {
        this.pitchingBallsInPlay = pitchingBallsInPlay;
    }

    private BigDecimal pitchingOnBasePercentage;

    @JsonProperty("pitchingOnBasePercentage")
    public BigDecimal getPitchingOnBasePercentage() {
        return pitchingOnBasePercentage;
    }

    @JsonProperty("PitchingOnBasePercentage")
    public void setPitchingOnBasePercentage(BigDecimal pitchingOnBasePercentage) {
        this.pitchingOnBasePercentage = pitchingOnBasePercentage;
    }

    private BigDecimal pitchingSluggingPercentage;

    @JsonProperty("pitchingOnSluggingPercentage")
    public BigDecimal getPitchingSluggingPercentage() {
        return pitchingSluggingPercentage;
    }

    @JsonProperty("PitchingOnSluggingPercentage")
    public void setPitchingSluggingPercentage(BigDecimal pitchingSluggingPercentage) {
        this.pitchingSluggingPercentage = pitchingSluggingPercentage;
    }

    private BigDecimal pitchingOnBasePlusSlugging;

    @JsonProperty("pitchingOnBasePlusSlugging")
    public BigDecimal getPitchingOnBasePlusSlugging() {
        return pitchingOnBasePlusSlugging;
    }

    @JsonProperty("PitchingOnBasePlusSlugging")
    public void setPitchingOnBasePlusSlugging(BigDecimal pitchingOnBasePlusSlugging) {
        this.pitchingOnBasePlusSlugging = pitchingOnBasePlusSlugging;
    }

    private BigDecimal pitchingStrikeoutsPerNineInnings;

    @JsonProperty("pitchingStrikeoutsPerNineInnings")
    public BigDecimal getPitchingStrikeoutsPerNineInnings() {
        return pitchingStrikeoutsPerNineInnings;
    }

    @JsonProperty("PitchingStrikeoutsPerNineInnings")
    public void setPitchingStrikeoutsPerNineInnings(BigDecimal pitchingStrikeoutsPerNineInnings) {
        this.pitchingStrikeoutsPerNineInnings = pitchingStrikeoutsPerNineInnings;
    }

    private BigDecimal pitchingWalksPerNineInnings;

    @JsonProperty("pitchingWalksPerNineInnings")
    public BigDecimal getPitchingWalksPerNineInnings() {
        return pitchingWalksPerNineInnings;
    }

    @JsonProperty("PitchingWalksPerNineInnings")
    public void setPitchingWalksPerNineInnings(BigDecimal pitchingWalksPerNineInnings) {
        this.pitchingWalksPerNineInnings = pitchingWalksPerNineInnings;
    }

    private BigDecimal pitchingBattingAverageOnBallsInPlay;

    @JsonProperty("pitchingBattingAverageOnBallsInPlay")
    public BigDecimal getPitchingBattingAverageOnBallsInPlay() {
        return pitchingBattingAverageOnBallsInPlay;
    }

    @JsonProperty("PitchingBattingAverageOnBallsInPlay")
    public void setPitchingBattingAverageOnBallsInPlay(BigDecimal pitchingBattingAverageOnBallsInPlay) {
        this.pitchingBattingAverageOnBallsInPlay = pitchingBattingAverageOnBallsInPlay;
    }

    private BigDecimal pitchingWeightedOnBasePercentage;

    @JsonProperty("pitchingWeightedOnBasePercentage")
    public BigDecimal getPitchingWeightedOnBasePercentage() {
        return pitchingWeightedOnBasePercentage;
    }

    @JsonProperty("PitchingWeightedOnBasePercentage")
    public void setPitchingWeightedOnBasePercentage(BigDecimal pitchingWeightedOnBasePercentage) {
        this.pitchingWeightedOnBasePercentage = pitchingWeightedOnBasePercentage;
    }

    private BigDecimal doublePlays;

    @JsonProperty("doublePlays")
    public BigDecimal getDoublePlays() {
        return doublePlays;
    }

    @JsonProperty("DoublePlays")
    public void setDoublePlays(BigDecimal doublePlays) {
        this.doublePlays = doublePlays;
    }

    private BigDecimal pitchingDoublePlays;

    @JsonProperty("pitchingDoublePlays")
    public BigDecimal getPitchingDoublePlays() {
        return pitchingDoublePlays;
    }

    @JsonProperty("PitchingDoublePlays")
    public void setPitchingDoublePlays(BigDecimal pitchingDoublePlays) {
        this.pitchingDoublePlays = pitchingDoublePlays;
    }

    private Boolean battingOrderConfirmed;

    @JsonProperty("battingOrderConfirmed")
    public Boolean getBattingOrderConfirmed() {
        return battingOrderConfirmed;
    }

    @JsonProperty("BattingOrderConfirmed")
    public void setBattingOrderConfirmed(Boolean battingOrderConfirmed) {
        this.battingOrderConfirmed = battingOrderConfirmed;
    }

    private BigDecimal isolatedPower;

    @JsonProperty("isolatedPower")
    public BigDecimal getIsolatedPower() {
        return isolatedPower;
    }

    @JsonProperty("IsolatedPower")
    public void setIsolatedPower(BigDecimal isolatedPower) {
        this.isolatedPower = isolatedPower;
    }

    private BigDecimal fieldingIndependentPitching;

    @JsonProperty("fieldingIndependentPitching")
    public BigDecimal getFieldingIndependentPitching() {
        return fieldingIndependentPitching;
    }

    @JsonProperty("FieldingIndependentPitching")
    public void setFieldingIndependentPitching(BigDecimal fieldingIndependentPitching) {
        this.fieldingIndependentPitching = fieldingIndependentPitching;
    }

    private BigDecimal pitchingQualityStarts;

    @JsonProperty("pitchingQualityStarts")
    public BigDecimal getPitchingQualityStarts() {
        return pitchingQualityStarts;
    }

    @JsonProperty("PitchingQualityStarts")
    public void setPitchingQualityStarts(BigDecimal pitchingQualityStarts) {
        this.pitchingQualityStarts = pitchingQualityStarts;
    }

    private Integer pitchingInningStarted;

    @JsonProperty("pitchingInningStarted")
    public Integer getPitchingInningStarted() {
        return pitchingInningStarted;
    }

    @JsonProperty("PitchingInningStarted")
    public void setPitchingInningStarted(Integer pitchingInningStarted) {
        this.pitchingInningStarted = pitchingInningStarted;
    }

    private BigDecimal leftOnBase;

    @JsonProperty("leftOnBase")
    public BigDecimal getLeftOnBase() {
        return leftOnBase;
    }

    @JsonProperty("LeftOnBase")
    public void setLeftOnBase(BigDecimal leftOnBase) {
        this.leftOnBase = leftOnBase;
    }

    private BigDecimal pitchingHolds;

    @JsonProperty("pitchingHolds")
    public BigDecimal getPitchingHolds() {
        return pitchingHolds;
    }

    @JsonProperty("PitchingHolds")
    public void setPitchingHolds(BigDecimal pitchingHolds) {
        this.pitchingHolds = pitchingHolds;
    }

    private BigDecimal pitchingBlownSaves;

    @JsonProperty("pitchingBlownSaves")
    public BigDecimal getPitchingBlownSaves() {
        return pitchingBlownSaves;
    }

    @JsonProperty("PitchingBlownSaves")
    public void setPitchingBlownSaves(BigDecimal pitchingBlownSaves) {
        this.pitchingBlownSaves = pitchingBlownSaves;
    }

    private Integer substituteBattingOrder;

    @JsonProperty("substituteBattingOrder")
    public Integer getSubstituteBattingOrder() {
        return substituteBattingOrder;
    }

    @JsonProperty("SubstituteBattingOrder")
    public void setSubstituteBattingOrder(Integer substituteBattingOrder) {
        this.substituteBattingOrder = substituteBattingOrder;
    }

    private Integer substituteBattingOrderSequence;

    @JsonProperty("substituteBattingOrderSequence")
    public Integer getSubstituteBattingOrderSequence() {
        return substituteBattingOrderSequence;
    }

    @JsonProperty("SubstituteBattingOrderSequence")
    public void setSubstituteBattingOrderSequence(Integer substituteBattingOrderSequence) {
        this.substituteBattingOrderSequence = substituteBattingOrderSequence;
    }

    private BigDecimal fantasyPointsFantasyDraft;

    @JsonProperty("fantasyPointsFantasyDraft")
    public BigDecimal getFantasyPointsFantasyDraft() {
        return fantasyPointsFantasyDraft;
    }

    @JsonProperty("FantasyPointsFantasyDraft")
    public void setFantasyPointsFantasyDraft(BigDecimal fantasyPointsFantasyDraft) {
        this.fantasyPointsFantasyDraft = fantasyPointsFantasyDraft;
    }

    private BigDecimal fantasyPointsBatting;

    @JsonProperty("fantasyPointsBatting")
    public BigDecimal getFantasyPointsBatting() {
        return fantasyPointsBatting;
    }

    @JsonProperty("FantasyPointsBatting")
    public void setFantasyPointsBatting(BigDecimal fantasyPointsBatting) {
        this.fantasyPointsBatting = fantasyPointsBatting;
    }

    private BigDecimal fantasyPointsPitching;

    @JsonProperty("fantasyPointsPitching")
    public BigDecimal getFantasyPointsPitching() {
        return fantasyPointsPitching;
    }

    @JsonProperty("FantasyPointsPitching")
    public void setFantasyPointsPitching(BigDecimal fantasyPointsPitching) {
        this.fantasyPointsPitching = fantasyPointsPitching;
    }
}

