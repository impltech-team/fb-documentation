package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueDTO {
    private int venueId;
    private String venueName;
    private String location;
    private int capacity;
}
