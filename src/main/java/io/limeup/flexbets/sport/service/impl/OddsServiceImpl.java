package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.service.OddsService;
import io.limeup.flexbets.sport.dto.OddsBatchRequestDTO;
import io.limeup.flexbets.sport.dto.OddsResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OddsServiceImpl implements OddsService {
    @Override
    public List<OddsResponseDTO> listBatchOdds(OddsBatchRequestDTO request) {
        return null;
    }
}
