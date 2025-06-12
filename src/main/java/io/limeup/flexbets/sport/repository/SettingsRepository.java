package io.limeup.flexbets.sport.repository;

import io.limeup.flexbets.sport.model.Settings;
import io.limeup.flexbets.sport.model.enums.SettingName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingsRepository extends JpaRepository<Settings, String> {

    Optional<Settings> findByName(SettingName name);
}
