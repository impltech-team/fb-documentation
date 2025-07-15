package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.ParticipantDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.service.ParticipantService;
import io.limeup.flexbets.sport.service.resolver.ParticipantServiceResolver;
import io.limeup.flexbets.sport.validator.PositiveList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/participants")
@RequiredArgsConstructor
@Validated
public class ParticipantController {
    private final ParticipantServiceResolver serviceResolver;

    @GetMapping("/list")
    public ResponseEntity<PaginatedResponse<ParticipantDTO>> listParticipants(
            @RequestParam(name = "competition_id") Integer competitionId,
            @PositiveList
            @RequestParam(required = false, name = "participant_ids") List<Integer> participantIds,
            @RequestParam(required = false, name = "market_id") Integer marketId,
            @RequestParam(required = false, name = "max_historical_data_count", defaultValue = "5") Integer maxHistoricalDataCount,
            @ParameterObject @Valid RequestQueryDTO requestQuery) {
        ParticipantService service = serviceResolver.resolve(competitionId.toString());
        return ResponseEntity.ok(service.listParticipants(
                competitionId, participantIds, marketId, maxHistoricalDataCount, requestQuery));
    }

    @GetMapping("/{participant_id}")
    public ResponseEntity<ParticipantDTO> getParticipantById(
            @PathVariable("participant_id") Integer participantId,
            @RequestParam(name = "competition_id") Integer competitionId,
            @RequestParam(required = false, name = "market_id") Integer marketId,
            @RequestParam(required = false, name = "max_historical_data_count", defaultValue = "5") Integer maxHistoricalDataCount) {
        ParticipantService service = serviceResolver.resolve(competitionId.toString());
        return ResponseEntity.ok(service.getParticipantById(competitionId ,participantId, marketId, maxHistoricalDataCount));
    }
}
