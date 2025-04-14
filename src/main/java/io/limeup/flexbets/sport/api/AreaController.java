package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.AreaDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/areas")
@RequiredArgsConstructor
public class AreaController {
    private final AreaService areaService;

    @GetMapping("/list")
    public ResponseEntity<PaginatedResponse<AreaDTO>> listAreas(
            @RequestParam(required = false, name = "area_ids") List<Integer> areaIds,
            @RequestParam(required = false) String name,
            @ParameterObject RequestQueryDTO requestQuery) {
        return ResponseEntity.ok(areaService.listAreas(areaIds, name, requestQuery));
    }
}
