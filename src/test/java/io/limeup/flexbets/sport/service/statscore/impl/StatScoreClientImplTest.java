package io.limeup.flexbets.sport.service.statscore.impl;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.limeup.flexbets.sport.config.wiremock.BaseWireMockTest;
import io.limeup.flexbets.sport.dto.statscore.ListWrapper;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreEventParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSubParticipantDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.AreaQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.GroupQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.ParticipantQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.SportQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StandingByIdQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StandingQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.SeasonQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StageQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.VenueQueryParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

class StatScoreClientImplTest extends BaseWireMockTest {

    private static final String AREAS_LIST_ENDPOINT = "/events/123/sub-participants";
    private static final String PARTICIPANTS_LIST_ENDPOINT = "/participants";
    private static final String PARTICIPANT_BY_ID_ENDPOINT = "/participants/123";
    private static final String EVENTS_LIST_ENDPOINT = "/events";
    private static final String EVENT_BY_ID_ENDPOINT = "/events/123";
    private static final String SQUAD_SUB_PARTICIPANTS_ENDPOINT = "/participants/1040/squad";
    private static final String AREAS_ENDPOINT = "/areas";
    private static final String SPORTS_LIST_ENDPOINT = "/sports";
    private static final String SPORT_BY_ID_ENDPOINT = "/sports/1";
    private static final String VENUE_BY_ID_ENDPOINT = "/venues/810";
    private static final String VENUES_LIST_ENDPOINT = "/venues";
    private static final String BRACKETS_ENDPOINT = "/brackets/135822";
    private static final String GROUPS_ENDPOINT = "/groups";
    private static final String SEASONS_LIST_ENDPOINT = "/seasons";
    private static final String SEASON_BY_ID_ENDPOINT = "/seasons/62587";
    private static final String STAGES_LIST_ENDPOINT = "/stages";
    private static final String STAGE_BY_ID_ENDPOINT = "/stages/135822";
    private static final String STANDINGS_LIST_ENDPOINT = "/standings";
    private static final String STANDING_BY_ID_ENDPOINT = "/standings/182996";

    private StatScoreClientImpl statScoreClient;

    @BeforeEach
    public void initClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:9099")
                .build();

