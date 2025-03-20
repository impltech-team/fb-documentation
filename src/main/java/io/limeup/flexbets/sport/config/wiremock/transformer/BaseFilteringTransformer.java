package io.limeup.flexbets.sport.config.wiremock.transformer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.tomakehurst.wiremock.extension.ResponseTransformerV2;
import com.github.tomakehurst.wiremock.http.Response;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
public abstract class BaseFilteringTransformer implements ResponseTransformerV2 {

    protected final ObjectMapper objectMapper = new ObjectMapper();
    private static final Random RANDOM = new Random();

    @Override
    public Response transform(Response response, ServeEvent serveEvent) {
        try {
            LoggedRequest request = serveEvent.getRequest();

            // Extract pagination parameters
            int pageSize = Integer.parseInt(extractQueryParam(request, "page_size", "50"));
            int page = Integer.parseInt(extractQueryParam(request, "page", "1"));

            // Read response JSON
            JsonNode originalResponse = objectMapper.readTree(response.getBody());
            String fieldKey = getFieldKey();

            if (!originalResponse.has(fieldKey)) {
                log.warn("Field '{}' not found in response", fieldKey);
                return Response.response().body(objectMapper.writeValueAsString(originalResponse)).build();
            }

            ArrayNode dataArray = (ArrayNode) originalResponse.get(fieldKey);

            // ✅ Extract filters based on the specific API implementation
            Map<String, List<String>> filters = extractFilters(request);
            Map<String, String> greaterFilters = extractGreaterFilters(request);
            Map<String, String> lessFilters = extractLessFilters(request);
            Map<String, String> partialMatchFilters = extractPartialMatchFilters(request);
            boolean hasFilters = filters.values().stream().anyMatch(list -> !list.isEmpty()) ||
                    (greaterFilters != null && !greaterFilters.isEmpty()) ||
                    (lessFilters != null && !lessFilters.isEmpty()) ||
                    partialMatchFilters != null
                            && partialMatchFilters.values().stream().anyMatch(value -> value != null && !value.isEmpty());

            // ✅ Apply filters only if filters exist
            ArrayNode filteredDataArray = hasFilters ? filterDataArray(dataArray, filters, greaterFilters, lessFilters, partialMatchFilters) : dataArray;

            // ✅ Apply pagination logic
            int count = getCount(pageSize, page, filteredDataArray);
            int totalPages = (int) Math.ceil((double) count / pageSize);

            // ✅ Modify response JSON dynamically
            ((ObjectNode) originalResponse).set(fieldKey, filteredDataArray);
            ((ObjectNode) originalResponse).put("count", count);
            ((ObjectNode) originalResponse).put("total_pages", totalPages);

            return Response.response().body(objectMapper.writeValueAsString(originalResponse)).build();

        } catch (IOException e) {
            log.error("Error applying filters to response", e);
            return Response.response().status(500).body("Error applying filters to response").build();
        }
    }

    /**
     * Abstract method to be defined in subclasses.
     */
    protected abstract String getFieldKey();

    /**
     * Each subclass will define how to extract filters based on query parameters.
     */
    protected abstract Map<String, List<String>> extractFilters(LoggedRequest request);

    protected Map<String, String> extractGreaterFilters(LoggedRequest request) {
        return null;
    }

    protected Map<String, String> extractLessFilters(LoggedRequest request) {
        return null;
    }

    protected Map<String, String> extractPartialMatchFilters(LoggedRequest request) {
        return null;
    }

    /**
     * Filters the data array based on the extracted filters.
     */
    private ArrayNode filterDataArray(ArrayNode dataArray, Map<String, List<String>> filters, Map<String, String> greaterFilters, Map<String, String> lessFilters, Map<String, String> partialMatchFilters) {
        ArrayNode filteredDataArray = objectMapper.createArrayNode();
        for (JsonNode item : dataArray) {
            if (matchesFilters(item, filters)
                    && greaterFilters(item, greaterFilters)
                    && lessFilters(item, lessFilters)
                    && partialMatchFilters(item, partialMatchFilters)) {
                filteredDataArray.add(item);
            }
        }
        return filteredDataArray;
    }

