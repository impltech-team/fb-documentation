package io.limeup.flexbets.sport.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubParticipant extends BaseIdentifiableEntity {

    private String playerName;
    private String teamName;
    private Long competitionId;
    private Long participantId;
    private String position;
    private Integer shirtNumber;
    private String avatarUrl;

    private Integer areaId;
    private String areaName;
    private String gender;
    private String weight;
    private String height;
    private LocalDate birthDate;

    @OneToMany(mappedBy = "targetId", cascade = CascadeType.ALL)
    private List<EventStat> historicalStats;

    @ManyToOne
    private Event nextEvent;

}
