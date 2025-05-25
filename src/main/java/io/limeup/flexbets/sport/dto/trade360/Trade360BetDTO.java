package io.limeup.flexbets.sport.dto.trade360;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trade360BetDTO {
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("PlayerName")
    private String participantName;
    @JsonProperty("Line")
    private String line;
    @JsonProperty("BaseLine")
    private String baseLine;
    @JsonProperty("Price")
    private String price;
    @JsonProperty("Status")
    private Integer status;
    @JsonProperty("Settlement")
    private Integer settlement;
    @JsonProperty("SuspensionReason")
    private Integer suspensionReason;
    @JsonProperty("LastUpdate")
    private LocalDateTime lastUpdated;
}
