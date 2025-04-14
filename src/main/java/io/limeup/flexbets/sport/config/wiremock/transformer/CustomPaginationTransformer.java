package io.limeup.flexbets.sport.config.wiremock.transformer;

import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformerV2;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class CustomPaginationTransformer implements ResponseDefinitionTransformerV2 {

    private static final Random RANDOM = new Random();

    @Override
    public ResponseDefinition transform(ServeEvent serveEvent) {
        Request request = serveEvent.getRequest();
        ResponseDefinition responseDefinition = serveEvent.getResponseDefinition();

        int page = 1;
        int pageSize = 50;
        try {
            if (request.queryParameter(BaseFilteringTransformer.PAGE).isPresent()) {
                page = Integer.parseInt(request.queryParameter(BaseFilteringTransformer.PAGE).firstValue());
            }
            if (request.queryParameter(BaseFilteringTransformer.PAGE_SIZE).isPresent()) {
                pageSize = Integer.parseInt(request.queryParameter(BaseFilteringTransformer.PAGE_SIZE).firstValue());
            }
        } catch (NumberFormatException e) {
            log.warn("Invalid pagination parameters, using defaults");
        }

        int minCount = (page == 1) ? 1 : pageSize + 1;
        int maxCount = (int) Math.ceil(1.5 * pageSize);
        int count = RANDOM.nextInt(maxCount - minCount + 1) + minCount;
        int totalPages = (int) Math.ceil((double) count / pageSize);

        int currentPageCount;
        if (page < totalPages) {
            currentPageCount = pageSize; // Full page
        } else {
            currentPageCount = count % pageSize == 0 ? pageSize : count % pageSize; // Last page
        }

        log.info("Pagination computed -> Page: {}, Page Size: {}, Current Page Count: {}, Total Pages: {}, Count: {}",
                page, pageSize, currentPageCount, totalPages, count);

        return ResponseDefinitionBuilder.like(responseDefinition)
                .withTransformerParameter(BaseFilteringTransformer.PAGE, page)
                .withTransformerParameter(BaseFilteringTransformer.PAGE_SIZE, pageSize)
                .withTransformerParameter(BaseFilteringTransformer.CURRENT_PAGE_COUNT, currentPageCount)
                .withTransformerParameter(BaseFilteringTransformer.TOTAL_PAGES, totalPages)
                .withTransformerParameter(BaseFilteringTransformer.COUNT, count)
                .build();
    }

    @Override
    public String getName() {
        return "custom-pagination-transformer";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}

