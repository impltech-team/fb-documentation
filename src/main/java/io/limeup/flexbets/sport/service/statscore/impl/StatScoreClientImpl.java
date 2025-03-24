package io.limeup.flexbets.sport.service.statscore.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.limeup.flexbets.sport.dto.statscore.ListWrapper;
import io.limeup.flexbets.sport.dto.statscore.StatScoreResponse;
import io.limeup.flexbets.sport.dto.statscore.StatsScoreParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.ParticipantQueryParams;
import io.limeup.flexbets.sport.service.statscore.StatScoreClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@Service
public class StatScoreClientImpl implements StatScoreClient {

    private final WebClient webClient;

    private final ObjectMapper objectMapper;

    @Override
    public Mono<String> getEventSubParticipants(String eventId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/events/{eventId}/sub-participants")
                        .build(eventId))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<StatScoreResponse<ListWrapper<StatsScoreParticipantDTO>>> getParticipants(ParticipantQueryParams params) {
        return webClient.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder.path("/participants")
                            .queryParam("sport_id", params.getSportId());

                    if (params.getLimit() != null) builder.queryParam("limit", params.getLimit());
                    if (params.getPage() != null) builder.queryParam("page", params.getPage());
                    if (params.getSeasonId() != null) builder.queryParam("season_id", params.getSeasonId());
                    if (params.getAreaId() != null) builder.queryParam("area_id", params.getAreaId());
                    if (params.getSubtype() != null) builder.queryParam("subtype", params.getSubtype());
                    if (params.getMultiIds() != null) builder.queryParam("multi_ids", params.getMultiIds());

                    return builder.build();
                })
                .retrieve()
                .bodyToMono(String.class)
                .map(json -> {
                    StatScoreResponse<ListWrapper<StatsScoreParticipantDTO>> response = new StatScoreResponse<>();
                    List<StatsScoreParticipantDTO> items = new ArrayList<>();
                    JsonNode methodNode = null;

                    try {
                        JsonNode root = objectMapper.readTree(json);
                        methodNode = root.path("api").path("method");
                        JsonNode participantsNode = root.path("api").path("data").path("participants");
                        items = objectMapper
                                .readerFor(new TypeReference<List<StatsScoreParticipantDTO>>() {})
                                .readValue(participantsNode);
                    } catch (IOException e) {
                        log.info("Empty response from Sports-API participants.index {}", e.getStackTrace());
                    }
                    try {
                        StatScoreResponse.MethodInfo method = objectMapper
                                .treeToValue(methodNode, StatScoreResponse.MethodInfo.class);

                        ListWrapper<StatsScoreParticipantDTO> data = new ListWrapper<>();
                        data.setItems(items);

                        StatScoreResponse.ApiWrapper<ListWrapper<StatsScoreParticipantDTO>> api = new StatScoreResponse.ApiWrapper<>();
                        api.setMethod(method);
                        api.setData(data);

                        response.setApi(api);
                    } catch (Exception e) {
                        log.error("Error on JSON deserialization for response from Sports-API participants.index", e.getStackTrace());
                    }

                    return response;
                });
    }


    public Mono<StatsScoreParticipantDTO> getParticipantById(String participantId) {
        return webClient.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder.path("/participants/" + participantId);
                    return builder.build();
                })
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<StatScoreResponse<Map<String, StatsScoreParticipantDTO>>>() {})
                .map(resp -> resp.getApi().getData().get("participant"));
    }



}
