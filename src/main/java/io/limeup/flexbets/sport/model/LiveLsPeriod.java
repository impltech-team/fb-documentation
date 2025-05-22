package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "live_ls_period")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LiveLsPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fixtureId;

    private Integer lsPeriodType;
    private Boolean lsPeriodIsFinished;
    private Boolean lsPeriodIsConfirmed;
    private Integer lsPeriodSequenceNumber;

    @Column(name = "ls_period_result_position_1")
    private Integer lsPeriodResultPosition1;

    @Column(name = "ls_period_result_position_2")
    private Integer lsPeriodResultPosition2;
}
