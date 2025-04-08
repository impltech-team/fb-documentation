package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.EventStat;
import io.limeup.flexbets.sport.repository.projection.SubParticipantStatRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface StatRepository extends ExternalIdRepository<EventStat, Long> {

    void deleteByEventIdIn(Collection<Long> eventIds);

    @Query(value = """
        WITH unique_players AS (
            SELECT DISTINCT sp.id AS sub_participant_id,
                            sp.player_name,
                            sp.position,
                            p.team_name AS team_name,
                            p.id AS participant_id,
                            p.competition_id
            FROM sport.sub_participant sp
            JOIN sport.competition c ON sp.competition_id = c.id
            JOIN sport.participant p ON sp.participant_id = p.id
            JOIN sport.event_participant ep ON ep.participant_id = p.id
            JOIN sport.event e ON e.id = ep.event_id
            WHERE e.start_date > NOW()
              AND sp.position NOT IN ('Coach')
              AND (:competitionId IS NULL OR c.external_id = :competitionId)              
              AND (:positions IS NULL OR sp.position IN (:positions))                                                                               
              AND (:participantIds IS NULL OR p.external_id IN (:participantIds))
              AND (
                   :filter IS NULL OR
                   sp.player_name ILIKE CONCAT('%', :filter, '%') OR
                   p.team_name ILIKE CONCAT('%', :filter, '%') OR
                   sp.position ILIKE CONCAT('%', :filter, '%')
              )
        ),
        
        future_event_map AS (
            SELECT DISTINCT ON (sp.id)
                sp.id AS sub_participant_id,
                fe.id AS future_event_id,
                fe.external_id AS future_event_external_id,
                fe.name AS future_event_name,
                fe.start_date AS future_event_start_date,
                STRING_AGG(DISTINCT ep_part.acronym, ',') AS future_event_acronyms
            FROM sport.sub_participant sp
            JOIN sport.participant p ON sp.participant_id = p.id
            JOIN sport.event_participant ep ON ep.participant_id = p.id
            JOIN sport.event fe ON fe.id = ep.event_id
            LEFT JOIN sport.event_participant ep_ev ON ep_ev.event_id = fe.id
            LEFT JOIN sport.participant ep_part ON ep_part.id = ep_ev.participant_id
            WHERE fe.start_date > NOW()
            GROUP BY sp.id, fe.id, fe.external_id, fe.name, fe.start_date, ep_part.acronym
            ORDER BY sp.id, fe.start_date
        ),
        
        paged_players AS (
            SELECT up.sub_participant_id
            FROM unique_players up
            LEFT JOIN future_event_map fem ON fem.sub_participant_id = up.sub_participant_id
            ORDER BY
                CASE WHEN :sortBy = 'player_name' AND :sortOrder = 'asc' THEN up.player_name END ASC,
                CASE WHEN :sortBy = 'player_name' AND :sortOrder = 'desc' THEN up.player_name END DESC,
                CASE WHEN :sortBy = 'team' AND :sortOrder = 'asc' THEN up.team_name END ASC,
                CASE WHEN :sortBy = 'team' AND :sortOrder = 'desc' THEN up.team_name END DESC,
                CASE WHEN :sortBy = 'position' AND :sortOrder = 'asc' THEN up.position END ASC,
                CASE WHEN :sortBy = 'position' AND :sortOrder = 'desc' THEN up.position END DESC,
                CASE WHEN :sortBy = 'event_time' AND :sortOrder = 'asc' THEN fem.future_event_start_date END ASC,
                CASE WHEN :sortBy = 'event_time' AND :sortOrder = 'desc' THEN fem.future_event_start_date END DESC,
                up.sub_participant_id
            OFFSET :offset
            LIMIT :limit
        ),
        
        appeared_events AS (
            SELECT
                pp.sub_participant_id,
                es.event_id,
                e.start_date,
                e.name,
                STRING_AGG(DISTINCT p.acronym, ',') AS event_acronyms,
                ROW_NUMBER() OVER (
                    PARTITION BY pp.sub_participant_id
                    ORDER BY e.start_date DESC
                ) AS rn
            FROM paged_players pp
            LEFT JOIN sport.event_stat es
              ON es.target_id = pp.sub_participant_id
             AND es.stat_name = 'Appearance'
             AND es.value_raw = '1'
             AND es.value_numeric = 1.0
             AND es.target_type = 'SUBPARTICIPANT'
            LEFT JOIN sport.event e ON e.id = es.event_id AND e.start_date < NOW()
            LEFT JOIN sport.event_participant ep ON ep.event_id = e.id
            LEFT JOIN sport.participant p ON p.id = ep.participant_id
            GROUP BY es.target_id, es.event_id, e.name, e.start_date, pp.sub_participant_id, p.acronym
        ),
        
        top_5_played_events AS (
            SELECT sub_participant_id, event_id, name, start_date, event_acronyms
            FROM appeared_events
            WHERE rn <= 5
        )
        
        SELECT
            es.stat_name AS stat_name,
            es.value_raw AS value_raw,
            es.value_numeric AS value_numeric,
            es.event_id AS event_id,
            t5.name AS event_name,
            t5.start_date AS event_start_date,
            sp.external_id AS id,
            sp.player_name AS player_name,
            sp.position AS position,
            sp.shirt_number AS shirt_number,
            sp.gender AS gender,
            sp.avatar_url AS avatar_url,
            comp.external_id AS competition_id,
            comp.name AS competition_name,
            a.external_id AS area_id,
            a.name AS area_name,
            sp.weight AS weight,
            sp.height AS height,
            sp.birth_date AS birth_date,
            fem.future_event_external_id AS future_event_id,
            fem.future_event_name AS future_event_name,
            fem.future_event_start_date AS future_event_start_date,
            p.external_id AS participant_id,
            p.team_name AS team_name,
            t5.event_acronyms AS event_participant_acronyms,
            fem.future_event_acronyms
        FROM paged_players pp
            JOIN sport.sub_participant sp ON sp.id = pp.sub_participant_id
            JOIN sport.competition comp on comp.id = sp.competition_id
            JOIN sport.area a on a.id = sp.area_id
            LEFT JOIN future_event_map fem ON fem.sub_participant_id = sp.id
            JOIN sport.participant p ON p.id = sp.participant_id
            LEFT JOIN top_5_played_events t5 ON t5.sub_participant_id = sp.id
            LEFT JOIN sport.event_stat es
              ON es.event_id = t5.event_id
             AND es.target_id = t5.sub_participant_id
             AND es.target_type = 'SUBPARTICIPANT'
             AND (:statNames IS NULL OR es.stat_name IN (:statNames))
        """, nativeQuery = true)
    List<SubParticipantStatRow> listSubParticipantStats(
            @Param("competitionId") Integer competitionId,
            @Param("positions") List<String> positions,
            @Param("participantIds") List<Integer> participantIds,
            @Param("marketId") Integer marketId,
            @Param("filter") String filter,
            @Param("sortBy") String sortBy,
            @Param("sortOrder") String sortOrder,
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("statNames") List<String> statNames
    );
}
