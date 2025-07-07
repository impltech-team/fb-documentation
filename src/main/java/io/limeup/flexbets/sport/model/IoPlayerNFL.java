package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "io_player_nfl", schema = "sport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IoPlayerNFL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_id")
    private Long playerId;

    private String team;
    private Integer number;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String position;
    private String status;

    private String height;
    private Integer weight;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String college;
    private Integer experience;

    @Column(name = "fantasy_position")
    private String fantasyPosition;

    private Boolean active;

    @Column(name = "position_category")
    private String positionCategory;

    private String name;
    private Integer age;

    @Column(name = "experience_string")
    private String experienceString;

    @Column(name = "birth_date_string")
    private String birthDateString;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "bye_week")
    private Integer byeWeek;

    @Column(name = "upcoming_game_opponent")
    private String upcomingGameOpponent;

    @Column(name = "upcoming_game_week")
    private Integer upcomingGameWeek;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "average_draft_position")
    private Double averageDraftPosition;

    @Column(name = "depth_position_category")
    private String depthPositionCategory;

    @Column(name = "depth_position")
    private String depthPosition;

    @Column(name = "depth_order")
    private Integer depthOrder;

    @Column(name = "depth_display_order")
    private Integer depthDisplayOrder;

    @Column(name = "current_team")
    private String currentTeam;

    @Column(name = "college_draft_team")
    private String collegeDraftTeam;

    @Column(name = "college_draft_year")
    private Integer collegeDraftYear;

    @Column(name = "college_draft_round")
    private Integer collegeDraftRound;

    @Column(name = "college_draft_pick")
    private Integer collegeDraftPick;

    @Column(name = "is_undrafted_free_agent")
    private Boolean isUndraftedFreeAgent;

    @Column(name = "height_feet")
    private Integer heightFeet;

    @Column(name = "height_inches")
    private Integer heightInches;

    @Column(name = "upcoming_opponent_rank")
    private Integer upcomingOpponentRank;

    @Column(name = "upcoming_opponent_position_rank")
    private Integer upcomingOpponentPositionRank;

    @Column(name = "current_status")
    private String currentStatus;

    @Column(name = "upcoming_salary")
    private Integer upcomingSalary;

    @Column(name = "fantasy_alarm_player_id")
    private String fantasyAlarmPlayerId;

    @Column(name = "sport_radar_player_id")
    private String sportRadarPlayerId;

    @Column(name = "rotoworld_player_id")
    private String rotoworldPlayerId;

    @Column(name = "roto_wire_player_id")
    private String rotoWirePlayerId;

    @Column(name = "stats_player_id")
    private String statsPlayerId;

    @Column(name = "sports_direct_player_id")
    private String sportsDirectPlayerId;

    @Column(name = "xml_team_player_id")
    private String xmlTeamPlayerId;

    @Column(name = "fan_duel_player_id")
    private String fanDuelPlayerId;

    @Column(name = "draft_kings_player_id")
    private String draftKingsPlayerId;

    @Column(name = "yahoo_player_id")
    private String yahooPlayerId;

    @Column(name = "injury_status")
    private String injuryStatus;

    @Column(name = "injury_body_part")
    private String injuryBodyPart;

    @Column(name = "injury_start_date")
    private String injuryStartDate;

    @Column(name = "injury_notes", columnDefinition = "TEXT")
    private String injuryNotes;

    @Column(name = "fan_duel_name")
    private String fanDuelName;

    @Column(name = "draft_kings_name")
    private String draftKingsName;

    @Column(name = "yahoo_name")
    private String yahooName;

    @Column(name = "fantasy_position_depth_order")
    private Integer fantasyPositionDepthOrder;

    @Column(name = "injury_practice")
    private String injuryPractice;

    @Column(name = "injury_practice_description")
    private String injuryPracticeDescription;

    @Column(name = "declared_inactive")
    private Boolean declaredInactive;

    @Column(name = "upcoming_fan_duel_salary")
    private Integer upcomingFanDuelSalary;

    @Column(name = "upcoming_draft_kings_salary")
    private Integer upcomingDraftKingsSalary;

    @Column(name = "upcoming_yahoo_salary")
    private Integer upcomingYahooSalary;

    @Column(name = "team_id")
    private Integer teamId;

    @Column(name = "global_team_id")
    private Long globalTeamId;

    @Column(name = "fantasy_draft_player_id")
    private String fantasyDraftPlayerId;

    @Column(name = "fantasy_draft_name")
    private String fantasyDraftName;

    @Column(name = "usa_today_player_id")
    private String usaTodayPlayerId;

    @Column(name = "usa_today_headshot_url")
    private String usaTodayHeadshotUrl;

    @Column(name = "usa_today_headshot_no_background_url")
    private String usaTodayHeadshotNoBackgroundUrl;

    @Column(name = "usa_today_headshot_updated")
    private LocalDateTime usaTodayHeadshotUpdated;

    @Column(name = "usa_today_headshot_no_background_updated")
    private LocalDateTime usaTodayHeadshotNoBackgroundUpdated;
}

