package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "live_participant")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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