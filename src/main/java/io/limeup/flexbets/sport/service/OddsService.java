package io.limeup.flexbets.sport.service;

import io.limeup.flexbets.sport.dto.OddsBatchRequestDTO;
import io.limeup.flexbets.sport.dto.OddsResponseDTO;

import java.util.List;

public interface OddsService {
    List<OddsResponseDTO> listBatchOdds(OddsBatchRequestDTO request);
}
