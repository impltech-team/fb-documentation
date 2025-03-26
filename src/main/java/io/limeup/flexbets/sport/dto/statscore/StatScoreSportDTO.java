package io.limeup.flexbets.sport.dto.statscore;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatScoreSportDTO {
    private Integer id;
    private Integer lsId;
    private String name;
    private String url;
    private String active;
    private String hasTimer;
    private String participantQuantity;
    private String template;
    private String incidentsPositions;
    private Long ut;

    private List<SportStatus> statuses;
    private List<SportResult> results;
    private SportStats stats;
    private List<SportDetail> details;
    private List<SportIncident> incidents;
    private List<SportStandingType> standingsTypes;
    private List<SportVenueDetail> venuesDetails;
    private List<SportFormation> formations;

    @Data
    public static class SportStatus {
        private Integer id;
        private String name;
        private String shortName;
        private String type;
        private Long ut;
    }

    @Data
    public static class SportResult {
        private Integer id;
        private String name;
        private String shortName;
        private String code;
        private String type;
        private String dataType;
    }

    @Data
    public static class SportStats {
        private List<SportStat> team;
        private List<SportStat> person;

        @Data
        public static class SportStat {
            private Integer id;
            private String name;
            private String shortName;
            private String code;
            private String dataType;
        }
    }

    @Data
    public static class SportDetail {
        private Integer id;
        private String name;
        private String code;
        private String dataType;
        private Boolean isSeasonDetail;
        private String description;
        private String input;
        private String type;
        private String format;
        private String nullable;
        private String defaultValue;
        private List<String> relatedStatuses;
        private List<PossibleValue> possibleValues;

        @Data
        public static class PossibleValue {
            private String key;
            private String value;
        }
    }

    @Data
    public static class SportIncident {
        private Integer id;
        private String name;
        private String important;
        private String importantForTrader;
        private Integer sportId;
        private String type;
        private String group;
        private String category;
        private Long ut;
        private String code;
        private String forValue;
        private Boolean gameBreak;
        private List<Attribute> attributes;

        @Data
        public static class Attribute {
            private Integer ids;
            private String name;
            private String shortName;
            private Integer sortOrder;
        }
    }

    @Data
    public static class SportStandingType {
        private Integer id;
        private String name;
        private Long ut;
        private List<Column> columns;

        @Data
        public static class Column {
            private Integer id;
            private String name;
            private String shortName;
            private String code;
        }
    }

    @Data
    public static class SportVenueDetail {
        private Integer id;
        private String name;
        private String description;
    }

    @Data
    public static class SportFormation {
        private String formation;
    }
}
