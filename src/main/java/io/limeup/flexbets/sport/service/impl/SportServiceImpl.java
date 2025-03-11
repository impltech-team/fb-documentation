package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SportDTO;
import io.limeup.flexbets.sport.service.SportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportServiceImpl implements SportService {
    @Override
    public List<SportDTO> listSports(List<Integer> sportIds, String name, RequestQueryDTO requestQuery) {
        return null;
    }

    @Override
    public SportDTO getSportById(Integer sportId) {
        return null;
    }
}
