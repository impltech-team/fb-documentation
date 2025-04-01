package io.limeup.flexbets.sport.service.statscore.impl;

import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreGroupDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSeasonDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreStageDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.service.statscore.StatScoreDataService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatScoreDataServiceImpl implements StatScoreDataService {

    private final StatScoreProxyService statScoreProxyService;

    @Override
    public List<StatScoreEventDTO> getAllEvents(EventQueryParams queryParams) {
        return collectAllEventsToList(statScoreProxyService.listEvents(queryParams).getItems());
    }

    @Override
    public Map<StatScoreSeasonDTO, List<StatScoreEventDTO>> getAllEventsPerSeasons(EventQueryParams queryParams) {
        return collectAllEventsBySeason(statScoreProxyService.listEvents(queryParams).getItems());
    }

    private Map<StatScoreSeasonDTO, List<StatScoreEventDTO>> collectAllEventsBySeason(List<StatScoreCompetitionDTO> competitions) {
        return Optional.ofNullable(competitions).orElse(List.of()).stream()
                .flatMap(comp -> {
                    List<StatScoreSeasonDTO> seasons = Optional.ofNullable(comp.getSeasons())
                            .filter(list -> !list.isEmpty())
                            .orElseGet(() -> Optional.ofNullable(comp.getSeason())
                                    .map(List::of).orElse(List.of()));
                    return seasons.stream();
                })
                .collect(Collectors.toMap(
                        Function.identity(),
                        season -> {
                            List<StatScoreStageDTO> stages = Optional.ofNullable(season.getStages())
                                    .filter(list -> !list.isEmpty())
                                    .orElseGet(() -> Optional.ofNullable(season.getStage())
                                            .map(List::of).orElse(List.of()));

                            return stages.stream()
                                    .flatMap(stage -> {
                                        List<StatScoreGroupDTO> groups = Optional.ofNullable(stage.getGroups())
                                                .filter(list -> !list.isEmpty())
                                                .orElseGet(() -> Optional.ofNullable(stage.getGroup())
                                                        .map(List::of).orElse(List.of()));

                                        return groups.stream()
                                                .flatMap(group -> {
                                                    List<StatScoreEventDTO> events = Optional.ofNullable(group.getEvents())
                                                            .filter(list -> !list.isEmpty())
                                                            .orElseGet(() -> Optional.ofNullable(group.getEvent())
                                                                    .map(List::of).orElse(List.of()));
                                                    return events.stream();
                                                });
                                    })
                                    .toList();
                        }
                ));
    }

    private List<StatScoreEventDTO> collectAllEventsToList(List<StatScoreCompetitionDTO> competitions) {
        return Optional.ofNullable(competitions).orElse(List.of()).stream()
                .flatMap(comp -> {
                    List<StatScoreSeasonDTO> seasons = Optional.ofNullable(comp.getSeasons())
                            .filter(list -> !list.isEmpty())
                            .orElseGet(() -> Optional.ofNullable(comp.getSeason())
                                    .map(List::of).orElse(List.of()));
                    return seasons.stream();
                })
                .flatMap(season -> {
                    List<StatScoreStageDTO> stages = Optional.ofNullable(season.getStages())
                            .filter(list -> !list.isEmpty())
                            .orElseGet(() -> Optional.ofNullable(season.getStage())
                                    .map(List::of).orElse(List.of()));
                    return stages.stream();
                })
                .flatMap(stage -> {
                    List<StatScoreGroupDTO> groups = Optional.ofNullable(stage.getGroups())
                            .filter(list -> !list.isEmpty())
                            .orElseGet(() -> Optional.ofNullable(stage.getGroup())
                                    .map(List::of).orElse(List.of()));
                    return groups.stream();
                })
                .flatMap(group -> {
                    List<StatScoreEventDTO> events = Optional.ofNullable(group.getEvents())
                            .filter(list -> !list.isEmpty())
                            .orElseGet(() -> Optional.ofNullable(group.getEvent())
                                    .map(List::of).orElse(List.of()));
                    return events.stream();
                })
                .toList();
    }

}
