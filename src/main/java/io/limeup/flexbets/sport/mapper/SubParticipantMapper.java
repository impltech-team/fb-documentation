package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.config.FlexBetsSportConfiguration;
import io.limeup.flexbets.sport.dto.EventLiteDTO;
import io.limeup.flexbets.sport.dto.EventStatisticDTO;
import io.limeup.flexbets.sport.dto.HistoricalStatDTO;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.SubParticipant;
import io.limeup.flexbets.sport.repository.projection.SubParticipantStatRow;
import io.limeup.flexbets.sport.utils.UnitConversionUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import systems.uom.common.USCustomary;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class SubParticipantMapper {

    private final FlexBetsSportConfiguration configuration;

    public SubParticipant toEntity(StatScoreSubParticipantDTO dto, Area area, Competition competition) {
        if (dto == null) return null;

        SubParticipant entity = new SubParticipant();
        return updateEntity(entity, dto, area, competition);
    }

    public SubParticipant updateEntity(SubParticipant entity, StatScoreSubParticipantDTO dto, Area area, Competition competition) {
        if (dto == null || entity == null) return entity;
        entity.setExternalId(dto.getId());
        entity.setPlayerName(dto.getName());
        entity.setShirtNumber(dto.getShirtNr());
        entity.setAvatarUrl(dto.getLogo());
        entity.setGender(dto.getGender());

        if (dto.getDetails() != null) {
            if (StringUtils.isNotBlank(dto.getDetails().getWeight())) {
                entity.setWeight(UnitConversionUtils.convertWeightToPreferredUnit(Double.parseDouble(dto.getDetails().getWeight()),
                        configuration.isConvertToImperial(), USCustomary.POUND));
            }
            if (StringUtils.isNotBlank(dto.getDetails().getHeight())) {
                entity.setHeight(UnitConversionUtils.convertHeightToPreferredUnit(Double.parseDouble(dto.getDetails().getHeight()),
                        configuration.isConvertToImperial(), USCustomary.INCH));
            }
            entity.setPosition(dto.getDetails().getPositionName());

            if (dto.getDetails().getBirthdate() != null) {
                try {
                    entity.setBirthDate(LocalDate.parse(dto.getDetails().getBirthdate()));
                } catch (DateTimeParseException e) {
                    entity.setBirthDate(null);
                }
            }
        }

        entity.setArea(area);
        entity.setCompetition(competition);
        return entity;
    }

    public static List<SubParticipantDTO> toDTO(List<SubParticipantStatRow> statRowsRaw) {
        Map<Integer, List<SubParticipantStatRow>> groupedByPlayer = statRowsRaw.stream()
                .filter(row -> row.getId() != null)
                .collect(Collectors.groupingBy(
                        SubParticipantStatRow::getId,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        List<SubParticipantDTO> result = new ArrayList<>();

        for (Map.Entry<Integer, List<SubParticipantStatRow>> entry : groupedByPlayer.entrySet()) {
            List<SubParticipantStatRow> stats = entry.getValue();
            if (stats.isEmpty()) continue;

            SubParticipantStatRow first = stats.get(0);

            Map<String, List<SubParticipantStatRow>> statsByName = stats.stream()
                    .filter(r -> r.getStatName() != null)
                    .collect(Collectors.groupingBy(SubParticipantStatRow::getStatName));

            List<HistoricalStatDTO> historicalStats = new ArrayList<>();
            for (Map.Entry<String, List<SubParticipantStatRow>> statEntry : statsByName.entrySet()) {
                String statName = statEntry.getKey();
                List<SubParticipantStatRow> statRows = statEntry.getValue();

                List<EventStatisticDTO> eventStatistics = statRows.stream()
                        .map(row -> new EventStatisticDTO(
                                Optional.ofNullable(row.getEventId()).orElse(0),
                                row.getEventName(),
                                row.getEventStartDate(),
                                Optional.ofNullable(row.getValueNumeric()).orElse(0.0),
                                row.getValueRaw(),
                                extractOpponentFromAcronyms(row.getEventParticipantAcronyms(), row.getCurrentTeamAcronym())
                        ))
                        .collect(Collectors.toList());

                int count = statRows.size();
                DoubleSummaryStatistics statsSummary = statRows.stream()
                        .map(row -> Optional.ofNullable(row.getValueNumeric()).orElse(0.0))
                        .mapToDouble(Double::doubleValue)
                        .summaryStatistics();

                historicalStats.add(new HistoricalStatDTO(
                        statName,
                        statsSummary.getAverage(),
                        count,
                        (int) statsSummary.getMax(),
                        (int) statsSummary.getMin(),
                        eventStatistics
                ));
            }

            EventLiteDTO nextEvent = (first.getFutureEventId() != null && first.getFutureEventStartDate() != null)
                    ? new EventLiteDTO(
                    first.getFutureEventId(),
                    first.getFutureEventName(),
                    first.getFutureEventStartDate().toString(),
                    extractOpponentFromAcronyms(first.getFutureEventAcronyms(), first.getCurrentTeamAcronym())
            ) : null;

            SubParticipantDTO dto = new SubParticipantDTO(
                    first.getId(),
                    first.getPlayerName(),
                    first.getCompetitionId(),
                    first.getCompetitionName(),
                    first.getAvatarUrl(),
                    first.getParticipantId(),
                    first.getTeamName(),
                    first.getPosition(),
                    nextEvent,
                    Optional.ofNullable(first.getShirtNumber()).orElse(0),
                    first.getAreaId(),
                    first.getAreaName(),
                    first.getGender(),
                    first.getWeight(),
                    first.getHeight(),
                    first.getBirthDate(),
                    historicalStats,
                    new ArrayList<>()  // empty until trade360 integration
            );

            result.add(dto);
        }

        return result;
    }

    private static String extractOpponentFromAcronyms(String acronymsCsv, String currentTeamAcronym) {
        if (acronymsCsv == null || currentTeamAcronym == null) return null;
        return Arrays.stream(acronymsCsv.split(":"))
                .map(String::trim)
                .filter(acr -> !acr.equalsIgnoreCase(currentTeamAcronym))
                .findFirst()
                .orElse(null);
    }
}
