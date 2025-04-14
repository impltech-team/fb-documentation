package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.EventStat;
import io.limeup.flexbets.sport.repository.projection.ParticipantStatRow;
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
                            p.competition_id,
                            p.acronym AS current_team_acronym
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
				acronyms.event_acronyms AS future_event_acronyms
			FROM sport.sub_participant sp
			JOIN sport.participant p ON sp.participant_id = p.id
			JOIN sport.event_participant ep ON ep.participant_id = p.id
			JOIN sport.event fe ON fe.id = ep.event_id AND fe.start_date > NOW()

			LEFT JOIN (
				SELECT ep.event_id, STRING_AGG(p.acronym, ':' ORDER BY p.acronym) AS event_acronyms
				FROM sport.event_participant ep
				JOIN sport.participant p ON p.id = ep.participant_id
				GROUP BY ep.event_id
			) acronyms ON acronyms.event_id = fe.id

			ORDER BY sp.id, fe.start_date
		),
        
        paged_players AS (
            SELECT up.sub_participant_id, up.current_team_acronym
            FROM unique_players up
            LEFT JOIN future_event_map fem ON fem.sub_participant_id = up.sub_participant_id
            ORDER BY
                CASE WHEN :sortBy = 'player_name' AND :sortOrder = 'asc' THEN up.player_name END ASC,
                CASE WHEN :sortBy = 'player_name' AND :sortOrder = 'desc' THEN up.player_name END DESC,
                CASE WHEN :sortBy = 'team_name' AND :sortOrder = 'asc' THEN up.team_name END ASC,
                CASE WHEN :sortBy = 'team_name' AND :sortOrder = 'desc' THEN up.team_name END DESC,
                CASE WHEN :sortBy = 'position' AND :sortOrder = 'asc' THEN up.position END ASC,
                CASE WHEN :sortBy = 'position' AND :sortOrder = 'desc' THEN up.position END DESC,
                CASE WHEN :sortBy = 'event_time' AND :sortOrder = 'asc' THEN fem.future_event_start_date END ASC,
                CASE WHEN :sortBy = 'event_time' AND :sortOrder = 'desc' THEN fem.future_event_start_date END DESC,
                up.sub_participant_id
            OFFSET :offset
            LIMIT :limit
        ),
        
        event_participants AS (
            SELECT ep.event_id, STRING_AGG(DISTINCT p.acronym, ':' ORDER BY p.acronym) AS event_acronyms
            FROM
            sport.event_participant ep
            JOIN sport.participant p ON p.id = ep.participant_id
            GROUP BY ep.event_id
        ),
        
        appeared_events AS (
            SELECT
                pp.sub_participant_id,
                es.event_id,
                e.start_date,
                e.name,
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
        ),
        
        top_5_played_events AS (
            SELECT ae.sub_participant_id, ae.event_id, ae.name, ae.start_date, ep.event_acronyms
            FROM
            appeared_events ae
            LEFT JOIN event_participants ep ON ep.event_id = ae.event_id
            WHERE rn <= 5 AND ae.event_id IS NOT NULL
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
            fem.future_event_acronyms,
            pp.current_team_acronym
        FROM paged_players pp
            JOIN sport.sub_participant sp ON sp.id = pp.sub_participant_id
            JOIN sport.competition comp on comp.id = sp.competition_id
            JOIN sport.area a on a.id = sp.area_id
            LEFT JOIN future_event_map fem ON fem.sub_participant_id = sp.id
            JOIN sport.participant p ON p.id = sp.participant_id
            LEFT JOIN top_5_played_events t5 ON t5.sub_participant_id = sp.id
            JOIN sport.event_stat es
              ON es.event_id = t5.event_id
             AND es.target_id = t5.sub_participant_id
             AND es.target_type = 'SUBPARTICIPANT'
             AND (:statNames IS NULL OR es.stat_name IN (:statNames))
             ORDER BY
                CASE WHEN :sortBy = 'player_name' AND :sortOrder = 'asc' THEN sp.player_name END ASC,
                CASE WHEN :sortBy = 'player_name' AND :sortOrder = 'desc' THEN sp.player_name END DESC,
                CASE WHEN :sortBy = 'team_name' AND :sortOrder = 'asc' THEN p.team_name END ASC,
                CASE WHEN :sortBy = 'team_name' AND :sortOrder = 'desc' THEN p.team_name END DESC,
                CASE WHEN :sortBy = 'position' AND :sortOrder = 'asc' THEN sp.position END ASC,
                CASE WHEN :sortBy = 'position' AND :sortOrder = 'desc' THEN sp.position END DESC,
                CASE WHEN :sortBy = 'event_time' AND :sortOrder = 'asc' THEN fem.future_event_start_date END ASC,
                CASE WHEN :sortBy = 'event_time' AND :sortOrder = 'desc' THEN fem.future_event_start_date END DESC,
                fem.future_event_start_date
        """, nativeQuery = true)
    List<SubParticipantStatRow> listSubParticipantStats(
            @Param("competitionId") Integer competitionId,
            @Param("positions") Collection<String> positions,
            @Param("participantIds") Collection<Integer> participantIds,
            @Param("marketId") Integer marketId,
            @Param("filter") String filter,
            @Param("sortBy") String sortBy,
            @Param("sortOrder") String sortOrder,
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("statNames") Collection<String> statNames
    );

    @Query(value = """
	WITH future_event_map AS (
		SELECT DISTINCT ON (sp.id)
			sp.id AS sub_participant_id,
			fe.id AS future_event_id,
			fe.external_id AS future_event_external_id,
			fe.name AS future_event_name,
			fe.start_date AS future_event_start_date,
			acronyms.event_acronyms AS future_event_acronyms
		FROM sport.sub_participant sp
		JOIN sport.participant p ON sp.participant_id = p.id
		JOIN sport.event_participant ep ON ep.participant_id = p.id
		JOIN sport.event fe ON fe.id = ep.event_id AND fe.start_date > NOW()
		LEFT JOIN (
			SELECT ep.event_id, STRING_AGG(p.acronym, ':' ORDER BY p.acronym) AS event_acronyms
			FROM sport.event_participant ep
			JOIN sport.participant p ON p.id = ep.participant_id
			GROUP BY ep.event_id
		) acronyms ON acronyms.event_id = fe.id
		ORDER BY sp.id, fe.start_date
	),
	
	appeared_events AS (
		SELECT
			es.target_id AS sub_participant_id,
			es.event_id,
			e.name,
			e.start_date,
			STRING_AGG(DISTINCT p.acronym, ':' ORDER BY p.acronym) AS event_acronyms,
			ROW_NUMBER() OVER (
				PARTITION BY es.target_id
				ORDER BY e.start_date DESC
			) AS rn
		FROM sport.event_stat es
		JOIN sport.event e ON e.id = es.event_id
		JOIN sport.event_participant ep ON ep.event_id = e.id
		JOIN sport.participant p ON p.id = ep.participant_id
		WHERE es.stat_name = 'Appearance'
		  AND es.value_raw = '1'
		  AND es.value_numeric = 1.0
		  AND es.target_type = 'SUBPARTICIPANT'
		  AND e.start_date < NOW()
		  AND es.target_external_id = :subParticipantId
		GROUP BY es.target_id, es.event_id, e.name, e.start_date
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
		fem.future_event_acronyms,
		p.acronym AS current_team_acronym
	FROM sport.sub_participant sp
	JOIN sport.competition comp ON comp.id = sp.competition_id
	JOIN sport.area a ON a.id = sp.area_id
	JOIN sport.participant p ON p.id = sp.participant_id
	LEFT JOIN future_event_map fem ON fem.sub_participant_id = sp.id
	LEFT JOIN top_5_played_events t5 ON t5.sub_participant_id = sp.id
	JOIN sport.event_stat es
	  ON es.event_id = t5.event_id
	 AND es.target_id = t5.sub_participant_id
	 AND es.target_type = 'SUBPARTICIPANT'
	 AND (:statNames IS NULL OR es.stat_name IN (:statNames))
	WHERE sp.external_id = :subParticipantId
	  AND t5.event_id IS NOT NULL
    """, nativeQuery = true)
    List<SubParticipantStatRow> getSubParticipantStatsDetails(
            @Param("subParticipantId") Integer subParticipantId,
            @Param("statNames") Collection<String> statNames
    );

    @Query(value = """
    WITH unique_participants AS (
        SELECT DISTINCT p.id AS participant_id,
                        p.team_name,
                        p.acronym,
                        c.id AS competition_id,
                        c.name AS competition_name
        FROM sport.participant p
        JOIN sport.competition c ON p.competition_id = c.id
        JOIN sport.event_participant ep ON ep.participant_id = p.id
        JOIN sport.event e ON e.id = ep.event_id
        WHERE e.start_date > NOW()
          AND (:competitionId IS NULL OR c.external_id = :competitionId)
          AND (:participantIds IS NULL OR p.external_id IN (:participantIds))
          AND (
               :filter IS NULL OR
               p.team_name ILIKE CONCAT('%', :filter, '%') OR
               p.acronym ILIKE CONCAT('%', :filter, '%')
          )
    ),
    
    future_event_map AS (
        SELECT DISTINCT ON (p.id)
            p.id AS participant_id,
            e.id AS future_event_id,
            e.external_id AS future_event_external_id,
            e.name AS future_event_name,
            e.start_date AS future_event_start_date,
            STRING_AGG(DISTINCT p2.acronym, ',') AS future_event_acronyms
        FROM sport.participant p
        JOIN sport.event_participant ep ON ep.participant_id = p.id
        JOIN sport.event e ON e.id = ep.event_id AND e.start_date > NOW()
        LEFT JOIN sport.event_participant ep2 ON ep2.event_id = e.id
        LEFT JOIN sport.participant p2 ON p2.id = ep2.participant_id
        GROUP BY p.id, e.id, e.external_id, e.name, e.start_date
        ORDER BY p.id, e.start_date
    ),
    
    paged_participants AS (
        SELECT up.participant_id
        FROM unique_participants up
        LEFT JOIN future_event_map fem ON fem.participant_id = up.participant_id
        ORDER BY
            CASE WHEN :sortBy = 'team_name' AND :sortOrder = 'asc' THEN up.team_name END ASC,
            CASE WHEN :sortBy = 'team_name' AND :sortOrder = 'desc' THEN up.team_name END DESC,
            CASE WHEN :sortBy = 'acronym' AND :sortOrder = 'asc' THEN up.acronym END ASC,
            CASE WHEN :sortBy = 'acronym' AND :sortOrder = 'desc' THEN up.acronym END DESC,
            CASE WHEN :sortBy = 'event_time' AND :sortOrder = 'asc' THEN fem.future_event_start_date END ASC,
            CASE WHEN :sortBy = 'event_time' AND :sortOrder = 'desc' THEN fem.future_event_start_date END DESC,
            up.participant_id
        OFFSET :offset
        LIMIT :limit
    ),
    
    top_5_played_events AS (
        SELECT *
        FROM (
            SELECT
                pp.participant_id,
                e.id AS event_id,
                e.name,
                e.start_date,
                STRING_AGG(DISTINCT p2.acronym, ',') AS event_acronyms,
                ROW_NUMBER() OVER (
                    PARTITION BY pp.participant_id
                    ORDER BY e.start_date DESC
                ) AS rn
            FROM paged_participants pp
            JOIN sport.event_participant ep ON ep.participant_id = pp.participant_id
            JOIN sport.event e ON e.id = ep.event_id AND e.start_date < NOW()
            JOIN sport.event_participant ep2 ON ep2.event_id = e.id
            JOIN sport.participant p2 ON p2.id = ep2.participant_id
            WHERE e.start_date < NOW()
            GROUP BY pp.participant_id, e.id, e.name, e.start_date
        ) filtered
        WHERE rn <= 5
    )
    
    SELECT
        es.stat_name AS stat_name,
        es.value_raw AS value_raw,
        es.value_numeric AS value_numeric,
        es.event_id AS event_id,
        t5.name AS event_name,
        t5.start_date AS event_start_date,
        p.external_id AS id,
        p.team_name AS team_name,
        p.acronym AS acronym,
        comp.external_id AS competition_id,
        comp.name AS competition_name,
        fem.future_event_external_id AS future_event_id,
        fem.future_event_name AS future_event_name,
        fem.future_event_start_date AS future_event_start_date,
        t5.event_acronyms AS event_participant_acronyms,
        fem.future_event_acronyms
    FROM paged_participants pp
    JOIN sport.participant p ON p.id = pp.participant_id
    JOIN sport.competition comp ON comp.id = p.competition_id
    LEFT JOIN future_event_map fem ON fem.participant_id = p.id
    LEFT JOIN top_5_played_events t5 ON t5.participant_id = p.id
    LEFT JOIN sport.event_stat es
      ON es.event_id = t5.event_id
     AND es.target_id = t5.participant_id
     AND es.target_type = 'PARTICIPANT'
     AND (:statNames IS NULL OR es.stat_name IN (:statNames))
     ORDER BY
            CASE WHEN :sortBy = 'team_name' AND :sortOrder = 'asc' THEN p.team_name END ASC,
            CASE WHEN :sortBy = 'team_name' AND :sortOrder = 'desc' THEN p.team_name END DESC,
            CASE WHEN :sortBy = 'acronym' AND :sortOrder = 'asc' THEN p.acronym END ASC,
            CASE WHEN :sortBy = 'acronym' AND :sortOrder = 'desc' THEN p.acronym END DESC,
            CASE WHEN :sortBy = 'event_time' AND :sortOrder = 'asc' THEN fem.future_event_start_date END ASC,
            CASE WHEN :sortBy = 'event_time' AND :sortOrder = 'desc' THEN fem.future_event_start_date END DESC,
            fem.future_event_start_date
    """, nativeQuery = true)
    List<ParticipantStatRow> listParticipantStats(
            @Param("competitionId") Integer competitionId,
            @Param("participantIds") List<Integer> participantIds,
            @Param("marketId") Integer marketId,
            @Param("filter") String filter,
            @Param("sortBy") String sortBy,
            @Param("sortOrder") String sortOrder,
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("statNames") Collection<String> statNames
    );

    @Query(value = """
    WITH future_event_map AS (
        SELECT DISTINCT ON (p.id)
            p.id AS participant_id,
            fe.id AS future_event_id,
            fe.external_id AS future_event_external_id,
            fe.name AS future_event_name,
            fe.start_date AS future_event_start_date,
            STRING_AGG(DISTINCT ep_part.acronym, ',') AS future_event_acronyms
        FROM sport.participant p
        JOIN sport.event_participant ep ON ep.participant_id = p.id
        JOIN sport.event fe ON fe.id = ep.event_id
        LEFT JOIN sport.event_participant ep_ev ON ep_ev.event_id = fe.id
        LEFT JOIN sport.participant ep_part ON ep_part.id = ep_ev.participant_id
        WHERE fe.start_date > NOW()
        GROUP BY p.id, fe.id, fe.external_id, fe.name, fe.start_date
        ORDER BY p.id, fe.start_date
    ),
    
    top_5_played_events AS (
        SELECT *
        FROM (
            SELECT
                p.id AS participant_id,
                e.id AS event_id,
                e.name,
                e.start_date,
                STRING_AGG(DISTINCT p2.acronym, ',') AS event_acronyms,
                ROW_NUMBER() OVER (
                    PARTITION BY p.id
                    ORDER BY e.start_date DESC
                ) AS rn
            FROM sport.participant p
            JOIN sport.event_participant ep ON ep.participant_id = p.id
            JOIN sport.event e ON e.id = ep.event_id AND e.start_date < NOW()
            JOIN sport.event_participant ep2 ON ep2.event_id = e.id
            JOIN sport.participant p2 ON p2.id = ep2.participant_id
            WHERE p.external_id = :participantId
            GROUP BY p.id, e.id, e.name, e.start_date
        ) filtered
        WHERE rn <= 5
    )

    SELECT
        es.stat_name AS stat_name,
        es.value_raw AS value_raw,
        es.value_numeric AS value_numeric,
        es.event_id AS event_id,
        t5.name AS event_name,
        t5.start_date AS event_start_date,
        p.external_id AS id,
        p.team_name AS team_name,
        p.acronym AS acronym,
        comp.external_id AS competition_id,
        comp.name AS competition_name,
        fem.future_event_external_id AS future_event_id,
        fem.future_event_name AS future_event_name,
        fem.future_event_start_date AS future_event_start_date,
        t5.event_acronyms AS event_participant_acronyms,
        fem.future_event_acronyms
    FROM sport.participant p
    JOIN sport.competition comp ON comp.id = p.competition_id
    LEFT JOIN future_event_map fem ON fem.participant_id = p.id
    LEFT JOIN top_5_played_events t5 ON t5.participant_id = p.id
    LEFT JOIN sport.event_stat es
      ON es.event_id = t5.event_id
     AND es.target_id = p.id
     AND es.target_type = 'PARTICIPANT'
     AND (:statNames IS NULL OR es.stat_name IN (:statNames))
    WHERE p.external_id = :participantId
    """, nativeQuery = true)
    List<ParticipantStatRow> getParticipantStatsDetails(
            @Param("participantId") Integer participantId,
            @Param("statNames") Collection<String> statNames
    );

}
