package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueDTO {
    private Integer venueId;
    private String venueName;
    private String location;
}
