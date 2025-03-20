package io.limeup.flexbets.sport.config.wiremock.transformer;

import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class EventsFilteringTransformer extends BaseFilteringTransformer {
    @Override
    public String getName() {
        return "events-filtering-transformer";
    }

    @Override
    protected String getFieldKey() {
        return "events";
    }

    @Override
    protected Map<String, List<String>> extractFilters(LoggedRequest request) {
        Map<String, List<String>> filters = new HashMap<>();
        filters.put("status", Optional.ofNullable(extractQueryParam(request, "status"))
                .map(List::of)
                .orElse(Collections.emptyList()));
        filters.put("venue.venue_id", extractListQueryParam(request, "venue_ids"));
        filters.put("participants.participant_id", extractListQueryParam(request, "participant_ids"));
        return filters;
    }

    @Override
    protected Map<String, String> extractGreaterFilters(LoggedRequest request) {
        Map<String, String> filters = new HashMap<>();
        filters.put("event_date", extractQueryParam(request, "date_to"));
        return filters;
    }

    @Override
    protected Map<String, String> extractLessFilters(LoggedRequest request) {
        Map<String, String> filters = new HashMap<>();
        filters.put("event_date", extractQueryParam(request, "date_from"));
        return filters;
    }
}


