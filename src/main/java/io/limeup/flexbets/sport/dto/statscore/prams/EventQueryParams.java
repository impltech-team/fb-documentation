package io.limeup.flexbets.sport.dto.statscore.prams;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventQueryParams {

    @NotNull(message = "date_from is required")
    private LocalDateTime dateFrom;
    @NotNull(message = "date_to is required")
    private LocalDateTime dateTo;
    private Integer sportId;
    private Integer areaId;
    private Integer competitionId;
    private Integer seasonId;
    private Integer stageId;
    private Integer groupId;
    private Integer participantId;
    private String multiIds;
    private String venueType;
    private Integer venueId;
    private String sortType;
    private String sortOrder;
    private String relationStatus;
    private Integer statusId;
    private String statusType;
    private String coverageType;
    private String scoutsfeed;
    private String eventsDetails;
    private String competitionsDetails;
    private String itemStatus;
    private String verifiedResult;
    private String product;
    private String booked;
    @Min(1)
    private Integer page = 1;
    @Min(1)
    @Max(150)
    private Integer limit = 50;
    private String tz;
    private Long timestamp;
}

