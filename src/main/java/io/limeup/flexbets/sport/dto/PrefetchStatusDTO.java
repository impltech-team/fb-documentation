
package io.limeup.flexbets.sport.dto;

import io.limeup.flexbets.sport.model.PrefetchLog;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PrefetchStatusDTO {
    private Long id;
    private LocalDate prefetchDate;
    private Integer competitionId;
    private PrefetchLog.Status status;
    private LocalDateTime lastUpdated;
    private String errorMessage;
}
