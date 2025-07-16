package io.limeup.flexbets.sport.dto.sportsdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SportsDataNFLStadiumDTO {

    @JsonProperty("StadiumID")
    private Integer stadiumID;

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

    @JsonProperty("PlayingSurface")
    private String playingSurface;

    @JsonProperty("GeoLat")
    private Double geoLat;

    @JsonProperty("GeoLong")
    private Double geoLong;

    @JsonProperty("Type")
    private String type;
}