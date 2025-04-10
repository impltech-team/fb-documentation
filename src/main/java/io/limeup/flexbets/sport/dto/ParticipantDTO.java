package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDTO {
    private Integer id;
    private String teamName;
    private String acronym;
    private String competition;
    private Integer competitionId;
    private EventLiteDTO nextEvent;
    private List<HistoricalStatDTO> historicalStats;
    private List<OddsDTO> odds;
}
