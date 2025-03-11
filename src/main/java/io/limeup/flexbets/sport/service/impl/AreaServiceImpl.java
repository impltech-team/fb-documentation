package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.AreaDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.service.AreaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Override
    public List<AreaDTO> listAreas(List<Integer> areaIds, String name, RequestQueryDTO requestQuery) {
        return null;
    }
}
