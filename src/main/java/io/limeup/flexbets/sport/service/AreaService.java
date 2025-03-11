package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.AreaDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;

import java.util.List;

public interface AreaService {
    List<AreaDTO> listAreas(List<Integer> areaIds, String name, RequestQueryDTO requestQuery);
}
