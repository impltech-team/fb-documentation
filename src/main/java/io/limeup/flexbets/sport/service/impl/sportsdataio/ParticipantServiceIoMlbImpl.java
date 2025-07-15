package io.limeup.flexbets.sport.service.impl.sportsdataio;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.ParticipantDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.model.IoTeam;
import io.limeup.flexbets.sport.repository.sportsdataio.IoTeamRepository;
import io.limeup.flexbets.sport.service.ParticipantService;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("sportsDataIoParticipantService")
@Transactional
public class ParticipantServiceIoMlbImpl implements ParticipantService {

    private static final int COMPETITION_ID = 5466;
    private static final String COMPETITION_NAME = "MLB";

    private final IoTeamRepository teamRepository;

    public ParticipantServiceIoMlbImpl(IoTeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public PaginatedResponse<ParticipantDTO> listParticipants(Integer competitionId,
                                                              List<Integer> participantIds,
                                                              Integer marketId,
                                                              Integer maxHistoricalDataCount,
                                                              RequestQueryDTO requestQuery) {
        List<IoTeam> teams;
        if (participantIds != null && !participantIds.isEmpty()) {
            Set<Long> ids = participantIds.stream()
                    .map(Integer::longValue)
                    .collect(Collectors.toSet());
            teams = teamRepository.findAllByTeamIdIn(ids);
        } else {
            teams = teamRepository.findAll();
        }

        long count = teams.size();
        int from = requestQuery != null ? (requestQuery.getPage() - 1) * requestQuery.getPageSize() : 0;
        int to = requestQuery != null ? Math.min(from + requestQuery.getPageSize(), teams.size()) : teams.size();
        if (from > to) {
            teams = Collections.emptyList();
        } else {
            teams = teams.subList(from, to);
        }

        List<ParticipantDTO> dtoList = teams.stream()
                .map(this::toDto)
                .toList();

        Integer page = requestQuery != null ? requestQuery.getPage() : null;
        Integer pageSize = requestQuery != null ? requestQuery.getPageSize() : null;
        return PaginationUtils.buildPaginatedResponse(dtoList, count, page, pageSize);
    }

    @Override
    public ParticipantDTO getParticipantById(Integer participantId, Integer marketId, Integer maxHistoricalDataCount) {
        IoTeam team = teamRepository.findByTeamId(participantId.longValue())
                .orElseThrow(() -> new FlexBetsSportNotFoundException(
                        String.format("Participant %s Not Found", participantId)));
        return toDto(team);
    }

    private ParticipantDTO toDto(IoTeam team) {
        ParticipantDTO dto = new ParticipantDTO();
        dto.setId(team.getTeamId().intValue());
        dto.setTeamName(team.getName());
        dto.setAcronym(team.getKey());
        dto.setCompetition(COMPETITION_NAME);
        dto.setCompetitionId(COMPETITION_ID);
        dto.setHistoricalStats(new ArrayList<>());
        dto.setOdds(new ArrayList<>());
        return dto;
    }
}