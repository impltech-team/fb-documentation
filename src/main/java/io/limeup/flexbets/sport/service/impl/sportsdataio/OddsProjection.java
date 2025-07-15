package io.limeup.flexbets.sport.service.impl.sportsdataio;

import java.time.LocalDateTime;

public interface OddsProjection {
    Long getId();
    String getMarketName();
    Integer getMarketId();
    String getLine();
    String getBetType();
    String getPrice();
    String getStatus();
    LocalDateTime getLastUpdatedDate();

    Integer getTeamId();
}