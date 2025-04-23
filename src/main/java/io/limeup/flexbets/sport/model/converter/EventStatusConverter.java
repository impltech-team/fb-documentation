package io.limeup.flexbets.sport.model.converter;

import io.limeup.flexbets.sport.model.EventStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EventStatusConverter implements AttributeConverter<EventStatus, String> {

    @Override
    public String convertToDatabaseColumn(EventStatus status) {
        return status != null ? status.name().toLowerCase() : null;
    }

    @Override
    public EventStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        try {
            return EventStatus.valueOf(dbData.toUpperCase());
        } catch (IllegalArgumentException e) {
            return EventStatus.OTHER;
        }
    }
}

