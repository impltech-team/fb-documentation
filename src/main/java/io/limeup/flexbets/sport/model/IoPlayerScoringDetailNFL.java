package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "io_player_scoring_detail_nfl", schema = "sport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IoPlayerScoringDetailNFL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_key", columnDefinition = "text")
    private String gameKey;

    @Column(name = "season_type", columnDefinition = "text")
    private String seasonType;

    @Column(name = "player_id", columnDefinition = "text")
    private String playerId;

    @Column(name = "team", columnDefinition = "text")
    private String team;

    @Column(name = "season", columnDefinition = "text")
    private String season;

    @Column(name = "week", columnDefinition = "text")
    private String week;

    @Column(name = "scoring_type", columnDefinition = "text")
    private String scoringType;

    @Column(name = "length", columnDefinition = "text")
    private String length;

    @Column(name = "scoring_detail_id", columnDefinition = "text")
    private String scoringDetailId;

    @Column(name = "player_game_id", columnDefinition = "text")
    private String playerGameId;

    @Column(name = "scoring_play_id", columnDefinition = "text")
    private String scoringPlayId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_game_stats_id", referencedColumnName = "id")
    private IoPlayerStatsNFL playerGameStats;
}