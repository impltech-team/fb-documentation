package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "io_bet_outcome_nfl", schema = "sport")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IoBetOutcomeNFL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "outcome_id")
    private Long outcomeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "io_bet_nfl_id", referencedColumnName = "id")
    private IoBetNFL bet;

    private String name;
    private Integer odds;
    private Integer handicap;
    private Double total;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
