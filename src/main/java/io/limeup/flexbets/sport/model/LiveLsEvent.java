package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "live_ls_event")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LiveLsEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fixture_id", nullable = false)
    private Long fixtureId;

    @Column(name = "received_at")
    private LocalDateTime receivedAt;

}