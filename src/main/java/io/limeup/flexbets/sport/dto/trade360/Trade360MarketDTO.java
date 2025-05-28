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
public class Trade360MarketDTO {
    @JsonProperty("Id")
    private Integer id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Bets")
    private List<Trade360BetDTO> bets;
}
