package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.EventLiteDTO;
import io.limeup.flexbets.sport.dto.EventStatisticDTO;
import io.limeup.flexbets.sport.dto.HistoricalStatDTO;
import io.limeup.flexbets.sport.dto.ParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventParticipantDTO;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Participant;
import io.limeup.flexbets.sport.repository.projection.BetRow;
import io.limeup.flexbets.sport.repository.projection.ParticipantStatRow;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ParticipantMapper {

    public Participant toEntity(StatScoreEventParticipantDTO dto, Competition competition) {
        Participant entity = new Participant();
        return updateEntity(entity, dto, competition);
    }

    public Participant updateEntity(Participant entity, StatScoreEventParticipantDTO dto, Competition competition) {
        if (dto == null || entity == null) return entity;
        entity.setExternalId(dto.getId());
        entity.setTeamName(dto.getName());
        entity.setAcronym(dto.getAcronym());
        entity.setCompetition(competition);
        entity.setType(dto.getType());
        entity.setHistoricalStats(new ArrayList<>());
        entity.setTeamShortName(dto.getShortName());
        return entity;
    }

    public static List<ParticipantDTO> toDTO(List<ParticipantStatRow> statRowsRaw,  Map<Integer, List<BetRow>> eventIdBetsMap) {
        Map<Integer, List<ParticipantStatRow>> groupedByParticipant = statRowsRaw.stream()
                .filter(row -> row.getId() != null)
                .collect(Collectors.groupingBy(
                        ParticipantStatRow::getId,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        List<ParticipantDTO> result = new ArrayList<>();

        for (Map.Entry<Integer, List<ParticipantStatRow>> entry : groupedByParticipant.entrySet()) {
            List<ParticipantStatRow> stats = entry.getValue();
            if (stats.isEmpty()) continue;

            ParticipantStatRow first = stats.get(0);

            Map<String, List<ParticipantStatRow>> statsByName = stats.stream()
                    .filter(r -> r.getStatName() != null)
                    .collect(Collectors.groupingBy(ParticipantStatRow::getStatName));

            List<HistoricalStatDTO> historicalStats = new ArrayList<>();
            for (Map.Entry<String, List<ParticipantStatRow>> statEntry : statsByName.entrySet()) {
                String statName = statEntry.getKey();
                List<ParticipantStatRow> statRows = statEntry.getValue();

                List<EventStatisticDTO> eventStatistics = statRows.stream()
                        .map(row -> new EventStatisticDTO(
                                Optional.ofNullable(row.getEventId()).orElse(0),
                                row.getEventName(),
                                row.getEventStartDate(),
                                Optional.ofNullable(row.getValueNumeric()).orElse(0.0),
                                row.getValueRaw(),
                                extractOpponentFromAcronyms(row.getEventParticipantAcronyms(), row.getAcronym())
                        ))
                        .collect(Collectors.toList());

                DoubleSummaryStatistics summary = statRows.stream()
                        .map(row -> Optional.ofNullable(row.getValueNumeric()).orElse(0.0))
                        .mapToDouble(Double::doubleValue)
                        .summaryStatistics();

                historicalStats.add(new HistoricalStatDTO(
                        statName,
                        BigDecimal.valueOf(summary.getAverage()).setScale(2, RoundingMode.HALF_UP),
                        statRows.size(),
                        (int) summary.getMax(),
                        (int) summary.getMin(),
                        eventStatistics
                ));
            }

            EventLiteDTO nextEvent = (first.getFutureEventId() != null && first.getFutureEventStartDate() != null)
                    ? new EventLiteDTO(
                    first.getFutureEventId(),
                    first.getFutureEventName(),
                    first.getFutureEventStartDate(),
                    extractOpponentFromAcronyms(first.getFutureEventAcronyms(), first.getAcronym())
            ) : null;

            ParticipantDTO dto = new ParticipantDTO();
            dto.setId(first.getId());
            dto.setTeamName(first.getTeamName());
            dto.setAcronym(first.getAcronym());
            dto.setCompetition(first.getCompetitionName());
            dto.setCompetitionId(first.getCompetitionId());
            dto.setNextEvent(nextEvent);
            dto.setHistoricalStats(historicalStats);
            dto.setOdds(first.getFutureEventId() != null ? BetMapper.betRowListToOddsDtoList(eventIdBetsMap.get(first.getFutureEventId()))
                    : new ArrayList<>());

            result.add(dto);
        }

        return result;
    }

    static String extractOpponentFromAcronyms(String acronyms, String self) {
        if (acronyms == null || self == null) return null;
        return Arrays.stream(acronyms.split(","))
                .filter(a -> !a.equalsIgnoreCase(self))
                .findFirst()
                .orElse(null);
    }
}

