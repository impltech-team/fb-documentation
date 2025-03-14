package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.CompetitionDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/competitions")
@RequiredArgsConstructor
public class CompetitionController {
    private final CompetitionService competitionService;

    @GetMapping("/list")
    public ResponseEntity<List<CompetitionDTO>> listCompetitions(
            @RequestParam(required = false, name = "area_ids") List<Integer> areaIds,
            @RequestParam(required = false, name = "sport_ids") List<Integer> sportIds,
            @RequestParam(required = false, name = "date_from") String dateFrom,
            @RequestParam(required = false, name = "date_to") String dateTo,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false, name = "status_type") String statusType,
            @ParameterObject RequestQueryDTO requestQuery) {
        return ResponseEntity.ok(competitionService.listCompetitions(
                areaIds, sportIds, dateFrom, dateTo, type, gender, statusType, requestQuery));
    }
}
