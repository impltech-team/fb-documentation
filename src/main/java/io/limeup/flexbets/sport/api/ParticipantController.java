package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.ParticipantDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/participants")
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;

    @GetMapping("/list")
    public ResponseEntity<List<ParticipantDTO>> listParticipants(
            @RequestParam(name = "competition_id") Integer competitionId,
            @RequestParam(required = false, name = "participant_ids") List<Integer> participantIds,
            RequestQueryDTO requestQuery) {
        return ResponseEntity.ok(participantService.listParticipants(
                competitionId, participantIds, requestQuery));
    }

    @GetMapping("/{participant_id}")
    public ResponseEntity<ParticipantDTO> getParticipantById(@PathVariable("participant_id") Long participantId) {
        return ResponseEntity.ok(participantService.getParticipantById(participantId));
    }
}
