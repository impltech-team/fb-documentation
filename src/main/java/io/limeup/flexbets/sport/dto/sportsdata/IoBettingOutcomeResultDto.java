package io.limeup.flexbets.sport.dto.sportsdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IoBettingOutcomeResultDto(
        @JsonProperty("BettingOutcomeID")       Long outcomeId,
        @JsonProperty("BettingResultTypeID")    Integer  typeId,
        @JsonProperty("BettingResultType")      String  type,
        @JsonProperty("ActualValue")            String  actualValue
) {}
