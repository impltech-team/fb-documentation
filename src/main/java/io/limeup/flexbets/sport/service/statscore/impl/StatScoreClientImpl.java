package io.limeup.flexbets.sport.service.statscore.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.limeup.flexbets.sport.dto.statscore.ListWrapper;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreResponse;
import io.limeup.flexbets.sport.dto.statscore.StatScoreParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.ParticipantQueryParams;
import io.limeup.flexbets.sport.service.statscore.StatScoreClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class StatScoreClientImpl implements StatScoreClient {

    private final WebClient webClient;

    private final ObjectMapper objectMapper;

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreSubParticipantDTO>>> getEventSubParticipants(Integer eventId) {
        return fetchListWrapper(
                "/events/{eventId}/sub-participants",
                Map.of("eventId", eventId),
                Map.of(),
                "sub_participants",
                new TypeReference<>() {
                }
        );
    }


    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreParticipantDTO>>> getParticipants(ParticipantQueryParams params) {
        Map<String, Object> queryParams = new LinkedHashMap<>();
        queryParams.put("sport_id", params.getSportId());
        queryParams.put("limit", params.getLimit());
        queryParams.put("page", params.getPage());
        queryParams.put("season_id", params.getSeasonId());
        queryParams.put("area_id", params.getAreaId());
        queryParams.put("subtype", Optional.ofNullable(params.getSubtype()).map(Enum::name).orElse(null));
        queryParams.put("type", Optional.ofNullable(params.getType()).map(Enum::name).orElse(null));
        queryParams.put("virtual", params.getVirtual());
        queryParams.put("multi_ids", params.getMultiIds());

        return fetchListWrapper(
                "/participants",
                Map.of(),
                queryParams,
                "participants",
                new TypeReference<>() {
                }
        );
    }

    @Override
    public Mono<StatScoreParticipantDTO> getParticipantById(Integer participantId) {
        return webClient.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder.path("/participants/" + participantId);
                    return builder.build();
                })
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<StatScoreResponse<Map<String, StatScoreParticipantDTO>>>() {})
                .map(resp -> resp.getApi().getData().get("participant"));
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreCompetitionDTO>>> getEvents(EventQueryParams query) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put("date_from", query.getDateFrom());
        queryParams.put("date_to", query.getDateTo());
        queryParams.put("sport_id", query.getSportId());
        queryParams.put("area_id", query.getAreaId());
        queryParams.put("competition_id", query.getCompetitionId());
        queryParams.put("season_id", query.getSeasonId());
        queryParams.put("stage_id", query.getStageId());
        queryParams.put("group_id", query.getGroupId());
        queryParams.put("participant_id", query.getParticipantId());
        queryParams.put("multi_ids", query.getMultiIds());
        queryParams.put("venue_type", query.getVenueType());
        queryParams.put("venue_id", query.getVenueId());
        queryParams.put("sort_type", query.getSortType());
        queryParams.put("sort_order", query.getSortOrder());
        queryParams.put("relation_status", query.getRelationStatus());
        queryParams.put("status_id", query.getStatusId());
        queryParams.put("status_type", query.getStatusType());
        queryParams.put("coverage_type", query.getCoverageType());
        queryParams.put("scoutsfeed", query.getScoutsfeed());
        queryParams.put("events_details", query.getEventsDetails());
        queryParams.put("competitions_details", query.getCompetitionsDetails());
        queryParams.put("item_status", query.getItemStatus());
        queryParams.put("verified_result", query.getVerifiedResult());
        queryParams.put("product", query.getProduct());
        queryParams.put("booked", query.getBooked());
        queryParams.put("tz", query.getTz());
        queryParams.put("timestamp", query.getTimestamp());
        queryParams.put("page", query.getPage());
        queryParams.put("limit", query.getLimit());

        return fetchListWrapper(
                "/events",
                Map.of(),
                queryParams,
                "competitions",
                new TypeReference<>() {
                }
        );
    }

    @Override
    public Mono<StatScoreCompetitionDTO> getEventById(Integer eventId) {
        return webClient.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder.path("/events/" + eventId);
                    return builder.build();
                })
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<StatScoreResponse<Map<String, StatScoreCompetitionDTO>>>() {})
                .map(resp -> resp.getApi().getData().get("competition"));
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreSubParticipantDTO>>> getSquadSubParticipants(Integer participantId, Integer seasonId) {
        Map<String, Object> queryParams = new LinkedHashMap<>();
        queryParams.put("season_id", seasonId);
        return fetchListWrapper(
                "/participants/{participant_id}/squad",
                Map.of("participant_id", participantId),
                queryParams,
                "participants",
                new TypeReference<>() {
                }
        );
    }

    private <T> Mono<StatScoreResponse<ListWrapper<T>>> fetchListWrapper(
            String url,
            Map<String, Object> uriVariables,
            Map<String, Object> queryParams,
            String listNodeName,
            TypeReference<List<T>> itemType
    ) {
        return webClient.get()
                .uri(uriBuilder -> {
                    UriBuilder builder = uriBuilder.path(url);
                    if (queryParams != null) {
                        queryParams.forEach((key, value) -> {
                            if (value != null) builder.queryParam(key, value);
                        });
                    }
                    return builder.build(uriVariables);
                })
                .retrieve()
                .bodyToMono(String.class)
                .map(json -> {
                    StatScoreResponse<ListWrapper<T>> response = new StatScoreResponse<>();
                    List<T> items = new ArrayList<>();
                    JsonNode methodNode = null;

                    try {
                        JsonNode root = objectMapper.readTree(json);
                        methodNode = root.path("api").path("method");
                        JsonNode node = root.path("api").path("data").path(listNodeName);
                        items = objectMapper.readerFor(itemType).readValue(node);
                    } catch (IOException e) {
                        log.warn("Empty or malformed response from Sports-API ({})", url, e);
                    }

                    try {
                        StatScoreResponse.MethodInfo method = objectMapper.treeToValue(methodNode, StatScoreResponse.MethodInfo.class);
                        ListWrapper<T> wrapper = new ListWrapper<>();
                        wrapper.setItems(items);

                        StatScoreResponse.ApiWrapper<ListWrapper<T>> api = new StatScoreResponse.ApiWrapper<>();
                        api.setMethod(method);
                        api.setData(wrapper);

                        response.setApi(api);
                    } catch (Exception e) {
                        log.error("Error building StatScoreResponse from Sports-API ({})", url, e);
                    }

                    return response;
                });
    }
}
