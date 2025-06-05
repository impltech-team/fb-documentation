package io.limeup.flexbets.sport.repository.projection;

import java.time.LocalDateTime;

public interface BetRow {
    Long getId();
    Integer getEventExternalId();
    Integer getMarketExternalId();
    String getMarketName();
    String getMarketType();
    String getStatus();
    String getName();
    String getLine();
    String getPrice();
    String getParticipantName();
    LocalDateTime getLastUpdated();
}
