package io.limeup.flexbets.sport.dto.sportsdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class SportsDataTeamDTO {

    @JsonProperty("TeamID")
    private Integer teamId;

    @JsonProperty("Key")
    private String key;

    @JsonProperty("Active")
    private Boolean active;

    @JsonProperty("City")
    private String city;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("StadiumID")
    private Integer stadiumId;

    @JsonProperty("League")
    private String league;

    @JsonProperty("Division")
    private String division;

    @JsonProperty("PrimaryColor")
    private String primaryColor;

    @JsonProperty("SecondaryColor")
    private String secondaryColor;

    @JsonProperty("TertiaryColor")
    private String tertiaryColor;

    @JsonProperty("QuaternaryColor")
    private String quaternaryColor;

    @JsonProperty("WikipediaLogoUrl")
    private String wikipediaLogoUrl;

    @JsonProperty("WikipediaWordMarkUrl")
    private String wikipediaWordMarkUrl;

    @JsonProperty("GlobalTeamID")
    private Long globalTeamId;

    @JsonProperty("HeadCoach")
    private String headCoach;

    @JsonProperty("HittingCoach")
    private String hittingCoach;

    @JsonProperty("PitchingCoach")
    private String pitchingCoach;
}

