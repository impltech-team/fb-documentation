package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.EventDTO;
import io.limeup.flexbets.sport.dto.FullEventDTO;
import io.limeup.flexbets.sport.dto.ParticipantSummaryDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreGroupDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSeasonDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreStageDTO;
import io.limeup.flexbets.sport.model.Competition;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.EventStatus;
import io.limeup.flexbets.sport.model.Venue;
import io.limeup.flexbets.sport.repository.projection.EventRow;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EventMapperTest {

    private final EventMapper eventMapper = new EventMapper();

    @Test
    void toEntityShouldMapDtoToEntity() {
        StatScoreEventDTO dto = new StatScoreEventDTO();
        dto.setId(1);
        dto.setName("Match 1");
        dto.setStartDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        dto.setStatusType("scheduled");
        dto.setLsId(123456L);

        Competition competition = new Competition();
        Venue venue = new Venue();
        StatScoreSeasonDTO season = new StatScoreSeasonDTO();
        season.setId(5);
        season.setName("Season 2025");

        Event entity = eventMapper.toEntity(dto, competition, venue, season);

        assertThat(entity).isNotNull();
        assertThat(entity.getExternalId()).isEqualTo(1);
        assertThat(entity.getName()).isEqualTo("Match 1");
        assertThat(entity.getCompetition()).isSameAs(competition);
        assertThat(entity.getVenue()).isSameAs(venue);
        assertThat(entity.getSeasonExternalId()).isEqualTo(5);
        assertThat(entity.getSeasonName()).isEqualTo("Season 2025");
        assertThat(entity.getStatus()).isEqualTo(EventStatus.SCHEDULED);
        assertThat(entity.getLsId()).isEqualTo(123456L);
    }

    @Test
    void updateEntityWhenNullShouldReturnOriginal() {
        Event event = new Event();
        assertThat(eventMapper.updateEntity(event, null, null, null, null)).isSameAs(event);
        assertThat(eventMapper.updateEntity(null, new StatScoreEventDTO(), null, null, null)).isNull();
    }

    @Test
    void toDTOShouldMapGroupedRowsCorrectly() {
        EventRow row1 = mock(EventRow.class);
        when(row1.getId()).thenReturn(1L);
        when(row1.getExternalId()).thenReturn(100);
        when(row1.getEventName()).thenReturn("Event A");
        when(row1.getStartDate()).thenReturn(LocalDateTime.now());
        when(row1.getStatus()).thenReturn("scheduled");
        when(row1.getCompetitionId()).thenReturn(10);
        when(row1.getCompetitionName()).thenReturn("League A");
        when(row1.getVenueId()).thenReturn(5);
        when(row1.getVenueName()).thenReturn("Stadium A");
        when(row1.getVenueLocation()).thenReturn("City A");
        when(row1.getParticipantId()).thenReturn(1);
        when(row1.getTeamName()).thenReturn("Team A");
        when(row1.getAcronym()).thenReturn("TA");

        List<EventDTO> dtos = EventMapper.toDTO(List.of(row1));

        assertThat(dtos).hasSize(1);
        EventDTO dto = dtos.get(0);
        assertThat(dto.getId()).isEqualTo(100);
        assertThat(dto.getVenue()).isNotNull();
        assertThat(dto.getParticipants()).extracting(ParticipantSummaryDTO::getParticipantId)
                .containsExactly(1);
    }

    @Test
    void toDTOWhenNullListShouldReturnEmptyList() {
        List<EventDTO> result = EventMapper.toDTO(null);
        assertThat(result).isEmpty();
    }

    @Test
    void mapToFullEventDTOShouldMapCorrectly() {
        StatScoreCompetitionDTO competitionDTO = new StatScoreCompetitionDTO();
        competitionDTO.setId(10);
        competitionDTO.setName("Champions League");

        StatScoreEventDTO eventDTO = new StatScoreEventDTO();
        eventDTO.setId(100);
        eventDTO.setName("Final");
        eventDTO.setStartDate("2025-06-01 20:00");
        eventDTO.setStatusName("Scheduled");
        eventDTO.setVenueId(5);

        StatScoreGroupDTO groupDTO = new StatScoreGroupDTO();
        groupDTO.setEvent(eventDTO);

        StatScoreStageDTO stageDTO = new StatScoreStageDTO();
        stageDTO.setGroup(groupDTO);

        StatScoreSeasonDTO seasonDTO = new StatScoreSeasonDTO();
        seasonDTO.setStage(stageDTO);

        competitionDTO.setSeason(seasonDTO);

        Venue venue = new Venue();
        venue.setName("National Stadium");
        venue.setCountry("Ukraine");
        venue.setCity("Kyiv");

        FullEventDTO fullEventDTO = EventMapper.mapToFullEventDTO(competitionDTO, venue);

        assertThat(fullEventDTO).isNotNull();
        assertThat(fullEventDTO.getId()).isEqualTo(100);
        assertThat(fullEventDTO.getEventName()).isEqualTo("Final");
        assertThat(fullEventDTO.getCompetitionId()).isEqualTo(10);
        assertThat(fullEventDTO.getCompetition()).isEqualTo("Champions League");
        assertThat(fullEventDTO.getVenue()).isNotNull();
        assertThat(fullEventDTO.getVenue().getVenueName()).isEqualTo("National Stadium");
    }
}

