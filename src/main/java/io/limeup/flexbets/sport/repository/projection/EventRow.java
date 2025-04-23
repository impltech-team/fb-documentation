package io.limeup.flexbets.sport.repository.projection;

import java.time.LocalDateTime;

public interface EventRow {
    Long getId();
    Integer getExternalId();
    String getEventName();
    LocalDateTime getStartDate();
    String getStatus();
    Integer getCompetitionId();
    String getCompetitionName();
    Integer getParticipantId();
    String getTeamName();
    String getAcronym();
    Integer getVenueId();
    String getVenueLocation();
    String getVenueName();
    Integer getVenueCapacity();
}
