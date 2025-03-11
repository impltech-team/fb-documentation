package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetDTO {
    private Long id;
    private Long participantId;
    private String participantName;
    private String betType;
    private String price;
}
