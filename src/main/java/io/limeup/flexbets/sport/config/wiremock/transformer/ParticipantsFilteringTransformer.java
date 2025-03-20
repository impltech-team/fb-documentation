package io.limeup.flexbets.sport.config.wiremock.transformer;

import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ParticipantsFilteringTransformer extends BaseFilteringTransformer {
    @Override
    public String getName() {
        return "participants-filtering-transformer";
    }

    @Override
    protected String getFieldKey() {
        return "participants";
    }

    @Override
    protected Map<String, List<String>> extractFilters(LoggedRequest request) {
        Map<String, List<String>> filters = new HashMap<>();
        filters.put("id", extractListQueryParam(request, "participant_ids"));
        return filters;
    }
}

