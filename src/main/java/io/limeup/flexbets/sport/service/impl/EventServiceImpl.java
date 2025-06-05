package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.cache.EventBasedCache;
import io.limeup.flexbets.sport.dto.EventDTO;
import io.limeup.flexbets.sport.dto.FullEventDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.mapper.EventMapper;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.SubParticipant;
import io.limeup.flexbets.sport.model.Venue;
import io.limeup.flexbets.sport.model.enums.BetStatus;
import io.limeup.flexbets.sport.model.enums.EventStatus;
import io.limeup.flexbets.sport.repository.EventRepository;
import io.limeup.flexbets.sport.repository.SubParticipantRepository;
import io.limeup.flexbets.sport.repository.projection.BetRow;
import io.limeup.flexbets.sport.repository.projection.EventRow;
import io.limeup.flexbets.sport.service.BetService;
import io.limeup.flexbets.sport.service.EventService;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.VenueService;
import io.limeup.flexbets.sport.service.statscore.StatScoreClient;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import io.limeup.flexbets.sport.utils.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Service
public class EventServiceImpl extends ExternalIdReadServiceImpl<Event, EventDTO, Long> implements EventService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("event_name", "event_date");

    private final EventRepository eventRepository;

    private final StatScoreClient statScoreClient;

    private final VenueService venueService;

    private final BetService betService;

    private final SubParticipantRepository subParticipantRepository;

    protected EventServiceImpl(EventRepository repository, StatScoreClient statScoreClient,
                               VenueService venueService, BetService betService,
                               SubParticipantRepository subParticipantRepository) {
        super(repository);
        this.eventRepository = repository;
        this.statScoreClient = statScoreClient;
        this.venueService = venueService;
        this.betService = betService;
        this.subParticipantRepository = subParticipantRepository;
    }

    @EventBasedCache(cacheName = "eventsListCache",
            key = "T(java.util.Objects).hash(#competitionId, #dateFrom, #dateTo, #venueIds, #participantIds, #status, #requestQuery.page, #requestQuery.pageSize, #requestQuery.sortOrder, #requestQuery.sortBy, #requestQuery.filter)")
    @Override
    public PaginatedResponse<EventDTO> listEvents(Integer competitionId, LocalDateTime dateFrom, LocalDateTime dateTo, List<Integer> venueIds
            , List<Integer> participantIds, String status, RequestQueryDTO requestQuery) {
        ValidationUtils.validateSortFieldsInRequest(requestQuery, SUPPORTED_SORT_FIELDS);

        long count = eventRepository.countEvents(
                competitionId,
                dateFrom,
                dateTo,
                status,
                venueIds == null ? Collections.emptyList() : venueIds,
                participantIds == null ? Collections.emptyList() : participantIds);

        if (count == 0) {
            return PaginationUtils.buildPaginatedResponse(null, count, requestQuery.getPage(), requestQuery.getPageSize());
        }

        List<EventRow> eventRows = eventRepository.listEvents(
                competitionId,
                dateFrom,
                dateTo,
                status,
                venueIds == null ? Collections.emptyList() : venueIds,
                participantIds == null ? Collections.emptyList() : participantIds,
                requestQuery.getSortBy(),
                requestQuery.getSortOrder(),
                requestQuery.getPageSize(),
                (requestQuery.getPage() - 1) * requestQuery.getPageSize());

        return PaginationUtils.buildPaginatedResponse(
                EventMapper.toDTO(eventRows), count, requestQuery.getPage(), requestQuery.getPageSize());
    }

    @EventBasedCache(cacheName = "eventDetailsCache",
            key = "#eventId")
    @Override
    public FullEventDTO getEventById(Integer eventId) {
        StatScoreCompetitionDTO eventCompetition = statScoreClient.getEventById(eventId, false).block()
                .getApi().getData();
        Venue venue = venueService.readByExternalId(eventCompetition.getSeason().getStage().getGroup().getEvent().getVenueId()).orElse(null);
        Map<String, Integer> subParticipantNameIdMap = subParticipantRepository.findAll().stream()
                .filter(subParticipant -> subParticipant.getPlayerShortName() != null)
                .collect(Collectors.toMap(SubParticipant::getPlayerShortName, SubParticipant::getExternalId));

        Map<Integer, List<BetRow>> marketBetRowMap = betService.getBetsByExternalIdInAndBetStatus(List.of(eventId), BetStatus.OPEN)
                .stream()
                .collect(Collectors.groupingBy(BetRow::getMarketExternalId));

        return EventMapper.mapToFullEventDTO(eventCompetition, venue, marketBetRowMap, subParticipantNameIdMap);
    }

    @Override
    public void updateEventByIdOrByName(Integer eventDataId, String name, Long lsId, String status, LocalDateTime startDate) {
        Event event = eventRepository.findByName(name)
                .orElseGet(() -> eventRepository.findByExternalId(eventDataId).orElse(null));
        if (event != null) {
            updateEvent(lsId, status, startDate, event);
        }
    }

    private void updateEvent(Long lsId, String status, LocalDateTime startDate, Event event) {
        boolean needUpdate = false;
        if (!StringUtils.isEmpty(status) && !event.getStatus().name().equalsIgnoreCase(status)) {
            event.setStatus(EventStatus.valueOf(status.toUpperCase()));
            event.setStartDate(startDate);
            needUpdate = true;
        }

        if (lsId != null && !lsId.equals(event.getLsId())) {
            event.setLsId(lsId);
            needUpdate = true;
        }

        if (needUpdate) {
            eventRepository.save(event);
        }
    }
}
