package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.CompetitionDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.service.CompetitionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionService {
    @Override
    public List<CompetitionDTO> listCompetitions(List<Integer> areaIds, List<Integer> sportIds, String dateFrom, String dateTo, String type, String gender, String statusType, RequestQueryDTO requestQuery) {
        return null;
    }
}
