package io.limeup.flexbets.sport.service.statscore;

import io.limeup.flexbets.sport.dto.statscore.StatScoreEventDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSeasonDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;

import java.util.List;
import java.util.Map;

public interface StatScoreDataService {

    Map<StatScoreSeasonDTO, List<StatScoreEventDTO>> getAllEventsPerSeasons(EventQueryParams queryParams);

    List<StatScoreEventDTO> getAllEvents(EventQueryParams queryParams);
}
