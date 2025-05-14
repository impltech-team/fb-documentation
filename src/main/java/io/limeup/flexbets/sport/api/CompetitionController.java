package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.CompetitionDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.model.CompetitionType;
import io.limeup.flexbets.sport.model.StatusType;
import io.limeup.flexbets.sport.service.CompetitionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/competitions")
@RequiredArgsConstructor
@Validated
public class CompetitionController {
    private final CompetitionService competitionService;

    @GetMapping("/list")
    public ResponseEntity<PaginatedResponse<CompetitionDTO>> listCompetitions(
            @RequestParam(required = false, name = "area_id") Integer areaId,
            @RequestParam(required = false, name = "sport_id") Integer sportId,
            @RequestParam(required = false) CompetitionType type,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false, name = "status_type") StatusType statusType,
            @ParameterObject @Valid RequestQueryDTO requestQuery) {
        return ResponseEntity.ok(competitionService.listCompetitions(
                areaId, sportId, type, gender, statusType, requestQuery));
    }
}
