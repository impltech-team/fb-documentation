package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.cache.EventBasedCache;
import io.limeup.flexbets.sport.dto.EventDTO;
import io.limeup.flexbets.sport.dto.FullEventDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.mapper.EventMapper;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.Venue;
import io.limeup.flexbets.sport.repository.EventRepository;
import io.limeup.flexbets.sport.repository.projection.EventRow;
import io.limeup.flexbets.sport.service.ExternalIdReadServiceImpl;
import io.limeup.flexbets.sport.service.EventService;
import io.limeup.flexbets.sport.service.VenueService;
import io.limeup.flexbets.sport.service.statscore.StatScoreClient;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Transactional
@Service
public class EventServiceImpl extends ExternalIdReadServiceImpl<Event, EventDTO, Long> implements EventService {

    private final EventRepository eventRepository;

    private final StatScoreClient statScoreClient;

    private final VenueService venueService;

    protected EventServiceImpl(EventRepository repository, StatScoreClient statScoreClient,
                               VenueService venueService) {
        super(repository);
        this.eventRepository = repository;
        this.statScoreClient = statScoreClient;
        this.venueService = venueService;
    }

    @EventBasedCache(cacheName = "eventsListCache",
            key = "T(java.util.Objects).hash(#competitionId, #dateFrom, #dateTo, #venueIds, #participantIds, #status, #requestQuery.page, #requestQuery.pageSize, #requestQuery.sortOrder, #requestQuery.sortBy)")
    @Override
    public PaginatedResponse<EventDTO> listEvents(Integer competitionId, LocalDateTime dateFrom, LocalDateTime dateTo, List<Integer> venueIds
            , List<Integer> participantIds, String status, RequestQueryDTO requestQuery) {
        long count = eventRepository.countEvents(
                competitionId,
                dateFrom,
                dateTo,
                status,
                venueIds == null ? Collections.emptyList() : venueIds,
                participantIds == null ? Collections.emptyList() : participantIds);
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
        return EventMapper.mapToFullEventDTO(eventCompetition, venue);
    }
}
