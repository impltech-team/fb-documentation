package io.limeup.flexbets.sport.service.live;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveParticipantDTO {
    private Long id;
    private Integer counter;
    private String name;
    private String shortName;
    private String acronym;
    private Integer areaId;
    private String areaName;
    private String areaCode;
    private Long ut;
    private String type;
    private List<LiveResultDTO> results;
}