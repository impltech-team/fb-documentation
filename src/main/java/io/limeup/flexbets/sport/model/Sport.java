package io.limeup.flexbets.sport.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sport_seq")
    @SequenceGenerator(name = "sport_seq", sequenceName = "sport_id_seq")
    private Long id;

    @Column(unique = true)
    private Integer externalId;

    private String name;

}
