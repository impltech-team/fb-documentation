package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.MarketLiteDTO;
import io.limeup.flexbets.sport.model.MarketType;
import io.limeup.flexbets.sport.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/markets")
@RequiredArgsConstructor
public class MarketController {
    private final MarketService marketService;

    @GetMapping("/list")
    public ResponseEntity<List<MarketLiteDTO>> listMarkets(
            @RequestParam(name = "competition_id") Integer competitionId,
            @RequestParam(name = "marketType", required = false, defaultValue = "SUB_PARTICIPANT") MarketType marketType
    ) {
                return ResponseEntity.ok(marketService.listMarkets(competitionId, marketType));
    }
}
