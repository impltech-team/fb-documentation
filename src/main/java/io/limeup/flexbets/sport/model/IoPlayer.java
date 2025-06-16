package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "io_player", schema = "sport")
public class IoPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_id")
    private Long playerId;

    @Column(name = "sports_data_id")
    private String sportsDataId;

    private String status;

    @Column(name = "team_id")
    private Integer teamId;

    private String team;
    private Integer jersey;

    @Column(name = "position_category")
    private String positionCategory;

    private String position;

    @Column(name = "mlbam_id")
    private Long mlbamId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "bat_hand")
    private String batHand;

    @Column(name = "throw_hand")
    private String throwHand;

    private Integer height;
    private Integer weight;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "birth_city")
    private String birthCity;

    @Column(name = "birth_state")
    private String birthState;

    @Column(name = "birth_country")
    private String birthCountry;

    @Column(name = "high_school")
    private String highSchool;

    private String college;

    @Column(name = "pro_debut")
    private LocalDate proDebut;

    private String salary;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "sport_radar_player_id")
    private String sportRadarPlayerId;

    @Column(name = "rotoworld_player_id")
    private String rotoworldPlayerId;

    @Column(name = "roto_wire_player_id")
    private String rotoWirePlayerId;

    @Column(name = "fantasy_alarm_player_id")
    private String fantasyAlarmPlayerId;

    @Column(name = "stats_player_id")
    private String statsPlayerId;

    @Column(name = "sports_direct_player_id")
    private String sportsDirectPlayerId;

    @Column(name = "xml_team_player_id")
    private String xmlTeamPlayerId;

    @Column(name = "injury_status")
    private String injuryStatus;

    @Column(name = "injury_body_part")
    private String injuryBodyPart;

    @Column(name = "injury_start_date")
    private LocalDate injuryStartDate;

    @Column(name = "injury_notes", columnDefinition = "TEXT")
    private String injuryNotes;

    @Column(name = "fan_duel_player_id")
    private String fanDuelPlayerId;

    @Column(name = "draft_kings_player_id")
    private String draftKingsPlayerId;

    @Column(name = "yahoo_player_id")
    private String yahooPlayerId;

    @Column(name = "upcoming_game_id")
    private String upcomingGameId;

    @Column(name = "fan_duel_name")
    private String fanDuelName;

    @Column(name = "draft_kings_name")
    private String draftKingsName;

    @Column(name = "yahoo_name")
    private String yahooName;

    @Column(name = "global_team_id")
    private Long globalTeamId;

    @Column(name = "fantasy_draft_name")
    private String fantasyDraftName;

    @Column(name = "fantasy_draft_player_id")
    private String fantasyDraftPlayerId;

    private String experience;

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
