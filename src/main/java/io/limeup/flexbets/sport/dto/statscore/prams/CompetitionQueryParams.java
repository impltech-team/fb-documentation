package io.limeup.flexbets.sport.dto.statscore.prams;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionQueryParams {
    private String lang;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTo;
    private Integer page;
    private Integer limit;
    private String areaType;
    private String type;
    private Integer areaId;
    private Integer sportId;
    private Integer tourId;
    private String multiIds;
    private String gender;
    private Long timestamp;
    private String shortName;
    private String sortType;
    private Integer participantId;
    private String statusType;
    private String tz;
}

