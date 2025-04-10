package io.limeup.flexbets.sport.dto.statscore;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class StatScoreSubParticipantDTO {
    @EqualsAndHashCode.Include
    private Integer id;
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
    private String logo;
    private String virtual;
    private Integer shirtNr;
    private Details details;
    private Integer teamId;
    private Integer lineupId;
    private String position;
    private String positionType;
    private String positionReasonCode;
    private String positionReason;
    private Integer positionReasonId;
    private String teamConnection;
    private List<StatScoreStatDTO> stats;

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
        private String birthdate;
        private String bornPlace;
        private String isRetired;
        private String subtype;
    }

}
