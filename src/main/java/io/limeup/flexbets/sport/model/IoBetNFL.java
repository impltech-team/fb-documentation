package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "io_bet_nfl", schema = "sport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IoBetNFL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "io_event_nfl_id", referencedColumnName = "id")
    private IoEventNFL event;

    @Column(name = "market_id")
    private Long marketId;

    @Column(name = "betting_market_type_id")
    private Integer bettingMarketTypeId;

    @Column(name = "betting_market_type")
    private String bettingMarketType;

    @Column(name = "betting_bet_type_id")
    private Integer bettingBetTypeId;

    @Column(name = "betting_bet_type")
    private String bettingBetType;

    @Column(name = "betting_period_type_id")
    private Integer bettingPeriodTypeId;

    @Column(name = "betting_period_type")
    private String bettingPeriodType;

    private String name;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "team_key")
    private String teamKey;

    @Column(name = "player_id")
    private Long playerId;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "any_bets_available")
    private boolean anyBetsAvailable;

    @Column(name = "is_archived")
    private boolean isArchived;

    @Column(name = "archive_location")
    private String archiveLocation;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "bet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IoBetOutcomeNFL> bettingOutcomes;
}
