package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private int id;
    private String competition;
    private int competitionId;
    private String eventName;
    private String eventDate;
    private List<ParticipantSummaryDTO> participants;
    private VenueDTO venue;
    private OddsMarketDTO odds;
}
