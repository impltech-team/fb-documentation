package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSeasonDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreStatDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.mapper.EventMapper;
import io.limeup.flexbets.sport.mapper.EventStatMapper;
import io.limeup.flexbets.sport.mapper.ParticipantMapper;
import io.limeup.flexbets.sport.mapper.SubParticipantMapper;
import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.EventStat;
import io.limeup.flexbets.sport.model.Participant;
import io.limeup.flexbets.sport.model.StatTargetType;
import io.limeup.flexbets.sport.model.SubParticipant;
import io.limeup.flexbets.sport.model.Venue;
import io.limeup.flexbets.sport.repository.EventRepository;
import io.limeup.flexbets.sport.repository.ParticipantRepository;
import io.limeup.flexbets.sport.repository.StatRepository;
import io.limeup.flexbets.sport.repository.SubParticipantRepository;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.AreaService;
import io.limeup.flexbets.sport.service.CompetitionService;
import io.limeup.flexbets.sport.service.StatsService;
import io.limeup.flexbets.sport.dto.StatsBatchRequestDTO;
import io.limeup.flexbets.sport.dto.StatsResponseDTO;
import io.limeup.flexbets.sport.service.VenueService;
import io.limeup.flexbets.sport.service.statscore.StatScoreDataService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StatsServiceImpl extends ExternalIdReadServiceImpl<EventStat, StatsResponseDTO, Long> implements StatsService {

    private final StatScoreDataService statScoreDataService;

    private final StatScoreProxyService statScoreProxyService;

    private final ParticipantMapper participantMapper;

    private final ParticipantRepository participantRepository;

    private final CompetitionService competitionService;

    private final VenueService venueService;

    private final SubParticipantMapper subParticipantMapper;

    private final AreaService areaService;

    private final SubParticipantRepository subParticipantRepository;

    private final EventMapper eventMapper;

    private final EventRepository eventRepository;

    private final EventStatMapper eventStatMapper;

    private final StatRepository statRepository;

    protected StatsServiceImpl(StatRepository repository, StatScoreDataService statScoreDataService,
                               StatScoreProxyService statScoreProxyService, ParticipantMapper participantMapper,
                               ParticipantRepository participantRepository, CompetitionService competitionService,
                               VenueService venueService, SubParticipantMapper subParticipantMapper, AreaService areaService,
                               SubParticipantRepository subParticipantRepository, EventMapper eventMapper,
                               EventRepository eventRepository, EventStatMapper eventStatMapper, StatRepository statRepository) {
        super(repository);
        this.statScoreDataService = statScoreDataService;
        this.statScoreProxyService = statScoreProxyService;
        this.participantMapper = participantMapper;
        this.participantRepository = participantRepository;
        this.competitionService = competitionService;
        this.venueService = venueService;
        this.subParticipantMapper = subParticipantMapper;
        this.areaService = areaService;
        this.subParticipantRepository = subParticipantRepository;
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
        this.eventStatMapper = eventStatMapper;
        this.statRepository = statRepository;
    }

    @Override
    public List<StatsResponseDTO> listBatchStats(StatsBatchRequestDTO request) {
        return null;
    }

    record ParticipantEventSeasonCompetition(Participant participant, Event event, int seasonId,
                                             Competition competition, StatScoreEventParticipantDTO participantDTO) {}

    @Transactional
    @Override
    public void fetchStatData(int durationDays) {
        List<StatScoreDataService.EventContext> eventContexts = statScoreDataService.getAllEventsWithContext(EventQueryParams
                .builder()
                .limit(150)
                .dateFrom(LocalDateTime.now())
                .dateTo(LocalDateTime.now().plusDays(durationDays))
                .build());

        Map<Integer, Participant> participantsToSave = new HashMap<>();
        Map<Integer, Event> eventsToSave = new HashMap<>();
        Set<ParticipantEventSeasonCompetition> uniqueParticipantEventSeasonComp = new HashSet<>();
        Map<Integer, Set<Participant>> eventParticipantsMap = new HashMap<>();

        fetchEventAndParticipants(eventContexts, participantsToSave, uniqueParticipantEventSeasonComp, eventsToSave,
                eventParticipantsMap, false);

        List<Participant> participants = participantRepository.saveAllAndFlush(participantsToSave.values());

        Map<Long, Participant> participantById = participants.stream()
                .collect(Collectors.toMap(Participant::getId, Function.identity()));

        for (Event event : eventsToSave.values()) {
            Set<Participant> linkedParticipants = eventParticipantsMap.getOrDefault(event.getExternalId(), Set.of());
            event.setParticipants(linkedParticipants.stream()
                    .map(p -> participantById.get(p.getId()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
        }
        eventRepository.saveAllAndFlush(eventsToSave.values());

        for (ParticipantEventSeasonCompetition pesc : uniqueParticipantEventSeasonComp) {

            Participant participant = pesc.participant();
            if (participant == null) continue;

            List<StatScoreSubParticipantDTO> statScoreSubParticipants =
                    statScoreProxyService.listSquadSubParticipants(pesc.participant().getExternalId(), pesc.seasonId()).getItems();

            List<SubParticipant> existingSubs = subParticipantRepository
                    .findByExternalIdIn(statScoreSubParticipants.stream().map(StatScoreSubParticipantDTO::getId).collect(Collectors.toList()));
            Map<Integer, SubParticipant> existingSubsMap = existingSubs.stream()
                    .collect(Collectors.toMap(SubParticipant::getExternalId, Function.identity()));

            List<SubParticipant> toSave = new ArrayList<>();

            for (StatScoreSubParticipantDTO dto : statScoreSubParticipants) {
                SubParticipant existing = existingSubsMap.get(dto.getId());
                Area area = areaService.readByExternalId(dto.getAreaId()).orElse(null);
                if (existing == null) {
                    SubParticipant sub = subParticipantMapper.toEntity(dto, area, null, pesc.competition);
                    existingSubsMap.put(sub.getExternalId(), sub);
                    toSave.add(sub);
                } else {
                    subParticipantMapper.updateEntity(existing, dto, area, null, pesc.competition);
                    toSave.add(existing);
                }
            }

            if (!toSave.isEmpty()) {
                subParticipantRepository.saveAllAndFlush(toSave);
            }
            fetchHistoricalStats(pesc.participant().getExternalId());
        }
    }

    private void fetchEventAndParticipants(List<StatScoreDataService.EventContext> eventContexts,
                                           Map<Integer, Participant> participantsToSave,
                                           Set<ParticipantEventSeasonCompetition> uniqueParticipantEventSeasonComp,
                                           Map<Integer, Event> eventsToSave,
                                           Map<Integer, Set<Participant>> eventParticipantsMap,
                                           boolean isHistoricalData) {

        Set<Integer> allParticipantIds = eventContexts.stream()
                .flatMap(ctx -> Optional.ofNullable(ctx.event().getParticipants()).orElse(List.of()).stream())
                .map(StatScoreEventParticipantDTO::getId)
                .collect(Collectors.toSet());
        Set<Integer> allEventIds = eventContexts.stream()
                .map(StatScoreDataService.EventContext::event)
                .map(StatScoreEventDTO::getId)
                .collect(Collectors.toSet());

        Map<Integer, Participant> existingParticipants = participantRepository
                .findByExternalIdIn(new ArrayList<>(allParticipantIds)).stream()
                .collect(Collectors.toMap(Participant::getExternalId, Function.identity()));
        Map<Integer, Event> existingEvents = eventRepository
                .findByExternalIdIn(new ArrayList<>(allEventIds)).stream()
                .collect(Collectors.toMap(Event::getExternalId, Function.identity()));

        for (StatScoreDataService.EventContext ctx : eventContexts) {
            StatScoreEventDTO eventDTO = ctx.event();
            StatScoreSeasonDTO seasonDTO = ctx.season();
            StatScoreCompetitionDTO competitionDTO = ctx.competition();

            Competition competition = competitionService.readByExternalId(competitionDTO.getId())
                    .orElseGet(() -> competitionService.create(competitionDTO));
            Venue venue = venueService.readByExternalId(ctx.event().getVenueId())
                    .orElseGet(null);


            Event event = existingEvents.get(eventDTO.getId());
            if (event == null) {
                event = eventMapper.toEntity(eventDTO, competition, venue);
                eventsToSave.put(eventDTO.getId(), event);
                existingEvents.put(eventDTO.getId(), event);
            } else if (!isHistoricalData) {
                eventMapper.updateEntity(event, eventDTO, competition, venue);
                eventsToSave.put(eventDTO.getId(), event);
            }

            List<StatScoreEventParticipantDTO> eventParticipantDTOs =
                    Optional.ofNullable(eventDTO.getParticipants()).orElse(List.of());

            Set<Participant> eventParticipants = eventParticipantsMap
                    .computeIfAbsent(eventDTO.getId(), k -> new HashSet<>());

            for (StatScoreEventParticipantDTO dto : eventParticipantDTOs) {
                Participant participant = existingParticipants.get(dto.getId());

                if (participant == null) {
                    participant = participantMapper.toEntity(dto, competition);
                    participantsToSave.put(dto.getId(), participant);
                    existingParticipants.put(dto.getId(), participant);
                } else if (!isHistoricalData) {
                    participantMapper.updateEntity(participant, dto, competition);
                    participantsToSave.put(dto.getId(), participant);
                }
                eventParticipants.add(participant);
                uniqueParticipantEventSeasonComp.add(new ParticipantEventSeasonCompetition(
                        participant, event, seasonDTO.getId(), competition, dto));
            }
        }
    }

    private void fetchHistoricalStats(int participantId) {
        Map<Integer, Participant> participantsToSave = new HashMap<>();
        Map<Integer, Event> eventsToSave = new HashMap<>();
        Set<ParticipantEventSeasonCompetition> uniqueParticipantEventSeasonComp = new HashSet<>();
        Map<Integer, Set<Participant>> eventParticipantsMap = new HashMap<>();
        List<StatScoreDataService.EventContext> historicalEvents = statScoreDataService.getAllEventsWithContext(
                EventQueryParams.builder()
                        .participantId(participantId)
                        .dateTo(LocalDateTime.now())
                        .limit(5)
                        .sortType("start_date")
                        .sortOrder("desc")
                        .build());
        fetchEventAndParticipants(historicalEvents, participantsToSave, uniqueParticipantEventSeasonComp, eventsToSave,
                eventParticipantsMap, true);
        List<Participant> participants = participantRepository.saveAllAndFlush(new ArrayList<>(participantsToSave.values()));

        Map<Integer, Participant> participantById = participants.stream()
                .collect(Collectors.toMap(
                        Participant::getExternalId,
                        Function.identity(),
                        (existing, duplicate) -> existing
                ));


        for (Event event : eventsToSave.values()) {
            Set<Participant> linkedParticipants = eventParticipantsMap.getOrDefault(event.getId(), Set.of());
            event.setParticipants(linkedParticipants.stream()
                    .map(p -> participantById.get(p.getExternalId()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
        }
        eventRepository.saveAllAndFlush(eventsToSave.values());

        //refetch all stats for historical events per participant
        statRepository.deleteByEventIdIn(uniqueParticipantEventSeasonComp.stream().map(u -> u.event().getId()).collect(Collectors.toList()));

        Map<Event, List<ParticipantEventSeasonCompetition>> byEvent =
                uniqueParticipantEventSeasonComp.stream()
                        .collect(Collectors.groupingBy(ParticipantEventSeasonCompetition::event));

        byEvent.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getKey().getStartDate()))
                .forEach(entry -> {
                    Event event = entry.getKey();
                    List<ParticipantEventSeasonCompetition> pescList = entry.getValue();
                    List<StatScoreSubParticipantDTO> delayedStatsToProcess = new ArrayList<>();

                    Set<EventStat> eventStats = new HashSet<>();
                    List<StatScoreParticipantDTO> statScoreParticipants =
                            statScoreProxyService.listEventParticipants(event.getExternalId()).getItems();
                    List<StatScoreSubParticipantDTO> statScoreSubParticipants =
                            statScoreProxyService.listEventSubParticipants(event.getExternalId()).getItems();
                    //need statScoreParticipants to map sub-participants to correct participant
                    //need subNameToParticipantExternalIdMap as sub-participant sports-API ids from listEventParticipants and listEventSubParticipants are different
                    Map<String, Integer> subNameToParticipantExternalIdMap = statScoreParticipants.stream()
                            .filter(p -> p.getSubparticipants() != null)
                            .flatMap(p -> p.getSubparticipants().stream()
                                    .map(sub -> Map.entry(normalizeName(sub.getName()), p.getId())))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a));

                    List<Integer> subParticipantExternalIds = statScoreSubParticipants.stream()
                            .map(StatScoreSubParticipantDTO::getId)
                            .collect(Collectors.toList());

                    Map<Integer, SubParticipant> existingSubsMap = subParticipantRepository
                            .findByExternalIdIn(subParticipantExternalIds).stream()
                            .collect(Collectors.toMap(SubParticipant::getExternalId, Function.identity()));

                    List<SubParticipant> toSave = new ArrayList<>();

                    for (StatScoreSubParticipantDTO dto : statScoreSubParticipants) {
                        SubParticipant existing = existingSubsMap.get(dto.getId());
                        Area area = areaService.readByExternalId(dto.getAreaId()).orElse(null);
                        Integer parentParticipantExternalId = subNameToParticipantExternalIdMap.get(normalizeName(dto.getName()));
                        ParticipantEventSeasonCompetition pesc = pescList.stream()
                                .filter(p -> p.participant.getExternalId().equals(parentParticipantExternalId))
                                .findFirst().orElse(null);
                        if (existing == null) {

                            SubParticipant sub = subParticipantMapper.toEntity(dto, area, pesc.participant, pesc.competition);
                            toSave.add(sub);
                            delayedStatsToProcess.add(dto);
                            existingSubsMap.put(dto.getId(), sub);
                        } else {
                            subParticipantMapper.updateEntity(existing, dto, area, pesc.participant, pesc.competition);
                            toSave.add(existing);
                            fillStats(eventStats, event, dto.getStats(), existing.getId(), StatTargetType.SUBPARTICIPANT, existing.getExternalId());
                        }
                    }

                    for (ParticipantEventSeasonCompetition pesc : pescList) {
                        fillStats(eventStats, event, pesc.participantDTO.getStats(), pesc.participant.getId(), StatTargetType.PARTICIPANT, pesc.participantDTO.getId());
                    }

                    subParticipantRepository.saveAllAndFlush(toSave);

                    for (StatScoreSubParticipantDTO dto : delayedStatsToProcess) {
                        SubParticipant sub = existingSubsMap.get(dto.getId());
                        fillStats(eventStats, event, dto.getStats(), sub.getId(), StatTargetType.SUBPARTICIPANT, sub.getExternalId());
                    }
                    statRepository.saveAllAndFlush(eventStats);
                });
    }

    private void fillStats(Set<EventStat> eventStats, Event event, List<StatScoreStatDTO> stats,
                           Long id, StatTargetType subparticipant, Integer targetExternalId) {
        for (StatScoreStatDTO statDTO : stats) {
            eventStats.add(eventStatMapper.toEntity(statDTO, id, subparticipant, event, targetExternalId));
        }
    }

    private String normalizeName(String name) {
        return name == null ? "" : name.trim().toLowerCase();
    }
}
