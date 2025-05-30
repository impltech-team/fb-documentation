package io.limeup.flexbets.sport.batch.prefetch.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.limeup.flexbets.sport.model.*;
import io.limeup.flexbets.sport.model.enums.EventStatus;
import io.limeup.flexbets.sport.repository.*;
import io.limeup.flexbets.sport.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSSEventMessageListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final LiveEventRepository liveEventRepository;
    private final LiveEventDetailRepository detailRepository;
    private final LiveEventBetStatusRepository betStatusRepository;
    private final LiveParticipantRepository participantRepository;
    private final LiveParticipantResultRepository resultRepository;
    private final EventService eventService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String json = new String(message.getBody());
            JsonNode root = objectMapper.readTree(json);

            if (!"event".equals(root.get("type").asText())) return;

            JsonNode ev = root.path("data").path("event");
            long id = root.get("id").asLong();
            int eventDataId = ev.get("id").asInt();
            String lsIdString = ev.get("lsId").asText();
            Long lsId = lsIdString != null ? Long.parseLong(lsIdString) : null;
            LocalDateTime startDate = LocalDateTime.parse(ev.path("start_date").asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String statusType = ev.path("status_type").asText();
            String name = ev.path("name").asText();

            eventService.updateEventByIdOrByName(eventDataId, name, lsId, statusType, startDate);

            if (liveEventRepository.existsById(id)) {
                log.info("ℹ️ Event {} already exists. Skipping.", id);
                return;
            }
            log.info("ℹ️ Event {} will bew written.", id);
            LiveEvent liveEvent = LiveEvent.builder()
                    .id(id)
                    .uuid(UUID.fromString(root.get("uuid").asText()))
                    .type(root.path("type").asText(null))
                    .source(root.path("source").asInt())
                    .ut(root.path("ut").asLong())
                    .eventDataId((long) eventDataId)
                    .lsId(lsId)
                    .action(ev.path("action").asText())
                    .startDate(startDate)
                    .ftOnly("yes".equalsIgnoreCase(ev.path("ft_only").asText()))
                    .coverageType(ev.path("coverage_type").asText(null))
                    .statusId(ev.path("status_id").asInt())
                    .statusName(ev.path("status_name").asText())
                    .sportId(ev.path("sport_id").asInt())
                    .sportName(ev.path("sport_name").asText())
                    .day(ev.path("day").isNull() ? null : ev.path("day").asInt())
                    .neutralVenue("yes".equalsIgnoreCase(ev.path("neutral_venue").asText()))
                    .itemStatus(ev.path("item_status").asText(null))
                    .clockTime(ev.path("clock_time").asText(null))
                    .clockStatus(ev.path("clock_status").asText(null))
                    .areaId(ev.path("area_id").asInt())
                    .areaName(ev.path("area_name").asText())
                    .competitionId(ev.path("competition_id").asInt())
                    .competitionName(ev.path("competition_name").asText())
                    .seasonId(ev.path("season_id").asInt())
                    .stageId(ev.path("stage_id").asInt())
                    .stageName(ev.path("stage_name").asText(null))
                    .groupId(ev.path("group_id").isNull() ? null : ev.path("group_id").asInt())
                    .tourId(ev.path("tour_id").isNull() ? null : ev.path("tour_id").asInt())
                    .tourName(ev.path("tour_name").asText(null))
                    .gender(ev.path("gender").asText())
                    .betStatus(ev.path("bet_status").asText(null))
                    .betCards(ev.path("bet_cards").asText(null))
                    .betCorners(ev.path("bet_corners").asText(null))
                    .relationStatus(ev.path("relation_status").asText())
                    .statusType(statusType)
                    .name(name)
                    .roundId(ev.path("round_id").isNull() ? null : ev.path("round_id").asInt())
                    .roundName(ev.path("round_name").asText(null))
                    .scoutsfeed("yes".equalsIgnoreCase(ev.path("scoutsfeed").asText()))
                    .latency(ev.path("latency").asText(null))
                    .eventStatsLvl(ev.path("event_stats_lvl").asText(null))
                    .eventStatsLive(ev.path("event_stats_lvl_live").asText(null))
                    .eventStatsAfter(ev.path("event_stats_lvl_after").asText(null))
                    .verifiedResult("yes".equalsIgnoreCase(ev.path("verified_result").asText()))
                    .isStatsVerified("yes".equalsIgnoreCase(ev.path("is_stats_verified").asText()))
                    .isCoverageLimited("yes".equalsIgnoreCase(ev.path("is_coverage_limited").asText()))
                    .playedTime(ev.path("played_time").asText(null))
                    .participantsIdStatsModified(ev.path("participants_id_stats_modified").toString())
                    .build();

            liveEventRepository.save(liveEvent);
            log.info("✅ Saved live_event {}", id);

            JsonNode details = ev.path("details");
            if (details.isArray()) {
                for (JsonNode detail : details) {
                    if (detail.hasNonNull("id")) {
                        int detailId = detail.get("id").asInt();
                        if (!detailRepository.existsByEventAndDetailId(liveEvent, detailId)) {
                            detailRepository.save(LiveEventDetail.builder()
                                    .event(liveEvent)
                                    .detailId(detailId)
                                    .value(detail.path("value").asText(null))
                                    .build());
                        }
                    } else {
                        log.warn("⚠️ Detail without 'id': {}", detail);
                    }
                }
            } else {
                log.warn("⚠️ 'details' is not an array or missing: {}", details);
            }


            JsonNode betting = ev.path("betting").path("bet_statuses");
            if (betting.hasNonNull("name") && betting.hasNonNull("value")) {
                String betName = betting.get("name").asText();
                String value = betting.get("value").asText();

                if (!betStatusRepository.existsByEventAndName(liveEvent, betName)) {
                    betStatusRepository.save(LiveEventBetStatus.builder()
                            .event(liveEvent)
                            .name(betName)
                            .value(value)
                            .build());
                }
            } else {
                log.warn("⚠️ Missing 'name' or 'value' in betting.bet_statuses for event {}", liveEvent.getId());
            }


            JsonNode participants = ev.path("participants");
            if (participants.isArray()) {
                for (JsonNode p : participants) {
                    long pid = p.get("id").asLong();
                    LiveParticipant participant = participantRepository.save(LiveParticipant.builder()
                            .id(pid)
                            .event(liveEvent)
                            .counter(p.path("counter").asInt())
                            .name(p.path("name").asText())
                            .shortName(p.path("short_name").asText(null))
                            .acronym(p.path("acronym").asText(null))
                            .areaId(p.path("area_id").asInt())
                            .areaName(p.path("area_name").asText(null))
                            .areaCode(p.path("area_code").asText(null))
                            .ut(p.path("ut").asLong())
                            .type(p.path("type").asText())
                            .lineupsCopied("yes".equalsIgnoreCase(ev.path("lineups_copied").asText()))
                            .stats(p.path("stats").toString())
                            .eventStatusStats(p.path("event_status_stats").toString())
                            .subparticipants(p.path("subparticipants").toString())
                            .build());

                    JsonNode results = p.path("results");
                    if (results.isArray()) {
                        for (JsonNode result : results) {
                            int resultId = result.get("id").asInt();
                            if (!resultRepository.existsByParticipantAndResultId(participant, resultId)) {
                                resultRepository.save(LiveParticipantResult.builder()
                                        .participant(participant)
                                        .resultId(resultId)
                                        .value(result.path("value").asText(null))
                                        .build());
                            }
                        }
                    }
                    log.info("✅ Saved live_event {} to DB", liveEvent.getId());
                }
            }

        } catch (Exception e) {
            log.error("❌ Failed to process Redis message", e);
        }
    }
}