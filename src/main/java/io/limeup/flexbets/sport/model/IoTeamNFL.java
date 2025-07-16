package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "io_team_nfl")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IoTeamNFL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "player_id")
    private Long playerId;

    @Column(name = "key")
    private String key;

    private String city;
    private String name;

    @Column(name = "full_name")
    private String fullName;

    private String conference;
    private String division;

    @Column(name = "stadium_id")
    private Integer stadiumId;

    @Column(name = "bye_week")
    private Integer byeWeek;

    @Column(name = "head_coach")
    private String headCoach;

    @Column(name = "offensive_coordinator")
    private String offensiveCoordinator;

    @Column(name = "defensive_coordinator")
    private String defensiveCoordinator;

    @Column(name = "special_teams_coach")
    private String specialTeamsCoach;

    @Column(name = "offensive_scheme")
    private String offensiveScheme;

    @Column(name = "defensive_scheme")
    private String defensiveScheme;

    @Column(name = "upcoming_salary")
    private Double upcomingSalary;

    @Column(name = "upcoming_opponent")
    private String upcomingOpponent;

    @Column(name = "upcoming_opponent_rank")
    private Integer upcomingOpponentRank;

    @Column(name = "upcoming_opponent_position_rank")
    private Integer upcomingOpponentPositionRank;

    @Column(name = "primary_color")
    private String primaryColor;

    @Column(name = "secondary_color")
    private String secondaryColor;

    @Column(name = "tertiary_color")
    private String tertiaryColor;

    @Column(name = "quaternary_color")
    private String quaternaryColor;

    @Column(name = "wikipedia_logo_url")
    private String wikipediaLogoUrl;

    @Column(name = "wikipedia_word_mark_url")
    private String wikipediaWordMarkUrl;

    @Column(name = "global_team_id")
    private Long globalTeamId;

    @Column(name = "draft_kings_name")
    private String draftKingsName;

    @Column(name = "draft_kings_player_id")
    private Long draftKingsPlayerId;

    @Column(name = "fan_duel_name")
    private String fanDuelName;

    @Column(name = "fan_duel_player_id")
    private Long fanDuelPlayerId;

    @Column(name = "fantasy_draft_name")
    private String fantasyDraftName;

    @Column(name = "fantasy_draft_player_id")
    private Long fantasyDraftPlayerId;

    @Column(name = "yahoo_name")
    private String yahooName;

    @Column(name = "yahoo_player_id")
    private Long yahooPlayerId;

    @Column(name = "average_draft_position")
    private Double averageDraftPosition;

    @Column(name = "average_draft_position_ppr")
    private Double averageDraftPositionPpr;

    @Column(name = "average_draft_position_2qb")
    private Double averageDraftPosition2qb;

    @Column(name = "average_draft_position_dynasty")
    private Double averageDraftPositionDynasty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id", referencedColumnName = "stadium_id", insertable = false, updatable = false)
    private IoStadiumNFL stadium;
}
