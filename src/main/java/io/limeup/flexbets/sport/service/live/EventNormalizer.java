package io.limeup.flexbets.sport.service.live;

import io.limeup.flexbets.sport.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventNormalizer {

    public Map<String, Object> normalizeFull(
            LiveEvent ev,
            List<LiveLsScoreboard> score,

            List<LiveLsPeriodIncident> incidents,
            List<LiveLsStatistic> stats,
            List<LiveLsParticipant> parts,
            List<LiveParticipant> baseParts,
            List<LiveLsMarket> markets,
            List<LiveLsBet> bets
    ) {
        Map<String, Object> dto = new LinkedHashMap<>();

        dto.put("id", ev.getId());
        dto.put("action", ev.getAction());
        dto.put("start_date", ev.getStartDate().toString());
        dto.put("ft_only", ev.getFtOnly() ? "yes" : "no");
        dto.put("coverage_type", ev.getCoverageType());
        dto.put("status_id", ev.getStatusId());
        dto.put("status_name", ev.getStatusName());
        dto.put("sport_id", ev.getSportId());
        dto.put("sport_name", ev.getSportName());
        dto.put("day", ev.getDay());
        dto.put("neutral_venue", ev.getNeutralVenue() ? "yes" : "no");
        dto.put("item_status", ev.getItemStatus());
        dto.put("clock_time", ev.getClockTime());
        dto.put("clock_status", ev.getClockStatus());
        dto.put("area_id", ev.getAreaId());
        dto.put("area_name", ev.getAreaName());
        dto.put("competition_id", ev.getCompetitionId());
        dto.put("competition_name", ev.getCompetitionName());
        dto.put("season_id", ev.getSeasonId());
        dto.put("stage_id", ev.getStageId());
        dto.put("stage_name", ev.getStageName());
        dto.put("gender", ev.getGender());
        dto.put("bet_status", ev.getBetStatus());
        dto.put("bet_cards", ev.getBetCards());
        dto.put("bet_corners", ev.getBetCorners());
        dto.put("relation_status", ev.getRelationStatus());
        dto.put("status_type", ev.getStatusType());
        dto.put("name", ev.getName());
        dto.put("round_id", ev.getRoundId());
        dto.put("round_name", ev.getRoundName());
        dto.put("played_time", ev.getPlayedTime());

        List<Map<String, Object>> partDtos = new ArrayList<>();

        Set<Long> allIds = new HashSet<>();
        parts.forEach(p -> allIds.add(p.getId()));
        baseParts.forEach(bp -> allIds.add(bp.getId()));

        Map<Long, LiveLsParticipant> lsMap = parts.stream()
                .collect(Collectors.toMap(LiveLsParticipant::getId, p -> p));

        Map<Long, LiveParticipant> baseMap = baseParts.stream()
                .collect(Collectors.toMap(LiveParticipant::getId, p -> p));

        for (Long id : allIds) {
            Map<String, Object> pDto = new LinkedHashMap<>();

            LiveLsParticipant ls = lsMap.get(id);
            LiveParticipant base = baseMap.get(id);

            pDto.put("id", id);

            pDto.put("position", ls != null ? ls.getLsParticipantPosition() : null);

            pDto.put("name", base != null ? base.getName() : ls != null ? ls.getLsParticipantName() : null);
            pDto.put("acronym", base != null ? base.getAcronym() : null);
            pDto.put("short_name", base != null ? base.getShortName() : null);

            pDto.put("stats", stats.stream()
                    .filter(s -> s.getFixtureId().equals(ev.getLsId()) && s.getFixtureId().equals(id))
                    .map(s -> Map.of(
                            "id", s.getLsStatType(),
                            "value", s.getLsStatResultPosition2() - s.getLsStatResultPosition1()
                    )).toList());

            pDto.put("results", score.stream().flatMap(sc -> Stream.of(
                    Map.of("id", 1, "value", sc.getLsScoreboardResultPosition1()),
                    Map.of("id", 2, "value", sc.getLsScoreboardResultPosition2())
            )).toList());

            pDto.put("subparticipants", List.<Object>of());

            partDtos.add(pDto);
        }

        dto.put("participants", partDtos);

        dto.put("incidents", incidents.stream().map(i -> Map.<String, Object>of(
                "period", i.getLsIncidentPeriod(),
                "incident_type", i.getLsIncidentType(),
                "x_pos", i.getLsIncidentResultPosition1(),
                "y_pos", i.getLsIncidentResultPosition2(),
                "seconds", i.getLsIncidentSeconds()
        )).toList());

        //this data do not needed now
        //scorebord
   //     dto.put("scoreboard", score.isEmpty() ? null : score.get(score.size() - 1));
        //periods
       // dto.put("periods", periods);


        dto.put("markets", markets.stream().map(m -> {
            Map<String, Object> marketMap = new LinkedHashMap<>();
            marketMap.put("id", m.getMarketId());
            marketMap.put("name", m.getMarketName());
            marketMap.put("mainLine", m.getMarketMainLine());

            List<Map<String, Object>> betList = bets.stream()
                    .filter(b -> Objects.equals(b.getMarketId(), m.getId()))
                    .map(b -> {
                        Map<String, Object> betMap = new LinkedHashMap<>();
                        betMap.put("id", b.getBetId());
                        betMap.put("price", b.getBetPrice());
                        betMap.put("startPrice", b.getBetStartPrice());
                        betMap.put("status", b.getBetStatus());
                        return betMap;
                    }).toList();

            marketMap.put("bets", betList);
            return marketMap;
        }).toList());


        return dto;
    }
}