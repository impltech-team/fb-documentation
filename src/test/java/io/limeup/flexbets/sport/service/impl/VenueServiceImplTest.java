package io.limeup.flexbets.sport.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static io.limeup.flexbets.sport.TestDataFactory.*;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.model.Venue;
import io.limeup.flexbets.sport.repository.VenueRepository;
import io.limeup.flexbets.sport.service.statscore.StatScoreProxyService;
import io.limeup.flexbets.sport.mapper.VenueMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import io.limeup.flexbets.sport.dto.statscore.StatScoreVenueDTO;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class VenueServiceImplTest {

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private StatScoreProxyService statScoreProxyService;

    @Mock
    private VenueMapper venueMapper;

    @InjectMocks
    private VenueServiceImpl venueService;

    @Test
    void createShouldSaveVenue() {
        StatScoreVenueDTO venueDTO = new StatScoreVenueDTO();
        venueDTO.setId(1);
        venueDTO.setName("Olympic Stadium");

        Venue venue = createTestVenue( 1, "Olympic Stadium");

        when(venueMapper.toEntity(any(StatScoreVenueDTO.class))).thenReturn(venue);
        when(venueRepository.save(any(Venue.class))).thenReturn(venue);

        Venue savedVenue = venueService.create(venueDTO);

        assertThat(savedVenue).isNotNull();
        assertThat(savedVenue.getExternalId()).isEqualTo(1);
        verify(venueRepository).save(any(Venue.class));
    }

    @Test
    void fetchVenueDataShouldFetchAndSaveVenues() {
        StatScoreVenueDTO venueDTO = new StatScoreVenueDTO();
        venueDTO.setId(10);
        venueDTO.setName("Stadium A");

        when(statScoreProxyService.listVenues(any(), eq(true)))
                .thenReturn(PaginatedResponse.<StatScoreVenueDTO>builder()
                        .items(List.of(venueDTO))
                        .build());

        when(venueRepository.findByExternalIdIn(anyList()))
                .thenReturn(Collections.emptyList());

        when(venueMapper.toEntity(any(StatScoreVenueDTO.class)))
                .thenReturn(new Venue());

        when(venueRepository.saveAllAndFlush(anyList()))
                .thenReturn(List.of(new Venue()));

        venueService.fetchVenueData();

        verify(statScoreProxyService, atLeastOnce()).listVenues(any(), eq(true));
        verify(venueRepository).findByExternalIdIn(anyList());
        verify(venueRepository).saveAllAndFlush(anyList());
    }
}

