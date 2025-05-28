package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.CompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.model.enums.CompetitionType;
import io.limeup.flexbets.sport.model.enums.StatusType;
import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Sport;
import io.limeup.flexbets.sport.utils.ConstantUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CompetitionMapperTest {

    private final CompetitionMapper competitionMapper = new CompetitionMapper();

    @Test
    void toEntityShouldMapDtoToEntity() {
        StatScoreCompetitionDTO dto = new StatScoreCompetitionDTO();
        dto.setId(1);
        dto.setName("Champions League");
        dto.setGender("male");
        dto.setType("country_league");
        dto.setStatus("active");

        Sport sport = new Sport();
        sport.setExternalId(10);
        sport.setName(ConstantUtils.TestConstants.FOOTBALL);

        Area area = new Area();
        area.setExternalId(100);
        area.setName("Europe");

        Competition entity = competitionMapper.toEntity(dto, sport, area);

        assertThat(entity).isNotNull();
        assertThat(entity.getExternalId()).isEqualTo(1);
        assertThat(entity.getName()).isEqualTo("Champions League");
        assertThat(entity.getGender()).isEqualTo("male");
        assertThat(entity.getType()).isEqualTo(CompetitionType.COUNTRY_LEAGUE);
        assertThat(entity.getStatusType()).isEqualTo(StatusType.ACTIVE);
        assertThat(entity.getSport()).isSameAs(sport);
        assertThat(entity.getArea()).isSameAs(area);
    }

    @Test
    void updateEntityShouldUpdateFields() {
        Competition entity = new Competition();
        StatScoreCompetitionDTO dto = new StatScoreCompetitionDTO();
        dto.setId(2);
        dto.setName("World Cup");
        dto.setGender("male");
        dto.setType("international");
        dto.setStatus("active");

        Sport sport = new Sport();
        Area area = new Area();

        Competition updated = competitionMapper.updateEntity(entity, dto, sport, area);

        assertThat(updated).isNotNull();
        assertThat(updated.getExternalId()).isEqualTo(2);
        assertThat(updated.getName()).isEqualTo("World Cup");
        assertThat(updated.getGender()).isEqualTo("male");
        assertThat(updated.getType()).isEqualTo(CompetitionType.INTERNATIONAL);
        assertThat(updated.getStatusType()).isEqualTo(StatusType.ACTIVE);
    }

    @Test
    void updateEntityWhenEntityIsNullShouldReturnNull() {
        StatScoreCompetitionDTO dto = new StatScoreCompetitionDTO();
        Competition result = competitionMapper.updateEntity(null, dto, null, null);

        assertThat(result).isNull();
    }

    @Test
    void updateEntityWhenDtoIsNullShouldReturnOriginalEntity() {
        Competition entity = new Competition();
        entity.setExternalId(1);

        Competition result = competitionMapper.updateEntity(entity, null, null, null);

        assertThat(result).isSameAs(entity);
        assertThat(result.getExternalId()).isEqualTo(1);
    }

    @Test
    void updateEntityWhenInvalidTypeOrStatusShouldFallbackGracefully() {
        StatScoreCompetitionDTO dto = new StatScoreCompetitionDTO();
        dto.setType("invalid_type");
        dto.setStatus("invalid_status");

        Competition entity = new Competition();
        Competition updated = competitionMapper.updateEntity(entity, dto, null, null);

        assertThat(updated).isNotNull();
        assertThat(updated.getType()).isEqualTo(CompetitionType.UNDEFINED);
        assertThat(updated.getStatusType()).isNull();
    }

    @Test
    void toDTOShouldMapCompetitionList() {
        Competition competition = new Competition();
        competition.setExternalId(5);
        competition.setName("La Liga");
        competition.setGender("male");
        competition.setType(CompetitionType.COUNTRY_LEAGUE);
        competition.setStatusType(StatusType.ACTIVE);

        Sport sport = new Sport();
        sport.setExternalId(20);
        sport.setName(ConstantUtils.TestConstants.FOOTBALL);

        Area area = new Area();
        area.setExternalId(30);
        area.setName("Spain");

        competition.setSport(sport);
        competition.setArea(area);

        List<CompetitionDTO> dtos = CompetitionMapper.toDTO(List.of(competition));

        assertThat(dtos).hasSize(1);
        CompetitionDTO dto = dtos.get(0);
        assertThat(dto.getId()).isEqualTo(5);
        assertThat(dto.getName()).isEqualTo("La Liga");
        assertThat(dto.getGender()).isEqualTo("male");
        assertThat(dto.getSportId()).isEqualTo(20);
        assertThat(dto.getSportName()).isEqualTo(ConstantUtils.TestConstants.FOOTBALL);
        assertThat(dto.getAreaId()).isEqualTo(30);
        assertThat(dto.getAreaName()).isEqualTo("Spain");
        assertThat(dto.getType()).isEqualTo("COUNTRY_LEAGUE");
        assertThat(dto.getStatusType()).isEqualTo("ACTIVE");
    }

    @Test
    void toDTOWhenListIsNullShouldReturnEmptyList() {
        List<CompetitionDTO> result = CompetitionMapper.toDTO(null);

        assertThat(result).isEmpty();
    }
}

