package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.EventDTO;
import io.limeup.flexbets.sport.dto.FullEventDTO;
import io.limeup.flexbets.sport.dto.ParticipantSummaryDTO;
import io.limeup.flexbets.sport.dto.VenueDTO;
import io.limeup.flexbets.sport.dto.statscore.*;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.Venue;
import io.limeup.flexbets.sport.model.enums.EventStatus;
import io.limeup.flexbets.sport.model.enums.MarketType;
import io.limeup.flexbets.sport.repository.projection.BetRow;
import io.limeup.flexbets.sport.repository.projection.EventRow;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    public Event toEntity(StatScoreEventDTO dto, Competition competition, Venue venue, StatScoreSeasonDTO season) {
        if (dto == null) {
            return null;
        }

        Event entity = new Event();
        return updateEntity(entity, dto, competition, venue, season);
    }

    public Event updateEntity(Event entity, StatScoreEventDTO dto, Competition competition, Venue venue, StatScoreSeasonDTO season) {
        if (entity == null || dto == null) {
            return entity;
        }
        entity.setExternalId(dto.getId());
        entity.setName(dto.getName());

        if (dto.getStartDate() != null) {
            entity.setStartDate(LocalDateTime.parse(dto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }

        entity.setCompetition(competition);
        entity.setVenue(venue);
        entity.setSeasonExternalId(season.getId());
        entity.setSeasonName(season.getName());
        entity.setStatus(EventStatus.valueOf(dto.getStatusType().toUpperCase()));
        entity.setLsId(dto.getLsId());
        return entity;
    }

    public static List<EventDTO> toDTO(List<EventRow> rows) {
        if (rows == null || rows.isEmpty()) return List.of();

        return rows.stream()
                .collect(Collectors.groupingBy(
                        EventRow::getId,
                        LinkedHashMap::new,
                        Collectors.toList()
                ))
                .values()
                .stream()
                .map(EventMapper::mapGroupedRows)
                .toList();
    }

    private static EventDTO mapGroupedRows(List<EventRow> group) {
        EventRow first = group.get(0);

        return EventDTO.builder()
                .id(first.getExternalId())
                .eventName(first.getEventName())
                .eventDate(first.getStartDate())
                .status(first.getStatus().toUpperCase())
                .competition(first.getCompetitionName())
                .competitionId(first.getCompetitionId())
                .venue(VenueDTO.builder()
                        .venueId(first.getVenueId())
                        .venueName(first.getVenueName())
                        .location(first.getVenueLocation())
                        .build())
                .participants(group.stream()
                        .map(row -> new ParticipantSummaryDTO(
                                row.getParticipantId(),
                                row.getTeamName(),
                                row.getAcronym()))
                        .distinct()
                        .collect(Collectors.toList()))
                .build();
    }

    public static FullEventDTO mapToFullEventDTO(StatScoreCompetitionDTO eventCompetitionDTO, Venue venue, Map<Integer, List<BetRow>> markets,
                                                 Map<String, Integer> subParticipantNameIdMap) {
        FullEventDTO fullEvent = new FullEventDTO();
        StatScoreEventDTO dto = eventCompetitionDTO.getSeason().getStage().getGroup().getEvent();
        fullEvent.setId(dto.getId());
        fullEvent.setEventName(dto.getName());
        fullEvent.setEventDate(dto.getStartDate());
        fullEvent.setStatus(dto.getStatusName());
        fullEvent.setCompetitionId(eventCompetitionDTO.getId());
        fullEvent.setCompetition(eventCompetitionDTO.getName());

        fillWithVenue(venue, fullEvent, dto);
        fillWithParticipants(fullEvent, dto);
        fillWithIncidents(fullEvent, dto);
        fillWithMarkets(fullEvent, markets, subParticipantNameIdMap);

        return fullEvent;
    }

    private static void fillWithVenue(Venue venue, FullEventDTO fullEvent, StatScoreEventDTO dto) {
        if (venue != null) {
            FullEventDTO.Venue venueDTO = new FullEventDTO.Venue();
            venueDTO.setVenueId(dto.getVenueId());
            venueDTO.setVenueName(venue.getName());
            venueDTO.setLocation(venue.getCountry() + " " + venue.getCity());
            fullEvent.setVenue(venueDTO);
        }
    }

    private static void fillWithIncidents(FullEventDTO fullEvent, StatScoreEventDTO dto) {
        if (!CollectionUtils.isEmpty(dto.getEventIncidents())) {
            List<FullEventDTO.Incident> incidents = dto.getEventIncidents().stream()
                    .map(i -> {
                        FullEventDTO.Incident incident = new FullEventDTO.Incident();
                        incident.setParticipantId(i.getParticipantId());
                        incident.setParticipantName(i.getParticipantName());
                        incident.setSubParticipantId(i.getSubParticipantId());
                        incident.setSubParticipantName(i.getSubParticipantName());
                        incident.setTime(i.getEventTime());
                        incident.setInfo(i.getInfo());
                        return incident;
                    })
                    .collect(Collectors.toList());
            fullEvent.setIncidents(incidents);
        }
    }

    private static void fillWithParticipants(FullEventDTO fullEvent, StatScoreEventDTO dto) {
        if (!CollectionUtils.isEmpty(dto.getParticipants())) {
            List<FullEventDTO.Participant> participants = new ArrayList<>();
            for (StatScoreEventParticipantDTO p : dto.getParticipants()) {
                FullEventDTO.Participant participant = new FullEventDTO.Participant();
                participant.setParticipantId(p.getId());
                participant.setParticipantName(p.getName());
                participant.setAcronym(p.getAcronym());
                participant.setHome(p.getCounter() == 1);

                fillWithScore(p, participant);
                fillWithLineups(p, participant);
                fillWithStats(p, participant);

                participants.add(participant);
            }

            fullEvent.setParticipants(participants);
        }
    }

    private static void fillWithStats(StatScoreEventParticipantDTO p, FullEventDTO.Participant participant) {
        if (!CollectionUtils.isEmpty(p.getStats())) {
            List<FullEventDTO.Participant.Stat> stats = p.getStats().stream()
                    .map(s -> {
                        FullEventDTO.Participant.Stat stat = new FullEventDTO.Participant.Stat();
                        stat.setStatName(s.getName());
                        stat.setValue(s.getValue());
                        return stat;
                    })
                    .collect(Collectors.toList());
            participant.setStats(stats);
        }
    }

    private static void fillWithLineups(StatScoreEventParticipantDTO p, FullEventDTO.Participant participant) {
        if (!CollectionUtils.isEmpty(p.getLineups())) {
            List<FullEventDTO.Participant.Lineup> lineups = p.getLineups().stream()
                    .map(lu -> new FullEventDTO.Participant.Lineup(
                            lu.getId(),
                            lu.getParticipantName(),
                            lu.getType()
                    ))
                    .collect(Collectors.toList());
            participant.setLineups(lineups);
        }
    }

    private static void fillWithScore(StatScoreEventParticipantDTO p, FullEventDTO.Participant participant) {
        if (p.getStats() != null) {
            for (StatScoreResultDTO result : p.getResults()) {
                if ("Result".equalsIgnoreCase(result.getName()) && !StringUtils.isEmpty(result.getValue())) {
                    participant.setScore(Integer.parseInt(result.getValue()));
                    break;
                }
            }
        }
    }

    private static void fillWithMarkets(FullEventDTO fullEvent, Map<Integer, List<BetRow>> markets, Map<String, Integer> subParticipantNameIdMap) {
        List<FullEventDTO.Market> marketList = new ArrayList<>();

        for (Map.Entry<Integer, List<BetRow>> entry : markets.entrySet()) {
            List<BetRow> bets = entry.getValue();
            Optional<BetRow> marketInfo = bets.stream().findFirst();
            if (marketInfo.isPresent()) {
                FullEventDTO.Market market = new FullEventDTO.Market();
                market.setId(marketInfo.get().getMarketExternalId());
                market.setName(marketInfo.get().getMarketName());
                market.setType(marketInfo.get().getMarketType());
                fillWithBets(market, bets, subParticipantNameIdMap);

                if(!CollectionUtils.isEmpty(market.getBets())) {
                    marketList.add(market);
                }
            }

            fullEvent.setMarkets(marketList);
        }
    }

    private static void fillWithBets(FullEventDTO.Market market, List<BetRow> bets, Map<String, Integer> subParticipants) {
        List<FullEventDTO.Bet> result = new ArrayList<>();
        bets.forEach(bet -> {
            FullEventDTO.Bet betToSave = new FullEventDTO.Bet();
            betToSave.setId(bet.getId());
            betToSave.setType(bet.getName() + " " + bet.getLine());
            betToSave.setPrice(bet.getPrice());

            if (MarketType.SUB_PARTICIPANT.name().equals(market.getType())) {
                String participantName = bet.getParticipantName();
                Integer participantId = subParticipants.get(participantName);
                if (participantId != null) {
                    betToSave.setParticipantId(participantId);
                    betToSave.setParticipantName(participantName);
                    result.add(betToSave);
                }
            } else {
                result.add(betToSave);
            }

            market.setBets(result);
        });
    }
}
