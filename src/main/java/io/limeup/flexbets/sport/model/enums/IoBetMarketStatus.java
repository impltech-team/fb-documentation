package io.limeup.flexbets.sport.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum IoBetMarketStatus {
    PLAYER_PROP("Player Prop"),
    TEAM_PROP("Team Prop");

    private final String name;

    IoBetMarketStatus(String name) {
        this.name = name;
    }
}
