package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.config.FlexBetsSportConfiguration;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreStatDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.model.Area;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.SubParticipant;
import io.limeup.flexbets.sport.repository.projection.SubParticipantStatRow;
import io.limeup.flexbets.sport.utils.ConstantUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SubParticipantMapperTest {

    private SubParticipantMapper mapper;

    @BeforeEach
    void setUp() {
        FlexBetsSportConfiguration configuration = new FlexBetsSportConfiguration();
        mapper = new SubParticipantMapper(configuration);
    }

    @Test
    void toEntityShouldMapFieldsCorrectly() {
        StatScoreSubParticipantDTO.Details details = new StatScoreSubParticipantDTO.Details();
        details.setWeight("70");
        details.setHeight("180");
        details.setPositionName(ConstantUtils.TestConstants.FORWARD);
        details.setBirthdate("1995-08-15");

        StatScoreStatDTO statsDTO = new StatScoreStatDTO();
        statsDTO.setDataType("decimal");
        statsDTO.setId(1);
        statsDTO.setName("Assists");
        statsDTO.setShortName("Assists");
        statsDTO.setValue("11");

        StatScoreSubParticipantDTO dto = new StatScoreSubParticipantDTO();
        dto.setId(1);
        dto.setName("Player 1");
        dto.setShirtNr(10);
        dto.setLogo("avatar.png");
        dto.setGender("male");
        dto.setDetails(details);
        dto.setStats(List.of(statsDTO));

        Area area = new Area();
        Competition competition = new Competition();

        SubParticipant entity = mapper.toEntity(dto, area, competition);

        assertThat(entity).isNotNull();
        assertThat(entity.getExternalId()).isEqualTo(1);
        assertThat(entity.getPlayerName()).isEqualTo("Player 1");
        assertThat(entity.getShirtNumber()).isEqualTo(10);
        assertThat(entity.getAvatarUrl()).isEqualTo("avatar.png");
        assertThat(entity.getGender()).isEqualTo("male");
        assertThat(entity.getWeight()).isEqualTo("70");
        assertThat(entity.getHeight()).isEqualTo("180");
        assertThat(entity.getPosition()).isEqualTo(ConstantUtils.TestConstants.FORWARD);
        assertThat(entity.getBirthDate()).isEqualTo(LocalDate.of(1995, 8, 15));
        assertThat(entity.getArea()).isEqualTo(area);
        assertThat(entity.getCompetition()).isEqualTo(competition);
    }

    @Test
    void updateEntityShouldHandleNullBirthdateGracefully() {
        StatScoreSubParticipantDTO.Details details = new StatScoreSubParticipantDTO.Details();
        details.setBirthdate("invalid-date");

        StatScoreSubParticipantDTO dto = new StatScoreSubParticipantDTO();
        dto.setId(2);
        dto.setName("Player 2");
        dto.setDetails(details);

        SubParticipant entity = new SubParticipant();

        SubParticipant updated = mapper.updateEntity(entity, dto, null, null);

        assertThat(updated).isNotNull();
        assertThat(updated.getBirthDate()).isNull();
    }

    @Test
    void toDTOShouldMapStatsCorrectly() {
        SubParticipantStatRow row = mock(SubParticipantStatRow.class);

        when(row.getId()).thenReturn(1);
        when(row.getPlayerName()).thenReturn("Test Player");
        when(row.getCompetitionId()).thenReturn(100);
        when(row.getCompetitionName()).thenReturn("Premier League");
        when(row.getAvatarUrl()).thenReturn("avatar.jpg");
        when(row.getParticipantId()).thenReturn(5);
        when(row.getTeamName()).thenReturn("Team A");
        when(row.getPosition()).thenReturn(ConstantUtils.TestConstants.FORWARD);
        when(row.getShirtNumber()).thenReturn(9);
        when(row.getAreaId()).thenReturn(10);
        when(row.getAreaName()).thenReturn("Europe");
        when(row.getGender()).thenReturn("male");
        when(row.getWeight()).thenReturn("80");
        when(row.getHeight()).thenReturn("185");
        when(row.getStatName()).thenReturn("Assists");
        when(row.getValueNumeric()).thenReturn(11.0);
        when(row.getValueRaw()).thenReturn("11");
        when(row.getFutureEventAcronyms()).thenReturn("EVT:RER");
        when(row.getFutureEventId()).thenReturn(2);
        when(row.getFutureEventName()).thenReturn("EVT:RER");
        when(row.getFutureEventStartDate()).thenReturn(LocalDateTime.now().plusDays(1));
        when(row.getBirthDate()).thenReturn(LocalDate.of(1990, 1, 1));

        List<SubParticipantDTO> dtos = mapper.toDTO(List.of(row), new HashMap<>());

        assertThat(dtos).isNotEmpty();
        SubParticipantDTO dto = dtos.get(0);
        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getPlayerName()).isEqualTo("Test Player");
        assertThat(dto.getCompetitionId()).isEqualTo(100);
        assertThat(dto.getCompetition()).isEqualTo("Premier League");
        assertThat(dto.getAvatarUrl()).isEqualTo("avatar.jpg");
        assertThat(dto.getParticipantId()).isEqualTo(5);
        assertThat(dto.getTeam()).isEqualTo("Team A");
        assertThat(dto.getPosition()).isEqualTo(ConstantUtils.TestConstants.FORWARD);
        assertThat(dto.getShirtNr()).isEqualTo(9);
        assertThat(dto.getAreaId()).isEqualTo(10);
        assertThat(dto.getAreaName()).isEqualTo("Europe");
        assertThat(dto.getGender()).isEqualTo("male");
        assertThat(dto.getWeight()).isNotBlank();
        assertThat(dto.getHeight()).isNotBlank();
        assertThat(dto.getBirthDate()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(dto.getNextEvent()).isNotNull();
        assertThat(dto.getHistoricalStats()).isNotEmpty();
    }

    @Test
    void toDTOWithEmptyFutureEventShouldMapStatsCorrectly() {
        SubParticipantStatRow row = mock(SubParticipantStatRow.class);

        when(row.getId()).thenReturn(1);
        when(row.getPlayerName()).thenReturn("Test Player");
        when(row.getCompetitionId()).thenReturn(100);
        when(row.getCompetitionName()).thenReturn("Premier League");
        when(row.getAvatarUrl()).thenReturn("avatar.jpg");
        when(row.getParticipantId()).thenReturn(5);
        when(row.getTeamName()).thenReturn("Team A");
        when(row.getPosition()).thenReturn(ConstantUtils.TestConstants.FORWARD);
        when(row.getShirtNumber()).thenReturn(9);
        when(row.getAreaId()).thenReturn(10);
        when(row.getAreaName()).thenReturn("Europe");
        when(row.getGender()).thenReturn("male");
        when(row.getWeight()).thenReturn("80");
        when(row.getHeight()).thenReturn("185");
        when(row.getStatName()).thenReturn("Assists");
        when(row.getValueNumeric()).thenReturn(11.0);
        when(row.getValueRaw()).thenReturn("11");
        when(row.getBirthDate()).thenReturn(LocalDate.of(1990, 1, 1));

        List<SubParticipantDTO> dtos = mapper.toDTO(List.of(row), new HashMap<>());

        assertThat(dtos).isNotEmpty();
        SubParticipantDTO dto = dtos.get(0);
        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getPlayerName()).isEqualTo("Test Player");
        assertThat(dto.getCompetitionId()).isEqualTo(100);
        assertThat(dto.getCompetition()).isEqualTo("Premier League");
        assertThat(dto.getAvatarUrl()).isEqualTo("avatar.jpg");
        assertThat(dto.getParticipantId()).isEqualTo(5);
        assertThat(dto.getTeam()).isEqualTo("Team A");
        assertThat(dto.getPosition()).isEqualTo(ConstantUtils.TestConstants.FORWARD);
        assertThat(dto.getShirtNr()).isEqualTo(9);
        assertThat(dto.getAreaId()).isEqualTo(10);
        assertThat(dto.getAreaName()).isEqualTo("Europe");
        assertThat(dto.getGender()).isEqualTo("male");
        assertThat(dto.getWeight()).isNotBlank();
        assertThat(dto.getHeight()).isNotBlank();
        assertThat(dto.getBirthDate()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(dto.getNextEvent()).isNull();
        assertThat(dto.getHistoricalStats()).isNotEmpty();
    }

    @Test
    void toDTOWithEmptyFutureEventAndHistoricalStatsShouldMapStatsCorrectly() {
        SubParticipantStatRow row = mock(SubParticipantStatRow.class);

        when(row.getId()).thenReturn(1);
        when(row.getPlayerName()).thenReturn("Test Player");
        when(row.getCompetitionId()).thenReturn(100);
        when(row.getCompetitionName()).thenReturn("Premier League");
        when(row.getAvatarUrl()).thenReturn("avatar.jpg");
        when(row.getParticipantId()).thenReturn(5);
        when(row.getTeamName()).thenReturn("Team A");
        when(row.getPosition()).thenReturn(ConstantUtils.TestConstants.FORWARD);
        when(row.getShirtNumber()).thenReturn(9);
        when(row.getAreaId()).thenReturn(10);
        when(row.getAreaName()).thenReturn("Europe");
        when(row.getGender()).thenReturn("male");
        when(row.getWeight()).thenReturn("80");
        when(row.getHeight()).thenReturn("185");
        when(row.getBirthDate()).thenReturn(LocalDate.of(1990, 1, 1));

        List<SubParticipantDTO> dtos = mapper.toDTO(List.of(row), new HashMap<>());

        assertThat(dtos).isNotEmpty();
        SubParticipantDTO dto = dtos.get(0);
        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getPlayerName()).isEqualTo("Test Player");
        assertThat(dto.getCompetitionId()).isEqualTo(100);
        assertThat(dto.getCompetition()).isEqualTo("Premier League");
        assertThat(dto.getAvatarUrl()).isEqualTo("avatar.jpg");
        assertThat(dto.getParticipantId()).isEqualTo(5);
        assertThat(dto.getTeam()).isEqualTo("Team A");
        assertThat(dto.getPosition()).isEqualTo(ConstantUtils.TestConstants.FORWARD);
        assertThat(dto.getShirtNr()).isEqualTo(9);
        assertThat(dto.getAreaId()).isEqualTo(10);
        assertThat(dto.getAreaName()).isEqualTo("Europe");
        assertThat(dto.getGender()).isEqualTo("male");
        assertThat(dto.getWeight()).isNotBlank();
        assertThat(dto.getHeight()).isNotBlank();
        assertThat(dto.getBirthDate()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(dto.getNextEvent()).isNull();
        assertThat(dto.getHistoricalStats()).isEmpty();
    }

    @Test
    void extractOpponentFromAcronymsShouldReturnOpponent() {
        String acronyms = "ABC:DEF:GHI";
        String self = "ABC";

        String opponent = SubParticipantMapper.extractOpponentFromAcronyms(acronyms, self);

        assertThat(opponent).isEqualTo("DEF");
    }

    @Test
    void extractOpponentFromAcronymsShouldReturnNullIfNotFound() {
        String acronyms = "ABC";
        String self = "ABC";

        String opponent = SubParticipantMapper.extractOpponentFromAcronyms(acronyms, self);

        assertThat(opponent).isNull();
    }

    @Test
    void updateEntityShouldReturnSameEntityWhenDtoIsNull() {
        SubParticipant entity = new SubParticipant();
        SubParticipant result = mapper.updateEntity(entity, null, null, null);
        assertThat(result).isEqualTo(entity);
    }

    @Test
    void updateEntityShouldReturnNullWhenEntityIsNull() {
        StatScoreSubParticipantDTO dto = new StatScoreSubParticipantDTO();
        SubParticipant result = mapper.updateEntity(null, dto, null, null);
        assertThat(result).isNull();
    }

    @Test
    void toEntityShouldReturnNullWhenDtoIsNull() {
        SubParticipant result = mapper.toEntity(null, null, null);
        assertThat(result).isNull();
    }

    @Test
    void toDTOShouldIgnoreRowsWithNullId() {
        SubParticipantStatRow row = mock(SubParticipantStatRow.class);
        when(row.getId()).thenReturn(null);

        List<SubParticipantDTO> result = mapper.toDTO(List.of(row), new HashMap<>());
        assertThat(result).isEmpty();
    }

    @Test
    void extractOpponentFromAcronymsShouldReturnNullWhenInputIsNull() {
        assertThat(SubParticipantMapper.extractOpponentFromAcronyms(null, "ABC")).isNull();
        assertThat(SubParticipantMapper.extractOpponentFromAcronyms("ABC:DEF", null)).isNull();
    }

}

