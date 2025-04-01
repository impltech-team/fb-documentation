package io.limeup.flexbets.sport.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Competition extends BaseIdentifiableEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private CompetitionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sport_id", referencedColumnName = "id")
    private Sport sport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    private Area area;

    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    private String gender;
}
