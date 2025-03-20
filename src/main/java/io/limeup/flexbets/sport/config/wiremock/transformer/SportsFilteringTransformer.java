package io.limeup.flexbets.sport.config.wiremock.transformer;

import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SportsFilteringTransformer extends BaseFilteringTransformer {
    @Override
    public String getName() {
        return "sports-filtering-transformer";
    }

    @Override
    protected String getFieldKey() {
        return "sports";
    }

    @Override
    protected Map<String, List<String>> extractFilters(LoggedRequest request) {
        Map<String, List<String>> filters = new HashMap<>();
        filters.put("id", extractListQueryParam(request, "sport_ids"));
        return filters;
    }

    @Override
    protected Map<String, String> extractPartialMatchFilters(LoggedRequest request) {
        Map<String, String> filters = new HashMap<>();
        filters.put("name", extractQueryParam(request, "name"));
        return filters;
    }

}
