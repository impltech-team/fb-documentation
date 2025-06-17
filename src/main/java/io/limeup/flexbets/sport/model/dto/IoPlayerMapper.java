package io.limeup.flexbets.sport.model.dto;

import io.limeup.flexbets.sport.dto.sportsdata.SportsDataPlayerDTO;
import io.limeup.flexbets.sport.model.IoPlayer;
import org.springframework.stereotype.Component;

@Component
public class IoPlayerMapper {
    public void merge(IoPlayer entity, SportsDataPlayerDTO dto) {
        entity.setPlayerId(dto.getPlayerID());
        entity.setSportsDataId(dto.getSportsDataID());
        entity.setStatus(dto.getStatus());
        entity.setTeamId(dto.getTeamID());
        entity.setTeam(dto.getTeam());
        entity.setJersey(dto.getJersey());
        entity.setPositionCategory(dto.getPositionCategory());
        entity.setPosition(dto.getPosition());
        entity.setMlbamId(dto.getMlbamid());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setBatHand(dto.getBatHand());
        entity.setThrowHand(dto.getThrowHand());
        entity.setHeight(dto.getHeight());
        entity.setWeight(dto.getWeight());
        entity.setBirthDate(dto.getBirthDate());
        entity.setBirthCity(dto.getBirthCity());
        entity.setBirthState(dto.getBirthState());
        entity.setBirthCountry(dto.getBirthCountry());
        entity.setHighSchool(dto.getHighSchool());
        entity.setCollege(dto.getCollege());
        entity.setProDebut(dto.getProDebut());
        entity.setSalary(dto.getSalary());
        entity.setPhotoUrl(dto.getPhotoUrl());
        entity.setSportRadarPlayerId(dto.getSportRadarPlayerID());
        entity.setRotoworldPlayerId(dto.getRotoworldPlayerID());
        entity.setRotoWirePlayerId(dto.getRotoWirePlayerID());
        entity.setFantasyAlarmPlayerId(dto.getFantasyAlarmPlayerID());
        entity.setStatsPlayerId(dto.getStatsPlayerID());
        entity.setSportsDirectPlayerId(dto.getSportsDirectPlayerID());
        entity.setXmlTeamPlayerId(dto.getXmlTeamPlayerID());
        entity.setInjuryStatus(dto.getInjuryStatus());
        entity.setInjuryBodyPart(dto.getInjuryBodyPart());
        entity.setInjuryStartDate(dto.getInjuryStartDate());
        entity.setInjuryNotes(dto.getInjuryNotes());
        entity.setFanDuelPlayerId(dto.getFanDuelPlayerID());
        entity.setDraftKingsPlayerId(dto.getDraftKingsPlayerID());
        entity.setYahooPlayerId(dto.getYahooPlayerID());
        entity.setUpcomingGameId(dto.getUpcomingGameID());
        entity.setFanDuelName(dto.getFanDuelName());
        entity.setDraftKingsName(dto.getDraftKingsName());
        entity.setYahooName(dto.getYahooName());
        entity.setGlobalTeamId(dto.getGlobalTeamID());
        entity.setFantasyDraftName(dto.getFantasyDraftName());
        entity.setFantasyDraftPlayerId(dto.getFantasyDraftPlayerID());
        entity.setExperience(dto.getExperience());
        entity.setUsaTodayPlayerId(dto.getUsaTodayPlayerID());
        entity.setUsaTodayHeadshotUrl(dto.getUsaTodayHeadshotUrl());
        entity.setUsaTodayHeadshotNoBackgroundUrl(dto.getUsaTodayHeadshotNoBackgroundUrl());
        entity.setUsaTodayHeadshotUpdated(dto.getUsaTodayHeadshotUpdated());
        entity.setUsaTodayHeadshotNoBackgroundUpdated(dto.getUsaTodayHeadshotNoBackgroundUpdated());
    }
    public IoPlayer toEntity(SportsDataPlayerDTO dto) {
        return IoPlayer.builder()
                .playerId(dto.getPlayerID())
                .sportsDataId(dto.getSportsDataID())
                .status(dto.getStatus())
                .teamId(dto.getTeamID())
                .team(dto.getTeam())
                .jersey(dto.getJersey())
                .positionCategory(dto.getPositionCategory())
                .position(dto.getPosition())
                .mlbamId(dto.getMlbamid())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .batHand(dto.getBatHand())
                .throwHand(dto.getThrowHand())
                .height(dto.getHeight())
                .weight(dto.getWeight())
                .birthDate(dto.getBirthDate())
                .birthCity(dto.getBirthCity())
                .birthState(dto.getBirthState())
                .birthCountry(dto.getBirthCountry())
                .highSchool(dto.getHighSchool())
                .college(dto.getCollege())
                .proDebut(dto.getProDebut())
                .salary(dto.getSalary())
                .photoUrl(dto.getPhotoUrl())
                .sportRadarPlayerId(dto.getSportRadarPlayerID())
                .rotoworldPlayerId(dto.getRotoworldPlayerID())
                .rotoWirePlayerId(dto.getRotoWirePlayerID())
                .fantasyAlarmPlayerId(dto.getFantasyAlarmPlayerID())
                .statsPlayerId(dto.getStatsPlayerID())
                .sportsDirectPlayerId(dto.getSportsDirectPlayerID())
                .xmlTeamPlayerId(dto.getXmlTeamPlayerID())
                .injuryStatus(dto.getInjuryStatus())
                .injuryBodyPart(dto.getInjuryBodyPart())
                .injuryStartDate(dto.getInjuryStartDate())
                .injuryNotes(dto.getInjuryNotes())
                .fanDuelPlayerId(dto.getFanDuelPlayerID())
                .draftKingsPlayerId(dto.getDraftKingsPlayerID())
                .yahooPlayerId(dto.getYahooPlayerID())
                .upcomingGameId(dto.getUpcomingGameID())
                .fanDuelName(dto.getFanDuelName())
                .draftKingsName(dto.getDraftKingsName())
                .yahooName(dto.getYahooName())
                .globalTeamId(dto.getGlobalTeamID())
                .fantasyDraftName(dto.getFantasyDraftName())
                .fantasyDraftPlayerId(dto.getFantasyDraftPlayerID())
                .experience(dto.getExperience())
                .usaTodayPlayerId(dto.getUsaTodayPlayerID())
                .usaTodayHeadshotUrl(dto.getUsaTodayHeadshotUrl())
                .usaTodayHeadshotNoBackgroundUrl(dto.getUsaTodayHeadshotNoBackgroundUrl())
                .usaTodayHeadshotUpdated(dto.getUsaTodayHeadshotUpdated())
                .usaTodayHeadshotNoBackgroundUpdated(dto.getUsaTodayHeadshotNoBackgroundUpdated())
                .build();
    }

}
