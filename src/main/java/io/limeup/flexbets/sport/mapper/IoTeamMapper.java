package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.sportsdata.SportsDataTeamDTO;
import io.limeup.flexbets.sport.model.IoBet;
import io.limeup.flexbets.sport.model.IoTeam;
import org.springframework.stereotype.Component;

@Component
public class IoTeamMapper {
    public static IoTeam toEntity(SportsDataTeamDTO dto) {
        if (dto == null) {
            return null;
        }

        IoTeam entity = new IoTeam();
        entity = updateEntity(entity, dto);
        return entity;
    }

    public static IoTeam updateEntity(IoTeam entity, SportsDataTeamDTO dto) {
        if (entity == null || dto == null) {
            return entity;
        }

        entity.setTeamId(dto.getTeamId());
        entity.setKey(dto.getKey());
        entity.setActive(dto.getActive());
        entity.setCity(dto.getCity());
        entity.setName(dto.getName());
        entity.setStadiumId(dto.getStadiumId());
        entity.setLeague(dto.getLeague());
        entity.setDivision(dto.getDivision());
        entity.setPrimaryColor(dto.getPrimaryColor());
        entity.setSecondaryColor(dto.getSecondaryColor());
        entity.setTertiaryColor(dto.getTertiaryColor());
        entity.setQuaternaryColor(dto.getQuaternaryColor());
        entity.setWikipediaLogoUrl(dto.getWikipediaLogoUrl());
        entity.setWikipediaWordMarkUrl(dto.getWikipediaWordMarkUrl());
        entity.setGlobalTeamId(dto.getGlobalTeamId());
        entity.setHeadCoach(dto.getHeadCoach());
        entity.setHittingCoach(dto.getHittingCoach());
        entity.setPitchingCoach(dto.getPitchingCoach());

        return entity;
    }

}
