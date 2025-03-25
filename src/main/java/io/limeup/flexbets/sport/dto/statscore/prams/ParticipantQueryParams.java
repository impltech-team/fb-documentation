package io.limeup.flexbets.sport.dto.statscore.prams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantQueryParams {

    private Integer sportId;
    private Integer limit = 50;
    private Integer page = 1;
    private Integer seasonId;
    private Integer areaId;
    private Subtype subtype;
    private Type type;
    private String multiIds;
    private String virtual;

    public enum Subtype {
        ATHLETE("athlete"),
        COACH("coach"),
        REFEREE("referee"),
        DIRECTOR("director");

        private final String value;

        Subtype(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Type {
        TEAM("team"),
        PERSON("person");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}

