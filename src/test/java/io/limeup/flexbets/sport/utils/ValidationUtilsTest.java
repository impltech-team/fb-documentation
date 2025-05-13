package io.limeup.flexbets.sport.utils;

import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import org.junit.jupiter.api.Test;
import jakarta.validation.ValidationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ValidationUtilsTest {

    @Test
    void shouldPassWhenSortByIsValid() {
        var dto = new RequestQueryDTO();
        dto.setSortBy("name");
        var validFields = List.of("name", "date");

        assertDoesNotThrow(() -> ValidationUtils.validateSortFieldsInRequest(dto, validFields));
    }

    @Test
    void shouldThrowExceptionWhenSortByIsInvalid() {
        var dto = new RequestQueryDTO();
        dto.setSortBy("price");
        var validFields = List.of("name", "date");

        assertThatThrownBy(() -> ValidationUtils.validateSortFieldsInRequest(dto, validFields))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Invalid sortBy: price");
    }

    @Test
    void shouldPassWhenSortByIsBlank() {
        var dto = new RequestQueryDTO();
        var validFields = List.of("name", "date");

        assertDoesNotThrow(() -> ValidationUtils.validateSortFieldsInRequest(dto, validFields));
    }

    @Test
    void shouldPassWhenSortByIsNull() {
        var dto = new RequestQueryDTO();
        dto.setSortBy(null);
        var validFields = List.of("name", "date");

        assertDoesNotThrow(() -> ValidationUtils.validateSortFieldsInRequest(dto, validFields));
    }
}

