package io.limeup.flexbets.sport.config.wiremock.transformer;

import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CompetitionsFilteringTransformer extends BaseFilteringTransformer {
    @Override
    public String getName() {
        return "competitions-filtering-transformer";
    }

    @Override
    protected String getFieldKey() {
        return "competitions";
    }

    @Override
    protected Map<String, List<String>> extractFilters(LoggedRequest request) {
        Map<String, List<String>> filters = new HashMap<>();
        filters.put("area_id", extractListQueryParam(request, "area_ids"));
        filters.put("sport_id", extractListQueryParam(request, "sport_ids"));
        filters.put("type", extractListQueryParam(request, "type"));
        filters.put("gender", extractListQueryParam(request, "gender"));
        filters.put("status_type", extractListQueryParam(request, "status_type"));
        return filters;
    }

    @Override
    protected Map<String, String> extractGreaterFilters(LoggedRequest request) {
        Map<String, String> filters = new HashMap<>();
        filters.put("end_date", extractQueryParam(request, "date_to"));
        return filters;
    }

    @Override
    protected Map<String, String> extractLessFilters(LoggedRequest request) {
        Map<String, String> filters = new HashMap<>();
        filters.put("start_date", extractQueryParam(request, "date_from"));
        return filters;
    }
}
