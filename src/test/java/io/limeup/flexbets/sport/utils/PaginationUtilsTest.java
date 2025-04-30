package io.limeup.flexbets.sport.utils;

import static org.assertj.core.api.Assertions.assertThat;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreResponse;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

class PaginationUtilsTest {

    @Test
    void buildPaginatedResponseShouldBuildCorrectly() {
        var response = PaginationUtils.buildPaginatedResponse(
                List.of("player1", "player2"), 2L, 1, 2);

        assertThat(response.getItems()).containsExactly("player1", "player2");
        assertThat(response.getCount()).isEqualTo(2L);
        assertThat(response.getPage()).isEqualTo(1);
        assertThat(response.getPageSize()).isEqualTo(2);
        assertThat(response.getTotalPages()).isEqualTo(1);
    }

    @Test
    void buildStatScorePaginatedResponseShouldHandleNullResponse() {
        var response = PaginationUtils.buildStatScorePaginatedResponse(null, 1, 10);

        assertThat(response.getItems()).isEmpty();
        assertThat(response.getCount()).isEqualTo(0);
    }

    @Test
    void buildStatScorePaginatedResponseForSingleRootShouldHandleData() {
        var apiWrapper = new StatScoreResponse.ApiWrapper<>();
        var methodInfo = new StatScoreResponse.MethodInfo();
        methodInfo.setTotalItems(1);
        apiWrapper.setMethod(methodInfo);
        apiWrapper.setData("someData");

        var statScoreResponse = new StatScoreResponse<>();
        statScoreResponse.setApi(apiWrapper);

        var response = PaginationUtils.buildStatScorePaginatedResponseForSingleRoot(
                statScoreResponse, 1, 10, "root");

        assertThat(response.getRootName()).isEqualTo("root");
        assertThat(response.getData()).isEqualTo("someData");
        assertThat(response.getCount()).isEqualTo(1);
    }

    @Test
    void fetchAllPaginatedDataShouldFetchAllPages() {
        Supplier<RequestQueryDTO> supplier = RequestQueryDTO::new;
        Function<RequestQueryDTO, PaginatedResponse<String>> fetchPageFunc = query -> {
            if (query.getPage() == 1) {
                return PaginatedResponse.<String>builder()
                        .items(List.of("team1", "team2"))
                        .count(4L)
                        .page(1)
                        .pageSize(2)
                        .totalPages(2)
                        .build();
            } else {
                return PaginatedResponse.<String>builder()
                        .items(List.of("team3", "team4"))
                        .count(4L)
                        .page(2)
                        .pageSize(2)
                        .totalPages(2)
                        .build();
            }
        };

        var result = PaginationUtils.fetchAllPaginatedData(fetchPageFunc,  Function.identity(), supplier, (query, page) -> {
            query.setPage(page);
            query.setPageSize(2);
            return query;
        });

        assertThat(result).containsExactly("team1", "team2", "team3", "team4");
    }


    @Test
    void getPageRequestWithSort() {
        RequestQueryDTO requestQuery = new RequestQueryDTO();
        requestQuery.setPage(2);
        requestQuery.setPageSize(10);
        requestQuery.setSortBy("name");
        requestQuery.setSortOrder("desc");

        PageRequest pageRequest = PaginationUtils.getPageRequest(requestQuery);

        assertThat(pageRequest.getPageNumber()).isEqualTo(1);
        assertThat(pageRequest.getPageSize()).isEqualTo(10);
        assertThat(pageRequest.getSort().getOrderFor("name")).isNotNull();
        assertThat(pageRequest.getSort().getOrderFor("name").getDirection()).isEqualTo(Sort.Direction.DESC);
    }

    @Test
    void getPageRequestWithoutSort() {
        RequestQueryDTO requestQuery = new RequestQueryDTO();
        requestQuery.setPage(1);
        requestQuery.setPageSize(5);

        PageRequest pageRequest = PaginationUtils.getPageRequest(requestQuery);

        assertThat(pageRequest.getPageNumber()).isEqualTo(0);
        assertThat(pageRequest.getPageSize()).isEqualTo(5);
        assertThat(pageRequest.getSort().isSorted()).isFalse();
    }

    @Test
    void getPageRequestWhenSortByIdShouldReplaceToExternalId() {
        RequestQueryDTO requestQuery = new RequestQueryDTO();
        requestQuery.setPage(1);
        requestQuery.setPageSize(10);
        requestQuery.setSortBy("id");
        requestQuery.setSortOrder("asc");

        PageRequest pageRequest = PaginationUtils.getPageRequest(requestQuery);

        assertThat(pageRequest.getSort().getOrderFor("external_id")).isNotNull();
    }
}

