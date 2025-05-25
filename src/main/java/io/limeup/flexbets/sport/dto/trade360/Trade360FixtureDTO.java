package io.limeup.flexbets.sport.dto.trade360;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trade360FixtureDTO {
    @JsonProperty("StartDate")
    private LocalDateTime startDateTime;
    @JsonProperty("Participants")
    private List<Trade360ParticipantDTO> participants;
}
