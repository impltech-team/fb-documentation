package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Long>, BatchRepositoryCustom<Area> {

}
