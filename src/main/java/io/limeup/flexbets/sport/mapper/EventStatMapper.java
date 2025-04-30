package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.statscore.StatScoreResultDTO;
import io.limeup.flexbets.sport.dto.statscore.StatScoreStatDTO;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.EventStat;
import io.limeup.flexbets.sport.model.StatDataType;
import io.limeup.flexbets.sport.model.StatTargetType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EventStatMapper {

    public EventStat toEntity(StatScoreStatDTO dto, Long targetId, StatTargetType type, Event event, Integer targetExternalId) {
        return toEntityInternal(dto.getId(), dto.getName(), dto.getValue(), dto.getDataType(), targetId, type, event, targetExternalId);
    }

    public EventStat toEntity(StatScoreResultDTO dto, Long targetId, StatTargetType type, Event event, Integer targetExternalId) {
        return toEntityInternal(dto.getId(), dto.getName(), dto.getValue(), dto.getDataType(), targetId, type, event, targetExternalId);
    }

    private EventStat toEntityInternal(Integer id, String name, String value, String dataTypeStr, Long targetId, StatTargetType type, Event event, Integer targetExternalId) {
        EventStat stat = new EventStat();
        stat.setExternalId(id);
        stat.setTargetExternalId(targetExternalId);
        stat.setStatName(name);
        stat.setTargetId(targetId);
        stat.setTargetType(type);
        stat.setCreatedAt(LocalDateTime.now());
        stat.setEvent(event);
        stat.setValueRaw(value);

        StatDataType dataType = StatDataType.DECIMAL;
        if (dataTypeStr != null) {
            try {
                dataType = StatDataType.valueOf(dataTypeStr.toUpperCase());
            } catch (IllegalArgumentException ignored) {}
        }
        stat.setDataType(dataType);

        try {
            if (dataType == StatDataType.INTEGER || dataType == StatDataType.BINARY || dataType == StatDataType.DECIMAL) {
                stat.setValueNumeric(Double.parseDouble(value));
            }
        } catch (Exception e) {
            stat.setValueNumeric(null);
        }

        return stat;
    }

}
