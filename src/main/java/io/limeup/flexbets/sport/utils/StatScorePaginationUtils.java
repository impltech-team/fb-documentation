package io.limeup.flexbets.sport.utils;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.statscore.ListWrapper;
import io.limeup.flexbets.sport.dto.statscore.StatScoreResponse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StatScorePaginationUtils {

    public static <T> PaginatedResponse<T> buildPaginatedResponse(
            StatScoreResponse<ListWrapper<T>> response,
            Integer page,
            Integer limit
    ) {
        int total = Optional.ofNullable(response)
                .map(r -> r.getApi().getMethod().getTotal_items())
                .orElse(0);

        List<T> items = Optional.ofNullable(response)
                .map(r -> r.getApi().getData())
                .map(ListWrapper::getItems)
                .orElse(Collections.emptyList());

        PaginatedResponse.PaginatedResponseBuilder<T> builder = PaginatedResponse.<T>builder()
                .count(total)
                .items(items);

        if (page != null && limit != null) {
            builder
                    .page(page)
                    .pageSize(limit)
                    .totalPages((int) Math.ceil((double) total / limit));
        }

        return builder.build();
    }

}
