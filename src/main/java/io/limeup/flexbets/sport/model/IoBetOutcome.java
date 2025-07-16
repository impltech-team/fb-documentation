package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "io_bet_outcome", schema = "sport")
public class IoBetOutcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "io_bet_id", referencedColumnName = "id")
    private IoBet bet;

    @Column(name = "outcome_id")
    private Long outcomeId;

    @Column(name = "outcome_type_id")
    private Integer outcomeTypeId;

    @Column(name = "outcome_type")
    private String outcomeType;

    @Column(name = "payout_american")
    private String payoutAmerican;

    @Column(name = "payout_decimal")
    private String payoutDecimal;

    private String participant;

    private String value;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "player_id")
    private Long playerId;

    @Column(name = "is_available")
    private boolean available;

    @Column(name = "is_alternate")
    private boolean alternate;

    @Column(name = "result_type_id")
    private Integer resultTypeId;

    @Column(name = "result_type")
    private String resultType;

    @Column(name = "result_value")
    private String resultValue;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
