package io.limeup.flexbets.sport.service.statscore.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.limeup.flexbets.sport.dto.statscore.ListWrapper;
import io.limeup.flexbets.sport.dto.statscore.StatScoreAreaDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreBracketDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreResponse;
import io.limeup.flexbets.sport.dto.statscore.StatScoreParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportLiteDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreStandingDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreVenueDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.AreaQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.CompetitionQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.GroupQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.ParticipantQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.SportQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StandingByIdQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StandingQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.SeasonQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StageQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.VenueQueryParams;
import io.limeup.flexbets.sport.service.statscore.StatScoreClient;
import io.limeup.flexbets.sport.utils.StatScoreDateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Mono<StatScoreResponse<StatScoreParticipantDTO>> getParticipantById(Integer participantId) {
        return fetchSingleNodeWrapped(
                "/participants/" + participantId,
                Map.of(),
                Map.of(),
                "participant",
                new TypeReference<>() {}
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreCompetitionDTO>>> getEvents(EventQueryParams query) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put("date_from", StatScoreDateTimeUtils.formatDateTime(query.getDateFrom()));
        queryParams.put("date_to", StatScoreDateTimeUtils.formatDateTime(query.getDateTo()));
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
    public Mono<StatScoreResponse<StatScoreCompetitionDTO>> getEventById(Integer eventId) {
        return fetchSingleNodeWrapped(
                "/events/" + eventId,
                Map.of(),
                Map.of(),
                "competition",
                new TypeReference<>() {}
        );
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

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreAreaDTO>>> getAreas(AreaQueryParams query) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put("lang", query.getLang());
        queryParams.put("page", query.getPage());
        queryParams.put("limit", query.getLimit());
        queryParams.put("parent_area_id", query.getParentAreaId());
        queryParams.put("timestamp", query.getTimestamp());

        return fetchListWrapper(
                "/areas",
                Map.of(),
                queryParams,
                "areas",
                new TypeReference<>() {}
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreSportLiteDTO>>> getSports(SportQueryParams query) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put("page", query.getPage());
        queryParams.put("limit", query.getLimit());
        queryParams.put("timestamp", query.getTimestamp());

        return fetchListWrapper(
                "/sports",
                Map.of(),
                queryParams,
                "sports",
                new TypeReference<>() {}
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreSportDTO>> getSportById(Integer sportId) {
        return fetchSingleNodeWrapped(
                "/sports/" + sportId,
                Map.of(),
                Map.of(),
                "sport",
                new TypeReference<>() {}
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreVenueDTO>>> getVenues(VenueQueryParams query) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put("sport_id", query.getSportId());
        queryParams.put("participant_id", query.getParticipantId());
        queryParams.put("timestamp", query.getTimestamp());
        queryParams.put("page", query.getPage());
        queryParams.put("limit", query.getLimit());

        return fetchListWrapper(
                "/venues",
                Map.of(),
                queryParams,
                "venues",
                new TypeReference<>() {}
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreVenueDTO>> getVenueById(Integer venueId) {
        return fetchSingleNodeWrapped(
                "/venues/" + venueId,
                Map.of(),
                Map.of(),
                "venue",
                new TypeReference<>() {}
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreBracketDTO>>> getBrackets(Integer stageId) {
        return fetchListWrapper(
                "/brackets/{stage_id}",
                Map.of("stage_id", stageId),
                Map.of(),
                "nodes",
                new TypeReference<>() {}
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreCompetitionDTO>> getGroups(GroupQueryParams query) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put("stage_id", query.getStageId());
        queryParams.put("timestamp", query.getTimestamp());
        queryParams.put("page", query.getPage());
        queryParams.put("limit", query.getLimit());

        return fetchSingleNodeWrapped(
                "/groups",
                Map.of(),
                queryParams,
                "competition",
                new TypeReference<>() {}
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreCompetitionDTO>>> getSeasons(SeasonQueryParams query) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put("lang", query.getLang());
        queryParams.put("page", query.getPage());
        queryParams.put("limit", query.getLimit());
        queryParams.put("sport_id", query.getSportId());
        queryParams.put("competition_id", query.getCompetitionId());
        queryParams.put("participant_id", query.getParticipantId());
        queryParams.put("multi_ids", query.getMultiIds());
        queryParams.put("year", query.getYear());
        queryParams.put("sort_type", query.getSortType());
        queryParams.put("sort_order", query.getSortOrder());
        queryParams.put("area_id", query.getAreaId());
        queryParams.put("timestamp", query.getTimestamp());

        return fetchListWrapper(
                "/seasons",
                Map.of(),
                Map.of(),
                "competitions",
                new TypeReference<>() {}
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreCompetitionDTO>> getSeasonById(Integer seasonId) {
        return fetchSingleNodeWrapped(
                "/seasons/" + seasonId,
                Map.of(),
                Map.of(),
                "competition",
                new TypeReference<>() {}
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreCompetitionDTO>> getStages(StageQueryParams query) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put("season_id", query.getSeasonId());
        queryParams.put("timestamp", query.getTimestamp());
        queryParams.put("page", query.getPage());
        queryParams.put("limit", query.getLimit());

        return fetchSingleNodeWrapped(
                "/stages",
                Map.of(),
                queryParams,
                "competition",
                new TypeReference<>() {}
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreCompetitionDTO>> getStageById(Integer stageId) {
        return fetchSingleNodeWrapped(
                "/stages/" + stageId,
                Map.of(),
                Map.of(),
                "competition",
                new TypeReference<>() {}
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreStandingDTO>>> getStandings(StandingQueryParams query) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put("lang", query.getLang());
        queryParams.put("page", query.getPage());
        queryParams.put("limit", query.getLimit());
        queryParams.put("object_type", query.getObjectType());
        queryParams.put("object_id", query.getObjectId());
        queryParams.put("type_id", query.getTypeId());
        queryParams.put("subtype", query.getSubtype());
        queryParams.put("sport_id", query.getSportId());
        queryParams.put("competition_id", query.getCompetitionId());
        queryParams.put("season_id", query.getSeasonId());
        queryParams.put("stage_id", query.getStageId());
        queryParams.put("timestamp", query.getTimestamp());
        queryParams.put("item_status", query.getItemStatus());

        return fetchListWrapper(
                "/standings",
                Map.of(),
                queryParams,
                "standings_list",
                new TypeReference<>() {}
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreStandingDTO>> getStandingById(Integer standingId, StandingByIdQueryParams query) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put("lang", query.getLang());
        queryParams.put("subparticipant_id", query.getSubParticipantId());
        queryParams.put("participant_id", query.getParticipantId());

        return fetchSingleNodeWrapped(
                "/standings/" + standingId,
                Map.of(),
                queryParams,
                "standings",
                new TypeReference<>() {}
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreCompetitionDTO>>> getCompetitions(CompetitionQueryParams query) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put("lang", query.getLang());
        queryParams.put("date_from", StatScoreDateTimeUtils.formatDateTime(query.getDateFrom()));
        queryParams.put("date_to", StatScoreDateTimeUtils.formatDateTime(query.getDateTo()));
        queryParams.put("page", query.getPage());
        queryParams.put("limit", query.getLimit());
        queryParams.put("area_type", query.getAreaType());
        queryParams.put("type", query.getType());
        queryParams.put("area_id", query.getAreaId());
        queryParams.put("sport_id", query.getSportId());
        queryParams.put("tour_id", query.getTourId());
        queryParams.put("multi_ids", query.getMultiIds());
        queryParams.put("gender", query.getGender());
        queryParams.put("timestamp", query.getTimestamp());
        queryParams.put("short_name", query.getShortName());
        queryParams.put("sort_type", query.getSortType());
        queryParams.put("participant_id", query.getParticipantId());
        queryParams.put("status_type", query.getStatusType());
        queryParams.put("tz", query.getTz());

        return fetchListWrapper(
                "/competitions",
                Map.of(),
                queryParams,
                "competitions",
                new TypeReference<>() {}
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreCompetitionDTO>> getCompetitionById(Integer competitionId) {
        return fetchSingleNodeWrapped(
                "/competitions/" + competitionId,
                Map.of(),
                Map.of(),
                "competitions",
                new TypeReference<>() {}
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreParticipantDTO>>> getEventParticipants(Integer eventId) {
        return fetchListWrapper(
                "/events/{eventId}/participants",
                Map.of("eventId", eventId),
                Map.of(),
                "participants",
                new TypeReference<>() {
                }
        );
    }

    private <T> Mono<StatScoreResponse<T>> fetchSingleNodeWrapped(
            String path,
            Map<String, Object> pathVariables,
            Map<String, Object> queryParams,
            String nodeName,
            TypeReference<T> nodeType
    ) {
        return webClient.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder.path(path);
                    queryParams.forEach((key, value) -> {
                        if (value != null) {
                            builder.queryParam(key, value);
                        }
                    });
                    return builder.build(pathVariables);
                })
                .retrieve()
                .bodyToMono(String.class)
                .map(json -> {
                    StatScoreResponse<T> response = new StatScoreResponse<>();
                    try {
                        JsonNode root = objectMapper.readTree(json);
                        JsonNode methodNode = root.path("api").path("method");
                        JsonNode node = root.path("api").path("data").path(nodeName);

                        T data = objectMapper.readerFor(nodeType).readValue(node);
                        StatScoreResponse.MethodInfo method = objectMapper.treeToValue(methodNode, StatScoreResponse.MethodInfo.class);

                        StatScoreResponse.ApiWrapper<T> api = new StatScoreResponse.ApiWrapper<>();
                        api.setData(data);
                        api.setMethod(method);

                        response.setApi(api);
                    } catch (IOException e) {
                        log.error("Failed to parse single node response for {}: {}", path, e.getMessage(), e);
                        throw new RuntimeException("Malformed response from Sports-API", e);
                    }
                    return response;
                });
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
                        throw new RuntimeException("Malformed response from Sports-API", e);
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
                        throw new RuntimeException("Malformed response from Sports-API", e);
                    }

                    return response;
                });
    }
}
