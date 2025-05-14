package io.limeup.flexbets.sport.utils;

import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import jakarta.validation.ValidationException;

import java.util.Collection;

public class ValidationUtils {

    private ValidationUtils() {
        
    }

    public static void validateSortFieldsInRequest(RequestQueryDTO requestQuery, Collection<String> validSortFields) {
        if (io.micrometer.common.util.StringUtils.isNotBlank(requestQuery.getSortBy()) && !validSortFields.contains(requestQuery.getSortBy())) {
            throw new ValidationException(String.format("Invalid sortBy: %s. Available options: %s", requestQuery.getSortBy(), validSortFields));
        }
    }

}
