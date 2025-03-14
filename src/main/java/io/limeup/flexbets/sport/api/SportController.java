package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SportDTO;
import io.limeup.flexbets.sport.service.SportService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/sports")
@RequiredArgsConstructor
public class SportController {
    private final SportService sportService;

    @GetMapping("/list")
    public ResponseEntity<List<SportDTO>> listSports(
            @RequestParam(required = false, name = "sport_ids") List<Integer> sportIds,
            @RequestParam(required = false) String name,
            @ParameterObject RequestQueryDTO requestQuery) {
        return ResponseEntity.ok(sportService.listSports(sportIds, name, requestQuery));
    }

    @GetMapping("/{sport_id}")
    public ResponseEntity<SportDTO> getSportById(@PathVariable("sport_id") Integer sportId) {
        return ResponseEntity.ok(sportService.getSportById(sportId));
    }
}
