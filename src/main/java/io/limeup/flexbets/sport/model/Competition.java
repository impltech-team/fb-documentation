package io.limeup.flexbets.sport.model;

import io.limeup.flexbets.sport.model.enums.CompetitionType;
import io.limeup.flexbets.sport.model.enums.StatusType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "competition_seq")
    @SequenceGenerator(name = "competition_seq", sequenceName = "competition_id_seq")
    private Long id;

    @Column(unique = true)
    private Integer externalId;

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

    @Column(name = "ls_id", unique = true)
    private Integer lsId;
}
