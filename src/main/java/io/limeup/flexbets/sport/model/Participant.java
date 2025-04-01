package io.limeup.flexbets.sport.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Participant extends BaseIdentifiableEntity {

    private String teamName;
    private String acronym;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id", referencedColumnName = "id")
    private Competition competition;

    @OneToMany(mappedBy = "targetId", cascade = CascadeType.ALL)
    private List<EventStat> historicalStats;

    @ManyToOne
    private Event nextEvent;
}
