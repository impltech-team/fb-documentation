package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventLiteDTO {
    private int eventId;
    private String eventName;
    private LocalDateTime eventDate;
    private String opponent;
}
