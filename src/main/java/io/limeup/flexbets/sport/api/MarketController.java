package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.MarketDTO;
import io.limeup.flexbets.sport.dto.MarketLiteDTO;
import io.limeup.flexbets.sport.model.enums.MarketType;
import io.limeup.flexbets.sport.service.MarketService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            @RequestParam(name = "marketType", required = false) MarketType marketType
    ) {
        return ResponseEntity.ok(marketService.listMarkets(competitionId, marketType));
    }

    @Hidden
    @GetMapping
    public List<MarketDTO> getAllMarketsFullDTO() {
        return marketService.getAllMarketsFullDTO();
    }

    @Hidden
    @PostMapping
    public MarketDTO createMarket(@RequestBody MarketDTO dto) {
        return marketService.createMarket(dto);
    }

    @Hidden
    @PutMapping("/{id}")
    public MarketDTO updateMarket(@PathVariable Integer id, @RequestBody MarketDTO dto) {
        return marketService.updateMarket(id, dto);
    }

    @Hidden
    @DeleteMapping("/{id}")
    public void deleteMarket(@PathVariable Integer id) {
        marketService.deleteMarket(id);
    }

    @Hidden
    @PostMapping("/{id}/enable")
    public void enableMarket(@PathVariable Integer id) {
        marketService.setMarketEnabled(id, true);
    }

    @Hidden
    @PostMapping("/{id}/disable")
    public void disableMarket(@PathVariable Integer id) {
        marketService.setMarketEnabled(id, false);
    }
}
