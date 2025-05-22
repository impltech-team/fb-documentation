package io.limeup.flexbets.sport.converter;

import io.limeup.flexbets.sport.model.enums.StatusType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StatusTypeConverter implements Converter<String, StatusType> {

    @Override
    public StatusType convert(String source) {
        return StatusType.valueOf(source.toUpperCase());
    }
}
