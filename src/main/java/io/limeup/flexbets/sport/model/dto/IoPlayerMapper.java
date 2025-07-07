package io.limeup.flexbets.sport.model.dto;

import io.limeup.flexbets.sport.dto.EventLiteDTO;
import io.limeup.flexbets.sport.dto.OddsDTO;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;
import io.limeup.flexbets.sport.dto.sportsdata.SportsDataPlayerDTO;
import io.limeup.flexbets.sport.mapper.IoBetMapper;
import io.limeup.flexbets.sport.model.IoPlayer;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataBetRow;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataPlayerRow;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class IoPlayerMapper {
    private final IoBetMapper betMapper;

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
        // populate by script
        //  entity.setPhotoUrl(dto.getPhotoUrl());
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
                // .photoUrl(dto.getPhotoUrl())
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

    public List<SubParticipantDTO> toSubParticipantDTOList(List<SportsDataPlayerRow> players, Map<Long, List<SportsDataBetRow>> playerIdBetMap) {
        return players.stream()
                .map(player -> {
                    Long playerId = Long.valueOf(player.getId());
                    List<SportsDataBetRow> bets =
                            playerIdBetMap.getOrDefault(playerId, List.of());

                    return toSubParticipantDTO(player, bets);
                })
                .toList();
    }

    public SubParticipantDTO toSubParticipantDTO(SportsDataPlayerRow player, List<SportsDataBetRow> bets ){


        SubParticipantDTO result = new SubParticipantDTO();
        result.setId(player.getId());
        result.setPlayerName(player.getPlayerName());
        result.setCompetitionId(5466);
        result.setCompetition("MLB");
        result.setAvatarUrl(player.getAvatarUrl());

        result.setParticipantId(player.getPlayerTeamId());
        result.setTeam(player.getPlayerTeamName());
        result.setPosition(player.getPosition());
        result.setShirtNr(player.getShirtNumber());
        result.setAreaName(player.getCountry());
        result.setAreaName(player.getCountry());
        result.setGender("male");
        result.setWeight(player.getWeight().toString());
        result.setHeight(player.getHeight().toString());
        result.setBirthDate(player.getBirthDate());
        result.setNextEvent(EventLiteDTO.builder()
                .eventId(player.getEventId())
                .eventName(player.getEventName())
                .eventDate(player.getEventDatetime())
                .opponent(player.getOpponentTeamKey())
                .build());
        List<OddsDTO> odds = new ArrayList<>();
        if(!CollectionUtils.isEmpty(bets)){
            odds = betMapper.toOddsDTOList(bets);
        }
        result.setOdds(odds);


        return result;
    }
}
