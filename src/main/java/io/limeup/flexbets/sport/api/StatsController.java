package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.StatsBatchRequestDTO;
import io.limeup.flexbets.sport.dto.StatsResponseDTO;
import io.limeup.flexbets.sport.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @PostMapping("/batch")
    public ResponseEntity<List<StatsResponseDTO>> listBatchStats(@RequestBody StatsBatchRequestDTO request) {
        return ResponseEntity.ok(statsService.listBatchStats(request));
    }

}
