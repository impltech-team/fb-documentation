package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.statscore.StatScoreVenueDTO;
import io.limeup.flexbets.sport.model.Venue;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class VenueMapperTest {

    private final VenueMapper mapper = new VenueMapper();

    @Test
    void toEntityShouldMapFieldsCorrectly() {
        StatScoreVenueDTO dto = new StatScoreVenueDTO();
        dto.setId(1);
        dto.setName("Stadium One");
        dto.setCity("Kyiv");
        dto.setCountry("Ukraine");
        dto.setLat(50.45);
        dto.setLng(30.52);
        dto.setOpened("2000-05-15");
        dto.setShortName("Stadium 1");
        dto.setStatus("open");
        dto.setUrl("http://stadium1.example.com");

        Venue venue = mapper.toEntity(dto);

        assertThat(venue).isNotNull();
        assertThat(venue.getExternalId()).isEqualTo(1);
        assertThat(venue.getName()).isEqualTo("Stadium One");
        assertThat(venue.getCity()).isEqualTo("Kyiv");
        assertThat(venue.getCountry()).isEqualTo("Ukraine");
        assertThat(venue.getLat()).isEqualTo(50.45);
        assertThat(venue.getLng()).isEqualTo(30.52);
        assertThat(venue.getOpened()).isEqualTo(LocalDate.of(2000, 5, 15));
        assertThat(venue.getShortName()).isEqualTo("Stadium 1");
        assertThat(venue.getStatus()).isEqualTo("open");
        assertThat(venue.getUrl()).isEqualTo("http://stadium1.example.com");
    }

    @Test
    void updateEntityShouldHandleInvalidOpenedDateGracefully() {
        StatScoreVenueDTO dto = new StatScoreVenueDTO();
        dto.setOpened("invalid-date");

        Venue venue = new Venue();

        Venue updated = mapper.updateEntity(venue, dto);

        assertThat(updated).isNotNull();
        assertThat(updated.getOpened()).isNull();
    }

    @Test
    void updateEntityShouldReturnNullIfDtoIsNull() {
        Venue venue = new Venue();

        Venue updated = mapper.updateEntity(venue, null);

        assertThat(updated).isSameAs(venue);
    }

    @Test
    void toEntityShouldReturnNullIfDtoIsNull() {
        Venue venue = mapper.toEntity(null);

        assertThat(venue).isNull();
    }
}

