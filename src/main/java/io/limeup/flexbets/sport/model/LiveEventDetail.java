package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "live_event_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LiveEventDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private LiveEvent event;

    @Column(name = "detail_id", nullable = false)
    private Integer detailId;

    private String value;
}