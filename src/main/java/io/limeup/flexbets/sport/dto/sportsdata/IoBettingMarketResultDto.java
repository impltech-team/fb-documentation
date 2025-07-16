package io.limeup.flexbets.sport.dto.sportsdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IoBettingMarketResultDto {
    @JsonProperty("IsMarketResultingSupported") boolean resultSupported;
    @JsonProperty("BettingOutcomeResults") List<IoBettingOutcomeResultDto> results;
}
