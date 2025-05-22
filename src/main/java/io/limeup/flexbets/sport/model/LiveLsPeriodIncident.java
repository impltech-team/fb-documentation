package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "live_ls_period_incident")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LiveLsPeriodIncident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long periodId;

    private Integer lsIncidentPeriod;
    private Integer lsIncidentType;
    private Integer lsIncidentSeconds;
    private String lsIncidentParticipantPosition;
    @Column(name = "ls_incident_result_position_1")
    private Integer lsIncidentResultPosition1;
    @Column(name = "ls_incident_result_position_2")
    private Integer lsIncidentResultPosition2;
}

