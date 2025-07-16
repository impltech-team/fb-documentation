package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.sportsdata.SportsDataNFLStadiumDTO;
import io.limeup.flexbets.sport.model.IoStadiumNFL;
import org.springframework.stereotype.Component;

@Component
public class IoStadiumNFLMapper {

    public IoStadiumNFL toEntity(SportsDataNFLStadiumDTO dto) {
        if (dto == null) return null;

        return IoStadiumNFL.builder()
                .stadiumId(dto.getStadiumID())
                .name(dto.getName())
                .city(dto.getCity())
                .state(dto.getState())
                .country(dto.getCountry())
                .capacity(dto.getCapacity())
                .playingSurface(dto.getPlayingSurface())
                .geoLat(dto.getGeoLat())
                .geoLong(dto.getGeoLong())
                .type(dto.getType())
                .build();
    }

    public void updateEntity(IoStadiumNFL entity, SportsDataNFLStadiumDTO dto) {
        if (entity == null || dto == null) return;

        entity.setName(dto.getName());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setCapacity(dto.getCapacity());
        entity.setPlayingSurface(dto.getPlayingSurface());
        entity.setGeoLat(dto.getGeoLat());
        entity.setGeoLong(dto.getGeoLong());
        entity.setType(dto.getType());
    }
}
