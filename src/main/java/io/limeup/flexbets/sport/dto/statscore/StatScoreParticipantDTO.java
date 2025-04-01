package io.limeup.flexbets.sport.dto.statscore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@JsonIgnoreProperties
public class StatScoreParticipantDTO {
    @EqualsAndHashCode.Include
    private int id;
    private String type;
    private String name;
    private String shortName;
    private String acronym;
    private String gender;
    private Integer areaId;
    private String areaName;
    private String areaCode;
    private Integer sportId;
    private String sportName;
    private String national;
    private String website;
    private Long ut;
    private String slug;
    private String virtual;
    private Integer shirtNr;

    private Details details;
    private List<StatScoreVenueDTO> venues;

    @Data
    public static class Details {
        private String founded;
        private String phone;
        private String email;
        private String address;
        private Integer venueId;
        private String venueName;
        private String weight;
        private String height;
        private String nickname;
        private String positionName;
        private Integer positionId;
        private String birthdate;
        private String bornPlace;
        private String isRetired;
        private String subtype;
    }
}
