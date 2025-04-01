package io.limeup.flexbets.sport.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Event extends BaseIdentifiableEntity {

    private String name;

    private LocalDateTime startDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id", referencedColumnName = "id")
    private Competition competition;

    private String homeTeamName;

    private String homeTeamShortCode;

    private String awayTeamName;

    private String awayTeamShortCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", referencedColumnName = "id")
    private Venue venue;
}
