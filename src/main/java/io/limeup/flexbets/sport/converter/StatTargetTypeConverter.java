package io.limeup.flexbets.sport.converter;

import io.limeup.flexbets.sport.model.StatTargetType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StatTargetTypeConverter implements Converter<String, StatTargetType> {

    @Override
    public StatTargetType convert(String source) {
        return StatTargetType.valueOf(source.toUpperCase());
    }
}
