package io.limeup.flexbets.sport.dto.statscore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatScoreStandingDTO {
    private Integer id;
    private String name;
    private Integer sportId;
    private String sportName;
    private Integer typeId;
    private String typeName;
    private String subtype;
    private Integer objectId;
    private String objectType;
    private String objectName;
    private String itemStatus;
    private String resetGroupRank;
    private Long ut;
    private List<Group> groups;

    @Data
    public static class Group {
        private Integer id;
        private String name;
        private Long ut;
        private List<Participant> participants;
        private List<Correction> corrections;
        private List<Zone> zones;
    }

    @Data
    public static class Participant {
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
        private String oldParticipantId;
        private String slug;
        private String logo;
        private String virtual;
        private Integer shirtNr;
        private List<Column> columns;
    }

    @Data
    public static class Column {
        private Integer id;
        private String name;
        private String shortName;
        private String code;
    }

    @Data
    public static class Correction {
        private Integer participantId;
        private String participantName;
        private String value;
        private String reason;
    }

    @Data
    public static class Zone {
        private Integer id;
        private String name;
        private String colour;
    }
}
