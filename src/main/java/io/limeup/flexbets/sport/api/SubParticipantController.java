package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;
import io.limeup.flexbets.sport.service.SubParticipantService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/v1/sub-participants")
@RequiredArgsConstructor
@Tag(name = "Sub-Participants", description = "Manage sub-participant data")
@Validated
public class SubParticipantController {
    private final SubParticipantService subParticipantService;

    @GetMapping("/list")
    public ResponseEntity<PaginatedResponse<SubParticipantDTO>> listSubParticipants(
            @RequestParam(name = "competition_id") Integer competitionId,
            @RequestParam(required = false) List<String> positions,
            @RequestParam(required = false, name = "participant_ids") List<Integer> participantIds,
            @RequestParam(required = false, name = "market_id") Integer marketId,
            @ParameterObject @Valid RequestQueryDTO requestQuery) {
        return ResponseEntity.ok(subParticipantService.listSubParticipants(
                competitionId, positions, participantIds, marketId, requestQuery));
    }

    @GetMapping("/{sub-participant_id}")
    public ResponseEntity<SubParticipantDTO> getSubParticipantById(
            @PathVariable("sub-participant_id") Integer subParticipantId,
            @RequestParam(required = false, name = "market_id") Integer marketId) {
        return ResponseEntity.ok(subParticipantService.getSubParticipantById(subParticipantId, marketId));
    }
}
