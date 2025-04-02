package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.AreaDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.model.Area;

import java.util.List;

public interface AreaService extends ReadService<Area, AreaDTO, Long> {

    List<AreaDTO> listAreas(List<Integer> areaIds, String name, RequestQueryDTO requestQuery);

    void fetchAreaData();

}
