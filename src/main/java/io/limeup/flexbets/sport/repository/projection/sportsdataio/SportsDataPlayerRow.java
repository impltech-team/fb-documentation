package io.limeup.flexbets.sport.repository.projection.sportsdataio;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface SportsDataPlayerRow {
    Integer      getId();
    String       getAvatarUrl();
    String       getPlayerName();
    Integer      getShirtNumber();
    Integer      getPlayerTeamId();
    String       getPlayerTeamName();
    String       getPosition();
    Integer      getHeight();
    Integer      getWeight();
    String       getCountry();
    LocalDate    getBirthDate();
    Integer      getEventId();
    LocalDateTime getEventDatetime();
    String       getEventName();
    String       getOpponentTeamKey();

}
