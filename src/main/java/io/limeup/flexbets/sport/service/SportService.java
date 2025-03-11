package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.SportDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;

import java.util.List;

public interface SportService {
    List<SportDTO> listSports(List<Integer> sportIds, String name, RequestQueryDTO requestQuery);

    SportDTO getSportById(Integer sportId);
}
