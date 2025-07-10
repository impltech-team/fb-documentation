package io.limeup.flexbets.sport.repository.sportsdataio;


import io.limeup.flexbets.sport.model.IoVenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IoVenueRepository extends JpaRepository<IoVenue, Integer> {

    Optional<IoVenue> findByStadiumId(Integer stadiumId);
}
