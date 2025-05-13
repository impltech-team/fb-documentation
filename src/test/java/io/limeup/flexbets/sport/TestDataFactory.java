package io.limeup.flexbets.sport;

import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.EventStatus;
import io.limeup.flexbets.sport.model.Market;
import io.limeup.flexbets.sport.model.MarketType;
import io.limeup.flexbets.sport.model.Participant;
import io.limeup.flexbets.sport.model.Sport;
import io.limeup.flexbets.sport.model.Venue;

import java.time.LocalDateTime;

public class TestDataFactory {

    private TestDataFactory() {
    }

    public static Area createTestArea(Integer id, String name) {
        Area area = new Area();
        area.setExternalId(id);
        area.setName(name);
        return area;
    }

    public static Sport createTestSport(Integer id, String name) {
        Sport sport = new Sport();
        sport.setExternalId(id);
        sport.setName(name);
        return sport;
    }

    public static Competition createTestCompetition(Integer id, String name, Sport sport, Area area) {
        Competition competition = new Competition();
        competition.setExternalId(id);
        competition.setName(name);
        competition.setSport(sport);
        competition.setArea(area);
        return competition;
    }

    public static Market createTestMarket(Integer id, MarketType type) {
        Market market = new Market();
        market.setExternalId(id);
        market.setMarketType(type);
        return market;
    }

    public static Venue createTestVenue(Integer id, String name) {
        Venue venue = new Venue();
        venue.setExternalId(id);
        venue.setName(name);
        return venue;
    }

    public static Event createTestEvent(Integer id, String name, EventStatus eventStatus, Competition competition, LocalDateTime start, Venue venue) {
        Event event = new Event();
        event.setExternalId(id);
        event.setName(name);
        event.setStatus(eventStatus);
        event.setCompetition(competition);
        event.setStartDate(start);
        event.setVenue(venue);
        event.setSeasonExternalId(1);
        event.setLsId(1L);
        event.setSeasonName("Season 1");
        return event;
    }

    public static Participant createTestParticipant(Integer id, String acronym, String teamName, String type, Competition competition) {
        Participant participant = new Participant();
        participant.setExternalId(id);
        participant.setAcronym(acronym);
        participant.setTeamName(teamName);
        participant.setType(type);
        participant.setCompetition(competition);
        return participant;
    }
}
