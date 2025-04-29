package io.limeup.flexbets.sport;

import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Market;
import io.limeup.flexbets.sport.model.MarketType;
import io.limeup.flexbets.sport.model.Sport;
import io.limeup.flexbets.sport.model.Venue;

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
}
