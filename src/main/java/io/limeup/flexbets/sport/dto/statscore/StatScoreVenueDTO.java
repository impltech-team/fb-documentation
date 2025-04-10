package io.limeup.flexbets.sport.dto.statscore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class StatScoreVenueDTO {
    @EqualsAndHashCode.Include
    private Integer id;
    private String name;
    private String shortName;
    private String country;
    private String city;
    private String status;
    private String url;
    private String opened;
    private String photo;
    private Double lat;
    private Double lng;
    private Long ut;
}

