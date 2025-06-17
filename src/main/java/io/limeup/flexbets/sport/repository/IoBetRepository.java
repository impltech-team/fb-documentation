package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.IoBet;
import io.limeup.flexbets.sport.model.IoEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IoBetRepository extends JpaRepository<IoBet, Long> {

    List<IoBet> findByEvent(IoEvent event);

}
