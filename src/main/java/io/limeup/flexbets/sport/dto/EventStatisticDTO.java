package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventStatisticDTO {
    private int eventId;
    private String eventName;
    private String eventDate;
    private int value;
    private String opponent;
}
