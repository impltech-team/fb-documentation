package io.limeup.flexbets.sport.converter;

import io.limeup.flexbets.sport.model.CompetitionType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CompetitionTypeConverter implements Converter<String, CompetitionType> {

    @Override
    public CompetitionType convert(String source) {
        return CompetitionType.valueOf(source.toUpperCase());
    }
}
