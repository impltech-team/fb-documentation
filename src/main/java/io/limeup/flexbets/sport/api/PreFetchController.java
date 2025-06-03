package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.AreaDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.PrefetchStatusDTO;
import io.limeup.flexbets.sport.service.PreFetchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/prefetch")
@RequiredArgsConstructor
@Validated
public class PreFetchController {

    private final PreFetchService preFetchService;

    @GetMapping("/check")
    public ResponseEntity<List<PrefetchStatusDTO>> listAreas() {
        List<PrefetchStatusDTO> status = preFetchService.preFetchStatus();
        return ResponseEntity.ok(status);

    }
}
