package io.limeup.flexbets.sport.service.live;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventNormalizer {


    public LiveEventDTO normalize(JsonNode eventNode, JsonNode partNodes, JsonNode resultJson) {
        try {
            Map<Long, List<LiveResultDTO>> resultsMap = new HashMap<>();
            for (JsonNode r : resultJson) {
                Long participantId = r.path("participant").path("id").asLong();
                LiveResultDTO result = LiveResultDTO.builder()
                        .id(r.path("result_id").asLong())
                        .value(r.path("value").asText(null))
                        .build();
                resultsMap.computeIfAbsent(participantId, k -> new ArrayList<>()).add(result);
            }

            List<LiveParticipantDTO> participants = new ArrayList<>();
            for (JsonNode p : partNodes) {
                long participantId = p.path("id").asLong();

                LiveParticipantDTO participant = LiveParticipantDTO.builder()
                        .id(participantId)
                        .counter(p.path("counter").asInt())
                        .name(p.path("name").asText(null))
                        .shortName(p.path("short_name").asText(null))
                        .acronym(p.path("acronym").asText(null))
                        .areaId(p.path("area_id").asInt())
                        .areaName(p.path("area_name").asText(null))
                        .areaCode(p.path("area_code").asText(null))
                        .ut(p.path("ut").asLong())
                        .type(p.path("type").asText(null))
                        .results(resultsMap.getOrDefault(participantId, List.of()))
                        .build();

                participants.add(participant);
            }

            return LiveEventDTO.builder()
                    .id(eventNode.path("event_data_id").asLong())
                    .action(eventNode.path("action").asText(null))
                    .startDate(eventNode.path("start_date").asText(null))
                    .ftOnly(eventNode.path("ft_only").asText(null))
                    .coverageType(eventNode.path("coverage_type").asText(null))
                    .statusId(eventNode.path("status_id").asInt())
                    .statusName(eventNode.path("status_name").asText(null))
                    .sportId(eventNode.path("sport_id").asInt())
                    .sportName(eventNode.path("sport_name").asText(null))
                    .day(eventNode.has("day") && !eventNode.get("day").isNull() ? eventNode.get("day").asInt() : null)
                    .neutralVenue(eventNode.path("neutral_venue").asText(null))
                    .itemStatus(eventNode.path("item_status").asText(null))
                    .areaId(eventNode.path("area_id").asInt())
                    .areaName(eventNode.path("area_name").asText(null))
                    .competitionId(eventNode.path("competition_id").asInt())
                    .competitionName(eventNode.path("competition_name").asText(null))
                    .seasonId(eventNode.path("season_id").asInt())
                    .stageId(eventNode.path("stage_id").asInt())
                    .stageName(eventNode.path("stage_name").asText(null))
                    .groupId(eventNode.has("group_id") && !eventNode.get("group_id").isNull() ? eventNode.get("group_id").asInt() : null)
                    .tourId(eventNode.has("tour_id") && !eventNode.get("tour_id").isNull() ? eventNode.get("tour_id").asInt() : null)
                    .tourName(eventNode.path("tour_name").asText(null))
                    .gender(eventNode.path("gender").asText(null))
                    .betStatus(eventNode.path("bet_status").asText(null))
                    .betCards(eventNode.path("bet_cards").asText(null))
                    .betCorners(eventNode.path("bet_corners").asText(null))
                    .relationStatus(eventNode.path("relation_status").asText(null))
                    .statusType(eventNode.path("status_type").asText(null))
                    .name(eventNode.path("name").asText(null))
                    .roundId(eventNode.has("round_id") && !eventNode.get("round_id").isNull() ? eventNode.get("round_id").asInt() : null)
                    .roundName(eventNode.path("round_name").asText(null))
                    .playedTime(eventNode.path("played_time").asText(null))
                    .participants(participants)
                    .build();

        } catch (Exception e) {
            log.error("❌ Normalization failed", e);
            return null;
        }
    }

}
