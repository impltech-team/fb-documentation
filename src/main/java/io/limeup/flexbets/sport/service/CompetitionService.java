package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.CompetitionDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.model.Competition;

import java.util.List;

public interface CompetitionService extends ReadService<Competition, CompetitionDTO, Long> {

    List<CompetitionDTO> listCompetitions(List<Integer> areaIds, List<Integer> sportIds, String dateFrom, String dateTo, String type, String gender, String statusType, RequestQueryDTO requestQuery);

    void fetchCompetitionData();

}
