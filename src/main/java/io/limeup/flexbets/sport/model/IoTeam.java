package io.limeup.flexbets.sport.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "io_team", schema = "sport")
public class IoTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_id", unique = true)
    private Integer teamId;

    @Column(name = "key", nullable = false)
    private String key;

    private Boolean active;

    private String city;

    private String name;

    @Column(name = "stadium_id")
    private Integer stadiumId;

    private String league;

    private String division;

    @Column(name = "primary_color")
    private String primaryColor;

    @Column(name = "secondary_color")
    private String secondaryColor;

    @Column(name = "tertiary_color")
    private String tertiaryColor;

    @Column(name = "quaternary_color")
    private String quaternaryColor;

    @Column(name = "wikipedia_logo_url")
    private String wikipediaLogoUrl;

    @Column(name = "wikipedia_word_mark_url")
    private String wikipediaWordMarkUrl;

    @Column(name = "global_team_id")
    private Long globalTeamId;

    @Column(name = "head_coach")
    private String headCoach;

    @Column(name = "hitting_coach")
    private String hittingCoach;

    @Column(name = "pitching_coach")
    private String pitchingCoach;
}
