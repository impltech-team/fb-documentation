package io.limeup.flexbets.sport.model;

import io.limeup.flexbets.sport.dto.statscore.prams.FetchIoStatus;
import io.limeup.flexbets.sport.dto.statscore.prams.FetchIoType;
import io.limeup.flexbets.sport.dto.statscore.prams.SportIoType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "io_fetch_log")
public class IoFetchLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FetchIoType fetchType;

    @Enumerated(EnumType.STRING)
    private SportIoType sportType;

    @Enumerated(EnumType.STRING)
    private FetchIoStatus status;

    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private Long durationMs;

    @Column(columnDefinition = "text")
    private String details;
}