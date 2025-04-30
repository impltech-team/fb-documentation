package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.SportDTO;
import io.limeup.flexbets.sport.dto.SportLiteDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportLiteDTO;
import io.limeup.flexbets.sport.model.Sport;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SportMapperTest {

    private final SportMapper sportMapper = new SportMapper();

    @Test
    void toEntityFromLiteDTOShouldMapFields() {
        StatScoreSportLiteDTO dto = new StatScoreSportLiteDTO();
        dto.setId(1);
        dto.setName("Football");

        Sport sport = sportMapper.toEntity(dto);

        assertThat(sport).isNotNull();
        assertThat(sport.getExternalId()).isEqualTo(1);
        assertThat(sport.getName()).isEqualTo("Football");
    }

    @Test
    void toEntityFromFullDTOShouldMapFields() {
        StatScoreSportDTO dto = new StatScoreSportDTO();
        dto.setId(2);
        dto.setName("Basketball");

        Sport sport = sportMapper.toEntity(dto);

        assertThat(sport).isNotNull();
        assertThat(sport.getExternalId()).isEqualTo(2);
        assertThat(sport.getName()).isEqualTo("Basketball");
    }

    @Test
    void updateEntityFromLiteDTOShouldUpdateFields() {
        Sport sport = new Sport();
        StatScoreSportLiteDTO dto = new StatScoreSportLiteDTO();
        dto.setId(3);
        dto.setName("Tennis");

        Sport updated = sportMapper.updateEntity(sport, dto);

        assertThat(updated.getExternalId()).isEqualTo(3);
        assertThat(updated.getName()).isEqualTo("Tennis");
    }

    @Test
    void updateEntityFromFullDTOShouldUpdateFields() {
        Sport sport = new Sport();
        StatScoreSportDTO dto = new StatScoreSportDTO();
        dto.setId(4);
        dto.setName("Volleyball");

        Sport updated = sportMapper.updateEntity(sport, dto);

        assertThat(updated.getExternalId()).isEqualTo(4);
        assertThat(updated.getName()).isEqualTo("Volleyball");
    }

    @Test
    void toLiteDTOShouldMapList() {
        Sport sport = new Sport();
        sport.setExternalId(5);
        sport.setName("Hockey");

        List<SportLiteDTO> result = SportMapper.toLiteDTO(List.of(sport));

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(5);
        assertThat(result.get(0).getName()).isEqualTo("Hockey");
    }

    @Test
    void statScoreToFlexBetsDTOShouldMapNestedFields() {
        StatScoreSportDTO dto = new StatScoreSportDTO();
        dto.setId(6);
        dto.setName("Rugby");

        StatScoreSportDTO.SportStatus status = new StatScoreSportDTO.SportStatus();
        status.setName("Live");
        status.setShortName("L");
        dto.setStatuses(List.of(status));

        StatScoreSportDTO.SportResult resultType = new StatScoreSportDTO.SportResult();
        resultType.setType("win");
        resultType.setName("Win");
        dto.setResults(List.of(resultType));

        StatScoreSportDTO.SportDetail detail = new StatScoreSportDTO.SportDetail();
        detail.setCode("goal");
        detail.setName("Goal Scored");
        dto.setDetails(List.of(detail));

        StatScoreSportDTO.SportStats stats = new StatScoreSportDTO.SportStats();
        StatScoreSportDTO.SportStats.SportStat teamStat = new StatScoreSportDTO.SportStats.SportStat();
        teamStat.setCode("attack");
        teamStat.setName("Attacks");
        stats.setTeam(List.of(teamStat));
        StatScoreSportDTO.SportStats.SportStat personStat = new StatScoreSportDTO.SportStats.SportStat();
        personStat.setCode("yellow_card");
        personStat.setName("Yellow Card");
        stats.setPerson(List.of(personStat));
        dto.setStats(stats);

        StatScoreSportDTO.SportIncident incident = new StatScoreSportDTO.SportIncident();
        incident.setCode("red_card");
        incident.setName("Red Card");
        dto.setIncidents(List.of(incident));

        SportDTO mapped = SportMapper.statScoreToFlexBetsDTO(dto);

        assertThat(mapped).isNotNull();
        assertThat(mapped.getId()).isEqualTo(6);
        assertThat(mapped.getName()).isEqualTo("Rugby");
        assertThat(mapped.getStatuses()).hasSize(1);
        assertThat(mapped.getResultTypes()).hasSize(1);
        assertThat(mapped.getEventDetails()).hasSize(1);
        assertThat(mapped.getTeamStatistics()).hasSize(1);
        assertThat(mapped.getPlayerStatistics()).hasSize(1);
        assertThat(mapped.getIncidents()).hasSize(1);
    }
}

