package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {

}
