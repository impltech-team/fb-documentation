package io.limeup.flexbets.sport.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BetStatus {
    OPEN(1),
    SUSPENDED(2),
    SETTLED(3);

    private final int id;

    BetStatus(int id) {
        this.id = id;
    }

    public static BetStatus getById(int id) {
        return Arrays.stream(BetStatus.values())
                .filter(betStatus -> betStatus.id == id)
                .findFirst().orElse(null);
    }
}
