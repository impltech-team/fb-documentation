package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsBatchRequestDTO {
    private List<OddsQuery> queries;

    @Data
    public static class OddsQuery {
        private Integer marketId;
        private Integer subParticipantId;
        private Integer eventId;
    }
}
