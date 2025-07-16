package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.EventLiteDTO;
import io.limeup.flexbets.sport.dto.OddsDTO;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;
import io.limeup.flexbets.sport.dto.sportsdata.SportsDataNFLPlayerDTO;
import io.limeup.flexbets.sport.model.IoPlayerNFL;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataBetRow;
import io.limeup.flexbets.sport.repository.projection.sportsdataio.SportsDataPlayerRow;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class IoPlayerNFLMapper {

    private final IoBetNFLMapper betMapper;

    public void merge(IoPlayerNFL entity, SportsDataNFLPlayerDTO dto) {
        entity.setPlayerId(dto.getPlayerID());
        entity.setStatus(dto.getStatus());
        entity.setPosition(dto.getPosition());
        entity.setPositionCategory(dto.getPositionCategory());
        entity.setFantasyPosition(dto.getFantasyPosition());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setHeight(dto.getHeight());
        entity.setWeight(dto.getWeight());
        entity.setBirthDate(dto.getBirthDate());
        entity.setCollege(dto.getCollege());
        entity.setExperience(dto.getExperience());
        entity.setExperienceString(dto.getExperienceString());
        entity.setCurrentStatus(dto.getCurrentStatus());
        entity.setActive(dto.getActive());
        entity.setDeclaredInactive(dto.getDeclaredInactive());
        entity.setByeWeek(dto.getByeWeek());
        entity.setShortName(dto.getShortName());
        entity.setPhotoUrl(dto.getPhotoUrl());
        entity.setSportRadarPlayerId(dto.getSportRadarPlayerID());
        entity.setFanDuelPlayerId(dto.getFanDuelPlayerID());
        entity.setDraftKingsPlayerId(dto.getDraftKingsPlayerID());
        entity.setYahooPlayerId(dto.getYahooPlayerID());
        entity.setUsaTodayPlayerId(dto.getUsaTodayPlayerID());
        entity.setUsaTodayHeadshotUrl(dto.getUsaTodayHeadshotUrl());
        entity.setUsaTodayHeadshotNoBackgroundUrl(dto.getUsaTodayHeadshotNoBackgroundUrl());
        entity.setUsaTodayHeadshotUpdated(dto.getUsaTodayHeadshotUpdated());
        entity.setUsaTodayHeadshotNoBackgroundUpdated(dto.getUsaTodayHeadshotNoBackgroundUpdated());
        entity.setGlobalTeamId(dto.getGlobalTeamID());
        entity.setFantasyDraftPlayerId(dto.getFantasyDraftPlayerID());
        entity.setFantasyDraftName(dto.getFantasyDraftName());
    }

    public IoPlayerNFL toEntity(SportsDataNFLPlayerDTO dto) {
        return IoPlayerNFL.builder()
                .playerId(dto.getPlayerID())
                .status(dto.getStatus())
                .position(dto.getPosition())
                .positionCategory(dto.getPositionCategory())
                .fantasyPosition(dto.getFantasyPosition())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .height(dto.getHeight())
                .weight(dto.getWeight())
                .birthDate(dto.getBirthDate())
                .college(dto.getCollege())
                .experience(dto.getExperience())
                .experienceString(dto.getExperienceString())
                .currentStatus(dto.getCurrentStatus())
                .active(dto.getActive())
                .declaredInactive(dto.getDeclaredInactive())
                .byeWeek(dto.getByeWeek())
                .shortName(dto.getShortName())
                .photoUrl(dto.getPhotoUrl())
                .sportRadarPlayerId(dto.getSportRadarPlayerID())
                .fanDuelPlayerId(dto.getFanDuelPlayerID())
                .draftKingsPlayerId(dto.getDraftKingsPlayerID())
                .yahooPlayerId(dto.getYahooPlayerID())
                .usaTodayPlayerId(dto.getUsaTodayPlayerID())
                .usaTodayHeadshotUrl(dto.getUsaTodayHeadshotUrl())
                .usaTodayHeadshotNoBackgroundUrl(dto.getUsaTodayHeadshotNoBackgroundUrl())
                .usaTodayHeadshotUpdated(dto.getUsaTodayHeadshotUpdated())
                .usaTodayHeadshotNoBackgroundUpdated(dto.getUsaTodayHeadshotNoBackgroundUpdated())
                .globalTeamId(dto.getGlobalTeamID())
                .fantasyDraftPlayerId(dto.getFantasyDraftPlayerID())
                .fantasyDraftName(dto.getFantasyDraftName())
                .build();
    }


    public SubParticipantDTO toSubParticipantDTO(SportsDataPlayerRow player, List<SportsDataBetRow> bets) {
        SubParticipantDTO result = new SubParticipantDTO();

        result.setId(player.getId() != null ? player.getId() : 0); // fallback to 0 if null
        result.setPlayerName(player.getPlayerName());
        result.setCompetitionId(5611); // NFL
        result.setCompetition("NFL");
        result.setAvatarUrl(player.getAvatarUrl());

        // Safely handle participantId (team ID)
        if (player.getPlayerTeamId() != null) {
            result.setParticipantId(player.getPlayerTeamId());
        } else {
            result.setParticipantId(0); // default/fallback
        }

        result.setTeam(player.getPlayerTeamName());
        result.setPosition(player.getPosition());
        result.setShirtNr(player.getShirtNumber()); // Already Integer, null-safe
        result.setAreaName(player.getCountry());

        result.setGender("male");
        result.setWeight(player.getWeight() != null ? player.getWeight().toString() : null);
        result.setHeight(player.getHeight() != null ? player.getHeight().toString() : null);
        result.setBirthDate(player.getBirthDate());

        if (player.getEventId() != null) {
            result.setNextEvent(EventLiteDTO.builder()
                    .eventId(player.getEventId())
                    .eventName(player.getEventName())
                    .eventDate(player.getEventDatetime())
                    .opponent(player.getOpponentTeamKey())
                    .build());
        }

        List<OddsDTO> odds = CollectionUtils.isEmpty(bets)
                ? Collections.emptyList()
                : betMapper.toOddsDTOList(bets);

        result.setOdds(odds);

        return result;
    }

}
