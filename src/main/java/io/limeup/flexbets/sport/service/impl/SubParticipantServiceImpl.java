package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.service.SubParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubParticipantServiceImpl implements SubParticipantService {

    private final StatScoreProxyServiceImpl statScoreService;

    @Override
    public List<SubParticipantDTO> listSubParticipants(Integer competitionId, List<String> positions, List<Integer> participantIds, Integer marketId, RequestQueryDTO requestQuery) {
        return null;
    }

    @Override
    public SubParticipantDTO getSubParticipantById(Integer subParticipantId) {
        return null;
    }

    @Override
    public void fetchStatData(int durationDays) {
        List<StatScoreEventDTO> events = statScoreService.getAllEvents(EventQueryParams
                .builder()
                .limit(500)
                .dateFrom(LocalDateTime.now())
                .dateTo(LocalDateTime.now().plusDays(durationDays))
                .build());
    }
}
