package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSeasonDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.model.SubParticipant;
import io.limeup.flexbets.sport.service.SubParticipantService;
import io.limeup.flexbets.sport.service.statscore.StatScoreDataService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubParticipantServiceImpl implements SubParticipantService {

    private final StatScoreDataService statScoreDataService;
    private final StatScoreProxyService statScoreService;

    @Override
    public List<SubParticipantDTO> listSubParticipants(Integer competitionId, List<String> positions, List<Integer> participantIds, Integer marketId, RequestQueryDTO requestQuery) {
        return null;
    }

    @Override
    public SubParticipantDTO getSubParticipantById(Integer subParticipantId) {
        return null;
    }

    record ParticipantEventSeason(int participantId, int eventId, int seasonId) {}

    @Override
    public void fetchStatData(int durationDays) {
        Map<StatScoreSeasonDTO, List<StatScoreEventDTO>> allEventsPerSeasons = statScoreDataService.getAllEventsPerSeasons(EventQueryParams
                .builder()
                .limit(500)
                .dateFrom(LocalDateTime.now())
                .dateTo(LocalDateTime.now().plusDays(durationDays))
                .build());
        Set<ParticipantEventSeason> uniqueParticipantEventSeason = allEventsPerSeasons.entrySet().stream()
                .flatMap(entry -> {
                    StatScoreSeasonDTO season = entry.getKey();
                    int seasonId = season.getId();

                    return entry.getValue().stream()
                            .flatMap(event -> Optional.ofNullable(event.getParticipants()).orElse(List.of()).stream()
                                    .map(participant -> new ParticipantEventSeason(participant.getId(), event.getId(), seasonId)));
                })
                .collect(Collectors.toSet());

        for (ParticipantEventSeason pes : uniqueParticipantEventSeason) {
            List<StatScoreSubParticipantDTO> statScoreSubParticipants =
                    statScoreService.listSquadSubParticipants(pes.participantId(), pes.seasonId()).getItems();
            //save raw subparticipants without historical stats to db
            List<SubParticipant> subParticipants = new ArrayList<>();
            fetchHistoricalStats(subParticipants, pes.participantId);
        }
    }

    private void fetchHistoricalStats(List<SubParticipant> subParticipants, int participantId) {
        List<StatScoreEventDTO> historicalEvents = statScoreDataService.getAllEvents(
                EventQueryParams.builder()
                        .participantId(participantId)
                        .dateTo(LocalDateTime.now())
                        .limit(5)
                        .sortType("start_date")
                        .sortOrder("desc")
                        .build());
        //improvement filter already synced to db historical events
        for (StatScoreEventDTO event : historicalEvents) {
            List<StatScoreSubParticipantDTO> items = statScoreService.listEventSubParticipants(event.getId()).getItems();
        }
    }

}
