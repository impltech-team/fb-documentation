package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventStatisticDTO {
    private int eventId;
    private String eventName;
    private LocalDateTime eventDate;
    private Double value;
    private String rawValue;
    private String opponent;
}
