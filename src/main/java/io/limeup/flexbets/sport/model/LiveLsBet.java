package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "live_ls_bet")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LiveLsBet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long marketId;

    private Long betId;
    private String betName;
    private Double betProbability;
    private String betSuspensionReason;
    private String betCalculatedMargin;
    private String betSerializedCalculatedMargin;
    private String betLine;
    private String betBaseLine;
    private Integer betStatus;
    private String betStartPrice;
    private String betPrice;
    private String betProviderId;
    private Instant betLastUpdate;
    private String betSerializedLastUpdate;
}

