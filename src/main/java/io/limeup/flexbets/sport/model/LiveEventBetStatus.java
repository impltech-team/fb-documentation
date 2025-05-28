package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "live_event_bet_status")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LiveEventBetStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private LiveEvent event;

    private String name;
    private String value;
}