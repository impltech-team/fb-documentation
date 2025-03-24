package io.limeup.flexbets.sport.dto.statscore.prams;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipantQueryParams {
    private Integer sportId;
    @Builder.Default
    private Integer limit = 50;
    @Builder.Default
    private Integer page = 1;
    private Integer seasonId;
    private Integer areaId;

    @Builder.Default
    private Subtype subtype = Subtype.ATHLETE;

    private String multiIds;

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
}

