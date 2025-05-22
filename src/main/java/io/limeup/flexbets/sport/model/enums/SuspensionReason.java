package io.limeup.flexbets.sport.model.enums;

import lombok.Getter;

@Getter
public enum SuspensionReason {
    PROVIDERS(1),
    MAJORITY(2),
    THRESHOLD(3),
    REFUND(4),
    MANDATORY_PROVIDERS(5),
    LIVESCORE_INCONSISTENCY(6),
    OUT_OF_PRICE_RANGE(6);

    private final int id;

    SuspensionReason(int id) {
        this.id = id;
    }
}
