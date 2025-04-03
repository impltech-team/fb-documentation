package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long>, BatchRepositoryCustom<Venue> {
}
