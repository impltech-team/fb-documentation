package io.limeup.flexbets.sport.repository.projection;

import java.time.LocalDateTime;

public interface ParticipantStatRow {
    Integer getId();
    String getTeamName();
    String getAcronym();
    Integer getCompetitionId();
    String getCompetitionName();
    Integer getFutureEventId();
    String getFutureEventName();
    LocalDateTime getFutureEventStartDate();
    String getEventParticipantAcronyms();
    String getFutureEventAcronyms();
    Integer getEventId();
    String getEventName();
    LocalDateTime getEventStartDate();
    String getStatName();
    String getValueRaw();
    Double getValueNumeric();
}
