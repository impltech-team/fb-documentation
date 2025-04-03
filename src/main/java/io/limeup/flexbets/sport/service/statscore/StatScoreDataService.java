package io.limeup.flexbets.sport.service.statscore;

import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreGroupDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSeasonDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreStageDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.service.statscore.impl.StatScoreDataServiceImpl;

import java.util.List;
import java.util.Map;

public interface StatScoreDataService {

    record EventContext(
            StatScoreEventDTO event,
            StatScoreGroupDTO group,
            StatScoreStageDTO stage,
            StatScoreSeasonDTO season,
            StatScoreCompetitionDTO competition
    ) {}

    List<EventContext> getAllEventsWithContext(EventQueryParams queryParams);

    List<StatScoreEventDTO> getAllEvents(EventQueryParams queryParams);
}
