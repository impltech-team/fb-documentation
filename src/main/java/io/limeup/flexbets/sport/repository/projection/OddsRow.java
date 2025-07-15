package io.limeup.flexbets.sport.repository.projection;

import java.time.LocalDateTime;

public interface OddsRow {
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