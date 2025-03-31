package io.limeup.flexbets.sport.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Event extends BaseIdentifiableEntity {

    private String name;
    private LocalDateTime startDate;
    private Long competitionId;
    private String competitionName;
    private String opponentShortCode;

}
