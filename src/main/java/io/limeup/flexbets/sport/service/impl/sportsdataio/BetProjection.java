package io.limeup.flexbets.sport.service.impl.sportsdataio;

public interface BetProjection {
    Long getBetId();

    Long getEventId();

    Integer getMarketTypeId();

    Long getBetTypeId();

    String getBetType();

    Long getOutcomeId();

    Long getPlayerId();

    String getParticipant();

    String getOutcomeType();

    String getValue();
}
