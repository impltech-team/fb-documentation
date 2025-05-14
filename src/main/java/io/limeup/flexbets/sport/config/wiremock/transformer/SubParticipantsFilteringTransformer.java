package io.limeup.flexbets.sport.config.wiremock.transformer;

import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SubParticipantsFilteringTransformer extends BaseFilteringTransformer {
    @Override
    public String getName() {
        return "sub-participants-filtering-transformer";
    }

    @Override
    protected String getFieldKey() {
        return "sub_participants";
    }

    @Override
    protected Map<String, List<String>> extractFilters(LoggedRequest request) {
        Map<String, List<String>> filters = new HashMap<>();
        filters.put("position", extractListQueryParam(request, "positions"));
        filters.put("participant_id", extractListQueryParam(request, "participant_ids"));
        return filters;
    }

    @Override
    protected Map<String, String> extractPartialMatchFilters(LoggedRequest request) {
        Map<String, String> filters = new HashMap<>();
        filters.put("position", extractQueryParam(request, BaseFilteringTransformer.FILTER, null));
        filters.put("team_name", extractQueryParam(request, BaseFilteringTransformer.FILTER, null));
        filters.put("player_name", extractQueryParam(request, BaseFilteringTransformer.FILTER, null));
        return filters;
    }
}

