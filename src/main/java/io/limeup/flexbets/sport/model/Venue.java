package io.limeup.flexbets.sport.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Venue extends BaseIdentifiableEntity {

    private String name;

    private String shortName;

    private String country;

    private String status;

    private String url;

    private String city;

    private Double lat;

    private Double lng;

    private LocalDate opened;

}
