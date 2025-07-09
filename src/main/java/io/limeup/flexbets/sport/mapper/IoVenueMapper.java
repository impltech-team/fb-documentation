package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.sportsdata.VenueImportDTO;
import io.limeup.flexbets.sport.model.IoVenue;
import org.springframework.stereotype.Component;

@Component
public class IoVenueMapper {


    public IoVenue toEntity(VenueImportDTO dto) {
        IoVenue venue = new IoVenue();
        venue.setStadiumId(dto.getStadiumId());
        venue.setActive(dto.getActive());
        venue.setName(dto.getName());
        venue.setCity(dto.getCity());
        venue.setState(dto.getState());
        venue.setCountry(dto.getCountry());
        venue.setCapacity(dto.getCapacity());
        venue.setSurface(dto.getSurface());
        venue.setLeftField(dto.getLeftField());
        venue.setMidLeftField(dto.getMidLeftField());
        venue.setLeftCenterField(dto.getLeftCenterField());
        venue.setMidLeftCenterField(dto.getMidLeftCenterField());
        venue.setCenterField(dto.getCenterField());
        venue.setMidRightCenterField(dto.getMidRightCenterField());
        venue.setRightCenterField(dto.getRightCenterField());
        venue.setMidRightField(dto.getMidRightField());
        venue.setRightField(dto.getRightField());
        venue.setGeoLat(dto.getGeoLat());
        venue.setGeoLong(dto.getGeoLong());
        venue.setAltitude(dto.getAltitude());
        venue.setHomePlateDirection(dto.getHomePlateDirection());
        venue.setType(dto.getType());
        return venue;
    }

    public void updateEntity(IoVenue entity, VenueImportDTO dto) {
        entity.setActive(dto.getActive());
        entity.setName(dto.getName());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setCapacity(dto.getCapacity());
        entity.setSurface(dto.getSurface());
        entity.setLeftField(dto.getLeftField());
        entity.setMidLeftField(dto.getMidLeftField());
        entity.setLeftCenterField(dto.getLeftCenterField());
        entity.setMidLeftCenterField(dto.getMidLeftCenterField());
        entity.setCenterField(dto.getCenterField());
        entity.setMidRightCenterField(dto.getMidRightCenterField());
        entity.setRightCenterField(dto.getRightCenterField());
        entity.setMidRightField(dto.getMidRightField());
        entity.setRightField(dto.getRightField());
        entity.setGeoLat(dto.getGeoLat());
        entity.setGeoLong(dto.getGeoLong());
        entity.setAltitude(dto.getAltitude());
        entity.setHomePlateDirection(dto.getHomePlateDirection());
        entity.setType(dto.getType());
    }


}
