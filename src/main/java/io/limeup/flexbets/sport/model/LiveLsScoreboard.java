package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "live_ls_scoreboard")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LiveLsScoreboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fixtureId;

    private Integer lsScoreboardStatus;
    private Integer lsScoreboardCurrentPeriod;
    private String lsScoreboardTime;

    @Column(name = "ls_scoreboard_result_position_1")
    private Integer lsScoreboardResultPosition1;
    @Column(name = "ls_scoreboard_result_position_2")
    private Integer lsScoreboardResultPosition2;
}
