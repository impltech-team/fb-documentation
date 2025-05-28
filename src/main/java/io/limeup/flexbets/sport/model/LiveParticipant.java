package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "live_participant")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LiveParticipant {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private LiveEvent event;
    private Integer counter;
    private String name;
    private String shortName;
    private String acronym;
    private Integer areaId;
    private String areaName;
    private String areaCode;
    private Long ut;
    private String type;
    private Boolean lineupsCopied;
    private String stats;
    private String eventStatusStats;
    private String subparticipants;

}