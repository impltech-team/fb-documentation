package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "live_ls_fixture_extra_data")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LiveLsFixtureExtraData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fixtureId;

    private String name;
    private String value;
}