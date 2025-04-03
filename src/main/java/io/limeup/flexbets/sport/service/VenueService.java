package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.VenueDTO;
import io.limeup.flexbets.sport.model.Venue;

public interface VenueService extends ReadService<Venue, VenueDTO, Long>{

    void fetchVenueData();

}
