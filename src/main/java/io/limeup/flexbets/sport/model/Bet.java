package io.limeup.flexbets.sport.model;

import io.limeup.flexbets.sport.model.enums.BetStatus;
import io.limeup.flexbets.sport.model.enums.MarketType;
import io.limeup.flexbets.sport.model.enums.SettlementType;
import io.limeup.flexbets.sport.model.enums.SuspensionReason;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bet_seq")
    @SequenceGenerator(name = "bet_seq", sequenceName = "bet_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "external_id", nullable = false)
    private Long externalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id", referencedColumnName = "id")
    private Market market;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BetStatus status;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "line", nullable = false)
    private String line;

    @Column(name = "baseline", nullable = false)
    private String baseline;

    @Column(name = "price", nullable = false)
    private String price;

    @Column(name = "participant_name")
    private String participantName;

    @Enumerated(EnumType.STRING)
    @Column(name = "settlement")
    private SettlementType settlement;

    @Enumerated(EnumType.STRING)
    @Column(name = "suspension_reason")
    private SuspensionReason suspensionReason;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}

