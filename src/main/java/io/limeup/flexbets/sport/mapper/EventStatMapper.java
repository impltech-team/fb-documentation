package io.limeup.flexbets.sport.mapper;

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
        EventStat stat = new EventStat();

        stat.setExternalId(dto.getId());
        stat.setTargetExternalId(targetExternalId);
        stat.setStatName(dto.getName());
        stat.setTargetId(targetId);
        stat.setTargetType(type);
        stat.setCreatedAt(LocalDateTime.now());
        stat.setEvent(event);

        stat.setValueRaw(dto.getValue());
        StatDataType dataType = StatDataType.DECIMAL;
        if (dto.getDataType() != null) {
            dataType = StatDataType.valueOf(dto.getDataType().toUpperCase());
        }
        stat.setDataType(dataType);

        try {
            if (dataType == StatDataType.INTEGER || dataType == StatDataType.BINARY || dataType == StatDataType.DECIMAL) {
                stat.setValueNumeric(Double.parseDouble(dto.getValue()));
            } else {
                stat.setValueNumeric(null);
            }
        } catch (Exception e) {
            stat.setValueNumeric(null);
        }

        return stat;
    }

}
