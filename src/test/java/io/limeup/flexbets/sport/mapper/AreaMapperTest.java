package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.AreaDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreAreaDTO;
import io.limeup.flexbets.sport.model.Area;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AreaMapperTest {

    private final AreaMapper areaMapper = new AreaMapper();

    @Test
    void toEntityShouldMapDtoToEntity() {
        StatScoreAreaDTO dto = new StatScoreAreaDTO();
        dto.setId(1);
        dto.setName("Ukraine");
        dto.setAreaCode("UA");
        dto.setParentAreaId(100);

        Area entity = areaMapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getExternalId()).isEqualTo(1);
        assertThat(entity.getName()).isEqualTo("Ukraine");
        assertThat(entity.getAreaCode()).isEqualTo("UA");
        assertThat(entity.getParentAreaId()).isEqualTo(100);
    }

    @Test
    void toEntityWhenDtoIsNullShouldReturnNull() {
        Area entity = areaMapper.toEntity(null);

        assertThat(entity).isNull();
    }

    @Test
    void updateEntityShouldUpdateFields() {
        Area entity = new Area();
        StatScoreAreaDTO dto = new StatScoreAreaDTO();
        dto.setId(2);
        dto.setName("Poland");
        dto.setAreaCode("PL");
        dto.setParentAreaId(200);

        Area updated = areaMapper.updateEntity(entity, dto);

        assertThat(updated).isNotNull();
        assertThat(updated.getExternalId()).isEqualTo(2);
        assertThat(updated.getName()).isEqualTo("Poland");
        assertThat(updated.getAreaCode()).isEqualTo("PL");
        assertThat(updated.getParentAreaId()).isEqualTo(200);
    }

    @Test
    void updateEntityWhenEntityIsNullShouldReturnNull() {
        StatScoreAreaDTO dto = new StatScoreAreaDTO();
        Area result = areaMapper.updateEntity(null, dto);

        assertThat(result).isNull();
    }

    @Test
    void updateEntityWhenDtoIsNullShouldReturnOriginalEntity() {
        Area entity = new Area();
        entity.setExternalId(1);

        Area result = areaMapper.updateEntity(entity, null);

        assertThat(result).isSameAs(entity);
        assertThat(result.getExternalId()).isEqualTo(1);
    }

    @Test
    void toDTOShouldMapListOfAreas() {
        Area area = new Area();
        area.setExternalId(5);
        area.setName("Germany");
        area.setAreaCode("DE");

        List<AreaDTO> dtos = AreaMapper.toDTO(List.of(area));

        assertThat(dtos).hasSize(1);
        assertThat(dtos.get(0).getId()).isEqualTo(5);
        assertThat(dtos.get(0).getName()).isEqualTo("Germany");
        assertThat(dtos.get(0).getCountryCode()).isEqualTo("DE");
    }

    @Test
    void toDTOWhenListIsNullShouldReturnEmptyList() {
        List<AreaDTO> result = AreaMapper.toDTO(null);
        assertThat(result).isEmpty();
    }
}
