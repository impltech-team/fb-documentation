package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDTO {
    private int id;
    private String teamName;
    private String acronym;
    private String competition;
    private int competitionId;
    private EventLiteDTO nextEvent;
    private List<HistoricalStatDTO> historicalStats;
    private List<OddsDTO> odds;
}
