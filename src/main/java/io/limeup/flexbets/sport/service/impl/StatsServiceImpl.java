package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreResultDTO;
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
import io.limeup.flexbets.sport.model.EventSubParticipant;
import io.limeup.flexbets.sport.model.Participant;
import io.limeup.flexbets.sport.model.enums.StatTargetType;
import io.limeup.flexbets.sport.model.SubParticipant;
import io.limeup.flexbets.sport.model.Venue;
import io.limeup.flexbets.sport.repository.EventRepository;
import io.limeup.flexbets.sport.repository.EventSubParticipantRepository;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class StatsServiceImpl extends ExternalIdReadServiceImpl<EventStat, StatsResponseDTO, Long>
        implements StatsService {

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

    private final EventSubParticipantRepository eventSubParticipantRepository;

    protected StatsServiceImpl(StatRepository repository, StatScoreDataService statScoreDataService,
            StatScoreProxyService statScoreProxyService, ParticipantMapper participantMapper,
            ParticipantRepository participantRepository, CompetitionService competitionService,
            VenueService venueService, SubParticipantMapper subParticipantMapper, AreaService areaService,
            SubParticipantRepository subParticipantRepository, EventMapper eventMapper,
            EventRepository eventRepository, EventStatMapper eventStatMapper, StatRepository statRepository,
            EventSubParticipantRepository eventSubParticipantRepository) {
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
        this.eventSubParticipantRepository = eventSubParticipantRepository;
    }

    @Override
    public List<StatsResponseDTO> listBatchStats(StatsBatchRequestDTO request) {
        return null;
    }

    record ParticipantEventSeasonCompetition(Participant participant, Event event, int seasonId,
            Competition competition, StatScoreEventParticipantDTO participantDTO) {
    }

    @Override
    public void fetchStatDataForCompetitionAndDate(Integer competitionId, LocalDate prefetchDate) {
        log.info("[START] fetchStatDataForCompetitionAndDate: competitionId={}, prefetchDate={}", competitionId,
                prefetchDate);
        List<StatScoreDataService.EventContext> eventContexts;
        try {
            log.info(
                    "Calling statScoreDataService.getAllEventsWithContext for competitionId={}, dateFrom={}, dateTo={}",
                    competitionId, prefetchDate.atStartOfDay(), prefetchDate.atStartOfDay().plusDays(1));
            eventContexts = statScoreDataService.getAllEventsWithContext(EventQueryParams
                    .builder()
                    .limit(150)
                    .dateFrom(prefetchDate.atStartOfDay())
                    .dateTo(prefetchDate.atStartOfDay().plusDays(1))
                    .competitionId(competitionId)
                    .build());
            log.info("Received {} event contexts from StatScoreDataService", eventContexts.size());
        } catch (Exception e) {
            log.error("Error fetching events from StatScoreDataService", e);
            throw e;
        }

        Map<Integer, Participant> participantsToSave = new HashMap<>();
        Map<Integer, Event> eventsToSave = new HashMap<>();
        Map<Integer, StatScoreSeasonDTO> eventIdToSeason = new HashMap<>();
        Map<Integer, StatScoreEventDTO> eventIdToEventDTO = new HashMap<>();

        Set<ParticipantEventSeasonCompetition> uniqueParticipantEventSeasonComp = new HashSet<>();

        log.info("Extracting event and participant data");
        extractEventParticipantsData(eventContexts, participantsToSave, uniqueParticipantEventSeasonComp, eventsToSave,
                false, eventIdToSeason, eventIdToEventDTO);
        log.info("Saving {} participants to DB", participantsToSave.size());
        try {
            participantRepository.saveAllAndFlush(participantsToSave.values());
            log.info("Saved participants to DB");
        } catch (Exception e) {
            log.error("Error saving participants to DB", e);
            throw e;
        }
        try {
            eventRepository.saveAllAndFlush(eventsToSave.values());
        } catch (Exception e) {
            log.error("Error saving eventRepository.saveAllAndFlush", e);
            throw e;
        }

        for (ParticipantEventSeasonCompetition pesc : uniqueParticipantEventSeasonComp) {
            log.info("Processing squad sub-participants for participantId={}, eventId={}",
                    pesc.participant().getExternalId(), pesc.event().getExternalId());
            processSquadSubParticipants(pesc);
            log.info("Fetching historical stats for participantId={}", pesc.participant().getExternalId());
            fetchHistoricalStats(pesc.participant().getExternalId());
        }
        log.info("[END] fetchStatDataForCompetitionAndDate: competitionId={}, prefetchDate={}", competitionId,
                prefetchDate);
    }

    private void processSquadSubParticipants(ParticipantEventSeasonCompetition pesc) {
        log.info("[START] processSquadSubParticipants: participantId={}, eventId={}",
                pesc.participant().getExternalId(), pesc.event().getExternalId());
        Participant participant = pesc.participant();
        if (participant == null)
            return;

        try {
            List<StatScoreSubParticipantDTO> subParticipantDTOs = statScoreProxyService
                    .listSquadSubParticipants(participant.getExternalId(), pesc.seasonId(), true)
                    .getItems().stream()
                    .filter(dto -> dto.getTeamConnection().equals("current"))
                    .peek(dto -> dto.setTeamId(participant.getExternalId()))
                    .collect(Collectors.toList());
            log.info("Fetched {} squad sub-participants from StatScoreProxyService", subParticipantDTOs.size());

            List<SubParticipant> existingSubs = subParticipantRepository
                    .findByExternalIdIn(subParticipantDTOs.stream().map(StatScoreSubParticipantDTO::getId).toList());
            Map<Integer, SubParticipant> existingSubsMap = existingSubs.stream()
                    .collect(Collectors.toMap(SubParticipant::getExternalId, Function.identity()));

            List<SubParticipant> toSave = syncSubParticipants(subParticipantDTOs, existingSubsMap, pesc.competition);

            subParticipantRepository.saveAllAndFlush(toSave);
            log.info("Saved {} sub-participants to DB", toSave.size());

            fillEventSubParticipants(subParticipantDTOs, existingSubsMap, null, Collections.singletonList(pesc), false);
        } catch (Exception e) {
            log.error("Error processing squad sub-participants", e);
            throw e;
        }
        log.info("[END] processSquadSubParticipants");
    }

    private List<SubParticipant> syncSubParticipants(List<StatScoreSubParticipantDTO> dtos,
            Map<Integer, SubParticipant> existingSubsMap,
            Competition competition) {
        List<SubParticipant> toSave = new ArrayList<>();
        for (StatScoreSubParticipantDTO dto : dtos) {
            Area area = areaService.readByExternalId(dto.getAreaId()).orElse(null);
            SubParticipant existing = existingSubsMap.get(dto.getId());

            if (existing == null) {
                SubParticipant sub = subParticipantMapper.toEntity(dto, area, competition);
                existingSubsMap.put(dto.getId(), sub);
                toSave.add(sub);
            } else {
                subParticipantMapper.updateEntity(existing, dto, area, competition);
                toSave.add(existing);
            }
        }
        return toSave;
    }

    private void extractEventParticipantsData(List<StatScoreDataService.EventContext> eventContexts,
            Map<Integer, Participant> participantsToSave,
            Set<ParticipantEventSeasonCompetition> uniqueParticipantEventSeasonComp,
            Map<Integer, Event> eventsToSave,
            boolean isHistoricalData,
            Map<Integer, StatScoreSeasonDTO> eventIdToSeason,
            Map<Integer, StatScoreEventDTO> eventIdToEventDTO) {
        log.info("[START] extractEventParticipantsData: eventContexts.size={}, isHistoricalData={}",
                eventContexts.size(), isHistoricalData);
        Set<Integer> allParticipantIds = eventContexts.stream()
                .flatMap(ctx -> Optional.ofNullable(ctx.event().getParticipants()).orElse(List.of()).stream())
                .map(StatScoreEventParticipantDTO::getId)
                .collect(Collectors.toSet());
        Set<Integer> allEventIds = eventContexts.stream()
                .map(StatScoreDataService.EventContext::event)
                .map(StatScoreEventDTO::getId)
                .collect(Collectors.toSet());

        Map<Integer, Participant> existingParticipants = null;
        Map<Integer, Event> existingEvents = null;
        try {
            existingParticipants = participantRepository
                    .findByExternalIdIn(new ArrayList<>(allParticipantIds)).stream()
                    .collect(Collectors.toMap(Participant::getExternalId, Function.identity()));
            log.info("Loaded {} existing participants from DB", existingParticipants.size());
            existingEvents = eventRepository
                    .findByExternalIdIn(new ArrayList<>(allEventIds)).stream()
                    .collect(Collectors.toMap(Event::getExternalId, Function.identity()));
            log.info("Loaded {} existing events from DB", existingEvents.size());
        } catch (Exception e) {
            log.error("Error loading existing participants/events from DB", e);
            throw e;
        }

        for (StatScoreDataService.EventContext ctx : eventContexts) {
            StatScoreEventDTO eventDTO = ctx.event();
            StatScoreSeasonDTO seasonDTO = ctx.season();
            StatScoreCompetitionDTO competitionDTO = ctx.competition();

            Competition competition = competitionService.readByExternalId(competitionDTO.getId())
                    .orElseGet(() -> competitionService.create(competitionDTO));
            Venue venue = venueService.readByExternalId(ctx.event().getVenueId()).orElse(null);

            Event event = existingEvents.get(eventDTO.getId());
            eventIdToEventDTO.put(eventDTO.getId(), eventDTO);

            if (event == null) {
                event = eventMapper.toEntity(eventDTO, competition, venue, ctx.season());
                eventsToSave.put(eventDTO.getId(), event);
                existingEvents.put(eventDTO.getId(), event);
            } else if (!isHistoricalData) {
                eventMapper.updateEntity(event, eventDTO, competition, venue, ctx.season());
                eventsToSave.put(eventDTO.getId(), event);
            }

            List<StatScoreEventParticipantDTO> eventParticipantDTOs = Optional.ofNullable(eventDTO.getParticipants())
                    .orElse(List.of());

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
                uniqueParticipantEventSeasonComp.add(new ParticipantEventSeasonCompetition(
                        participant, event, seasonDTO.getId(), competition, dto));
            }

            eventIdToSeason.put(eventDTO.getId(), seasonDTO); // <-- add this
        }
        log.info("[END] extractEventParticipantsData");
    }

    private void fetchHistoricalStats(int participantId) {
        log.info("[START] fetchHistoricalStats: participantId={}", participantId);
        Map<Integer, Participant> participantsToSave = new HashMap<>();
        Map<Integer, Event> eventsToSave = new HashMap<>();
        Map<Integer, StatScoreSeasonDTO> eventIdToSeason = new HashMap<>();
        Map<Integer, StatScoreEventDTO> eventIdToEventDTO = new HashMap<>();

        Set<ParticipantEventSeasonCompetition> uniqueParticipantEventSeasonComp = new HashSet<>();

        try {
            List<StatScoreDataService.EventContext> historicalEvents = statScoreDataService.getAllEventsWithContext(
                    EventQueryParams.builder()
                            .participantId(participantId)
                            .dateTo(LocalDateTime.now())
                            .limit(5)
                            .sortType("start_date")
                            .sortOrder("desc")
                            .build());
            log.info("Fetched {} historical event contexts for participantId={}", historicalEvents.size(),
                    participantId);
            extractEventParticipantsData(historicalEvents, participantsToSave, uniqueParticipantEventSeasonComp,
                    eventsToSave, true, eventIdToSeason, eventIdToEventDTO);
            participantRepository.saveAllAndFlush(new ArrayList<>(participantsToSave.values()));
            log.info("Saved {} historical participants to DB", participantsToSave.size());
            for (Event event : eventsToSave.values()) {
                try {
                    Optional<Event> existing = eventRepository.findByExternalId(event.getExternalId());
                    StatScoreSeasonDTO seasonDTO = eventIdToSeason.get(event.getExternalId());
                    StatScoreEventDTO eventDTO = eventIdToEventDTO.get(event.getExternalId());
                    if (existing.isPresent()) {
                        log.info("Updating existing event in DB: id={}", event.getId());

                        eventMapper.updateEntity(existing.get(), eventDTO, event.getCompetition(), event.getVenue(),
                                seasonDTO);
                        eventRepository.save(existing.get());
                    } else {
                        log.info("Saving new event to DB: id={}", event.getId());
                        eventRepository.save(event);
                    }
                } catch (Exception e) {
                    log.error("Error saving event to DB: id={}", event.getId(), e);
                    throw e;
                }
            }
            log.info("Saved {} historical events to DB", eventsToSave.size());
            statRepository.deleteByEventIdIn(uniqueParticipantEventSeasonComp.stream()
                    .map(u -> u.event().getId())
                    .collect(Collectors.toList()));
            log.info("Deleted old stats for {} events", uniqueParticipantEventSeasonComp.size());
            Map<Event, List<ParticipantEventSeasonCompetition>> byEvent = uniqueParticipantEventSeasonComp.stream()
                    .collect(Collectors.groupingBy(ParticipantEventSeasonCompetition::event));
            byEvent.entrySet().stream()
                    .sorted(Comparator.comparing(e -> e.getKey().getStartDate()))
                    .forEach(entry -> processHistoricalStatsPerEvent(entry.getValue()));
        } catch (Exception e) {
            log.error("Error fetching historical stats for participantId={}", participantId, e);
            throw e;
        }
        log.info("[END] fetchHistoricalStats: participantId={}", participantId);
    }

    private void processHistoricalStatsPerEvent(List<ParticipantEventSeasonCompetition> pescList) {
        log.info("[START] processHistoricalStatsPerEvent: eventId={}", pescList.getFirst().event.getId());
        Event event = pescList.getFirst().event;
        Set<EventStat> eventStats = new HashSet<>();
        List<SubParticipant> toSave;

        try {
            List<StatScoreSubParticipantDTO> statScoreSubParticipants = statScoreProxyService
                    .listEventSubParticipants(event.getExternalId(), true).getItems();
            log.info("Fetched {} event sub-participants from StatScoreProxyService", statScoreSubParticipants.size());
            List<Integer> subParticipantExternalIds = statScoreSubParticipants.stream()
                    .map(StatScoreSubParticipantDTO::getId)
                    .collect(Collectors.toList());

            Map<Integer, SubParticipant> existingSubsMap = subParticipantRepository
                    .findByExternalIdIn(subParticipantExternalIds).stream()
                    .collect(Collectors.toMap(SubParticipant::getExternalId, Function.identity()));
            toSave = syncSubParticipants(statScoreSubParticipants, existingSubsMap, pescList.get(0).competition);
            subParticipantRepository.saveAllAndFlush(toSave);
            log.info("Saved {} event sub-participants to DB", toSave.size());

            fillEventSubParticipants(statScoreSubParticipants, existingSubsMap, eventStats, pescList, true);

            for (ParticipantEventSeasonCompetition pesc : pescList) {
                fillParticipantStats(eventStats, event, pesc.participantDTO.getStats(),
                        pesc.participant.getId(), StatTargetType.PARTICIPANT, pesc.participantDTO.getId(),
                        pescList.get(0).participantDTO.getResults());
            }
            statRepository.saveAllAndFlush(eventStats);
            log.info("Saved {} event stats to DB", eventStats.size());
        } catch (Exception e) {
            log.error("Error processing historical stats per event", e);
            throw e;
        }
        log.info("[END] processHistoricalStatsPerEvent: eventId={}", pescList.getFirst().event.getId());
    }

    private void fillEventSubParticipants(Collection<StatScoreSubParticipantDTO> subParticipantsDTO,
            Map<Integer, SubParticipant> existingSubsMap,
            Set<EventStat> eventStats,
            List<ParticipantEventSeasonCompetition> pescList,
            boolean isHistoricalData) {
        log.info("[START] fillEventSubParticipants: eventId={}, subParticipantsCount={}, isHistoricalData={}",
                pescList.getFirst().event.getId(), subParticipantsDTO.size(), isHistoricalData);
        List<Integer> subParticipantIds = subParticipantsDTO.stream()
                .map(StatScoreSubParticipantDTO::getId)
                .toList();
        Event event = pescList.getFirst().event;

        List<EventSubParticipant> existingESPs = eventSubParticipantRepository
                .findExistingByEventAndSubParticipantIds(event.getExternalId(), subParticipantIds);

        Map<String, EventSubParticipant> existingMap = existingESPs.stream()
                .collect(Collectors.toMap(
                        esp -> key(esp.getEvent().getExternalId(), esp.getParticipant().getExternalId(),
                                esp.getSubParticipant().getExternalId()),
                        Function.identity()));

        List<EventSubParticipant> toSave = new ArrayList<>();

        for (StatScoreSubParticipantDTO dto : subParticipantsDTO) {
            SubParticipant sub = existingSubsMap.get(dto.getId());
            if (sub == null)
                continue;

            Integer teamId = dto.getTeamId();
            ParticipantEventSeasonCompetition participantEventSeasonCompetition = pescList.stream()
                    .filter(p -> p.participant.getExternalId().equals(teamId))
                    .findFirst()
                    .orElseThrow(NullPointerException::new);
            Participant resolvedParticipant = participantEventSeasonCompetition.participant;
            String mapKey = key(event.getExternalId(), resolvedParticipant.getExternalId(), sub.getExternalId());

            EventSubParticipant esp = existingMap.get(mapKey);
            if (esp != null) {
                esp.setPosition(dto.getPosition());
                esp.setPositionReason(dto.getPositionReason());
            } else {
                esp = new EventSubParticipant();
                esp.setHomeTeam(participantEventSeasonCompetition.participantDTO.getCounter() == 1);
                esp.setEvent(event);
                esp.setParticipant(resolvedParticipant);
                esp.setSubParticipant(sub);
                esp.setPosition(dto.getPosition());
                esp.setPositionReason(dto.getPositionReason());
            }

            toSave.add(esp);

            if (isHistoricalData) {
                fillStats(eventStats, event, dto.getStats(), sub.getId(), StatTargetType.SUBPARTICIPANT,
                        sub.getExternalId());
            }
        }

        try {
            eventSubParticipantRepository.saveAllAndFlush(toSave);
            log.info("Saved {} event sub-participants to DB", toSave.size());
        } catch (Exception e) {
            log.error("Error saving event sub-participants to DB", e);
            throw e;
        }
        log.info("[END] fillEventSubParticipants: eventId={}", pescList.getFirst().event.getId());
    }

    private String key(Integer eventId, Integer participantId, Integer subId) {
        return eventId + "-" + participantId + "-" + subId;
    }

    private void fillParticipantStats(Set<EventStat> eventStats, Event event, List<StatScoreStatDTO> stats,
            Long id, StatTargetType type, Integer targetExternalId, List<StatScoreResultDTO> results) {
        if (results != null && !results.isEmpty()) {
            results.stream()
                    .filter(r -> r.getName().equalsIgnoreCase("Result"))
                    .forEach(result -> eventStats
                            .add(eventStatMapper.toEntity(result, id, type, event, targetExternalId)));
        }
        fillStats(eventStats, event, stats, id, type, targetExternalId);
    }

    private void fillStats(Set<EventStat> eventStats, Event event, List<StatScoreStatDTO> stats,
            Long id, StatTargetType type, Integer targetExternalId) {
        for (StatScoreStatDTO statDTO : stats) {
            eventStats.add(eventStatMapper.toEntity(statDTO, id, type, event, targetExternalId));
        }
    }

}
