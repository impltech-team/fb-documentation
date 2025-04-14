package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.service.AreaService;
import io.limeup.flexbets.sport.service.SportService;
import io.limeup.flexbets.sport.service.StatsService;
import io.limeup.flexbets.sport.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/data/fetch")
@RequiredArgsConstructor
public class SportDataPreFetchingController {

    private final StatsService statsService;
    private final AreaService areaService;
    private final SportService sportService;
    private final VenueService venueService;

    @PostMapping("/stats")
    public void statsFetch(@RequestParam(value = "days", required = false, defaultValue = "1") Integer days) {
        statsService.fetchStatData(days);
    }

    @PostMapping("/static")
    public void staticDataFetch() {
        areaService.fetchAreaData();
        venueService.fetchVenueData();
        sportService.fetchSportData();
    }

}
