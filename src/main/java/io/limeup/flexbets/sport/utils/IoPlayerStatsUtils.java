package io.limeup.flexbets.sport.utils;

import io.limeup.flexbets.sport.dto.EventStatisticDTO;
import io.limeup.flexbets.sport.dto.HistoricalStatDTO;
import io.limeup.flexbets.sport.model.IoPlayerGameStats;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;

public class IoPlayerStatsUtils {

    private static final Map<String, Function<IoPlayerGameStats, Number>> GAME_EXTRACT = Map.ofEntries(
            Map.entry("At bats", IoPlayerGameStats::getAtBats),
            Map.entry("Runs", IoPlayerGameStats::getRuns),
            Map.entry("Hits", IoPlayerGameStats::getHits),
            Map.entry("Singles", IoPlayerGameStats::getSingles),
            Map.entry("Doubles", IoPlayerGameStats::getDoubles),
            Map.entry("Triples", IoPlayerGameStats::getTriples),
            Map.entry("Home runs", IoPlayerGameStats::getHomeRuns),
            Map.entry("Runs batted in", IoPlayerGameStats::getRunsBattedIn),
            Map.entry("Strikeouts", IoPlayerGameStats::getStrikeouts),
            Map.entry("Walks", IoPlayerGameStats::getWalks),
            Map.entry("Hit by pitch", IoPlayerGameStats::getHitByPitch),
            Map.entry("Outs", IoPlayerGameStats::getOuts),
            Map.entry("Sacrifices", IoPlayerGameStats::getSacrifices),
            Map.entry("Sacrifice flies", IoPlayerGameStats::getSacrificeFlies),
            Map.entry("Ground into DP", IoPlayerGameStats::getGroundIntoDoublePlay),
            Map.entry("Stolen bases", IoPlayerGameStats::getStolenBases),
            Map.entry("Caught stealing", IoPlayerGameStats::getCaughtStealing)
    );

    public static List<HistoricalStatDTO> buildStatsFromGames(List<IoPlayerGameStats> games, Map<Integer, String> teamNames) {
        return GAME_EXTRACT.entrySet().stream()
                .map(e -> buildStat(e.getKey(), e.getValue(), games, teamNames))
                .filter(h -> h.getCount() > 0)
                .toList();
    }

    private static HistoricalStatDTO buildStat(String name, Function<IoPlayerGameStats, Number> getter,
                                               List<IoPlayerGameStats> games, Map<Integer, String> teamNames) {
        List<EventStatisticDTO> ev = new ArrayList<>(games.size());
        DoubleSummaryStatistics stats = new DoubleSummaryStatistics();

        for (IoPlayerGameStats g : games) {
            Number n = getter.apply(g);
            if (n == null) continue;
            double v = n.doubleValue();
            stats.accept(v);

            ev.add(EventStatisticDTO.builder()
                    .eventId(Math.toIntExact(g.getGameId()))
                    .eventName(teamNames.getOrDefault(g.getTeamId(), "") + " - " + teamNames.getOrDefault(g.getOpponentId(), ""))
                    .eventDate(g.getGameDatetime())
                    .value(v)
                    .rawValue(n.toString())
                    .opponent(teamNames.get(g.getOpponentId()))
                    .build());
        }

        if (stats.getCount() == 0) {
            return HistoricalStatDTO.builder().statName(name).count(0).eventStatistics(List.of()).build();
        }

        return HistoricalStatDTO.builder()
                .statName(name)
                .average(round(stats.getAverage(), 2))
                .count((int) stats.getCount())
                .maxValue((int) stats.getMax())
                .minValue((int) stats.getMin())
                .eventStatistics(ev)
                .build();
    }

    private static BigDecimal round(double value, int scale) {
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP);
    }
}
