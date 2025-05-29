package io.limeup.flexbets.sport.service.trade360;

import io.limeup.flexbets.sport.dto.trade360.Trade360ApiRequestBodyParams;
import io.limeup.flexbets.sport.dto.trade360.Trade360BetDTO;
import io.limeup.flexbets.sport.dto.trade360.Trade360MarketDTO;
import io.limeup.flexbets.sport.dto.trade360.Trade360SnapshotResponseDTO;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.Participant;
import io.limeup.flexbets.sport.repository.EventRepository;
import io.limeup.flexbets.sport.repository.ParticipantRepository;
import io.limeup.flexbets.sport.service.BetService;
import io.limeup.flexbets.sport.service.CompetitionService;
import io.limeup.flexbets.sport.utils.Trade360ApiUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class Trade360DataService {

    Trade360PrematchClientService prematchClientService;
    Trade360ApiUtils apiUtils;
    CompetitionService competitionService;
    EventRepository eventRepository;
    ParticipantRepository participantRepository;
    BetService betService;

    public void fetchDataFromTrade360ApiForCompetitionAndDate(Integer competitionId, LocalDate prefetchDate){
        Optional<Competition> competitionOptional = competitionService.readByExternalId(competitionId);
        if(competitionOptional.isPresent()){
            Competition competition = competitionOptional.get();
            LocalDate toDate = prefetchDate.plusDays(1);

            Trade360ApiRequestBodyParams apiRequestBodyParams = new Trade360ApiRequestBodyParams();
            apiRequestBodyParams.setLeagues(List.of(competition.getLsId()));
            apiRequestBodyParams.setFromDate(prefetchDate.atStartOfDay().atZone(ZoneOffset.UTC).toEpochSecond());
            apiRequestBodyParams.setToDate(toDate.atStartOfDay().atZone(ZoneOffset.UTC).toEpochSecond());
            String jsonResponse = prematchClientService.post("/GetEvents", apiRequestBodyParams);
            List<Trade360SnapshotResponseDTO> data = null;
            try {
                data = apiUtils.getDataFromTrade360SnapshotResponseJson(jsonResponse);
            } catch (IOException ex) {
                log.error("Error in data parsing from Trade360 response API : {}", ex.getMessage());
            }

            if(data != null && !data.isEmpty()){
                processFixtureEventsWithNullLsId(data, prefetchDate.atStartOfDay(), toDate.atStartOfDay());
                data.forEach(event -> {
                    Map<Integer, List<Trade360BetDTO>> marketBetsMap = event.getMarkets().stream()
                                    .collect(Collectors.toMap(Trade360MarketDTO::getId, Trade360MarketDTO::getBets));
                    betService.updateBetsInfoFromTrade360(event.getFixtureId(), marketBetsMap);
                });
            }
        }
    }

    //Works only for 2-participant events for now
    private void processFixtureEventsWithNullLsId(List<Trade360SnapshotResponseDTO> data, LocalDateTime startDate, LocalDateTime endDate){
        List<Event> events = eventRepository.findAllByStartDateBetweenAndLsIdIsNull(startDate, endDate);
        Map<String, String> allParticipants = participantRepository.findAll().stream()
                .filter(participant -> participant.getTeamName() != null && participant.getTeamShortName() != null)
                .collect(Collectors.toMap(Participant::getTeamName, Participant::getTeamShortName));
        Map<String, Long> eventNameIdMap = new HashMap<>();
        data.forEach(fixtureEvent -> {
            if(fixtureEvent.getFixture().getParticipants().size() == 2) {
                String homeTeamName = fixtureEvent.getFixture().getParticipants().stream()
                        .filter(participant -> participant.getPosition().equals(1))
                        .findFirst().get().getName();
                String homeShortName = allParticipants.get(homeTeamName);

                String awayTeamName = fixtureEvent.getFixture().getParticipants().stream()
                        .filter(participant -> participant.getPosition().equals(2))
                        .findFirst().get().getName();
                String awayShortName = allParticipants.get(awayTeamName);

                if (homeShortName != null && awayShortName != null) {
                    eventNameIdMap.put(String.format("%s - %s", homeShortName, awayShortName), fixtureEvent.getFixtureId());
                }
            }
        });

        List<Event> eventsToSave = new ArrayList<>();

        events.forEach(event -> {
            Long fixtureId = eventNameIdMap.get(event.getName());
            if(fixtureId != null){
                event.setLsId(fixtureId);
                eventsToSave.add(event);
            }
        });

        eventRepository.saveAll(eventsToSave);
    }
}
