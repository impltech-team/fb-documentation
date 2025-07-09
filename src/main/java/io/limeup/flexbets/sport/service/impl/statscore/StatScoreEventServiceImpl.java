package io.limeup.flexbets.sport.service.impl.statscore;

import io.limeup.flexbets.sport.cache.EventBasedCache;
import io.limeup.flexbets.sport.dto.EventDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.mapper.EventMapper;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.enums.EventStatus;
import io.limeup.flexbets.sport.repository.EventRepository;
import io.limeup.flexbets.sport.repository.projection.EventRow;
import io.limeup.flexbets.sport.service.EventService;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import io.limeup.flexbets.sport.utils.ValidationUtils;
import jakarta.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class StatScoreEventServiceImpl implements EventService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("event_name", "event_date");
    private static final Set<String> SUPPORTED_STATUS_SORT_FIELDS = Set.of("SCHEDULED",
            "FINISHED",
            "LIVE",
            "CANCELLED",
            "INTERRUPTED",
            "DELETED",
            "OTHER"
    );

    private final EventRepository eventRepository;

    public StatScoreEventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    //    @EventBasedCache(cacheName = "eventsListCache",
//            key = "T(java.util.Objects).hash(#competitionId, #dateFrom, #dateTo, #venueIds, #participantIds, #status, #requestQuery.page, #requestQuery.pageSize, #requestQuery.sortOrder, #requestQuery.sortBy, #requestQuery.filter)")
    @Override
    public PaginatedResponse<EventDTO> listEvents(Integer competitionId, LocalDateTime dateFrom, LocalDateTime dateTo, List<Integer> venueIds
            , List<Integer> participantIds, String status, RequestQueryDTO requestQuery) {
        ValidationUtils.validateSortFieldsInRequest(requestQuery, SUPPORTED_SORT_FIELDS);
        if(status != null && !status.isBlank() &&  SUPPORTED_STATUS_SORT_FIELDS.stream()
                .noneMatch(s -> s.equalsIgnoreCase(status))) {
            throw new ValidationException(String.format("Invalid status: %s. Available options: %s", status, SUPPORTED_STATUS_SORT_FIELDS));
        }

        if (dateTo == null) {
            dateTo = LocalDateTime.now();
        }
        if (dateFrom == null) {
            dateFrom = dateTo.minusHours(24);
        }

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
                EventMapper.toDTO(eventRows), (long) eventRows.size(), requestQuery.getPage(), requestQuery.getPageSize());
    }

    //    @EventBasedCache(cacheName = "eventDetailsCache",
//            key = "#eventId")
    @Override
    public EventDTO getEventById(Integer eventId) {
        List<EventRow> eventRows = eventRepository.getEventDetails(eventId);

        if (eventRows.isEmpty()) {
            throw new FlexBetsSportNotFoundException(String.format("Event %s Not Found", eventId));
        }

        return EventMapper.toDTO(eventRows).getFirst();
    }

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
