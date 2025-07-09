package io.limeup.flexbets.sport.service.impl.sportsdataio;

import io.limeup.flexbets.sport.cache.EventBasedCache;
import io.limeup.flexbets.sport.dto.EventDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.repository.VenueRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoEventRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoPlayerRepository;
import io.limeup.flexbets.sport.service.BetService;
import io.limeup.flexbets.sport.service.EventService;
import io.limeup.flexbets.sport.service.VenueService;
import io.limeup.flexbets.sport.service.statscore.StatScoreClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class SportDataIoEventServiceImpl  implements EventService {

    private static final Set<String> SUPPORTED_SORT_FIELDS = Set.of("event_name", "event_date");

    private final IoEventRepository ioEventRepository;

    private final StatScoreClient statScoreClient;

    private final VenueService venueService;

    private final VenueRepository venueRepository;

    private final BetService betService;

    private final IoPlayerRepository subParticipantRepository;

    protected SportDataIoEventServiceImpl(IoEventRepository repository, StatScoreClient statScoreClient,
                                          VenueService venueService, VenueRepository venueRepository, BetService betService, IoPlayerRepository subParticipantRepository
    ) {
        this.ioEventRepository = repository;
        this.statScoreClient = statScoreClient;
        this.venueService = venueService;
        this.venueRepository = venueRepository;
        this.betService = betService;
        this.subParticipantRepository = subParticipantRepository;
    }

    @EventBasedCache(cacheName = "eventsListCache",
            key = "T(java.util.Objects).hash(#competitionId, #dateFrom, #dateTo, #venueIds, #participantIds, #status, #requestQuery.page, " +
                    "#requestQuery.pageSize, #requestQuery.sortOrder, #requestQuery.sortBy, #requestQuery.filter)")
    @Override
    public PaginatedResponse<EventDTO> listEvents(Integer competitionId, LocalDateTime dateFrom, LocalDateTime dateTo, List<Integer> venueIds
            , List<Integer> participantIds, String status, RequestQueryDTO requestQuery) {
//        ValidationUtils.validateSortFieldsInRequest(requestQuery, SUPPORTED_SORT_FIELDS);
//
//        long count = ioEventRepository.countEvents(
//                competitionId,
//                dateFrom,
//                dateTo,
//                status,
//                venueIds == null ? Collections.emptyList() : venueIds,
//                participantIds == null ? Collections.emptyList() : participantIds);
//
//        if (count == 0) {
//            return PaginationUtils.buildPaginatedResponse(null, count, requestQuery.getPage(), requestQuery.getPageSize());
//        }
//
//        List<EventRow> eventRows = ioEventRepository.listEvents(
//                competitionId,
//                dateFrom,
//                dateTo,
//                status,
//                venueIds == null ? Collections.emptyList() : venueIds,
//                participantIds == null ? Collections.emptyList() : participantIds,
//                requestQuery.getSortBy(),
//                requestQuery.getSortOrder(),
//                requestQuery.getPageSize(),
//                (requestQuery.getPage() - 1) * requestQuery.getPageSize());
//
//        return PaginationUtils.buildPaginatedResponse(
//                EventMapper.toDTO(eventRows), count, requestQuery.getPage(), requestQuery.getPageSize());
        return null;

    }

    //    @EventBasedCache(cacheName = "eventDetailsCache",
//            key = "#eventId")
    @Override
    public EventDTO getEventById(Integer eventId) {

//        IoEvent event = ioEventRepository.findByGameId(Long.valueOf(eventId))
//                .orElseThrow(() -> new FlexBetsSportNotFoundException("Event not found: " + eventId));
//
//        Venue venue = venueRepository.findById(Long.valueOf(event.getStadiumId())).orElse(null);
//
////    Map<Integer, List<BetRow>> marketBetRowMap = betService.getBetsByExternalIdInAndBetStatus(
////                    List.of(eventId), BetStatus.SUSPENDED)
////            .stream()
////            .collect(Collectors.groupingBy(BetRow::getMarketExternalId));
//
//        Map<String, Long> subParticipantNameIdMap = new HashMap<>();
//
//        for (IoPlayer sub : subParticipantRepository.findAll()) {
//            String name = sub.getFirstName(); // або sub.getName(), якщо треба інше поле
//            Long id = sub.getPlayerId();
//
//            if (name != null) {
//                // Не додаємо, якщо вже є — аналог (v1, v2) -> v1
//                subParticipantNameIdMap.putIfAbsent(name, id);
//            }
//        }
//
//
//        // === Створення DTO ===
//        FullEventDTO dto = new EventDTO();
//        dto.setId(event.getId().intValue());
//        dto.setEventName(event.getHomeTeam() + " vs " + event.getAwayTeam());
//        dto.setEventDate(event.getDatetime());
//        dto.setStatus(event.getStatus());
//        dto.setCompetitionId(event.getSeasonType()); // Приклад — залежно від логіки
//        dto.setCompetition("Season " + event.getSeason()); // Аналогічно — треба виносити, якщо є окрема Competition
//
//        // === Venue ===
//        if (venue != null) {
//            FullEventDTO.Venue venueDTO = new FullEventDTO.Venue();
//            venueDTO.setVenueId(venue.getExternalId());
//            venueDTO.setVenueName(venue.getName());
//            venueDTO.setLocation(venue.getCountry() + " " + venue.getCity());
//            dto.setVenue(venueDTO);
//        }
//
//        // === Participants ===
//        List<EventDTO> participants = new ArrayList<>();
//        FullEventDTO.Participant home = new FullEventDTO.Participant();
//        home.setParticipantId(event.getHomeTeamId());
//        home.setParticipantName(event.getHomeTeam());
//        home.setHome(true);
//        home.setScore(event.getHomeTeamRuns());
////    home.setAcronym(event.);
//        participants.add(home);
//
//        FullEventDTO.Participant away = new FullEventDTO.Participant();
//        away.setParticipantId(event.getAwayTeamId());
//        away.setParticipantName(event.getAwayTeam());
//        away.setHome(false);
//        away.setScore(event.getAwayTeamRuns());
//        participants.add(away);

//        dto.setParticipants(participants);

        // === Markets + Bets ===
//    List<FullEventDTO.Market> marketList = new ArrayList<>();
//    for (Map.Entry<Integer, List<BetRow>> entry : marketBetRowMap.entrySet()) {
//        List<BetRow> bets = entry.getValue();
//        if (bets.isEmpty()) continue;
//
//        BetRow sample = bets.get(0);
//
//        FullEventDTO.Market market = new FullEventDTO.Market();
//        market.setId(sample.getMarketExternalId());
//        market.setName(sample.getMarketName());
//        market.setType(sample.getMarketType());
//
//        FullEventDTO.Participant participant =
//                EventMapper.getParticipantInfoByMarketName(market.getName(), home, away);
//
//        List<FullEventDTO.Bet> betDTOs = new ArrayList<>();
//        for (BetRow b : bets) {
//            FullEventDTO.Bet betDTO = new FullEventDTO.Bet();
//            betDTO.setId(b.getId());
//            betDTO.setType(b.getName() + " " + b.getLine());
//            betDTO.setPrice(b.getPrice());
//
//            if ("SUB_PARTICIPANT".equalsIgnoreCase(market.getType())) {
//                String name = b.getParticipantName();
//                Long id = subParticipantNameIdMap.get(name);
//                if (id != null) {
//                    betDTO.setParticipantId(Math.toIntExact(id));
//                    betDTO.setParticipantName(name);
//                    betDTOs.add(betDTO);
//                }
//            } else if ("PARTICIPANT".equalsIgnoreCase(market.getType()) && participant != null) {
//                betDTO.setParticipantId(participant.getParticipantId());
//                betDTO.setParticipantName(participant.getParticipantName());
//                betDTOs.add(betDTO);
//            } else {
//                betDTOs.add(betDTO);
//            }
//        }
//
//        market.setBets(betDTOs);
//        if (!betDTOs.isEmpty()) marketList.add(market);
//    }
//    dto.setMarkets(marketList);

        return null;
    }




}
