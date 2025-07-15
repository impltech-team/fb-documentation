package io.limeup.flexbets.sport.service.impl.sportsdataio;

import io.limeup.flexbets.sport.dto.*;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.model.IoBet;
import io.limeup.flexbets.sport.model.IoEvent;
import io.limeup.flexbets.sport.model.IoTeam;
import io.limeup.flexbets.sport.repository.sportsdataio.*;
import io.limeup.flexbets.sport.service.EventService;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import io.limeup.flexbets.sport.utils.ValidationUtils;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventServiceIoMlbImpl implements EventService {
    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("event_name", "event_date");
    private static final Set<String> SUPPORTED_STATUS_SORT_FIELDS = Set.of("Final", "Scheduled", "InProgress", "Delayed", "Postponed");

    private static final int COMPETITION_ID = 5466;
    private static final String COMPETITION_NAME = "MLB";

    private final IoEventRepository eventRepository;
    private final IoVenueRepository venueRepository;
    private final IoTeamRepository teamRepository;
    private final IoBetRepository betRepository;

    private final IoBetOutcomeRepository ioBetOutcomeRepository;

    public EventServiceIoMlbImpl(IoEventRepository eventRepository,
                                 IoVenueRepository venueRepository,
                                 IoTeamRepository teamRepository, IoBetRepository betRepository, IoBetOutcomeRepository ioBetOutcomeRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.teamRepository = teamRepository;
        this.betRepository = betRepository;
        this.ioBetOutcomeRepository = ioBetOutcomeRepository;
    }
//    @EventBasedCache(cacheName = "eventsListCache",
//            key = "T(java.util.Objects).hash(#competitionId, #dateFrom, #dateTo, #venueIds, #participantIds, #status, #requestQuery.page, #requestQuery.pageSize, #requestQuery.sortOrder, #requestQuery.sortBy, #requestQuery.filter)")

    @Override
    public PaginatedResponse<EventDTO> listEvents(Integer competitionId, LocalDateTime dateFrom, LocalDateTime dateTo,
                                                  List<Integer> venueIds, List<Integer> participantIds, String status,
                                                  RequestQueryDTO requestQuery) {
        ValidationUtils.validateSortFieldsInRequest(requestQuery, SUPPORTED_SORT_FIELDS);
        if (status != null && !status.isBlank() && SUPPORTED_STATUS_SORT_FIELDS.stream()
                .noneMatch(s -> s.equalsIgnoreCase(status))) {
            throw new ValidationException(String.format("Invalid status: %s. Available options: %s", status, SUPPORTED_STATUS_SORT_FIELDS));
        }
        if (dateTo == null) {
            dateTo = LocalDateTime.now();
        }
        if (dateFrom == null) {
            dateFrom = dateTo.minusHours(24);
        }

        List<Integer> venueIdsSafe = (venueIds == null || venueIds.isEmpty()) ? new ArrayList<>() : venueIds;
        List<Integer> participantIdsSafe = (participantIds == null || participantIds.isEmpty()) ? new ArrayList<>() : participantIds;

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

        return PaginationUtils.buildPaginatedResponse(dtoList,  (long) events.size(), requestQuery.getPage(), requestQuery.getPageSize());
    }

    //    @EventBasedCache(cacheName = "eventDetailsCache",
//            key = "#eventId")
    @Override
    public EventDTO getEventById(Integer eventId) {
        IoEvent event = eventRepository.findByGameId(eventId.longValue())
                .orElseThrow(() -> new FlexBetsSportNotFoundException(String.format("Event %s Not Found", eventId)));
        return mapToDto(event);
    }

    private EventDTO mapToDto(IoEvent event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getGameId() != null ? event.getGameId().intValue() : 0);
        IoTeam home = teamRepository.findByTeamId((event.getHomeTeamId())).get();
        IoTeam away = teamRepository.findByTeamId((event.getAwayTeamId())).get();
        dto.setEventName(home.getCity()+ " " + home.getName()
                + " vs " + away.getCity()+ " " + away.getName());
        dto.setEventDate(event.getDatetimeUtc());
        dto.setStatus(event.getStatus());
        dto.setCompetitionId(COMPETITION_ID);
        dto.setCompetition(COMPETITION_NAME);
        dto.setParticipants(List.of(
                new ParticipantSummaryDTO(event.getHomeTeamId() , home.getName(), home.getKey()),
                new ParticipantSummaryDTO(event.getAwayTeamId(), away.getName(), away.getKey())
        ));
        List<IoBet> allByEvent = betRepository.findAllByEventAndAnyBetsAvailableTrue(event);

        List<EventMarketDTO> eventMarketsDto = allByEvent.stream()
                .filter(s -> s.getMarketTypeId() == 2)
                .map(bet -> {
                    EventMarketDTO eventMarketDTO = new EventMarketDTO();
                    eventMarketDTO.setMarketId(String.valueOf(bet.getBetTypeId()));
                    eventMarketDTO.setMarketName(bet.getBetType());
                    List<BetDTO> betDtos = ioBetOutcomeRepository.findAllByBetAndAvailableTrue(bet).stream()
                            .filter(s -> s.getValue() != null)
                            .map(outcome -> {
                                BetDTO betDTO = new BetDTO();
                                betDTO.setId(outcome.getOutcomeId());
                                betDTO.setParticipantId(outcome.getPlayerId());
                                betDTO.setParticipantName(outcome.getParticipant());
                                betDTO.setBetType(outcome.getOutcomeType());
                                betDTO.setPrice(outcome.getValue());
                                return betDTO;
                            })
                            .toList();

                    eventMarketDTO.setBets(betDtos);
                    return betDtos.isEmpty() ? null : eventMarketDTO;
                })
                .filter(Objects::nonNull)
                .toList();

        dto.setMarkets(eventMarketsDto);

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
}
