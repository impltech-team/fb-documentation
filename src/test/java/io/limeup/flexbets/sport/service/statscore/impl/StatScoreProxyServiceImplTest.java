package io.limeup.flexbets.sport.service.statscore.impl;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.SingleRootItemPaginatedResponse;
import io.limeup.flexbets.sport.dto.statscore.ListWrapper;
import io.limeup.flexbets.sport.dto.statscore.StatScoreAreaDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreBracketDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreCompetitionDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreResponse;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportLiteDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreStandingDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreVenueDTO;
import io.limeup.flexbets.sport.dto.statscore.params.AreaQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.CompetitionQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.EventQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.GroupQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.ParticipantQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.SeasonQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.SportQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.StageQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.StandingByIdQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.StandingQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.VenueQueryParams;
import io.limeup.flexbets.sport.service.statscore.StatScoreClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class StatScoreProxyServiceImplTest {

    @Mock
    private StatScoreClient statScoreClient;

    @InjectMocks
    private StatScoreProxyServiceImpl statScoreProxyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listEventSubParticipantsShouldReturnPaginatedResponse() {
        StatScoreResponse<ListWrapper<StatScoreSubParticipantDTO>> response = buildListResponse(new StatScoreSubParticipantDTO());
        when(statScoreClient.getEventSubParticipants(anyInt(), anyBoolean()))
                .thenReturn(Mono.just(response));

        PaginatedResponse<StatScoreSubParticipantDTO> result = statScoreProxyService.listEventSubParticipants(123, true);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(1);

        verify(statScoreClient).getEventSubParticipants(eq(123), eq(true));
    }

    @Test
    void getParticipantByIdShouldReturnParticipant() {
        StatScoreResponse<StatScoreParticipantDTO> response = buildSingleResponse(new StatScoreParticipantDTO());
        when(statScoreClient.getParticipantById(anyInt(), anyBoolean()))
                .thenReturn(Mono.just(response));

        StatScoreParticipantDTO result = statScoreProxyService.getParticipantById(321, true);

        assertThat(result).isNotNull();

        verify(statScoreClient).getParticipantById(eq(321), eq(true));
    }

    @Test
    void listGroupsShouldReturnSingleRootPaginatedResponse() {
        StatScoreResponse<StatScoreCompetitionDTO> response = buildSingleResponse(new StatScoreCompetitionDTO());
        when(statScoreClient.getGroups(any(GroupQueryParams.class), anyBoolean()))
                .thenReturn(Mono.just(response));

        SingleRootItemPaginatedResponse<StatScoreCompetitionDTO> result = statScoreProxyService.listGroups(new GroupQueryParams(), true);

        assertThat(result).isNotNull();
        assertThat(result.getRootName()).isEqualTo("competition");

        verify(statScoreClient).getGroups(any(GroupQueryParams.class), eq(true));
    }

    @Test
    void listCompetitionsShouldReturnPaginatedResponse() {
        StatScoreResponse<ListWrapper<StatScoreCompetitionDTO>> response = buildListResponse(new StatScoreCompetitionDTO());
        when(statScoreClient.getCompetitions(any(CompetitionQueryParams.class), anyBoolean()))
                .thenReturn(Mono.just(response));

        PaginatedResponse<StatScoreCompetitionDTO> result = statScoreProxyService.listCompetitions(new CompetitionQueryParams(), true);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(1);

        verify(statScoreClient).getCompetitions(any(CompetitionQueryParams.class), eq(true));
    }

    @Test
    void getVenueByIdShouldReturnVenue() {
        StatScoreResponse<StatScoreVenueDTO> response = buildSingleResponse(new StatScoreVenueDTO());
        when(statScoreClient.getVenueById(anyInt(), anyBoolean()))
                .thenReturn(Mono.just(response));

        StatScoreVenueDTO result = statScoreProxyService.getVenueById(555, true);

        assertThat(result).isNotNull();

        verify(statScoreClient).getVenueById(eq(555), eq(true));
    }

    @Test
    void listSquadSubParticipantsShouldReturnPaginatedResponse() {
        StatScoreResponse<ListWrapper<StatScoreSubParticipantDTO>> response = buildListResponse(new StatScoreSubParticipantDTO());
        when(statScoreClient.getSquadSubParticipants(anyInt(), anyInt(), anyBoolean()))
                .thenReturn(Mono.just(response));

        PaginatedResponse<StatScoreSubParticipantDTO> result = statScoreProxyService.listSquadSubParticipants(123, 1, true);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(1);

        verify(statScoreClient).getSquadSubParticipants(eq(123), eq(1), eq(true));
    }

    @Test
    void listParticipantsShouldReturnPaginatedResponse() {
        StatScoreResponse<ListWrapper<StatScoreParticipantDTO>> response = buildListResponse(new StatScoreParticipantDTO());
        when(statScoreClient.getParticipants(any(ParticipantQueryParams.class), anyBoolean()))
                .thenReturn(Mono.just(response));

        PaginatedResponse<StatScoreParticipantDTO> result = statScoreProxyService.listParticipants(new ParticipantQueryParams(), true);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(1);

        verify(statScoreClient).getParticipants(any(ParticipantQueryParams.class), eq(true));
    }

    @Test
    void listEventsShouldReturnPaginatedResponse() {
        StatScoreResponse<ListWrapper<StatScoreCompetitionDTO>> response = buildListResponse(new StatScoreCompetitionDTO());
        when(statScoreClient.getEvents(any(EventQueryParams.class), anyBoolean()))
                .thenReturn(Mono.just(response));

        PaginatedResponse<StatScoreCompetitionDTO> result = statScoreProxyService.listEvents(new EventQueryParams(), true);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(1);

        verify(statScoreClient).getEvents(any(EventQueryParams.class), eq(true));
    }

    @Test
    void getEventByIdShouldReturnEvent() {
        StatScoreResponse<StatScoreCompetitionDTO> response = buildSingleResponse(new StatScoreCompetitionDTO());
        when(statScoreClient.getEventById(anyInt(), anyBoolean()))
                .thenReturn(Mono.just(response));

        StatScoreCompetitionDTO result = statScoreProxyService.getEventById(999, true);

        assertThat(result).isNotNull();

        verify(statScoreClient).getEventById(eq(999), eq(true));
    }

    @Test
    void listAreasShouldReturnPaginatedResponse() {
        StatScoreResponse<ListWrapper<StatScoreAreaDTO>> response = buildListResponse(new StatScoreAreaDTO());
        when(statScoreClient.getAreas(any(AreaQueryParams.class), anyBoolean()))
                .thenReturn(Mono.just(response));

        PaginatedResponse<StatScoreAreaDTO> result = statScoreProxyService.listAreas(new AreaQueryParams(), true);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(1);

        verify(statScoreClient).getAreas(any(AreaQueryParams.class), eq(true));
    }

    @Test
    void listSportsShouldReturnPaginatedResponse() {
        StatScoreResponse<ListWrapper<StatScoreSportLiteDTO>> response = buildListResponse(new StatScoreSportLiteDTO());
        when(statScoreClient.getSports(any(SportQueryParams.class), anyBoolean()))
                .thenReturn(Mono.just(response));

        PaginatedResponse<StatScoreSportLiteDTO> result = statScoreProxyService.listSports(new SportQueryParams(), true);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(1);

        verify(statScoreClient).getSports(any(SportQueryParams.class), eq(true));
    }

    @Test
    void getSportByIdShouldReturnSport() {
        StatScoreResponse<StatScoreSportDTO> response = buildSingleResponse(new StatScoreSportDTO());
        when(statScoreClient.getSportById(anyInt(), anyBoolean()))
                .thenReturn(Mono.just(response));

        StatScoreSportDTO result = statScoreProxyService.getSportById(42, true);

        assertThat(result).isNotNull();

        verify(statScoreClient).getSportById(eq(42), eq(true));
    }

    @Test
    void listVenuesShouldReturnPaginatedResponse() {
        StatScoreResponse<ListWrapper<StatScoreVenueDTO>> response = buildListResponse(new StatScoreVenueDTO());
        when(statScoreClient.getVenues(any(VenueQueryParams.class), anyBoolean()))
                .thenReturn(Mono.just(response));

        PaginatedResponse<StatScoreVenueDTO> result = statScoreProxyService.listVenues(new VenueQueryParams(), true);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(1);

        verify(statScoreClient).getVenues(any(VenueQueryParams.class), eq(true));
    }

    @Test
    void listBracketsByStageIdShouldReturnPaginatedResponse() {
        StatScoreResponse<ListWrapper<StatScoreBracketDTO>> response = buildListResponse(new StatScoreBracketDTO());
        when(statScoreClient.getBrackets(anyInt(), anyBoolean()))
                .thenReturn(Mono.just(response));

        PaginatedResponse<StatScoreBracketDTO> result = statScoreProxyService.listBracketsByStageId(7, true);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(1);

        verify(statScoreClient).getBrackets(eq(7), eq(true));
    }

    @Test
    void listSeasonsShouldReturnPaginatedResponse() {
        StatScoreResponse<ListWrapper<StatScoreCompetitionDTO>> response = buildListResponse(new StatScoreCompetitionDTO());
        when(statScoreClient.getSeasons(any(SeasonQueryParams.class), anyBoolean()))
                .thenReturn(Mono.just(response));

        PaginatedResponse<StatScoreCompetitionDTO> result = statScoreProxyService.listSeasons(new SeasonQueryParams(), true);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(1);

        verify(statScoreClient).getSeasons(any(SeasonQueryParams.class), eq(true));
    }

    @Test
    void getSeasonByIdShouldReturnSeason() {
        StatScoreResponse<StatScoreCompetitionDTO> response = buildSingleResponse(new StatScoreCompetitionDTO());
        when(statScoreClient.getSeasonById(anyInt(), anyBoolean()))
                .thenReturn(Mono.just(response));

        StatScoreCompetitionDTO result = statScoreProxyService.getSeasonById(55, true);

        assertThat(result).isNotNull();

        verify(statScoreClient).getSeasonById(eq(55), eq(true));
    }

    @Test
    void listStagesShouldReturnSingleRootPaginatedResponse() {
        StatScoreResponse<StatScoreCompetitionDTO> response = buildSingleResponse(new StatScoreCompetitionDTO());
        when(statScoreClient.getStages(any(StageQueryParams.class), anyBoolean()))
                .thenReturn(Mono.just(response));

        SingleRootItemPaginatedResponse<StatScoreCompetitionDTO> result = statScoreProxyService.listStages(new StageQueryParams(), true);

        assertThat(result).isNotNull();
        assertThat(result.getRootName()).isEqualTo("competition");

        verify(statScoreClient).getStages(any(StageQueryParams.class), eq(true));
    }

    @Test
    void getStageByIdShouldReturnStage() {
        StatScoreResponse<StatScoreCompetitionDTO> response = buildSingleResponse(new StatScoreCompetitionDTO());
        when(statScoreClient.getStageById(anyInt(), anyBoolean()))
                .thenReturn(Mono.just(response));

        StatScoreCompetitionDTO result = statScoreProxyService.getStageById(77, true);

        assertThat(result).isNotNull();

        verify(statScoreClient).getStageById(eq(77), eq(true));
    }

    @Test
    void listStandingsShouldReturnPaginatedResponse() {
        StatScoreResponse<ListWrapper<StatScoreStandingDTO>> response = buildListResponse(new StatScoreStandingDTO());
        when(statScoreClient.getStandings(any(StandingQueryParams.class), anyBoolean()))
                .thenReturn(Mono.just(response));

        PaginatedResponse<StatScoreStandingDTO> result = statScoreProxyService.listStandings(new StandingQueryParams(), true);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(1);

        verify(statScoreClient).getStandings(any(StandingQueryParams.class), eq(true));
    }

    @Test
    void getStandingShouldReturnStanding() {
        StatScoreResponse<StatScoreStandingDTO> response = buildSingleResponse(new StatScoreStandingDTO());
        when(statScoreClient.getStandingById(anyInt(), any(StandingByIdQueryParams.class), anyBoolean()))
                .thenReturn(Mono.just(response));

        StatScoreStandingDTO result = statScoreProxyService.getStanding(10, new StandingByIdQueryParams(), true);

        assertThat(result).isNotNull();

        verify(statScoreClient).getStandingById(eq(10), any(StandingByIdQueryParams.class), eq(true));
    }

    @Test
    void getCompetitionShouldReturnCompetition() {
        StatScoreResponse<StatScoreCompetitionDTO> response = buildSingleResponse(new StatScoreCompetitionDTO());
        when(statScoreClient.getCompetitionById(anyInt(), anyBoolean()))
                .thenReturn(Mono.just(response));

        StatScoreCompetitionDTO result = statScoreProxyService.getCompetition(22, true);

        assertThat(result).isNotNull();

        verify(statScoreClient).getCompetitionById(eq(22), eq(true));
    }

    @Test
    void listEventParticipantsShouldReturnPaginatedResponse() {
        StatScoreResponse<ListWrapper<StatScoreParticipantDTO>> response = buildListResponse(new StatScoreParticipantDTO());
        when(statScoreClient.getEventParticipants(anyInt(), anyBoolean()))
                .thenReturn(Mono.just(response));

        PaginatedResponse<StatScoreParticipantDTO> result = statScoreProxyService.listEventParticipants(222, true);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).hasSize(1);

        verify(statScoreClient).getEventParticipants(eq(222), eq(true));
    }


    private <T> StatScoreResponse<ListWrapper<T>> buildListResponse(T item) {
        ListWrapper<T> wrapper = new ListWrapper<>();
        wrapper.setItems(Collections.singletonList(item));

        StatScoreResponse.ApiWrapper<ListWrapper<T>> api = new StatScoreResponse.ApiWrapper<>();
        api.setData(wrapper);

        StatScoreResponse.MethodInfo methodInfo = new StatScoreResponse.MethodInfo();
        methodInfo.setTotalItems(1);
        api.setMethod(methodInfo);

        StatScoreResponse<ListWrapper<T>> response = new StatScoreResponse<>();
        response.setApi(api);

        return response;
    }

    private <T> StatScoreResponse<T> buildSingleResponse(T item) {
        StatScoreResponse.ApiWrapper<T> api = new StatScoreResponse.ApiWrapper<>();
        api.setData(item);

        StatScoreResponse<T> response = new StatScoreResponse<>();
        response.setApi(api);

        return response;
    }
}

