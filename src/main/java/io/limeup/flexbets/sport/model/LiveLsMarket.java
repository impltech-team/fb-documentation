package io.limeup.flexbets.sport.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "live_ls_market")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LiveLsMarket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fixtureId;

    private Long marketId;
    private String marketName;
    private String marketMainLine;
}
