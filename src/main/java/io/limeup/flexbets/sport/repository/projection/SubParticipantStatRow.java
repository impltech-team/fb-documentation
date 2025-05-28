package io.limeup.flexbets.sport.repository.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface SubParticipantStatRow {
    String getStatName();
    String getValueRaw();
    Double getValueNumeric();
    Integer getEventId();
    String getEventName();
    LocalDateTime getEventStartDate();
    Integer getId();
    String getPlayerName();
    String getPlayerShortName();
    String getPosition();
    Integer getShirtNumber();
    String getGender();
    String getAvatarUrl();
    String getTeamName();
    Integer getCompetitionId();
    String getCompetitionName();
    Integer getParticipantId();
    Integer getAreaId();
    String getAreaName();
    String getWeight();
    String getHeight();
    LocalDate getBirthDate();
    Integer getFutureEventId();
    String getFutureEventName();
    LocalDateTime getFutureEventStartDate();
    String getEventParticipantAcronyms();
    String getFutureEventAcronyms();
    String getCurrentTeamAcronym();
}
