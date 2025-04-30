package io.limeup.flexbets.sport.config.wiremock.transformer;

import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AreasFilteringTransformerTest {

    private AreasFilteringTransformer transformer;
    private LoggedRequest request;

    @BeforeEach
    void setUp() {
        transformer = new AreasFilteringTransformer();
        request = mock(LoggedRequest.class);
    }

    @Test
    void getNameShouldReturnTransformerName() {
        assertThat(transformer.getName()).isEqualTo("areas-filtering-transformer");
    }

    @Test
    void getFieldKeyShouldReturnAreas() {
        assertThat(transformer.getFieldKey()).isEqualTo("areas");
    }

    @Test
    void extractPartialMatchFiltersShouldExtractNameFilter() {
        when(request.queryParameter("name")).thenReturn(
                new com.github.tomakehurst.wiremock.http.QueryParameter("name", List.of("R"))
        );

        Map<String, String> filters = transformer.extractPartialMatchFilters(request);

        assertThat(filters).containsEntry("name", "R");
    }

    @Test
    void extractFiltersShouldReturnEmptyWhenNoAreaIds() {
        when(request.queryParameter("area_ids")).thenReturn(
                new com.github.tomakehurst.wiremock.http.QueryParameter("area_ids", List.of())
        );

        Map<String, List<String>> filters = transformer.extractFilters(request);

        assertThat(filters.get("id")).isEmpty();
    }

    @Test
    void extractPartialMatchFiltersShouldReturnNullValueWhenNoNameParam() {
        when(request.queryParameter("name")).thenReturn(
                new com.github.tomakehurst.wiremock.http.QueryParameter("name", List.of())
        );

        Map<String, String> filters = transformer.extractPartialMatchFilters(request);

        assertThat(filters).containsKey("name");
        assertThat(filters.get("name")).isNull();
    }

}

