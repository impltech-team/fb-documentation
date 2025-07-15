package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;
import io.limeup.flexbets.sport.service.SubParticipantService;
import io.limeup.flexbets.sport.service.resolver.SubParticipantServiceResolver;
import io.limeup.flexbets.sport.validator.PositiveList;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/sub-participants")
@RequiredArgsConstructor
@Tag(name = "Sub-Participants", description = "Manage sub-participant data")
@Validated
public class SubParticipantController {
    private final SubParticipantServiceResolver serviceResolver;

    @GetMapping("/list")
    public ResponseEntity<PaginatedResponse<SubParticipantDTO>> listSubParticipants(
            @RequestParam(name = "competition_id") Integer competitionId,
            @PositiveList(checkPositive = false)
            @RequestParam(required = false, name = "positions") List<String> positions,
            @PositiveList
            @RequestParam(required = false, name = "participant_ids") List<Integer> participantIds,
            @RequestParam(required = false, name = "market_id") Integer marketId,
            @RequestParam(required = false, name = "odds") Boolean odds,
            @RequestParam(required = false, name = "max_historical_data_count", defaultValue = "5") Integer maxHistoricalDataCount,
            @ParameterObject @Valid RequestQueryDTO requestQuery) {
        SubParticipantService service = serviceResolver.resolve(competitionId.toString());
        return ResponseEntity.ok(service.listSubParticipants(
                competitionId, positions, participantIds, marketId, odds, maxHistoricalDataCount, requestQuery));
    }

    @GetMapping("/{sub-participant_id}")
    public ResponseEntity<SubParticipantDTO> getSubParticipantById(
            @PathVariable("sub-participant_id") Integer subParticipantId,
            @RequestParam(name = "competition_id") Integer competitionId,
            @RequestParam(required = false, name = "market_id") Integer marketId,
            @RequestParam(required = false, name = "max_historical_data_count", defaultValue = "5") Integer maxHistoricalDataCount) {
        SubParticipantService service = serviceResolver.resolve(competitionId.toString());
        return ResponseEntity.ok(service.getSubParticipantById(subParticipantId, marketId, maxHistoricalDataCount));
    }
}
