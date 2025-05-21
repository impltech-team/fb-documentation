package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "live_participant_result", schema = "sport")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LiveParticipantResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private LiveParticipant participant;

    private Integer resultId;
    private String value;

}