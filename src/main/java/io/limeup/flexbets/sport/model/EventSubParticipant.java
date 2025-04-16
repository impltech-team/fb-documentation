package io.limeup.flexbets.sport.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event_sub_participant")
public class EventSubParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "esp_seq")
    @SequenceGenerator(name = "esp_seq", sequenceName = "event_sub_participant_id_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_participant_id")
    private SubParticipant subParticipant;

    private String position;
    private String positionReason;

    @Column(name = "is_home_team")
    private boolean isHomeTeam;
}
