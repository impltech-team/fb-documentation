package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.service.AreaService;
import io.limeup.flexbets.sport.service.BatchJobRunner;
import io.limeup.flexbets.sport.service.CompetitionService;
import io.limeup.flexbets.sport.service.SportService;
import io.limeup.flexbets.sport.service.VenueService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/data/fetch")
@RequiredArgsConstructor
public class SportDataPreFetchingController {

    private final BatchJobRunner batchJobRunner;
    private final AreaService areaService;
    private final SportService sportService;
    private final VenueService venueService;
    private final CompetitionService competitionService;

    @Hidden
    @PostMapping("/stats/")
    public ResponseEntity<String> statsFetchScheduled(@RequestParam(value = "days", required = false, defaultValue = "5") Integer days) {
        return batchJobRunner.runStatsPreFetchingJob(days);
    }

    @PostMapping("/stats")
    public ResponseEntity<String> statsFetch(@RequestParam(value = "days", required = false, defaultValue = "5") Integer days) {
        return batchJobRunner.runStatsPreFetchingJob(days);
    }

    @PostMapping("/static")
    public void staticDataFetch() {
        areaService.fetchAreaData();
        venueService.fetchVenueData();
        sportService.fetchSportData();
        competitionService.fetchCompetitionData();
    }

}
