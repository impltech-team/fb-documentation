package io.limeup.flexbets.sport.dto.trade360;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trade360SnapshotResponseDTO {
    @JsonProperty("FixtureId")
    Long fixtureId;
    @JsonProperty("Fixture")
    Trade360FixtureDTO fixture;
    @JsonProperty("Markets")
    List<Trade360MarketDTO> markets;
}