    private LocalDateTime parseDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            log.warn("Invalid date format: {}", dateStr);
            return null;
        }
    }

    private boolean partialMatchFilters(JsonNode item, Map<String, String> filters) {
        if (filters == null || filters.isEmpty() || filters.values().stream().anyMatch(Objects::isNull)) {
            return true;
        }

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String field = entry.getKey();
            String filterValue = entry.getValue();

            if (filterValue != null && !filterValue.isEmpty()) {
                if (item.has(field)) {
                    String itemValue = item.get(field).asText().toLowerCase();

                    if (itemValue.contains(filterValue.toLowerCase())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean greaterFilters(JsonNode item, Map<String, String>filters) {
        if (filters != null) {
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                String field = entry.getKey();
                LocalDateTime filterValue = parseDate(entry.getValue());
                if (filterValue != null) {
                    if (!item.has(field)) {
                        return false;
                    }
                    LocalDateTime itemValue = parseDate(item.get(field).asText().toLowerCase());
                    if (!itemValue.isBefore(filterValue)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean lessFilters(JsonNode item, Map<String, String>filters) {
        if (filters != null) {
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                String field = entry.getKey();
                LocalDateTime filterValue = parseDate(entry.getValue());
                if (filterValue != null) {
                    if (!item.has(field)) {
                        return false;
                    }
                    LocalDateTime itemValue = parseDate(item.get(field).asText().toLowerCase());
                    if (!itemValue.isAfter(filterValue)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Determines if an item matches filtering criteria.
     */
    private boolean matchesFilters(JsonNode item, Map<String, List<String>> filters) {
        for (Map.Entry<String, List<String>> entry : filters.entrySet()) {
            String fieldPath = entry.getKey();
            List<String> filterValues = entry.getValue();
            if (!filterValues.isEmpty()) {
                JsonNode fieldNode;
                if (fieldPath.contains(".")) {
                    fieldNode = getNestedField(item, fieldPath);
                } else {
                    fieldNode = item.has(fieldPath) ? item.get(fieldPath) : null;
                }

                if (fieldNode == null) {
                    return false;
                }

                if (fieldNode.isArray()) {
                    boolean match = false;
                    for (JsonNode arrayElement : fieldNode) {
                        String value = arrayElement.asText();
                        if (filterValues.contains(value)) {
                            match = true;
                        }
                    }
                    if (!match) {
                        return false;
                    }
                } else {
                    String value = fieldNode.asText().toLowerCase();
                    if (!filterValues.contains(value)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private JsonNode getNestedField(JsonNode node, String fieldPath) {
        String[] fields = fieldPath.split("\\.");
        JsonNode currentNode = node;
        for (String field : fields) {
            if (currentNode == null) {
                return null;
            }
            if (currentNode.isArray()) {
                ArrayNode arrayNode = (ArrayNode) currentNode;
                ArrayNode resultArray = objectMapper.createArrayNode();

                for (JsonNode element : arrayNode) {
                    JsonNode nestedField = getNestedField(element, field);
                    if (nestedField != null) {
                        resultArray.add(nestedField);
                    }
                }
                return resultArray.isEmpty() ? null : resultArray;
            }
            if (!currentNode.has(field)) {
                return null;
            }
            currentNode = currentNode.get(field);
        }
        return currentNode;
    }

    protected String extractQueryParam(LoggedRequest request, String param) {
        return extractQueryParam(request, param, null);
    }

    protected String extractQueryParam(LoggedRequest request, String param, String defaultValue) {
        return request.queryParameter(param).isPresent()
                ? request.queryParameter(param).firstValue()
                : defaultValue;
    }

    protected List<String> extractListQueryParam(LoggedRequest request, String param) {
        return request.queryParameter(param).isPresent()
                ? Arrays.stream(request.queryParameter(param).firstValue().split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private int getCount(int pageSize, int page, ArrayNode filteredDataArray) {
        int count = page == 1 ? filteredDataArray.size() : filteredDataArray.size() + pageSize * (page - 1);
        if (count == pageSize) {
            int minCount = (page == 1) ? 1 : pageSize + 1;
            int maxCount = (int) Math.ceil(1.5 * pageSize);
            count = RANDOM.nextInt(maxCount - minCount + 1) + minCount;
        }
        return count;
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}
