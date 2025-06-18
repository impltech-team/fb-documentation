package io.limeup.flexbets.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubParticipantDTO {
    private int id;
    private String playerName;
    private int competitionId;
    private String competition;
    private String avatarUrl;
    private int participantId;
    private String team;
    private String position;
    private EventLiteDTO nextEvent;
    private Integer shirtNr;
    private int areaId;
    private String areaName;
    private String gender;
    private String weight;
    private String height;
    private LocalDate birthDate;
    private List<HistoricalStatDTO> historicalStats;
    private List<OddsDTO> odds;
}
