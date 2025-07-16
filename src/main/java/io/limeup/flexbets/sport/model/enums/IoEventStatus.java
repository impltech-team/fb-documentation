package io.limeup.flexbets.sport.model.enums;

import lombok.Getter;

@Getter
public enum IoEventStatus {
    SCHEDULED("Scheduled"),
    IN_PROGRESS("InProgress"),
    FINAL("Final"),
    SUSPENDED("Suspended"),
    POSTPONED("Postponed"),
    DELAYED("Delayed"),
    CANCELED("Canceled"),
    FORFEIT("Forfeit");

    private final String name;

    IoEventStatus(String name) {
        this.name = name;
    }
}
