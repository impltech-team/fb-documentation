package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventLiteDTO {
    private int eventId;
    private String eventName;
    private String eventDate;
    private String opponent;
}
