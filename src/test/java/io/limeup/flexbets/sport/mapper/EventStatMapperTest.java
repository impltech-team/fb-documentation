package io.limeup.flexbets.sport.mapper;

import io.limeup.flexbets.sport.dto.statscore.StatScoreStatDTO;
import io.limeup.flexbets.sport.model.Event;
import io.limeup.flexbets.sport.model.EventStat;
import io.limeup.flexbets.sport.model.enums.StatDataType;
import io.limeup.flexbets.sport.model.enums.StatTargetType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class EventStatMapperTest {

    private final EventStatMapper mapper = new EventStatMapper();

    @Test
    void toEntityShouldMapCorrectlyWhenDataTypeIsDecimal() {
        StatScoreStatDTO dto = new StatScoreStatDTO();
        dto.setId(1);
        dto.setName("goals");
        dto.setValue("2.5");
        dto.setDataType("decimal");

        Event event = new Event();
        EventStat eventStat = mapper.toEntity(dto, 100L, StatTargetType.PARTICIPANT, event, 50);

        assertThat(eventStat).isNotNull();
        assertThat(eventStat.getExternalId()).isEqualTo(1);
        assertThat(eventStat.getStatName()).isEqualTo("goals");
        assertThat(eventStat.getTargetId()).isEqualTo(100L);
        assertThat(eventStat.getTargetType()).isEqualTo(StatTargetType.PARTICIPANT);
        assertThat(eventStat.getEvent()).isSameAs(event);
        assertThat(eventStat.getTargetExternalId()).isEqualTo(50);
        assertThat(eventStat.getValueRaw()).isEqualTo("2.5");
        assertThat(eventStat.getDataType()).isEqualTo(StatDataType.DECIMAL);
        assertThat(eventStat.getValueNumeric()).isEqualTo(2.5);
        assertThat(eventStat.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    void toEntityShouldHandleNullDataTypeDefaultToDecimal() {
        StatScoreStatDTO dto = new StatScoreStatDTO();
        dto.setId(2);
        dto.setName("shots");
        dto.setValue("10");

        EventStat eventStat = mapper.toEntity(dto, 200L, StatTargetType.SUBPARTICIPANT, new Event(), 60);

        assertThat(eventStat.getDataType()).isEqualTo(StatDataType.DECIMAL);
        assertThat(eventStat.getValueNumeric()).isEqualTo(10.0);
    }

    @Test
    void toEntityShouldHandleInvalidNumberParsing() {
        StatScoreStatDTO dto = new StatScoreStatDTO();
        dto.setId(3);
        dto.setName("invalid_stat");
        dto.setValue("not_a_number");
        dto.setDataType("integer");

        EventStat eventStat = mapper.toEntity(dto, 300L, StatTargetType.PARTICIPANT, new Event(), 70);

        assertThat(eventStat.getDataType()).isEqualTo(StatDataType.INTEGER);
        assertThat(eventStat.getValueNumeric()).isNull();
    }
}
