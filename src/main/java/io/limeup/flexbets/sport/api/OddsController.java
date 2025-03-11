package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.OddsBatchRequestDTO;
import io.limeup.flexbets.sport.dto.OddsResponseDTO;
import io.limeup.flexbets.sport.service.OddsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/odds")
@RequiredArgsConstructor
public class OddsController {
    private final OddsService oddsService;

    @PostMapping("/batch")
    public ResponseEntity<List<OddsResponseDTO>> listBatchOdds(@RequestBody OddsBatchRequestDTO request) {
        return ResponseEntity.ok(oddsService.listBatchOdds(request));
    }
}
