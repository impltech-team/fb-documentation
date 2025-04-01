package io.limeup.flexbets.sport.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Area extends BaseIdentifiableEntity {

    private String name;

    private String countryCode;
}
