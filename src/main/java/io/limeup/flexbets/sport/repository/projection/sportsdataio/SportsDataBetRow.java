package io.limeup.flexbets.sport.repository.projection.sportsdataio;

import java.time.LocalDateTime;

public interface SportsDataBetRow {
    Long getId();
    Long getPlayerId();
    Integer getMarketTypeId();
    String getMarketType();
    String getBetType();
    String getBetLine();
    String getPrice();
    LocalDateTime getLastUpdated();
}
