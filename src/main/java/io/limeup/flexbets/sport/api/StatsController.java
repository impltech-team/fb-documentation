package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.StatsBatchRequestDTO;
import io.limeup.flexbets.sport.dto.StatsResponseDTO;
import io.limeup.flexbets.sport.service.AreaService;
import io.limeup.flexbets.sport.service.SportService;
import io.limeup.flexbets.sport.service.StatsService;
import io.limeup.flexbets.sport.service.VenueService;
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
    private final AreaService areaService;
    private final SportService sportService;
    private final VenueService venueService;

    @PostMapping("/batch")
    public ResponseEntity<List<StatsResponseDTO>> listBatchStats(@RequestBody StatsBatchRequestDTO request) {
        return ResponseEntity.ok(statsService.listBatchStats(request));
    }

    @PostMapping("/test")
    public void test() {
        statsService.fetchStatData(1);
    }

    @PostMapping("/test2")
    public void test2() {
        areaService.fetchAreaData();
        venueService.fetchVenueData();
        sportService.fetchSportData();
    }
}
