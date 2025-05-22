package io.limeup.flexbets.sport.converter;

import io.limeup.flexbets.sport.model.enums.MarketType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MarketTypeConverter implements Converter<String, MarketType> {

    @Override
    public MarketType convert(String source) {
        return MarketType.valueOf(source.toUpperCase());
    }
}
