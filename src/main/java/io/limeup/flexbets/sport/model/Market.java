package io.limeup.flexbets.sport.model;

import io.limeup.flexbets.sport.model.enums.MarketType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "market_seq")
    @SequenceGenerator(name = "market_seq", sequenceName = "market_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;

    @Enumerated(EnumType.STRING)
    private MarketType marketType;

    @Column(name = "external_id")
    private Integer externalId;

    @Column(name = "market_name")
    private String marketName;

    @ElementCollection
    @CollectionTable(name = "market_linked_stats", joinColumns = @JoinColumn(name = "market_id"))
    @Column(name = "stat_name")
    private List<String> linkedStats = new ArrayList<>();

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;
}

