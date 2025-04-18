package io.limeup.flexbets.sport.model;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "prefetch_log", schema = "sport", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"prefetch_date", "competition_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrefetchLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prefetch_date", nullable = false)
    private LocalDate prefetchDate;

    @Column(name = "competition_id", nullable = false)
    private Integer competitionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @Column(name = "error_message")
    private String errorMessage;

    @PrePersist
    public void prePersist() {
        this.lastUpdated = LocalDateTime.now();
        if (status == null) {
            this.status = Status.PENDING;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }

    public enum Status {
        PENDING,
        SUCCESS,
        FAILED
    }
}
