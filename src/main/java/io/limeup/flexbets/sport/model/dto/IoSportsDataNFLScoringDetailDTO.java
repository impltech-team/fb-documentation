package io.limeup.flexbets.sport.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IoSportsDataNFLScoringDetailDTO {
    @JsonProperty("GameKey") private String gameKey;
    @JsonProperty("SeasonType") private Integer seasonType;
    @JsonProperty("PlayerID") private Long playerId;
    @JsonProperty("Team") private String team;
    @JsonProperty("Season") private Integer season;
    @JsonProperty("Week") private Integer week;
    @JsonProperty("ScoringType") private String scoringType;
    @JsonProperty("Length") private Integer length;
    @JsonProperty("ScoringDetailID") private Long scoringDetailId;
    @JsonProperty("PlayerGameID") private Long playerGameId;
    @JsonProperty("ScoringPlayID") private Long scoringPlayId;
}
