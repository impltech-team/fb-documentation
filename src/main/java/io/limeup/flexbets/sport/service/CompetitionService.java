package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.CompetitionDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;

import java.util.List;

public interface CompetitionService {
    List<CompetitionDTO> listCompetitions(List<Integer> areaIds, List<Integer> sportIds, String dateFrom, String dateTo, String type, String gender, String statusType, RequestQueryDTO requestQuery);
}
