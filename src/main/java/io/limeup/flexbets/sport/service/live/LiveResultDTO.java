package io.limeup.flexbets.sport.service.live;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LiveResultDTO {
    private Long id;
    private String value;
}