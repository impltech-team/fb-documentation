package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "io_bet", schema = "sport")
public class IoBet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "io_event_id", referencedColumnName = "id")
    private IoEvent event;

    @Column(name = "market_id")
    private Long marketId;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "market_type_id")
    private Integer marketTypeId;

    //Change to enum or db table settings to filter specific markets
    @Column(name = "market_type")
    private String marketType;

    @Column(name = "bet_type_id")
    private Integer betTypeId;

    @Column(name = "bet_type")
    private String betType;

    @Column(name = "period_type_id")
    private Integer periodTypeId;

    @Column(name = "period_type")
    private String periodType;

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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "bet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IoBetOutcome> betOutcomes;
}
