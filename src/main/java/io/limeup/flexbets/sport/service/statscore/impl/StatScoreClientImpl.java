package io.limeup.flexbets.sport.service.statscore.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.limeup.flexbets.sport.dto.statscore.*;
import io.limeup.flexbets.sport.dto.statscore.prams.*;
import io.limeup.flexbets.sport.service.statscore.StatScoreClient;
import io.limeup.flexbets.sport.utils.ConstantUtils;
import io.limeup.flexbets.sport.utils.StatScoreDateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

@Slf4j
@Service
public class StatScoreClientImpl implements StatScoreClient {

    private final WebClient webClient;

    private final ObjectMapper objectMapper;

    public StatScoreClientImpl(
            @Qualifier("statScoreWebClient") WebClient webClient,
            ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreSubParticipantDTO>>> getEventSubParticipants(Integer eventId, boolean retryEnabled) {
        return fetchListWrapper(
                "/events/{eventId}/sub-participants",
                Map.of("eventId", eventId),
                Map.of(),
                ConstantUtils.StatScoreWebClient.SUB_PARTICIPANTS,
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreParticipantDTO>>> getParticipants(ParticipantQueryParams params, boolean retryEnabled) {
        Map<String, Object> queryParams = new LinkedHashMap<>();
        queryParams.put(ConstantUtils.StatScoreWebClient.SPORT_ID, params.getSportId());
        queryParams.put(ConstantUtils.StatScoreWebClient.LIMIT, params.getLimit());
        queryParams.put(ConstantUtils.StatScoreWebClient.PAGE, params.getPage());
        queryParams.put(ConstantUtils.StatScoreWebClient.SEASON_ID, params.getSeasonId());
        queryParams.put(ConstantUtils.StatScoreWebClient.AREA_ID, params.getAreaId());
        queryParams.put("subtype", Optional.ofNullable(params.getSubtype()).map(Enum::name).orElse(null));
        queryParams.put("type", Optional.ofNullable(params.getType()).map(Enum::name).orElse(null));
        queryParams.put("virtual", params.getVirtual());
        queryParams.put(ConstantUtils.StatScoreWebClient.MULTI_IDS, params.getMultiIds());

        return fetchListWrapper(
                "/participants",
                Map.of(),
                queryParams,
                ConstantUtils.StatScoreWebClient.PARTICIPANTS,
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreParticipantDTO>> getParticipantById(Integer participantId, boolean retryEnabled) {
        return fetchSingleNodeWrapped(
                "/participants/" + participantId,
                Map.of(),
                Map.of(),
                ConstantUtils.StatScoreWebClient.PARTICIPANT,
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreCompetitionDTO>>> getEvents(EventQueryParams query, boolean retryEnabled) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put("date_from", StatScoreDateTimeUtils.formatDateTime(query.getDateFrom()));
        queryParams.put("date_to", StatScoreDateTimeUtils.formatDateTime(query.getDateTo()));
        queryParams.put(ConstantUtils.StatScoreWebClient.SPORT_ID, query.getSportId());
        queryParams.put(ConstantUtils.StatScoreWebClient.AREA_ID, query.getAreaId());
        queryParams.put(ConstantUtils.StatScoreWebClient.COMPETITION_ID, query.getCompetitionId());
        queryParams.put(ConstantUtils.StatScoreWebClient.SEASON_ID, query.getSeasonId());
        queryParams.put(ConstantUtils.StatScoreWebClient.STAGE_ID, query.getStageId());
        queryParams.put("group_id", query.getGroupId());
        queryParams.put(ConstantUtils.StatScoreWebClient.PARTICIPANT_ID, query.getParticipantId());
        queryParams.put(ConstantUtils.StatScoreWebClient.MULTI_IDS, query.getMultiIds());
        queryParams.put("venue_type", query.getVenueType());
        queryParams.put("venue_id", query.getVenueId());
        queryParams.put(ConstantUtils.StatScoreWebClient.SORT_TYPE, query.getSortType());
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
        queryParams.put(ConstantUtils.StatScoreWebClient.TIMESTAMP, query.getTimestamp());
        queryParams.put(ConstantUtils.StatScoreWebClient.PAGE, query.getPage());
        queryParams.put(ConstantUtils.StatScoreWebClient.LIMIT, query.getLimit());

        return fetchListWrapper(
                "/events",
                Map.of(),
                queryParams,
                ConstantUtils.StatScoreWebClient.COMPETITIONS,
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreCompetitionDTO>> getEventById(Integer eventId, boolean retryEnabled) {
        return fetchSingleNodeWrapped(
                "/events/" + eventId,
                Map.of(),
                Map.of(),
                ConstantUtils.StatScoreWebClient.COMPETITION,
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreSubParticipantDTO>>> getSquadSubParticipants(Integer participantId, Integer seasonId, boolean retryEnabled) {
        Map<String, Object> queryParams = new LinkedHashMap<>();
        queryParams.put(ConstantUtils.StatScoreWebClient.SEASON_ID, seasonId);
        return fetchListWrapper(
                "/participants/{participant_id}/squad",
                Map.of(ConstantUtils.StatScoreWebClient.PARTICIPANT_ID, participantId),
                queryParams,
                ConstantUtils.StatScoreWebClient.PARTICIPANTS,
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreAreaDTO>>> getAreas(AreaQueryParams query, boolean retryEnabled) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put(ConstantUtils.StatScoreWebClient.LANG, query.getLang());
        queryParams.put(ConstantUtils.StatScoreWebClient.PAGE, query.getPage());
        queryParams.put(ConstantUtils.StatScoreWebClient.LIMIT, query.getLimit());
        queryParams.put("parent_area_id", query.getParentAreaId());
        queryParams.put(ConstantUtils.StatScoreWebClient.TIMESTAMP, query.getTimestamp());

        return fetchListWrapper(
                "/areas",
                Map.of(),
                queryParams,
                "areas",
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreSportLiteDTO>>> getSports(SportQueryParams query, boolean retryEnabled) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put(ConstantUtils.StatScoreWebClient.PAGE, query.getPage());
        queryParams.put(ConstantUtils.StatScoreWebClient.LIMIT, query.getLimit());
        queryParams.put(ConstantUtils.StatScoreWebClient.TIMESTAMP, query.getTimestamp());

        return fetchListWrapper(
                "/sports",
                Map.of(),
                queryParams,
                "sports",
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreSportDTO>> getSportById(Integer sportId, boolean retryEnabled) {
        return fetchSingleNodeWrapped(
                "/sports/" + sportId,
                Map.of(),
                Map.of(),
                "sport",
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreVenueDTO>>> getVenues(VenueQueryParams query, boolean retryEnabled) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put(ConstantUtils.StatScoreWebClient.SPORT_ID, query.getSportId());
        queryParams.put(ConstantUtils.StatScoreWebClient.PARTICIPANT_ID, query.getParticipantId());
        queryParams.put(ConstantUtils.StatScoreWebClient.TIMESTAMP, query.getTimestamp());
        queryParams.put(ConstantUtils.StatScoreWebClient.PAGE, query.getPage());
        queryParams.put(ConstantUtils.StatScoreWebClient.LIMIT, query.getLimit());

        return fetchListWrapper(
                "/venues",
                Map.of(),
                queryParams,
                "venues",
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreVenueDTO>> getVenueById(Integer venueId, boolean retryEnabled) {
        return fetchSingleNodeWrapped(
                "/venues/" + venueId,
                Map.of(),
                Map.of(),
                "venue",
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreBracketDTO>>> getBrackets(Integer stageId, boolean retryEnabled) {
        return fetchListWrapper(
                "/brackets/{stage_id}",
                Map.of(ConstantUtils.StatScoreWebClient.STAGE_ID, stageId),
                Map.of(),
                "nodes",
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreCompetitionDTO>> getGroups(GroupQueryParams query, boolean retryEnabled) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put(ConstantUtils.StatScoreWebClient.STAGE_ID, query.getStageId());
        queryParams.put(ConstantUtils.StatScoreWebClient.TIMESTAMP, query.getTimestamp());
        queryParams.put(ConstantUtils.StatScoreWebClient.PAGE, query.getPage());
        queryParams.put(ConstantUtils.StatScoreWebClient.LIMIT, query.getLimit());

        return fetchSingleNodeWrapped(
                "/groups",
                Map.of(),
                queryParams,
                ConstantUtils.StatScoreWebClient.COMPETITION,
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreCompetitionDTO>>> getSeasons(SeasonQueryParams query, boolean retryEnabled) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put(ConstantUtils.StatScoreWebClient.LANG, query.getLang());
        queryParams.put(ConstantUtils.StatScoreWebClient.PAGE, query.getPage());
        queryParams.put(ConstantUtils.StatScoreWebClient.LIMIT, query.getLimit());
        queryParams.put(ConstantUtils.StatScoreWebClient.SPORT_ID, query.getSportId());
        queryParams.put(ConstantUtils.StatScoreWebClient.COMPETITION_ID, query.getCompetitionId());
        queryParams.put(ConstantUtils.StatScoreWebClient.PARTICIPANT_ID, query.getParticipantId());
        queryParams.put(ConstantUtils.StatScoreWebClient.MULTI_IDS, query.getMultiIds());
        queryParams.put("year", query.getYear());
        queryParams.put(ConstantUtils.StatScoreWebClient.SORT_TYPE, query.getSortType());
        queryParams.put("sort_order", query.getSortOrder());
        queryParams.put(ConstantUtils.StatScoreWebClient.AREA_ID, query.getAreaId());
        queryParams.put(ConstantUtils.StatScoreWebClient.TIMESTAMP, query.getTimestamp());

        return fetchListWrapper(
                "/seasons",
                Map.of(),
                Map.of(),
                ConstantUtils.StatScoreWebClient.COMPETITIONS,
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreCompetitionDTO>> getSeasonById(Integer seasonId, boolean retryEnabled) {
        return fetchSingleNodeWrapped(
                "/seasons/" + seasonId,
                Map.of(),
                Map.of(),
                ConstantUtils.StatScoreWebClient.COMPETITION,
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreCompetitionDTO>> getStages(StageQueryParams query, boolean retryEnabled) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put(ConstantUtils.StatScoreWebClient.SEASON_ID, query.getSeasonId());
        queryParams.put(ConstantUtils.StatScoreWebClient.TIMESTAMP, query.getTimestamp());
        queryParams.put(ConstantUtils.StatScoreWebClient.PAGE, query.getPage());
        queryParams.put(ConstantUtils.StatScoreWebClient.LIMIT, query.getLimit());

        return fetchSingleNodeWrapped(
                "/stages",
                Map.of(),
                queryParams,
                ConstantUtils.StatScoreWebClient.COMPETITION,
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreCompetitionDTO>> getStageById(Integer stageId, boolean retryEnabled) {
        return fetchSingleNodeWrapped(
                "/stages/" + stageId,
                Map.of(),
                Map.of(),
                ConstantUtils.StatScoreWebClient.COMPETITION,
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreStandingDTO>>> getStandings(StandingQueryParams query, boolean retryEnabled) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put(ConstantUtils.StatScoreWebClient.LANG, query.getLang());
        queryParams.put(ConstantUtils.StatScoreWebClient.PAGE, query.getPage());
        queryParams.put(ConstantUtils.StatScoreWebClient.LIMIT, query.getLimit());
        queryParams.put("object_type", query.getObjectType());
        queryParams.put("object_id", query.getObjectId());
        queryParams.put("type_id", query.getTypeId());
        queryParams.put("subtype", query.getSubtype());
        queryParams.put(ConstantUtils.StatScoreWebClient.SPORT_ID, query.getSportId());
        queryParams.put(ConstantUtils.StatScoreWebClient.COMPETITION_ID, query.getCompetitionId());
        queryParams.put(ConstantUtils.StatScoreWebClient.SEASON_ID, query.getSeasonId());
        queryParams.put(ConstantUtils.StatScoreWebClient.STAGE_ID, query.getStageId());
        queryParams.put(ConstantUtils.StatScoreWebClient.TIMESTAMP, query.getTimestamp());
        queryParams.put("item_status", query.getItemStatus());

        return fetchListWrapper(
                "/standings",
                Map.of(),
                queryParams,
                "standings_list",
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreStandingDTO>> getStandingById(Integer standingId, StandingByIdQueryParams query, boolean retryEnabled) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put(ConstantUtils.StatScoreWebClient.LANG, query.getLang());
        queryParams.put("subparticipant_id", query.getSubParticipantId());
        queryParams.put(ConstantUtils.StatScoreWebClient.PARTICIPANT_ID, query.getParticipantId());

        return fetchSingleNodeWrapped(
                "/standings/" + standingId,
                Map.of(),
                queryParams,
                "standings",
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreCompetitionDTO>>> getCompetitions(CompetitionQueryParams query, boolean retryEnabled) {
        Map<String, Object> queryParams = new LinkedHashMap<>();

        queryParams.put(ConstantUtils.StatScoreWebClient.LANG, query.getLang());
        queryParams.put("date_from", StatScoreDateTimeUtils.formatDateTime(query.getDateFrom()));
        queryParams.put("date_to", StatScoreDateTimeUtils.formatDateTime(query.getDateTo()));
        queryParams.put(ConstantUtils.StatScoreWebClient.PAGE, query.getPage());
        queryParams.put(ConstantUtils.StatScoreWebClient.LIMIT, query.getLimit());
        queryParams.put("area_type", query.getAreaType());
        queryParams.put("type", query.getType());
        queryParams.put(ConstantUtils.StatScoreWebClient.AREA_ID, query.getAreaId());
        queryParams.put(ConstantUtils.StatScoreWebClient.SPORT_ID, query.getSportId());
        queryParams.put("tour_id", query.getTourId());
        queryParams.put(ConstantUtils.StatScoreWebClient.MULTI_IDS, query.getMultiIds());
        queryParams.put("gender", query.getGender());
        queryParams.put(ConstantUtils.StatScoreWebClient.TIMESTAMP, query.getTimestamp());
        queryParams.put("short_name", query.getShortName());
        queryParams.put(ConstantUtils.StatScoreWebClient.SORT_TYPE, query.getSortType());
        queryParams.put(ConstantUtils.StatScoreWebClient.PARTICIPANT_ID, query.getParticipantId());
        queryParams.put("status_type", query.getStatusType());
        queryParams.put("tz", query.getTz());

        return fetchListWrapper(
                "/competitions",
                Map.of(),
                queryParams,
                ConstantUtils.StatScoreWebClient.COMPETITIONS,
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<StatScoreCompetitionDTO>> getCompetitionById(Integer competitionId, boolean retryEnabled) {
        return fetchSingleNodeWrapped(
                "/competitions/" + competitionId,
                Map.of(),
                Map.of(),
                ConstantUtils.StatScoreWebClient.COMPETITIONS,
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    @Override
    public Mono<StatScoreResponse<ListWrapper<StatScoreParticipantDTO>>> getEventParticipants(Integer eventId, boolean retryEnabled) {
        return fetchListWrapper(
                "/events/{eventId}/participants",
                Map.of("eventId", eventId),
                Map.of(),
                ConstantUtils.StatScoreWebClient.PARTICIPANTS,
                new TypeReference<>() {
                },
                retryEnabled
        );
    }

    private <T> Mono<StatScoreResponse<T>> fetchSingleNodeWrapped(
            String path,
            Map<String, Object> pathVariables,
            Map<String, Object> queryParams,
            String nodeName,
            TypeReference<T> nodeType,
            boolean retryEnabled
    ) {
        Mono<StatScoreResponse<T>> mono = webClient.get()
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
        return callWithOptionalRetry(mono, retryEnabled);
    }

    private <T> Mono<StatScoreResponse<ListWrapper<T>>> fetchListWrapper(
            String url,
            Map<String, Object> uriVariables,
            Map<String, Object> queryParams,
            String listNodeName,
            TypeReference<List<T>> itemType,
            boolean retryEnabled
    ) {
        Mono<StatScoreResponse<ListWrapper<T>>> mono = webClient.get()
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
                        JsonNode dataNode = root.path("api").path("data");
                        JsonNode listNode = dataNode.path(listNodeName);
                        if (listNode.isArray()) {
                            items = objectMapper.readerFor(itemType).readValue(listNode);
                        }
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
        return callWithOptionalRetry(mono, retryEnabled);
    }

    public <T> Mono<T> callWithOptionalRetry(Mono<T> request, boolean retryEnabled) {
        if (retryEnabled) {
            return request.retryWhen(
                    Retry.backoff(3, Duration.ofSeconds(2))
                            .filter(this::isRetryableError)
                            .onRetryExhaustedThrow((spec, signal) -> signal.failure())
            );
        } else {
            return request;
        }
    }

    private boolean isRetryableError(Throwable t) {
        return t instanceof WebClientResponseException ex &&
                (ex.getStatusCode().is5xxServerError());
    }
}
