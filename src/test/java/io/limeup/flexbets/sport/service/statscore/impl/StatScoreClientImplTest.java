package io.limeup.flexbets.sport.service.statscore.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.limeup.flexbets.sport.dto.statscore.StatScoreResponse;
import io.limeup.flexbets.sport.dto.statscore.StatScoreSportDTO;
import io.limeup.flexbets.sport.dto.statscore.prams.AreaQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.CompetitionQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.EventQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.GroupQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.ParticipantQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.SeasonQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.SportQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StageQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StandingByIdQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.StandingQueryParams;
import io.limeup.flexbets.sport.dto.statscore.prams.VenueQueryParams;
import io.limeup.flexbets.sport.utils.ConstantUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatScoreClientImplTest {

    @Test
    void getSportByIdShouldReturnParsedDTO() {
        int sportId = 123;
        String json = """
                {
                  "api": {
                    "method": { "name": "sports.show" },
                    "data": {
                      "sport": { "id": 123, "name": "Football" }
                    }
                  }
                }
                """;

        StatScoreClientImpl client = setupClient(json);

        StatScoreResponse<StatScoreSportDTO> result = client.getSportById(sportId, false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi()).isNotNull();
        assertThat(result.getApi().getData().getId()).isEqualTo(123);
        assertThat(result.getApi().getData().getName()).isEqualTo(ConstantUtils.TestConstants.FOOTBALL);
    }

    @Test
    void getParticipantsShouldReturnParsedResponse() {
        String json = """
        {
          "api": {
            "method": { "name": "dummy.method" },
            "data": {
              "participants": [{ "id": 1 }]
            }
          }
        }
        """;

        StatScoreClientImpl client = setupClient(json);

        var result = client.getParticipants(new ParticipantQueryParams(), false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi()).isNotNull();
        assertThat(result.getApi().getData().getItems()).isNotEmpty();
    }

    @Test
    void getEventByIdShouldReturnParsedResponse() {
        String json = """
        {
          "api": {
            "method": { "name": "dummy.method" },
            "data": {
              "competition": { "id": 1 }
            }
          }
        }
        """;

        StatScoreClientImpl client = setupClient(json);

        var result = client.getEventById(1, false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi()).isNotNull();
    }

    @Test
    void getAreasShouldReturnParsedResponse() {
        String json = """
        {
          "api": {
            "method": { "name": "dummy.method" },
            "data": {
              "areas": [{ "id": 1 }]
            }
          }
        }
        """;

        StatScoreClientImpl client = setupClient(json);

        var result = client.getAreas(new AreaQueryParams(), false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi()).isNotNull();
        assertThat(result.getApi().getData().getItems()).isNotEmpty();
    }

    @Test
    void getVenuesShouldReturnParsedResponse() {
        String json = """
        {
          "api": {
            "method": { "name": "dummy.method" },
            "data": {
              "venues": [{ "id": 1 }]
            }
          }
        }
        """;

        StatScoreClientImpl client = setupClient(json);

        var result = client.getVenues(new VenueQueryParams(), false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi()).isNotNull();
        assertThat(result.getApi().getData().getItems()).isNotEmpty();
    }

    @Test
    void getEventParticipantsShouldReturnParsedResponse() {
        String json = """
        {
          "api": {
            "method": { "name": "dummy.method" },
            "data": {
              "participants": [{ "id": 1 }]
            }
          }
        }
        """;

        StatScoreClientImpl client = setupClient(json);

        var result = client.getEventParticipants(1, false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi()).isNotNull();
        assertThat(result.getApi().getData().getItems()).isNotEmpty();
    }

    @Test
    void getBracketsShouldReturnParsedResponse() {
        String json = """
        {
          "api": {
            "method": { "name": "dummy.method" },
            "data": {
              "nodes": [{ "id": 1 }]
            }
          }
        }
        """;

        StatScoreClientImpl client = setupClient(json);

        var result = client.getBrackets(1, false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi()).isNotNull();
        assertThat(result.getApi().getData().getItems()).isNotEmpty();

    }

    @Test
    void getEventsShouldReturnParsedDTOs() {
        String json = """
        {
          "api": {
            "method": { "name": "events.index" },
            "data": {
              "competitions": [
                { "id": 11, "name": "Championship" }
              ]
            }
          }
        }
        """;

        StatScoreClientImpl client = setupClient(json);

        EventQueryParams params = new EventQueryParams();
        params.setDateFrom(LocalDateTime.now().minusDays(1));
        params.setDateTo(LocalDateTime.now().plusDays(1));

        var result = client.getEvents(params, false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi().getData().getItems()).hasSize(1);
        assertThat(result.getApi().getData().getItems().get(0).getId()).isEqualTo(11);
    }

    @Test
    void getSportsShouldReturnParsedDTOs() {
        String json = """
        {
          "api": {
            "method": { "name": "sports.index" },
            "data": {
              "sports": [
                { "id": 1, "name": "Football" }
              ]
            }
          }
        }
        """;

        StatScoreClientImpl client = setupClient(json);

        var result = client.getSports(new SportQueryParams(), false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi().getData().getItems()).hasSize(1);
        assertThat(result.getApi().getData().getItems().get(0).getName()).isEqualTo("Football");
    }

    @Test
    void getGroupsShouldReturnParsedDTO() {
        String json = """
        {
          "api": {
            "method": { "name": "groups.index" },
            "data": {
              "competition": { "id": 44, "name": "Playoffs" }
            }
          }
        }
        """;

        StatScoreClientImpl client = setupClient(json);

        var result = client.getGroups(new GroupQueryParams(), false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi().getData().getId()).isEqualTo(44);
    }

    private StatScoreClientImpl setupClient(String json) {
        ObjectMapper objectMapper = spy(new ObjectMapper());
        ExchangeFunction exchangeFunction = mock(ExchangeFunction.class);

        ClientResponse response = ClientResponse.create(HttpStatus.OK)
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(json)
                .build();

        when(exchangeFunction.exchange(any(ClientRequest.class))).thenReturn(Mono.just(response));

        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost")
                .exchangeFunction(exchangeFunction)
                .build();

        return new StatScoreClientImpl(webClient, objectMapper);
    }

    @Test
    void getSeasonsShouldReturnParsedResponse() {
        String json = """
        {
          "api": {
            "method": { "name": "dummy.method" },
            "data": {
              "competitions": [{ "id": 1 }]
            }
          }
        }
        """;

        var client = setupClient(json);
        var result = client.getSeasons(new SeasonQueryParams(), false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi().getData().getItems()).isNotEmpty();
    }

    @Test
    void getSeasonByIdShouldReturnParsedResponse() {
        String json = """
        {
          "api": {
            "method": { "name": "dummy.method" },
            "data": {
              "competition": { "id": 1 }
            }
          }
        }
        """;

        var client = setupClient(json);
        var result = client.getSeasonById(1, false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi().getData().getId()).isEqualTo(1);
    }

    @Test
    void getStagesShouldReturnParsedResponse() {
        String json = """
        {
          "api": {
            "method": { "name": "dummy.method" },
            "data": {
              "competition": { "id": 1 }
            }
          }
        }
        """;

        var client = setupClient(json);
        var result = client.getStages(new StageQueryParams(), false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi().getData().getId()).isEqualTo(1);
    }

    @Test
    void getStageByIdShouldReturnParsedResponse() {
        String json = """
        {
          "api": {
            "method": { "name": "dummy.method" },
            "data": {
              "competition": { "id": 1 }
            }
          }
        }
        """;

        var client = setupClient(json);
        var result = client.getStageById(1, false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi().getData().getId()).isEqualTo(1);
    }

    @Test
    void getStandingsShouldReturnParsedResponse() {
        String json = """
        {
          "api": {
            "method": { "name": "dummy.method" },
            "data": {
              "standings_list": [{ "id": 1 }]
            }
          }
        }
        """;

        var client = setupClient(json);
        var result = client.getStandings(new StandingQueryParams(), false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi().getData().getItems()).isNotEmpty();
    }

    @Test
    void getStandingByIdShouldReturnParsedResponse() {
        String json = """
        {
          "api": {
            "method": { "name": "dummy.method" },
            "data": {
              "standings": { "id": 1 }
            }
          }
        }
        """;

        var client = setupClient(json);
        var result = client.getStandingById(1, new StandingByIdQueryParams(), false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi().getData().getId()).isEqualTo(1);
    }

    @Test
    void getCompetitionsShouldReturnParsedResponse() {
        String json = """
        {
          "api": {
            "method": { "name": "dummy.method" },
            "data": {
              "competitions": [{ "id": 1 }]
            }
          }
        }
        """;

        var client = setupClient(json);
        var result = client.getCompetitions(new CompetitionQueryParams(), false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi().getData().getItems()).isNotEmpty();
    }

    @Test
    void getCompetitionByIdShouldReturnParsedResponse() {
        String json = """
        {
          "api": {
            "method": { "name": "dummy.method" },
            "data": {
              "competitions": { "id": 1 }
            }
          }
        }
        """;

        var client = setupClient(json);
        var result = client.getCompetitionById(1, false).block();

        assertThat(result).isNotNull();
        assertThat(result.getApi().getData().getId()).isEqualTo(1);
    }

}

