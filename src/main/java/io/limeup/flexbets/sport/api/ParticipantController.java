package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.ParticipantDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.service.ParticipantService;
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
    private final ParticipantService participantService;

    @GetMapping("/list")
    public ResponseEntity<PaginatedResponse<ParticipantDTO>> listParticipants(
            @RequestParam(name = "competition_id") Integer competitionId,
            @RequestParam(required = false, name = "participant_ids") List<Integer> participantIds,
            @RequestParam(required = false, name = "market_id") Integer market_id,
            @ParameterObject @Valid RequestQueryDTO requestQuery) {
        return ResponseEntity.ok(participantService.listParticipants(
                competitionId, participantIds, market_id, requestQuery));
    }

    @GetMapping("/{participant_id}")
    public ResponseEntity<ParticipantDTO> getParticipantById(@PathVariable("participant_id") Integer participantId,
                                                             @RequestParam(required = false, name = "market_id") Integer marketId) {
        return ResponseEntity.ok(participantService.getParticipantById(participantId, marketId));
    }
}
