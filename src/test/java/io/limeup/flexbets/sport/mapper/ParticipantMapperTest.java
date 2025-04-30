package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.EventLiteDTO;
import io.limeup.flexbets.sport.dto.HistoricalStatDTO;
import io.limeup.flexbets.sport.dto.ParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventParticipantDTO;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Participant;
import io.limeup.flexbets.sport.repository.projection.ParticipantStatRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ParticipantMapperTest {

    private final ParticipantMapper participantMapper = new ParticipantMapper();

    @Test
    void toEntityShouldCreateParticipantFromDTO() {
        StatScoreEventParticipantDTO dto = new StatScoreEventParticipantDTO();
        dto.setId(1);
        dto.setName("Team A");
        dto.setAcronym("TA");
        dto.setType("team");

        Competition competition = new Competition();
        competition.setExternalId(10);

        Participant participant = participantMapper.toEntity(dto, competition);

        assertThat(participant).isNotNull();
        assertThat(participant.getExternalId()).isEqualTo(1);
        assertThat(participant.getTeamName()).isEqualTo("Team A");
        assertThat(participant.getAcronym()).isEqualTo("TA");
        assertThat(participant.getType()).isEqualTo("team");
        assertThat(participant.getCompetition()).isEqualTo(competition);
        assertThat(participant.getHistoricalStats()).isEmpty();
    }

    @Test
    void updateEntityShouldUpdateExistingParticipant() {
        Participant participant = new Participant();
        StatScoreEventParticipantDTO dto = new StatScoreEventParticipantDTO();
        dto.setId(2);
        dto.setName("Team B");
        dto.setAcronym("TB");
        dto.setType("club");

        Competition competition = new Competition();
        competition.setExternalId(20);

        Participant updated = participantMapper.updateEntity(participant, dto, competition);

        assertThat(updated.getExternalId()).isEqualTo(2);
        assertThat(updated.getTeamName()).isEqualTo("Team B");
        assertThat(updated.getAcronym()).isEqualTo("TB");
        assertThat(updated.getType()).isEqualTo("club");
        assertThat(updated.getCompetition()).isEqualTo(competition);
    }

    @Test
    void toDTOShouldMapStatRowsToParticipantDTOs() {
        ParticipantStatRow row = mock(ParticipantStatRow.class);
        when(row.getId()).thenReturn(1);
        when(row.getTeamName()).thenReturn("Team C");
        when(row.getAcronym()).thenReturn("TC");
        when(row.getCompetitionName()).thenReturn("Championship");
        when(row.getCompetitionId()).thenReturn(100);
        when(row.getStatName()).thenReturn("Goals");
        when(row.getValueNumeric()).thenReturn(5.0);
        when(row.getValueRaw()).thenReturn("5");
        when(row.getEventId()).thenReturn(101);
        when(row.getEventName()).thenReturn("Final Match");
        when(row.getEventStartDate()).thenReturn(LocalDateTime.now());
        when(row.getEventParticipantAcronyms()).thenReturn("TC,OC");

        when(row.getFutureEventId()).thenReturn(202);
        when(row.getFutureEventName()).thenReturn("Next Match");
        when(row.getFutureEventStartDate()).thenReturn(LocalDateTime.now().plusDays(1));
        when(row.getFutureEventAcronyms()).thenReturn("OC,TC");

        List<ParticipantDTO> result = ParticipantMapper.toDTO(List.of(row));

        assertThat(result).isNotEmpty();
        ParticipantDTO dto = result.get(0);
        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getTeamName()).isEqualTo("Team C");
        assertThat(dto.getCompetition()).isEqualTo("Championship");
        assertThat(dto.getCompetitionId()).isEqualTo(100);

        assertThat(dto.getHistoricalStats()).isNotEmpty();
        HistoricalStatDTO historicalStat = dto.getHistoricalStats().get(0);
        assertThat(historicalStat.getStatName()).isEqualTo("Goals");
        assertThat(historicalStat.getCount()).isEqualTo(1);
        assertThat(historicalStat.getAverage()).isEqualTo(BigDecimal.valueOf(5.0).setScale(2));

        assertThat(dto.getNextEvent()).isNotNull();
        EventLiteDTO nextEvent = dto.getNextEvent();
        assertThat(nextEvent.getEventId()).isEqualTo(202);
        assertThat(nextEvent.getEventName()).isEqualTo("Next Match");
    }

    @Test
    void extractOpponentFromAcronymsShouldReturnOpponent() {
        String acronyms = "TC,OC";
        String self = "TC";

        String opponent = ParticipantMapper.extractOpponentFromAcronyms(acronyms, self);

        assertThat(opponent).isEqualTo("OC");
    }

    @Test
    void extractOpponentFromAcronymsShouldReturnNullIfNoOpponent() {
        String acronyms = "TC";
        String self = "TC";

        String opponent = ParticipantMapper.extractOpponentFromAcronyms(acronyms, self);

        assertThat(opponent).isNull();
    }
}

