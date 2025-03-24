package io.limeup.flexbets.sport.service.impl;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.ParticipantDTO;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.statscore.ListWrapper;
import io.limeup.flexbets.sport.dto.statscore.StatScoreResponse;
import io.limeup.flexbets.sport.dto.statscore.StatsScoreParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.ParticipantQueryParams;
import io.limeup.flexbets.sport.mapper.StatScoreMapper;
import io.limeup.flexbets.sport.service.ParticipantService;
import io.limeup.flexbets.sport.service.statscore.StatScoreClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final StatScoreClient statScoreClient;
    private final StatScoreMapper statScoreMapper;

    @Override
    public PaginatedResponse<ParticipantDTO> listParticipants(Integer competitionId, List<Integer> participantIds, RequestQueryDTO requestQuery) {
        StatScoreResponse<ListWrapper<StatsScoreParticipantDTO>> result = statScoreClient.getParticipants(ParticipantQueryParams.builder()
                .multiIds(!CollectionUtils.isEmpty(participantIds) ? participantIds.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")) : null)
                //after data prefetching we gonna map competition_id to a season_id and sport_id, so this val won't be static
                .sportId(1)
                .seasonId(62587)
                .page(requestQuery.getPage())
                .limit(requestQuery.getPageSize())
                .build()).block();
        return PaginatedResponse.<ParticipantDTO>builder()
                .count(result.getApi().getMethod().getTotal_items())
                .page(requestQuery.getPage())
                .pageSize(requestQuery.getPageSize())
                .totalPages((int) Math.ceil((double) result.getApi().getMethod().getTotal_items() / requestQuery.getPageSize()))
                .items(result.getApi().getData().getItems().stream()
                        .map(statScoreMapper::mapToParticipantDTO)
                        .toList())
                .build();
    }

    @Override
    public ParticipantDTO getParticipantById(Long participantId) {
        return statScoreMapper.mapToParticipantDTO(statScoreClient.getParticipantById(participantId.toString()).block());
    }
}
