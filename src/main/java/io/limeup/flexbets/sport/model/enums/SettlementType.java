package io.limeup.flexbets.sport.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SettlementType {
    CANCELED(-1),
    LOSER(1),
    WINNER(2),
    REFUND(3),
    HALF_LOST(4),
    HALF_WON(5);

    private final int id;

    SettlementType(int id) {
        this.id = id;
    }

    public static SettlementType getById(Integer id) {
        if (id == null)
            return null;
        else
            return Arrays.stream(SettlementType.values())
                    .filter(settlementType -> settlementType.id == id)
                    .findFirst().orElse(null);
    }
}
