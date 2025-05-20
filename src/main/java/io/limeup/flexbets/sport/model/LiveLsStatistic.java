package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "live_ls_statistic")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LiveLsStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fixtureId;

    private Integer lsStatType;
    @Column(name = "ls_stat_result_position_1")
    private Integer lsStatResultPosition1;
    @Column(name = "ls_stat_result_position_2")
    private Integer lsStatResultPosition2;
}
