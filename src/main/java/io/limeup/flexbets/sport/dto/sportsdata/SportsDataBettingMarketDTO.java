package io.limeup.flexbets.sport.dto.sportsdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SportsDataBettingMarketDTO {

    @JsonProperty("BettingMarketID")
    private Long bettingMarketId;

    @JsonProperty("BettingEventID")
    private Long bettingEventId;

    @JsonProperty("BettingMarketTypeID")
    private Integer bettingMarketTypeId;

    @JsonProperty("BettingMarketType")
    private String bettingMarketType;

    @JsonProperty("BettingBetTypeID")
    private Integer bettingBetTypeId;

    @JsonProperty("BettingBetType")
    private String bettingBetType;

    @JsonProperty("BettingPeriodTypeID")
    private Integer bettingPeriodTypeId;

    @JsonProperty("BettingPeriodType")
    private String bettingPeriodType;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("TeamID")
    private Long teamId;

    @JsonProperty("TeamKey")
    private String teamKey;

    @JsonProperty("PlayerID")
    private Long playerId;

    @JsonProperty("PlayerName")
    private String playerName;

    @JsonProperty("Created")
    private LocalDateTime created;

    @JsonProperty("Updated")
    private LocalDateTime updated;

    @JsonProperty("AnyBetsAvailable")
    private Boolean anyBetsAvailable;

    @JsonProperty("IsArchived")
    private Boolean archived;

    @JsonProperty("BettingOutcomes")
    private List<BettingOutcomeDTO> bettingOutcomes;

    @Data
    public static class BettingOutcomeDTO {
        @JsonProperty("BettingOutcomeID")
        private Long bettingOutcomeId;

        @JsonProperty("BettingMarketID")
        private Long bettingMarketId;

        @JsonProperty("BettingOutcomeTypeID")
        private Integer bettingOutcomeTypeId;

        @JsonProperty("BettingOutcomeType")
        private String bettingOutcomeType;

        @JsonProperty("PayoutAmerican")
        private String payoutAmerican;

        @JsonProperty("PayoutDecimal")
        private String payoutDecimal;

        @JsonProperty("Value")
        private String value;

        @JsonProperty("Participant")
        private String participant;

        @JsonProperty("IsAvailable")
        private Boolean isAvailable;

        @JsonProperty("IsAlternate")
        private Boolean isAlternate;

        @JsonProperty("Created")
        private LocalDateTime created;

        @JsonProperty("Updated")
        private LocalDateTime updated;

        @JsonProperty("TeamID")
        private Long teamId;

        @JsonProperty("PlayerID")
        private Long playerId;

        @JsonProperty("IsInPlay")
        private Boolean isInPlay;
    }
}
