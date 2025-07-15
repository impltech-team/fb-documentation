package io.limeup.flexbets.sport.repository.sportsdataio;


import io.limeup.flexbets.sport.model.IoTeam;
import io.limeup.flexbets.sport.repository.projection.ParticipantStatRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IoTeamRepository extends JpaRepository<IoTeam, Long> {

    Optional<IoTeam> findByTeamId(Integer teamId);

    List<IoTeam> findAllByTeamIdIn(Set<Integer> collect);

    @Query(value = """ 
           WITH base_teams AS (
                SELECT
                     t.team_id,
                     t.name AS team_name,
                     t.city,
                     t.key AS acronym
            FROM io_team t
                 WHERE (:participantIds IS NULL OR t.team_id IN (:participantIds))
                    AND (
                        :filter IS NULL OR
                        t.name ILIKE CONCAT('%', :filter, '%') OR
                        t.key ILIKE CONCAT('%', :filter, '%')
                         )
                     ),
                        
                  future_events AS (
                           SELECT DISTINCT ON (t.team_id)
                                t.team_id,
                                e.id AS future_event_id,
                                CONCAT(ht.city, ' ', ht.name, ' vs ', at.city, ' ', at.name) AS future_event_name,
                                e.datetime_utc AS future_event_start_date,
                                ht.key || ',' || at.key AS future_event_acronyms
                            FROM base_teams t
                            JOIN io_event e ON t.team_id = e.home_team_id OR t.team_id = e.away_team_id
                            JOIN io_team ht ON ht.team_id = e.home_team_id
                            JOIN io_team at ON at.team_id = e.away_team_id
                            WHERE e.datetime_utc > NOW()
                            ORDER BY t.team_id, e.datetime_utc
                        )
                        
                        SELECT
                            t.team_id AS id,
                            t.team_name AS team_name,
                            t.acronym AS acronym,
                            5466 AS competition_id,
                            'MLB' AS competiotion_name,
                            f.future_event_id,
                            f.future_event_name,
                            f.future_event_start_date,
                            f.future_event_acronyms AS event_event_acronyms
                        FROM base_teams t
                        LEFT JOIN future_events f ON f.team_id = t.team_id
                        ORDER BY
                            CASE WHEN :sortBy = 'acronym' AND :sortOrder = 'asc' THEN t.acronym END ASC,
                            CASE WHEN :sortBy = 'acronym' AND :sortOrder = 'desc' THEN t.acronym END DESC,
                            CASE WHEN :sortBy = 'team_name' AND :sortOrder = 'asc' THEN t.team_name END ASC,
                            CASE WHEN :sortBy = 'team_name' AND :sortOrder = 'desc' THEN t.team_name END DESC,
                            f.future_event_start_date
                        OFFSET :offset
                        LIMIT :limit
            """, nativeQuery = true)
    List<ParticipantStatRow> listParticipantStats(
            @Param("participantIds") Collection<Integer> participantIds,

            @Param("filter") String filter,
            @Param("sortBy") String sortBy,
            @Param("sortOrder") String sortOrder,
            @Param("offset") int offset,
            @Param("limit") int limit
    );


    @Query(value = """ 

            WITH base_teams AS (
                            SELECT
                                t.team_id,
                                t.name AS team_name,
                                t.city,
                                t.key AS acronym
                            FROM io_team t
                            WHERE (:participantIds IS NULL OR t.team_id IN (:participantIds))
                              AND (
                                  :filter IS NULL OR
                                  t.name ILIKE CONCAT('%', :filter, '%') OR
                                  t.key ILIKE CONCAT('%', :filter, '%')
                              )
                        ),
                        
                        future_events AS (
                            SELECT DISTINCT ON (t.team_id)
                                t.team_id,
                                e.id AS future_event_id,
                                CONCAT(ht.city, ' ', ht.name, ' vs ', at.city, ' ', at.name) AS future_event_name,
                                e.datetime_utc AS future_event_start_date,
                                ht.key || ',' || at.key AS future_event_acronyms
                            FROM base_teams t
                            JOIN io_event e ON t.team_id = e.home_team_id OR t.team_id = e.away_team_id
                            JOIN io_team ht ON ht.team_id = e.home_team_id
                            JOIN io_team at ON at.team_id = e.away_team_id
                            WHERE e.datetime_utc > NOW()
                            ORDER BY t.team_id, e.datetime_utc
                        ),
                        bets AS (
                            SELECT DISTINCT b.team_id
                            FROM io_bet b
                            JOIN base_teams  sp  ON sp.team_id    = b.team_id
                             
                            WHERE b.bet_type_id = :marketId and b.any_bets_available = true
                          
                        )
                        
                        
                        SELECT
                            t.team_id AS id,
                            t.team_name AS team_name,
                            t.acronym AS acronym,
                            5466 AS competition_id,
                            'MLB' AS competiotion_name,
                            f.future_event_id,
                            f.future_event_name,
                            f.future_event_start_date,
                            f.future_event_acronyms AS event_event_acronyms
                        FROM base_teams t
                        LEFT JOIN future_events f ON f.team_id = t.team_id
                         JOIN bets b ON b.team_id = t.team_id
                        ORDER BY
                            CASE WHEN :sortBy = 'acronym' AND :sortOrder = 'asc' THEN t.acronym END ASC,
                            CASE WHEN :sortBy = 'acronym' AND :sortOrder = 'desc' THEN t.acronym END DESC,
                            CASE WHEN :sortBy = 'team_name' AND :sortOrder = 'asc' THEN t.team_name END ASC,
                            CASE WHEN :sortBy = 'team_name' AND :sortOrder = 'desc' THEN t.team_name END DESC,
                            f.future_event_start_date
                        OFFSET :offset
                        LIMIT :limit
            """, nativeQuery = true)
    List<ParticipantStatRow> listParticipantStatByTeamIdAndMarketId(
            @Param("marketId") Integer marketId,
            @Param("participantIds") Collection<Integer> participantIds,
            @Param("filter") String filter,
            @Param("sortBy") String sortBy,
            @Param("sortOrder") String sortOrder,
            @Param("offset") int offset,
            @Param("limit") int limit
    );
}
