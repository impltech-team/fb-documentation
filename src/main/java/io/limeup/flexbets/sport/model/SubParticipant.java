package io.limeup.flexbets.sport.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_participant_seq")
    @SequenceGenerator(name = "sub_participant_seq", sequenceName = "sub_participant_id_seq")
    private Long id;

    @Column(unique = true)
    private Integer externalId;

    private String playerName;
    private String position;
    private Integer shirtNumber;
    private String avatarUrl;

    private String gender;
    private String weight;
    private String height;
    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id", referencedColumnName = "id")
    private Competition competition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    private Area area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", referencedColumnName = "id")
    private Participant participant;

    @OneToMany(mappedBy = "targetId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EventStat> historicalStats;

}
