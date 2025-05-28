package io.limeup.flexbets.sport.converter;

import io.limeup.flexbets.sport.model.enums.StatTargetType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatTargetTypeConverterTest {

    private final StatTargetTypeConverter converter = new StatTargetTypeConverter();

    @Test
    void shouldConvertLowercaseInputToEnum() {
        StatTargetType result = converter.convert("participant");
        assertThat(result).isEqualTo(StatTargetType.PARTICIPANT);
    }

    @Test
    void shouldConvertUppercaseInputToEnum() {
        StatTargetType result = converter.convert("SUBPARTICIPANT");
        assertThat(result).isEqualTo(StatTargetType.SUBPARTICIPANT);
    }

    @Test
    void shouldThrowExceptionForInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> converter.convert("unknown_value"));
    }
}

