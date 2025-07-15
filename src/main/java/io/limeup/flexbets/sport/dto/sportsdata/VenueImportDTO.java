package io.limeup.flexbets.sport.dto.sportsdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VenueImportDTO {

    @JsonProperty("StadiumID")
    private Integer stadiumId;

    @JsonProperty("Active")
    private Boolean active;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("City")
    private String city;

    @JsonProperty("State")
    private String state;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Capacity")
    private Integer capacity;

    @JsonProperty("Surface")
    private String surface;

    @JsonProperty("LeftField")
    private Integer leftField;

    @JsonProperty("MidLeftField")
    private Integer midLeftField;

    @JsonProperty("LeftCenterField")
    private Integer leftCenterField;

    @JsonProperty("MidLeftCenterField")
    private Integer midLeftCenterField;

    @JsonProperty("CenterField")
    private Integer centerField;

    @JsonProperty("MidRightCenterField")
    private Integer midRightCenterField;

    @JsonProperty("RightCenterField")
    private Integer rightCenterField;

    @JsonProperty("MidRightField")
    private Integer midRightField;

    @JsonProperty("RightField")
    private Integer rightField;

    @JsonProperty("GeoLat")
    private Double geoLat;

    @JsonProperty("GeoLong")
    private Double geoLong;

    @JsonProperty("Altitude")
    private Integer altitude;

    @JsonProperty("HomePlateDirection")
    private Integer homePlateDirection;

    @JsonProperty("Type")
    private String type;
}

