package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSeasonDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.mapper.EventMapper;
import io.limeup.flexbets.sport.mapper.ParticipantMapper;
import io.limeup.flexbets.sport.mapper.SubParticipantMapper;
import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.EventStat;
import io.limeup.flexbets.sport.model.Participant;
import io.limeup.flexbets.sport.model.SubParticipant;
import io.limeup.flexbets.sport.model.Venue;
import io.limeup.flexbets.sport.repository.EventRepository;
import io.limeup.flexbets.sport.repository.ParticipantRepository;
import io.limeup.flexbets.sport.repository.StatRepository;
import io.limeup.flexbets.sport.repository.SubParticipantRepository;
import io.limeup.flexbets.sport.service.AbstractReadService;
import io.limeup.flexbets.sport.service.AreaService;
import io.limeup.flexbets.sport.service.CompetitionService;
import io.limeup.flexbets.sport.service.StatsService;
import io.limeup.flexbets.sport.dto.StatsBatchRequestDTO;
import io.limeup.flexbets.sport.dto.StatsResponseDTO;
import io.limeup.flexbets.sport.service.VenueService;
import io.limeup.flexbets.sport.service.statscore.StatScoreDataService;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class StatsServiceImpl extends AbstractReadService<EventStat, StatsResponseDTO, Long> implements StatsService {

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

    protected StatsServiceImpl(StatRepository repository, StatScoreDataService statScoreDataService,
                               StatScoreProxyService statScoreProxyService, ParticipantMapper participantMapper,
                               ParticipantRepository participantRepository, CompetitionService competitionService,
                               VenueService venueService, SubParticipantMapper subParticipantMapper, AreaService areaService,
                               SubParticipantRepository subParticipantRepository, EventMapper eventMapper, EventRepository eventRepository) {
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
    }

    @Override
    public List<StatsResponseDTO> listBatchStats(StatsBatchRequestDTO request) {
        return null;
    }

    record ParticipantEventSeasonCompetition(int participantId, int eventId, int seasonId, Competition competition) {}

    @Transactional
    @Override
    public void fetchStatData(int durationDays) {
        List<StatScoreDataService.EventContext> eventContexts = statScoreDataService.getAllEventsWithContext(EventQueryParams
                .builder()
                .limit(150)
                .dateFrom(LocalDateTime.now())
                .dateTo(LocalDateTime.now().plusDays(durationDays))
                .build());

        Set<Participant> participantsToSave = new HashSet<>();
        Set<ParticipantEventSeasonCompetition> uniqueParticipantEventSeasonComp = new HashSet<>();
        Set<Event> eventsToSave = new HashSet<>();
        Map<Long, Set<Participant>> eventParticipantsMap = new HashMap<>();

        for (StatScoreDataService.EventContext ctx : eventContexts) {
            StatScoreEventDTO event = ctx.event();
            StatScoreSeasonDTO season = ctx.season();
            StatScoreCompetitionDTO competition = ctx.competition();

            Competition competitionEntity = competitionService.readById((long) competition.getId())
                    .orElseGet(() -> competitionService.create(competition));
            Venue venueEntity = venueService.readById((long) ctx.event().getVenueId())
                    .orElseGet(null);


            Event eventEntity = eventMapper.toEntity(event, competitionEntity, venueEntity);
            eventsToSave.add(eventEntity);

            List<StatScoreEventParticipantDTO> eventParticipantDTOs =
                    Optional.ofNullable(event.getParticipants()).orElse(List.of());

            Set<Participant> eventParticipants = eventParticipantsMap.computeIfAbsent(
                    (long) event.getId(), k -> new HashSet<>());

            for (StatScoreEventParticipantDTO p : eventParticipantDTOs) {
                Participant participant = participantMapper.toEntity(p, competitionEntity);
                participantsToSave.add(participant);
                eventParticipants.add(participant);
                uniqueParticipantEventSeasonComp.add(new ParticipantEventSeasonCompetition(p.getId(),
                        event.getId(), season.getId(), competitionEntity));
            }
        }

        List<Participant> participants = participantRepository.saveOrUpdateBatch(participantsToSave);

        Map<Long, Participant> participantById = participants.stream()
                .collect(Collectors.toMap(Participant::getId, Function.identity()));

        for (Event event : eventsToSave) {
            Set<Participant> linkedParticipants = eventParticipantsMap.getOrDefault(event.getId(), Set.of());
            event.setParticipants(linkedParticipants.stream()
                    .map(p -> participantById.get(p.getId()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
        }
        eventRepository.saveOrUpdateBatch(eventsToSave);

        for (ParticipantEventSeasonCompetition pesc : uniqueParticipantEventSeasonComp) {
            List<StatScoreSubParticipantDTO> statScoreSubParticipants =
                    statScoreProxyService.listSquadSubParticipants(pesc.participantId(), pesc.seasonId()).getItems();

            Participant participant = participantById.get((long) pesc.participantId());
            if (participant == null) continue;

            List<SubParticipant> subParticipantsToSave = new ArrayList<>();

            for (StatScoreSubParticipantDTO dto : statScoreSubParticipants) {
                Area area = areaService.readById((long) dto.getAreaId()).orElse(null);
                SubParticipant sub = subParticipantMapper.toEntity(dto, area, participant, pesc.competition);
                subParticipantsToSave.add(sub);
            }

            subParticipantRepository.saveOrUpdateBatch(subParticipantsToSave);
            //fetchHistoricalStats(subParticipantsToSave, pesc.participantId());
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
            List<StatScoreSubParticipantDTO> items = statScoreProxyService.listEventSubParticipants(event.getId()).getItems();
        }
    }
}
