package io.limeup.flexbets.sport.model;

import io.limeup.flexbets.sport.model.enums.SettingName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Settings {

    @Id
    @Enumerated(EnumType.STRING)
    private SettingName name;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
