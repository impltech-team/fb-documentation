package io.limeup.flexbets.sport.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseIdentifiableEntity {

    @Id
    private Long id;

    public BaseIdentifiableEntity() {
    }

    public BaseIdentifiableEntity(Long id) {
        this.id = id;
    }

}
