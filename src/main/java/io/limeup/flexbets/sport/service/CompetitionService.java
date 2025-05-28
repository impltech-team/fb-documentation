package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.CompetitionDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.enums.CompetitionType;
import io.limeup.flexbets.sport.model.enums.StatusType;

public interface CompetitionService extends ExternalIdReadService<Competition, CompetitionDTO, Long> {

    PaginatedResponse<CompetitionDTO> listCompetitions(Integer areaId, Integer sportId, CompetitionType type, String gender,
                                                       StatusType statusType, RequestQueryDTO requestQuery);

    void fetchCompetitionData();

    Competition create(StatScoreCompetitionDTO competition);
}
