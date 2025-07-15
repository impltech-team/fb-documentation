package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.sportsdata.MLBDataPlayerGameDTO;
import io.limeup.flexbets.sport.service.sportdataio.SportsDataMlbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/mlb")
@RequiredArgsConstructor
@Validated
public class MLBGameController {
    private final SportsDataMlbService sportsDataMlbService;

    @GetMapping("/events/{event_id}/live/data/player/{player_id}")
    public ResponseEntity<MLBDataPlayerGameDTO> getEventById(@PathVariable("event_id") Integer eventId,
                                                             @PathVariable("player_id") Long playerId) {
        return ResponseEntity.ok(sportsDataMlbService.getPlayerGameDataByGameIdAndPlayerId(eventId, playerId));
    }
}
