package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.sportsdata.SportsDataNFLTeamDTO;
import io.limeup.flexbets.sport.dto.sportsdata.SportsDataTeamDTO;
import io.limeup.flexbets.sport.model.IoTeamNFL;
import io.limeup.flexbets.sport.model.IoStadiumNFL;
import org.springframework.stereotype.Component;

@Component
public class IoTeamNFLMapper {

    public IoTeamNFL toEntity(SportsDataNFLTeamDTO dto) {
        if (dto == null) return null;

        IoTeamNFL entity = new IoTeamNFL();
        return updateEntity(entity, dto);
    }

    public IoTeamNFL updateEntity(IoTeamNFL entity, SportsDataNFLTeamDTO dto) {
        if (entity == null || dto == null) return entity;

        entity.setTeamId(dto.getTeamId());
        entity.setKey(dto.getKey());
        entity.setCity(dto.getCity());
        entity.setName(dto.getName());
        entity.setConference(dto.getConference());
        entity.setDivision(dto.getDivision());
        entity.setFullName(dto.getFullName());
        entity.setByeWeek(dto.getByeWeek());
        entity.setAverageDraftPosition(dto.getAverageDraftPosition());
        entity.setAverageDraftPositionPpr(dto.getAverageDraftPositionPpr());
        entity.setHeadCoach(dto.getHeadCoach());
        entity.setOffensiveCoordinator(dto.getOffensiveCoordinator());
        entity.setDefensiveCoordinator(dto.getDefensiveCoordinator());
        entity.setSpecialTeamsCoach(dto.getSpecialTeamsCoach());
        entity.setOffensiveScheme(dto.getOffensiveScheme());
        entity.setDefensiveScheme(dto.getDefensiveScheme());
        entity.setUpcomingSalary(dto.getUpcomingSalary());
        entity.setUpcomingOpponent(dto.getUpcomingOpponent());
        entity.setUpcomingOpponentRank(dto.getUpcomingOpponentRank());
        entity.setUpcomingOpponentPositionRank(dto.getUpcomingOpponentPositionRank());
        entity.setPrimaryColor(dto.getPrimaryColor());
        entity.setSecondaryColor(dto.getSecondaryColor());
        entity.setTertiaryColor(dto.getTertiaryColor());
        entity.setQuaternaryColor(dto.getQuaternaryColor());
        entity.setWikipediaLogoUrl(dto.getWikipediaLogoUrl());
        entity.setWikipediaWordMarkUrl(dto.getWikipediaWordMarkUrl());
        entity.setGlobalTeamId(dto.getGlobalTeamId());
        entity.setDraftKingsName(dto.getDraftKingsName());
        entity.setDraftKingsPlayerId(dto.getDraftKingsPlayerId());
        entity.setFanDuelName(dto.getFanDuelName());
        entity.setFanDuelPlayerId(dto.getFanDuelPlayerId());
        entity.setFantasyDraftName(dto.getFantasyDraftName());
        entity.setFantasyDraftPlayerId(dto.getFantasyDraftPlayerId());
        entity.setYahooName(dto.getYahooName());
        entity.setYahooPlayerId(dto.getYahooPlayerId());
        entity.setAverageDraftPosition2qb(dto.getAverageDraftPosition2QB());
        entity.setAverageDraftPositionDynasty(dto.getAverageDraftPositionDynasty());

        // Stadium
        if (dto.getStadiumDetails() != null) {
            IoStadiumNFL stadium = new IoStadiumNFL();
            stadium.setStadiumId(dto.getStadiumDetails().getStadiumID());
            stadium.setName(dto.getStadiumDetails().getName());
            stadium.setCity(dto.getStadiumDetails().getCity());
            stadium.setState(dto.getStadiumDetails().getState());
            stadium.setCountry(dto.getStadiumDetails().getCountry());
            stadium.setCapacity(dto.getStadiumDetails().getCapacity());
            stadium.setPlayingSurface(dto.getStadiumDetails().getPlayingSurface());
            stadium.setGeoLat(dto.getStadiumDetails().getGeoLat());
            stadium.setGeoLong(dto.getStadiumDetails().getGeoLong());
            stadium.setType(dto.getStadiumDetails().getType());
            entity.setStadium(stadium);
        }

        return entity;
    }
}
