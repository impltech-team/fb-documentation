package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.SportDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SportLiteDTO;
import io.limeup.flexbets.sport.model.Sport;

import java.util.List;

public interface SportService extends ExternalIdReadService<Sport, SportDTO, Long> {

    PaginatedResponse<SportLiteDTO> listSports(List<Integer> sportIds, String name, RequestQueryDTO requestQuery);

    SportDTO getSportById(Integer sportId);

    void fetchSportData();
}
