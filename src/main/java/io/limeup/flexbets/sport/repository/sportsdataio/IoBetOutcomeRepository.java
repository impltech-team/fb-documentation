package io.limeup.flexbets.sport.repository.sportsdataio;

import io.limeup.flexbets.sport.model.IoBet;
import io.limeup.flexbets.sport.model.IoBetOutcome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IoBetOutcomeRepository extends JpaRepository<IoBetOutcome, Long> {


    List<IoBetOutcome> findAllByBet(IoBet ioBet);
}

