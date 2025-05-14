package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDTO {
    private int id;
    private String competition;
    private int competitionId;
    private String eventName;
    private LocalDateTime eventDate;
    private String status;
    private List<ParticipantSummaryDTO> participants;
    private VenueDTO venue;
    private List<EventMarketDTO> markets;
}
