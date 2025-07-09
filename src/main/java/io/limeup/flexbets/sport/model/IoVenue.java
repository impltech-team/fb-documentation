package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "io_venue", schema = "sport")

@Data
public class IoVenue {

    @Id
    @Column(name = "stadium_id")
    private Integer stadiumId;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "surface")
    private String surface;

    @Column(name = "left_field")
    private Integer leftField;

    @Column(name = "mid_left_field")
    private Integer midLeftField;

    @Column(name = "left_center_field")
    private Integer leftCenterField;

    @Column(name = "mid_left_center_field")
    private Integer midLeftCenterField;

    @Column(name = "center_field")
    private Integer centerField;

    @Column(name = "mid_right_center_field")
    private Integer midRightCenterField;

    @Column(name = "right_center_field")
    private Integer rightCenterField;

    @Column(name = "mid_right_field")
    private Integer midRightField;

    @Column(name = "right_field")
    private Integer rightField;

    @Column(name = "geo_lat")
    private Double geoLat;

    @Column(name = "geo_long")
    private Double geoLong;

    @Column(name = "altitude")
    private Integer altitude;

    @Column(name = "home_plate_direction")
    private Integer homePlateDirection;

    @Column(name = "type")
    private String type;
}
