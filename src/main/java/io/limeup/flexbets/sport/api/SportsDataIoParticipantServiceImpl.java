package io.limeup.flexbets.sport.api;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.ParticipantDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.error.FlexBetsSportNotFoundException;
import io.limeup.flexbets.sport.model.IoTeam;
import io.limeup.flexbets.sport.model.IoTeamNFL;
import io.limeup.flexbets.sport.repository.sportsdataio.IoTeamNFLRepository;
import io.limeup.flexbets.sport.repository.sportsdataio.IoTeamRepository;
import io.limeup.flexbets.sport.service.ParticipantService;
import io.limeup.flexbets.sport.utils.PaginationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service("sportsDataIoParticipantService")
@Transactional
public class SportsDataIoParticipantServiceImpl implements ParticipantService {

    private static final int COMPETITION_ID_NFL = 5611;
    private static final int COMPETITION_ID_MLB = 5466;

    private final IoTeamRepository teamRepository;
    private final IoTeamNFLRepository teamNFLRepository;

    public SportsDataIoParticipantServiceImpl(IoTeamRepository teamRepository, IoTeamNFLRepository teamNFLRepository) {
        this.teamRepository = teamRepository;
        this.teamNFLRepository = teamNFLRepository;
    }

    @Override
    public PaginatedResponse<ParticipantDTO> listParticipants(Integer competitionId,
                                                              List<Integer> participantIds,
                                                              Integer marketId,
                                                              Integer maxHistoricalDataCount,
                                                              RequestQueryDTO requestQuery) {
        List<ParticipantDTO> participantList;

        if (competitionId != null && competitionId == COMPETITION_ID_NFL) {
            List<IoTeamNFL> teams = getNFLTeams(participantIds);
            participantList = teams.stream()
                    .map(team -> toDto(team, COMPETITION_ID_NFL, "NFL"))
                    .toList();
        } else {
            List<IoTeam> teams = getMLBTeams(participantIds);
            participantList = teams.stream()
                    .map(team -> toDto(team, COMPETITION_ID_MLB, "MLB"))
                    .toList();
        }

        int totalCount = participantList.size();
        int fromIndex = getPageOffset(requestQuery);
        int toIndex = Math.min(fromIndex + getPageSize(requestQuery), totalCount);

        List<ParticipantDTO> pagedList = fromIndex > toIndex
                ? Collections.emptyList()
                : participantList.subList(fromIndex, toIndex);

        return PaginationUtils.buildPaginatedResponse(
                pagedList,
                (long) totalCount,
                requestQuery != null ? requestQuery.getPage() : null,
                requestQuery != null ? requestQuery.getPageSize() : null
        );
    }

    @Override
    public ParticipantDTO getParticipantById(Integer competitionId, Integer participantId, Integer marketId, Integer maxHistoricalDataCount) {
        if (participantId == null || competitionId == null) {
            throw new FlexBetsSportNotFoundException("Invalid participant or competition ID");
        }

        if (competitionId.equals(COMPETITION_ID_NFL)) {
            // Fetch from NFL repository
            return teamNFLRepository.findByTeamId(participantId.longValue())
                    .map(team -> toDto(team, COMPETITION_ID_NFL, "NFL"))
                    .orElseThrow(() -> new FlexBetsSportNotFoundException("NFL Participant " + participantId + " Not Found"));
        } else if (competitionId.equals(COMPETITION_ID_MLB)) {
            // Fetch from MLB repository
            return teamRepository.findByTeamId(participantId.longValue())
                    .map(team -> toDto(team, COMPETITION_ID_MLB, "MLB"))
                    .orElseThrow(() -> new FlexBetsSportNotFoundException("MLB Participant " + participantId + " Not Found"));
        } else {
            throw new FlexBetsSportNotFoundException("Unsupported competition ID: " + competitionId);
        }
    }

    // ========== Helpers ==========

    private List<IoTeamNFL> getNFLTeams(List<Integer> participantIds) {
        if (participantIds != null && !participantIds.isEmpty()) {
            Set<Long> ids = participantIds.stream().map(Integer::longValue).collect(Collectors.toSet());
            return teamNFLRepository.findAllByTeamIdIn(ids);
        }
        return teamNFLRepository.findAll();
    }

    private List<IoTeam> getMLBTeams(List<Integer> participantIds) {
        if (participantIds != null && !participantIds.isEmpty()) {
            Set<Long> ids = participantIds.stream().map(Integer::longValue).collect(Collectors.toSet());
            return teamRepository.findAllByTeamIdIn(ids);
        }
        return teamRepository.findAll();
    }

    private ParticipantDTO toDto(IoTeam team, int competitionId, String competitionName) {
        return ParticipantDTO.builder()
                .id(team.getTeamId().intValue())
                .teamName(team.getName())
                .acronym(team.getKey())
                .competition(competitionName)
                .competitionId(competitionId)
                .historicalStats(new ArrayList<>())
                .odds(new ArrayList<>())
                .build();
    }

    private ParticipantDTO toDto(IoTeamNFL team, int competitionId, String competitionName) {
        return ParticipantDTO.builder()
                .id(team.getTeamId().intValue())
                .teamName(team.getName())
                .acronym(team.getKey())
                .competition(competitionName)
                .competitionId(competitionId)
                .historicalStats(new ArrayList<>())
                .odds(new ArrayList<>())
                .build();
    }

    private int getPageOffset(RequestQueryDTO requestQuery) {
        return requestQuery != null ? (requestQuery.getPage() - 1) * requestQuery.getPageSize() : 0;
    }

    private int getPageSize(RequestQueryDTO requestQuery) {
        return requestQuery != null ? requestQuery.getPageSize() : Integer.MAX_VALUE;
    }
}