        statScoreClient = new StatScoreClientImpl(webClient, new com.fasterxml.jackson.databind.ObjectMapper());
    }

    @BeforeAll
    void setupStub() {
        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(AREAS_LIST_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_event_sub_participants_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(PARTICIPANTS_LIST_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_participants_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(PARTICIPANT_BY_ID_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_participant_by_id_response.json")));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(EVENTS_LIST_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_events_response.json")));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(EVENT_BY_ID_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_event_by_id_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(SQUAD_SUB_PARTICIPANTS_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_squad_sub_participants_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(AREAS_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_areas_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(SPORTS_LIST_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_sports_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(SPORT_BY_ID_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_sport_by_id_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(VENUE_BY_ID_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_venue_by_id_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(VENUES_LIST_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_venues_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(BRACKETS_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_brackets_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(GROUPS_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_groups_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(SEASONS_LIST_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_seasons_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(SEASON_BY_ID_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_season_by_id_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(STAGES_LIST_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_stages_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(STAGE_BY_ID_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_stage_by_id_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(STANDINGS_LIST_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_standings_response.json")
                        .withStatus(200)));

        getWireMockServer().stubFor(WireMock.get(WireMock.urlPathEqualTo(STANDING_BY_ID_ENDPOINT))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("statscore_standing_by_id_response.json")
                        .withStatus(200)));

    }

    @Test
    @DisplayName("Verify getEventSubParticipants returns Curry")
    void testGetEventSubParticipants() {
        StepVerifier.create(statScoreClient.getEventSubParticipants(123, false))
                .assertNext(resp -> {
                    ListWrapper<StatScoreSubParticipantDTO> wrapper = resp.getApi().getData();
                    List<StatScoreSubParticipantDTO> items = wrapper.getItems();
                    Assertions.assertEquals(1, items.size());
                    Assertions.assertEquals("Stephen Curry", items.get(0).getName());
                    Assertions.assertEquals(464938, items.get(0).getId());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getParticipants returns expected teams")
    void testGetParticipants() {
        ParticipantQueryParams params = new ParticipantQueryParams();
        params.setSportId(1);

        StepVerifier.create(statScoreClient.getParticipants(params, false))
                .assertNext(resp -> {
                    List<StatScoreParticipantDTO> participants = resp.getApi().getData().getItems();
                    Assertions.assertEquals(2, participants.size());

                    Assertions.assertEquals("Memphis Grizzlies", participants.get(0).getName());
                    Assertions.assertEquals("Philadelphia 76ers", participants.get(1).getName());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getParticipants with all query params")
    void testGetParticipantsWithAllParams() {
        ParticipantQueryParams params = new ParticipantQueryParams();
        params.setSportId(1);
        params.setLimit(10);
        params.setPage(1);
        params.setSeasonId(5);
        params.setAreaId(191);
        params.setType(ParticipantQueryParams.Type.TEAM);
        params.setSubtype(ParticipantQueryParams.Subtype.ATHLETE);
        params.setVirtual("no");
        params.setMultiIds("1,2,3");

        StepVerifier.create(statScoreClient.getParticipants(params, false))
                .assertNext(resp -> {
                    var items = resp.getApi().getData().getItems();
                    Assertions.assertFalse(items.isEmpty());

                    for (StatScoreParticipantDTO item : items) {
                        Assertions.assertEquals("team", item.getType());
                        Assertions.assertEquals("no", item.getVirtual());
                        Assertions.assertEquals(191, item.getAreaId());

                        Assertions.assertTrue(item.getId() == 1 || item.getId() == 2);

                        StatScoreParticipantDTO.Details details = item.getDetails();
                        Assertions.assertNotNull(details);
                        Assertions.assertEquals("athlete", details.getSubtype());
                    }
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getParticipantById returns expected team")
    void testGetParticipantById() {

        StepVerifier.create(statScoreClient.getParticipantById(123, false))
                .assertNext(participantResponse -> {
                    var participant = participantResponse.getApi().getData();
                    Assertions.assertEquals(123, participant.getId());
                    Assertions.assertEquals("Golden State Warriors", participant.getName());
                    Assertions.assertEquals("team", participant.getType());
                    Assertions.assertEquals("no", participant.getVirtual());
                    Assertions.assertNotNull(participant.getDetails());
                    Assertions.assertEquals("athlete", participant.getDetails().getSubtype());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getEvents returns correct events with all params")
    void testGetEventsWithAllParams() {
        EventQueryParams query = new EventQueryParams();
        query.setDateFrom(LocalDateTime.parse("2025-03-27 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        query.setDateTo(LocalDateTime.parse("2025-03-29 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        query.setSportId(1);
        query.setAreaId(191);
        query.setCompetitionId(101);
        query.setSeasonId(62587);
        query.setStageId(135822);
        query.setGroupId(1);
        query.setParticipantId(1040);
        query.setMultiIds("1040,1412");
        query.setVenueType("indoor");
        query.setVenueId(810);
        query.setSortType("start_date");
        query.setSortOrder("desc");
        query.setRelationStatus("finished");
        query.setStatusId(11);
        query.setStatusType("finished");
        query.setCoverageType("from_tv");
        query.setScoutsfeed("yes");
        query.setEventsDetails("yes");
        query.setCompetitionsDetails("yes");
        query.setItemStatus("active");
        query.setVerifiedResult("yes");
        query.setProduct("basketball_stats");
        query.setBooked("no");
        query.setTz("Europe/Kyiv");
        query.setTimestamp(1743066649L);
        query.setPage(1);
        query.setLimit(20);

        StepVerifier.create(statScoreClient.getEvents(query, false))
                .assertNext(resp -> {
                    var competitions = resp.getApi().getData().getItems();
                    Assertions.assertEquals(1, competitions.size());

                    var competition = competitions.get(0);
                    Assertions.assertEquals(101, competition.getId());
                    Assertions.assertEquals(1, competition.getSportId());
                    Assertions.assertEquals(191, competition.getAreaId());

                    var season = competition.getSeasons().get(0);
                    Assertions.assertEquals(62587, season.getId());

                    var stage = season.getStages().get(0);
                    Assertions.assertEquals(135822, stage.getId());

                    var group = stage.getGroups().get(0);
                    Assertions.assertEquals(1, group.getId());

                    List<StatScoreEventDTO> events = group.getEvents();
                    Assertions.assertFalse(events.isEmpty());

                    for (StatScoreEventDTO event : events) {
                        // Validate date range
                        LocalDateTime eventDate = LocalDateTime.parse(event.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        LocalDateTime from = query.getDateFrom();
                        LocalDateTime to = query.getDateTo();
                        Assertions.assertTrue((!eventDate.isBefore(from)) && (!eventDate.isAfter(to)), "Event is within the date range");

                        // Validate event properties
                        Assertions.assertEquals("from_tv", event.getCoverageType());
                        Assertions.assertEquals("yes", event.getScoutsfeed());
                        Assertions.assertEquals("finished", event.getRelationStatus());
                        Assertions.assertEquals("finished", event.getStatusType());
                        Assertions.assertEquals("active", event.getItemStatus());
                        Assertions.assertEquals("yes", event.getVerifiedResult());
                        Assertions.assertEquals(101, event.getCompetitionId());

                        // Validate participants
                        for (StatScoreEventParticipantDTO p : event.getParticipants()) {
                            Assertions.assertEquals(1, p.getSportId());
                            Assertions.assertEquals("Basketball", p.getSportName());
                            Assertions.assertEquals(191, p.getAreaId());
                            Assertions.assertTrue(query.getMultiIds().contains(String.valueOf(p.getId())));
                        }
                    }
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getEventById returns competition details")
    void testGetEventById() {
        StepVerifier.create(statScoreClient.getEventById(123, false))
                .assertNext(compResponse -> {
                    var comp = compResponse.getApi().getData();
                    Assertions.assertEquals(101, comp.getId());
                    Assertions.assertEquals("NBA", comp.getName());
                    Assertions.assertEquals("NBA", comp.getShortName());
                    Assertions.assertEquals("Basketball", comp.getSportName());
                    Assertions.assertEquals("USA", comp.getAreaName());
                    Assertions.assertEquals(1, comp.getSportId());
                    Assertions.assertEquals(191, comp.getAreaId());
                    Assertions.assertEquals("active", comp.getStatus());
                    var event = comp.getSeason().getStage().getGroup().getEvent();
                    Assertions.assertEquals("from_tv", event.getCoverageType());
                    Assertions.assertEquals("yes", event.getScoutsfeed());
                    Assertions.assertEquals("finished", event.getRelationStatus());
                    Assertions.assertEquals("finished", event.getStatusType());
                    Assertions.assertEquals("active", event.getItemStatus());
                    Assertions.assertEquals("yes", event.getVerifiedResult());
                    Assertions.assertEquals(101, event.getCompetitionId());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getSquadSubParticipants returns Curry")
    void testGetSquadSubParticipants() {
        StepVerifier.create(statScoreClient.getSquadSubParticipants(1040, 62587, false))
                .assertNext(resp -> {
                    var items = resp.getApi().getData().getItems();
                    Assertions.assertEquals(1, items.size());
                    Assertions.assertEquals("Stephen Curry", items.get(0).getName());
                    Assertions.assertEquals(30, items.get(0).getShirtNr());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getAreas returns England and USA")
    void testGetAreas() {
        AreaQueryParams params = new AreaQueryParams();
        params.setLang("en");
        params.setPage(1);
        params.setLimit(10);

        StepVerifier.create(statScoreClient.getAreas(params, false))
                .assertNext(resp -> {
                    var items = resp.getApi().getData().getItems();
                    Assertions.assertEquals(2, items.size());
                    Assertions.assertEquals("USA", items.get(0).getName());
                    Assertions.assertEquals("England", items.get(1).getName());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getSports returns list of sports")
    void testGetSports() {
        SportQueryParams params = new SportQueryParams();
        params.setPage(1);
        params.setLimit(2);

        StepVerifier.create(statScoreClient.getSports(params, false))
                .assertNext(resp -> {
                    var items = resp.getApi().getData().getItems();
                    Assertions.assertEquals(2, items.size());
                    Assertions.assertEquals("Basketball", items.get(0).getName());
                    Assertions.assertEquals("Football", items.get(1).getName());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getSportById returns Basketball")
    void testGetSportById() {
        StepVerifier.create(statScoreClient.getSportById(1, false))
                .assertNext(sportResponse -> {
                    var sport = sportResponse.getApi().getData();
                    Assertions.assertEquals(1, sport.getId());
                    Assertions.assertEquals("Basketball", sport.getName());
                    Assertions.assertNotNull(sport.getStatuses());
                    Assertions.assertFalse(sport.getStatuses().isEmpty());
                    Assertions.assertEquals("Finished", sport.getStatuses().get(0).getName());

                    Assertions.assertNotNull(sport.getStats());
                    Assertions.assertEquals("Points", sport.getStats().getTeam().get(0).getName());

                    Assertions.assertNotNull(sport.getIncidents());
                    Assertions.assertEquals("Foul", sport.getIncidents().get(0).getName());

                    Assertions.assertNotNull(sport.getVenuesDetails());
                    Assertions.assertEquals("Staples Center", sport.getVenuesDetails().get(0).getName());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getVenueById returns expected venue")
    void testGetVenueById() {
        StepVerifier.create(statScoreClient.getVenueById(810, false))
                .assertNext(venueResponse -> {
                    var venue = venueResponse.getApi().getData();
                    Assertions.assertEquals(810, venue.getId());
                    Assertions.assertEquals("Staples Center", venue.getName());
                    Assertions.assertEquals("USA", venue.getCountry());
                    Assertions.assertEquals("Los Angeles", venue.getCity());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getVenues returns expected venue list")
    void testGetVenues() {
        VenueQueryParams query = new VenueQueryParams();
        query.setSportId(1);
        query.setParticipantId(1040);
        query.setPage(1);
        query.setLimit(10);

        StepVerifier.create(statScoreClient.getVenues(query, false))
                .assertNext(resp -> {
                    var venues = resp.getApi().getData().getItems();
                    Assertions.assertFalse(venues.isEmpty());
                    Assertions.assertEquals(810, venues.get(0).getId());
                    Assertions.assertEquals("Staples Center", venues.get(0).getName());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getBrackets returns expected nodes")
    void testGetBrackets() {
        int stageId = 135822;
        StepVerifier.create(statScoreClient.getBrackets(stageId, false))
                .assertNext(resp -> {
                    var brackets = resp.getApi().getData().getItems();
                    Assertions.assertFalse(brackets.isEmpty());
                    Assertions.assertNotNull(brackets.get(0).getId());
                    Assertions.assertEquals(brackets.get(0).getRoundName(), "Quarterfinals");
                    Assertions.assertEquals(brackets.get(1).getRoundName(), "Semifinals");
                    Assertions.assertEquals(brackets.get(0).getStageId(), stageId);
                    Assertions.assertEquals(brackets.get(1).getStageId(), stageId);
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getGroups returns expected competition groups")
    void testGetGroups() {
        int stageId = 135822;
        GroupQueryParams query = new GroupQueryParams();
        query.setStageId(stageId);
        query.setPage(1);
        query.setLimit(10);

        StepVerifier.create(statScoreClient.getGroups(query, false))
                .assertNext(resp -> {
                    var comp = resp.getApi().getData();
                    Assertions.assertEquals(101, comp.getId());
                    Assertions.assertEquals("NBA", comp.getName());
                    Assertions.assertNotNull(comp.getSeason());
                    Assertions.assertFalse(comp.getSeason().getStages().isEmpty());
                    Assertions.assertNotNull(comp.getSeason().getStages().get(0).getGroups());
                    Assertions.assertEquals(comp.getSeason().getStages().get(0).getId(), stageId);
                    Assertions.assertEquals(comp.getSeason().getStages().get(0).getGroups().get(0).getName(), "Group A");
                    Assertions.assertEquals(comp.getSeason().getStages().get(0).getGroups().get(1).getName(), "Group B");
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getSeasons returns seasons list for a competition")
    void testGetSeasons() {
        SeasonQueryParams query = new SeasonQueryParams();
        query.setLang("en");
        query.setPage(1);
        query.setLimit(2);
        query.setSportId(1);
        query.setCompetitionId("101");
        query.setParticipantId(1040);
        query.setMultiIds("62587,62588");
        query.setSortType("year");
        query.setSortOrder("asc");
        query.setAreaId(191);
        query.setTimestamp(1743060000L);

        StepVerifier.create(statScoreClient.getSeasons(query, false))
                .assertNext(resp -> {
                    var competitions = resp.getApi().getData().getItems();
                    Assertions.assertEquals(1, competitions.size());

                    var comp = competitions.get(0);
                    Assertions.assertEquals(101, comp.getId());
                    Assertions.assertEquals("NBA", comp.getName());
                    Assertions.assertEquals("active", comp.getStatus());
                    Assertions.assertEquals(191, comp.getAreaId());
                    Assertions.assertEquals(1, comp.getSportId());

                    var seasons = comp.getSeasons();
                    Assertions.assertNotNull(seasons);
                    Assertions.assertEquals(2, seasons.size());

                    Assertions.assertEquals(62587, seasons.get(0).getId());
                    Assertions.assertEquals("NBA 2024/25", seasons.get(0).getName());
                    Assertions.assertEquals("2024/25", seasons.get(0).getYear());
                    Assertions.assertEquals("yes", seasons.get(0).getActual());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getSeasonById returns competition with single season details")
    void testGetSeasonById() {
        StepVerifier.create(statScoreClient.getSeasonById(62587, false))
                .assertNext(resp -> {
                    var competition = resp.getApi().getData();
                    Assertions.assertEquals(101, competition.getId());
                    Assertions.assertEquals("NBA", competition.getName());
                    Assertions.assertEquals("Basketball", competition.getSportName());
                    Assertions.assertEquals("USA", competition.getAreaName());

                    var season = competition.getSeason();
                    Assertions.assertNotNull(season);
                    Assertions.assertEquals(62587, season.getId());
                    Assertions.assertEquals("NBA 2024/25", season.getName());
                    Assertions.assertEquals("2024/25", season.getYear());

                    Assertions.assertNotNull(season.getStages());
                    Assertions.assertEquals(2, season.getStages().size());
                    Assertions.assertEquals("Regular Season", season.getStages().get(0).getName());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getStages returns expected stages with all params")
    void testGetStages() {
        StageQueryParams query = new StageQueryParams();
        query.setSeasonId(62587);
        query.setTimestamp(1743060000L);
        query.setPage(1);
        query.setLimit(50);

        StepVerifier.create(statScoreClient.getStages(query, false))
                .assertNext(resp -> {
                    var comp = resp.getApi().getData();
                    Assertions.assertEquals(101, comp.getId());
                    Assertions.assertEquals("NBA", comp.getName());
                    Assertions.assertEquals(1, comp.getSportId());
                    Assertions.assertEquals(191, comp.getAreaId());

                    var season = comp.getSeason();
                    Assertions.assertEquals(62587, season.getId());
                    Assertions.assertEquals("NBA 2024/25", season.getName());
                    Assertions.assertEquals("yes", season.getActual());

                    Assertions.assertEquals(2, season.getStages().size());
                    Assertions.assertEquals("NBA Cup - Championship", season.getStages().get(0).getName());
                    Assertions.assertEquals("Regular Season", season.getStages().get(1).getName());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getStageById returns expected stage details")
    void testGetStageById() {
        StepVerifier.create(statScoreClient.getStageById(135822, false))
                .assertNext(resp -> {
                    var comp = resp.getApi().getData();
                    Assertions.assertEquals(101, comp.getId());
                    Assertions.assertEquals("NBA", comp.getName());
                    Assertions.assertEquals(1, comp.getSportId());

                    var season = comp.getSeason();
                    Assertions.assertEquals(62587, season.getId());
                    Assertions.assertNotNull(season.getStage());
                    Assertions.assertEquals(135822, season.getStage().getId());
                    Assertions.assertEquals("Regular Season", season.getStage().getName());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getStandings returns standings with all query params")
    void testGetStandingsWithAllParams() {
        StandingQueryParams query = new StandingQueryParams();
        query.setLang("en");
        query.setPage(1);
        query.setLimit(50);
        query.setObjectType("season");
        query.setObjectId(62587);
        query.setTypeId(34);
        query.setSubtype("standings");
        query.setSportId(1);
        query.setCompetitionId(101);
        query.setSeasonId(62587);
        query.setStageId(135822);
        query.setTimestamp(1743061234L);
        query.setItemStatus("active");

        StepVerifier.create(statScoreClient.getStandings(query, false))
                .assertNext(resp -> {
                    var method = resp.getApi().getMethod();
                    Assertions.assertEquals("standings.index", method.getName());

                    var standings = resp.getApi().getData().getItems();
                    Assertions.assertEquals(1, standings.size());
                    var s = standings.get(0);
                    Assertions.assertEquals(182996, s.getId());
                    Assertions.assertEquals("Streaks - USA, NBA 2024/25", s.getName());
                    Assertions.assertEquals(1, s.getSportId());
                    Assertions.assertEquals("Basketball", s.getSportName());
                    Assertions.assertEquals("active", s.getItemStatus());
                    Assertions.assertEquals("yes", s.getResetGroupRank());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Verify getStandingById returns full group/participant data")
    void testGetStandingById() {
        StandingByIdQueryParams params = new StandingByIdQueryParams();
        params.setLang("en");
        params.setParticipantId("123");
        params.setSubParticipantId(12);

        StepVerifier.create(statScoreClient.getStandingById(182996, params, false))
                .assertNext(resp -> {
                    var s = resp.getApi().getData();
                    Assertions.assertEquals(182996, s.getId());
                    Assertions.assertEquals("Streaks - NBA", s.getName());
                    Assertions.assertEquals("season", s.getObjectType());
                    Assertions.assertEquals("NBA 2024/25", s.getObjectName());

                    var group = s.getGroups().get(0);
                    Assertions.assertEquals(1, group.getId());
                    Assertions.assertEquals("East", group.getName());

                    var p = group.getParticipants().get(0);
                    Assertions.assertEquals(1040, p.getId());
                    Assertions.assertEquals("team", p.getType());
                    Assertions.assertEquals("Philadelphia 76ers", p.getName());
                    Assertions.assertEquals("Basketball", p.getSportName());
                    Assertions.assertEquals("no", p.getVirtual());
                    Assertions.assertEquals("PHI", p.getAcronym());

                    Assertions.assertEquals(1, group.getCorrections().size());
                    Assertions.assertEquals("+1", group.getCorrections().get(0).getValue());

                    Assertions.assertEquals(1, group.getZones().size());
                    Assertions.assertEquals("Playoff", group.getZones().get(0).getName());
                })
                .verifyComplete();
    }

}

