package io.limeup.flexbets.sport.utils;

import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.SingleRootItemPaginatedResponse;
import io.limeup.flexbets.sport.dto.statscore.ListWrapper;
import io.limeup.flexbets.sport.dto.statscore.StatScoreResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PaginationUtils {

    private PaginationUtils() { }

    public static <T> PaginatedResponse<T> buildPaginatedResponse(
            List<T> items,
            Long totalCount,
            Integer page,
            Integer limit
    ) {
        PaginatedResponse.PaginatedResponseBuilder<T> builder = PaginatedResponse.<T>builder()
                .count(totalCount)
                .items(items);

        if (page != null && limit != null) {
            builder
                    .page(page)
                    .pageSize(limit)
                    .totalPages((int) Math.ceil((double) totalCount / limit));
        }

        return builder.build();
    }

    public static <T> PaginatedResponse<T> buildStatScorePaginatedResponse(
            StatScoreResponse<ListWrapper<T>> response,
            Integer page,
            Integer limit
    ) {
        long total = Optional.ofNullable(response)
                .map(r -> r.getApi().getMethod().getTotalItems())
                .orElse(0);

        List<T> items = Optional.ofNullable(response)
                .map(r -> r.getApi().getData())
                .map(ListWrapper::getItems)
                .orElse(Collections.emptyList());

        return buildPaginatedResponse(items, total, page, limit);
    }

    public static <T> SingleRootItemPaginatedResponse<T> buildStatScorePaginatedResponseForSingleRoot(
            StatScoreResponse<T> root,
            Integer page,
            Integer limit,
            String rootName
    ) {
        int total = Optional.ofNullable(root)
                .map(StatScoreResponse::getApi)
                .map(StatScoreResponse.ApiWrapper::getMethod)
                .map(StatScoreResponse.MethodInfo::getTotalItems)
                .orElse(0);
        var builder = SingleRootItemPaginatedResponse.<T>builder()
                .rootName(rootName)
                .count(total)
                .data(Optional.ofNullable(root)
                        .map(StatScoreResponse::getApi).get().getData());
        if (page != null && limit != null) {
            builder
                    .page(page)
                    .pageSize(limit)
                    .totalPages((int) Math.ceil((double) total / limit));
        }

        return builder.build();
    }

    public static <Q, D, E> List<E> fetchAllPaginatedData(
            Function<Q, PaginatedResponse<D>> fetchPageFunction,
            Function<D, E> mapToEntity,
            Supplier<Q> initialQuerySupplier,
            BiFunction<Q, Integer, Q> setPageFunction
    ) {
        List<E> allResults = new ArrayList<>();
        int page = 1;
        PaginatedResponse<D> response;

        do {
            Q query = setPageFunction.apply(initialQuerySupplier.get(), page);
            response = fetchPageFunction.apply(query);
            if (response.getItems() != null) {
                allResults.addAll(response.getItems().stream()
                        .map(mapToEntity)
                        .collect(Collectors.toList()));
            }
            page++;
        } while (page <= response.getTotalPages());

        return allResults;
    }


    public static PageRequest getPageRequest(RequestQueryDTO requestQuery) {
        PageRequest pageRequest;
        if (StringUtils.isNotBlank(requestQuery.getSortBy())) {
            if (requestQuery.getSortBy().equals("id")) {
                requestQuery.setSortBy("external_id");
            }
            Sort sort = Sort.by(Sort.Direction.fromString(requestQuery.getSortOrder()), requestQuery.getSortBy());
            pageRequest = PageRequest.of(requestQuery.getPage() - 1, requestQuery.getPageSize(), sort);
        } else {
            pageRequest = PageRequest.of(requestQuery.getPage() - 1, requestQuery.getPageSize());
        }
        return pageRequest;
    }

}
