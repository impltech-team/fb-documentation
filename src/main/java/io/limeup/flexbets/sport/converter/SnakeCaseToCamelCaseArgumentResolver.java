package io.limeup.flexbets.sport.converter;

import io.limeup.flexbets.sport.dto.RequestQueryDTO;
import io.limeup.flexbets.sport.dto.statscore.params.AreaQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.EventQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.GroupQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.ParticipantQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.SportQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.StandingByIdQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.StandingQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.SeasonQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.StageQueryParams;
import io.limeup.flexbets.sport.dto.statscore.params.VenueQueryParams;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SnakeCaseToCamelCaseArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Set<Class<?>> SUPPORTED_DTOS = Set.of(
            RequestQueryDTO.class,
            EventQueryParams.class,
            ParticipantQueryParams.class,
            AreaQueryParams.class,
            SportQueryParams.class,
            VenueQueryParams.class,
            GroupQueryParams.class,
            SeasonQueryParams.class,
            StageQueryParams.class,
            StandingQueryParams.class,
            StandingByIdQueryParams.class
    );

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return SUPPORTED_DTOS.contains(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Map<String, String[]> paramMap = webRequest.getParameterMap();

        // Convert snake_case to camelCase
        Map<String, String[]> convertedParams = paramMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> convertSnakeToCamel(entry.getKey()),
                        Map.Entry::getValue
                ));

        Object dto = parameter.getParameterType().getDeclaredConstructor().newInstance();

        // Bind modified parameters to DTO
        WebDataBinder binder = binderFactory.createBinder(webRequest, dto, parameter.getParameterName());
        new CustomServletRequestParameterMap(convertedParams).bind(binder);

        return dto;
    }

    private String convertSnakeToCamel(String snakeCase) {
        String[] parts = snakeCase.split("_");
        return parts[0] +
                java.util.Arrays.stream(parts, 1, parts.length)
                        .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                        .collect(Collectors.joining());
    }
}
