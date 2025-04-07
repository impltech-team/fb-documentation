package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.EventStat;

import java.util.Collection;

public interface StatRepository extends ExternalIdRepository<EventStat, Long> {

    void deleteByEventIdIn(Collection<Long> eventIds);

}
