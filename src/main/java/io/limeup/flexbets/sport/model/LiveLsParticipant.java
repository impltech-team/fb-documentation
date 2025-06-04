package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "live_ls_participant")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LiveLsParticipant {
    @Id
    private Long id;

    private Long fixtureId;

    private String lsParticipantName;
    private Integer lsParticipantPosition;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private LiveLsEvent event;
}

