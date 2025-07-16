package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "io_stadium_nfl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IoStadiumNFL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stadium_id", unique = true)
    private Integer stadiumId;

    @Column(name = "name", columnDefinition = "text")
    private String name;

    @Column(name = "city", columnDefinition = "text")
    private String city;

    @Column(name = "state", columnDefinition = "text")
    private String state;

    @Column(name = "country", columnDefinition = "text")
    private String country;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "playing_surface", columnDefinition = "text")
    private String playingSurface;

    @Column(name = "geo_lat")
    private Double geoLat;

    @Column(name = "geo_long")
    private Double geoLong;

    @Column(name = "type", columnDefinition = "text")
    private String type;
}
