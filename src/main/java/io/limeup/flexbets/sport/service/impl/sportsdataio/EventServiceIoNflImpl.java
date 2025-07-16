package io.limeup.flexbets.sport.service.impl.sportsdataio;

import io.limeup.flexbets.sport.cache.EventBasedCache;
import io.limeup.flexbets.sport.dto.*;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.model.*;
import io.limeup.flexbets.sport.repository.sportsdataio.*;
import io.limeup.flexbets.sport.service.EventService;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import io.limeup.flexbets.sport.utils.ValidationUtils;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventServiceIoNflImpl implements EventService {
    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("event_name", "event_date");
    private static final Set<String> SUPPORTED_STATUS_SORT_FIELDS = Set.of("Final", "Scheduled", "InProgress", "Delayed", "Postponed", "Canceled");

    private static final int COMPETITION_ID = 5611; // NFL competition ID
    private static final String COMPETITION_NAME = "NFL";

    private final IoEventNFLRepository eventRepository;
    private final IoStadiumNFLRepository stadiumRepository;
    private final IoTeamNFLRepository teamRepository;
    private final IoBetNFLRepository betRepository;
    private final IoBetOutcomeNFLRepository betOutcomeRepository;

    public EventServiceIoNflImpl(IoEventNFLRepository eventRepository,
                                 IoStadiumNFLRepository stadiumRepository,
                                 IoTeamNFLRepository teamRepository,
                                 IoBetNFLRepository betRepository,
                                 IoBetOutcomeNFLRepository betOutcomeRepository) {
        this.eventRepository = eventRepository;
        this.stadiumRepository = stadiumRepository;
        this.teamRepository = teamRepository;
        this.betRepository = betRepository;
        this.betOutcomeRepository = betOutcomeRepository;
    }

    @Override
    public PaginatedResponse<EventDTO> listEvents(Integer competitionId, LocalDateTime dateFrom, LocalDateTime dateTo,
                                                  List<Integer> stadiumIds, List<Integer> participantIds, String status,
                                                  RequestQueryDTO requestQuery) {
        ValidationUtils.validateSortFieldsInRequest(requestQuery, SUPPORTED_SORT_FIELDS);
        if (status != null && !status.isBlank() && SUPPORTED_STATUS_SORT_FIELDS.stream()
                .noneMatch(s -> s.equalsIgnoreCase(status))) {
            throw new ValidationException(String.format("Invalid status: %s. Available options: %s", status, SUPPORTED_STATUS_SORT_FIELDS));
        }

        ZoneId utcZone = ZoneOffset.UTC;
        if (dateTo == null) {
            dateTo = ZonedDateTime.now(utcZone).toLocalDateTime();
        } else {
            dateTo = dateTo.atZone(ZoneOffset.UTC).toLocalDateTime();
        }

        if (dateFrom == null) {
            dateFrom = dateTo.minusHours(24);
        } else {
            dateFrom = dateFrom.atZone(ZoneOffset.UTC).toLocalDateTime();
        }

        List<Integer> stadiumIdsSafe = (stadiumIds == null || stadiumIds.isEmpty()) ? new ArrayList<>() : stadiumIds;
        List<Integer> participantIdsSafe = (participantIds == null || participantIds.isEmpty()) ? new ArrayList<>() : participantIds;

        long total = eventRepository.countEvents(
                dateFrom,
                dateTo,
                status,
                stadiumIdsSafe,
                participantIdsSafe
        );

        if (total == 0) {
            return PaginationUtils.buildPaginatedResponse(null, total, requestQuery.getPage(), requestQuery.getPageSize());
        }

        List<IoEventNFL> events = eventRepository.listEvents(
                dateFrom,
                dateTo,
                status,
                stadiumIdsSafe,
                participantIdsSafe,
                requestQuery.getSortBy(),
                requestQuery.getSortOrder(),
                requestQuery.getPageSize(),
                (requestQuery.getPage() - 1) * requestQuery.getPageSize()
        );

        List<EventDTO> dtoList = events.stream().map(this::mapToDto).collect(Collectors.toList());

        return PaginationUtils.buildPaginatedResponse(dtoList, total, requestQuery.getPage(), requestQuery.getPageSize());
    }

    @EventBasedCache(cacheName = "eventDetailsCache", key = "#eventId")
    @Override
    public EventDTO getEventById(Integer eventId) {
        IoEventNFL event = eventRepository.findById(eventId.longValue())
                .orElseThrow(() -> new FlexBetsSportNotFoundException(String.format("Event %s Not Found", eventId)));
        return mapToDto(event);
    }

    private EventDTO mapToDto(IoEventNFL event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId() != null ? event.getId().intValue() : 0);

        IoTeamNFL home = teamRepository.findByTeamId(Long.valueOf(event.getHomeTeamId()))
                .orElseThrow(() -> new FlexBetsSportNotFoundException("Home team not found"));
        IoTeamNFL away = teamRepository.findByTeamId(Long.valueOf(event.getAwayTeamId()))
                .orElseThrow(() -> new FlexBetsSportNotFoundException("Away team not found"));

        dto.setEventName(filterNull(home.getCity(), home.getName())
                + " vs " + filterNull(away.getCity(), away.getName()));
        dto.setEventDate(event.getDatetimeUtc());
        dto.setStatus(event.getStatus());
        dto.setCompetitionId(COMPETITION_ID);
        dto.setCompetition(COMPETITION_NAME);
        dto.setParticipants(List.of(
                new ParticipantSummaryDTO(event.getHomeTeamId(), home.getName(), home.getKey()),
                new ParticipantSummaryDTO(event.getAwayTeamId(), away.getName(), away.getKey())
        ));

        // Get all available bets for this event
        Set<IoBetNFL> eventBets = betRepository.findByEventIdAndMarketTypes(
                event.getId(),
                List.of("Moneyline", "Point Spread", "Total Points") // Common NFL bet types
        );

        List<EventMarketDTO> eventMarketsDto = eventBets.stream()
                .map(bet -> {
                    EventMarketDTO eventMarketDTO = new EventMarketDTO();
                    eventMarketDTO.setMarketId(String.valueOf(bet.getMarketId()));
                    eventMarketDTO.setMarketName(bet.getBettingMarketType());

                    // Get all outcomes for this bet
                    List<BetDTO> betDtos = bet.getBettingOutcomes().stream()
                            .filter(outcome -> outcome.getOdds() != null)
                            .map(outcome -> {
                                BetDTO betDTO = new BetDTO();
                                betDTO.setId(outcome.getId());
                                betDTO.setParticipantId(outcome.getBet().getPlayerId());
                                betDTO.setParticipantName(outcome.getBet().getPlayerName());
                                betDTO.setBetType(outcome.getName());
                                betDTO.setPrice(outcome.getOdds().toString());
                                return betDTO;
                            })
                            .toList();

                    eventMarketDTO.setBets(betDtos);
                    return betDtos.isEmpty() ? null : eventMarketDTO;
                })
                .filter(Objects::nonNull)
                .toList();

        dto.setMarkets(eventMarketsDto);

        // Set stadium information if available
        if (event.getStadiumId() != null) {
            stadiumRepository.findByStadiumId(event.getStadiumId())
                    .ifPresent(stadium -> dto.setVenue(
                            VenueDTO.builder()
                                    .venueId(stadium.getStadiumId())
                                    .venueName(stadium.getName())
                                    .location(stadium.getCity() + ", " + stadium.getState())
                                    .build()
                    ));
        }
        return dto;
    }

    private String filterNull(String cityName, String teamName) {
        if (cityName == null) {
            return teamName;
        } else {
            return cityName + " " + teamName;
        }
    }
}