package io.limeup.flexbets.sport.service.impl.sportsdataio;

import io.limeup.flexbets.sport.cache.EventBasedCache;
import io.limeup.flexbets.sport.dto.*;
import io.limeup.flexbets.sport.model.IoEvent;
import io.limeup.flexbets.sport.model.IoTeam;
import io.limeup.flexbets.sport.repository.sportsdataio.IoEventRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoTeamRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoVenueRepository;
import io.limeup.flexbets.sport.service.EventService;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import io.limeup.flexbets.sport.utils.ValidationUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SportsDataIoEventServiceImpl implements EventService {
    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("event_name", "event_date");
    private static final int COMPETITION_ID = 5466;
    private static final String COMPETITION_NAME = "MLB";

    private final IoEventRepository eventRepository;
    private final IoVenueRepository venueRepository;
    private final IoTeamRepository teamRepository;

    public SportsDataIoEventServiceImpl(IoEventRepository eventRepository,
                                        IoVenueRepository venueRepository,
                                        IoTeamRepository teamRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.teamRepository = teamRepository;
    }
//    @EventBasedCache(cacheName = "eventsListCache",
//            key = "T(java.util.Objects).hash(#competitionId, #dateFrom, #dateTo, #venueIds, #participantIds, #status, #requestQuery.page, #requestQuery.pageSize, #requestQuery.sortOrder, #requestQuery.sortBy, #requestQuery.filter)")

    @Override
    public PaginatedResponse<EventDTO> listEvents(Integer competitionId, LocalDateTime dateFrom, LocalDateTime dateTo,
                                                  List<Integer> venueIds, List<Integer> participantIds, String status,
                                                  RequestQueryDTO requestQuery) {
        ValidationUtils.validateSortFieldsInRequest(requestQuery, SUPPORTED_SORT_FIELDS);

        if (dateTo == null) {
            dateTo = LocalDateTime.now();
        }
        if (dateFrom == null) {
            dateFrom = dateTo.minusHours(24);
        }

        List<Integer> venueIdsSafe = (venueIds == null || venueIds.isEmpty()) ? new ArrayList<>() : venueIds;
        List<Integer> participantIdsSafe = (participantIds == null || participantIds.isEmpty()) ? new ArrayList<>()  : participantIds;


        List<IoEvent> events = eventRepository.listEvents(
                dateFrom,
                dateTo,
                status,
                 venueIdsSafe,
                 participantIdsSafe,
                requestQuery.getSortBy(),
                requestQuery.getSortOrder(),
                requestQuery.getPageSize(),
                (requestQuery.getPage() - 1) * requestQuery.getPageSize()
        );

        List<EventDTO> dtoList = events.stream().map(this::mapToDto).collect(Collectors.toList());

        return PaginationUtils.buildPaginatedResponse(dtoList, (long) dtoList.size(), requestQuery.getPage(), requestQuery.getPageSize());
    }

//    @EventBasedCache(cacheName = "eventDetailsCache",
//            key = "#eventId")
    @Override
    public EventDTO getEventById(Integer eventId) {
        IoEvent event = eventRepository.findByGameId(eventId.longValue())
                .orElseThrow(() -> new RuntimeException("Event %s not found".formatted(eventId)));
        return mapToDto(event);
    }

    private EventDTO mapToDto(IoEvent event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getGameId() != null ? event.getGameId().intValue() : 0);
        dto.setEventName(event.getHomeTeam() + " vs " + event.getAwayTeam());
        dto.setEventDate(event.getDatetimeUtc());
        dto.setStatus(event.getStatus());
        dto.setCompetitionId(COMPETITION_ID);
        dto.setCompetition(COMPETITION_NAME);
        dto.setParticipants(List.of(
                buildParticipantSummary(event.getHomeTeamId(), event.getHomeTeam()),
                buildParticipantSummary(event.getAwayTeamId(), event.getAwayTeam())
        ));
        if (event.getStadiumId() != null) {
            venueRepository.findByStadiumId(event.getStadiumId()).ifPresent(v -> dto.setVenue(
                    VenueDTO.builder()
                            .venueId(v.getStadiumId())
                            .venueName(v.getName())
                            .location(v.getCity())
                            .build()
            ));
        }
        return dto;
    }
    private ParticipantSummaryDTO buildParticipantSummary(Integer teamId, String teamName) {
        String acronym = null;
        if (teamId != null) {
            acronym = teamRepository.findByTeamId(teamId.longValue())
                    .map(IoTeam::getKey)
                    .orElse(null);
        }
        return new ParticipantSummaryDTO(teamId != null ? teamId : 0, teamName, acronym);
    }

}
