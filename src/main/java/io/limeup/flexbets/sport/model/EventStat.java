package io.limeup.flexbets.sport.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventStat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_stat_seq")
    @SequenceGenerator(name = "event_stat_seq", sequenceName = "event_stat_id_seq")
    private Long id;

    private Integer externalId;

    @Enumerated(EnumType.STRING)
    private StatTargetType targetType;

    private Long targetId;

    private Integer targetExternalId;

    private String statName;

    private String valueRaw;

    private Double valueNumeric;

    @Enumerated(EnumType.STRING)
    private StatDataType dataType;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;
}

